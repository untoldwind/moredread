package net.untoldwind.moredread.model.op.merge.coplanar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.untoldwind.moredread.model.ModelPlugin;
import net.untoldwind.moredread.model.mesh.FaceId;
import net.untoldwind.moredread.model.mesh.IEdge;
import net.untoldwind.moredread.model.mesh.IFace;
import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.IVertex;
import net.untoldwind.moredread.model.mesh.Mesh;
import net.untoldwind.moredread.model.mesh.PolyMesh;
import net.untoldwind.moredread.model.op.IUnaryOperation;

import com.jme.math.FastMath;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;

public class CoplanarMergeOperation implements IUnaryOperation {

	@Override
	public Mesh<?, ?> perform(final IMesh mesh) {
		final Set<FaceId> visitedFaces = new HashSet<FaceId>();
		final List<FaceId> unmergableFaces = new ArrayList<FaceId>();
		final List<Set<FaceId>> facesToMerge = new ArrayList<Set<FaceId>>();

		for (final IFace face : mesh.getFaces()) {
			if (visitedFaces.contains(face.getIndex())) {
				continue;
			}

			final Set<FaceId> coplanarFaces = new HashSet<FaceId>();

			collectCoplanarNeigbours(face, coplanarFaces, visitedFaces);

			if (coplanarFaces.size() > 1) {
				// There is no point to add lists with single entry, there is
				// nothing to merge
				facesToMerge.add(coplanarFaces);
			} else {
				unmergableFaces.add(face.getIndex());
			}
		}

		// Small optimization: If there is nothing to merge we do not do
		// anything
		if (facesToMerge.isEmpty() && (mesh instanceof Mesh<?, ?>)) {
			return (Mesh<?, ?>) mesh;
		}

		final PolyMesh result = new PolyMesh();

		final Map<Integer, Integer> vertexMap = new HashMap<Integer, Integer>();

		for (final FaceId faceIndex : unmergableFaces) {
			transferFace(result, mesh, faceIndex, vertexMap);
		}
		for (final Set<FaceId> faces : facesToMerge) {
			mergeFaces(result, mesh, faces, vertexMap);
		}

		return result;
	}

	private void transferFace(final PolyMesh target, final IMesh source,
			final FaceId faceIndex, final Map<Integer, Integer> vertexMap) {
		final IFace face = source.getFace(faceIndex);
		final List<? extends IVertex> oldVertices = face.getVertices();
		final int stripCounts[] = face.getPolygonStripCounts();
		final int targetStrips[][] = new int[stripCounts.length][];
		int count = 0;

		for (int i = 0; i < stripCounts.length; i++) {
			targetStrips[i] = new int[stripCounts[i]];
			for (int j = 0; j < stripCounts[i]; j++) {
				final IVertex oldVertex = oldVertices.get(count++);
				Integer newIndex = vertexMap.get(oldVertex.getIndex());

				if (newIndex == null) {
					newIndex = target.addVertex(oldVertex.getPoint(),
							oldVertex.isSmooth()).getIndex();
					vertexMap.put(oldVertex.getIndex(), newIndex);
				}

				targetStrips[i][j] = newIndex;
			}
		}

		target.addFace(targetStrips);
	}

	private void mergeFaces(final PolyMesh target, final IMesh source,
			final Set<FaceId> faces, final Map<Integer, Integer> vertexMap) {
		final Map<Integer, List<Integer>> borderEdges = new HashMap<Integer, List<Integer>>();

		for (final FaceId faceIndex : faces) {
			final IFace face = source.getFace(faceIndex);

			for (final IEdge edge : face.getEdges()) {
				int count = 0;

				for (final IFace edgeFaces : edge.getFaces()) {
					if (faces.contains(edgeFaces.getIndex())) {
						count++;
					}
				}
				if (count == 1) {
					List<Integer> nextVertices = borderEdges.get(edge
							.getVertex1().getIndex());
					if (nextVertices == null) {
						nextVertices = new ArrayList<Integer>();
						borderEdges.put(edge.getVertex1().getIndex(),
								nextVertices);
					}
					nextVertices.add(edge.getVertex2().getIndex());
					nextVertices = borderEdges
							.get(edge.getVertex2().getIndex());
					if (nextVertices == null) {
						nextVertices = new ArrayList<Integer>();
						borderEdges.put(edge.getVertex2().getIndex(),
								nextVertices);
					}
					nextVertices.add(edge.getVertex1().getIndex());
				}
			}
		}

		// Check edge per vertex
		boolean validEdges = true;
		for (final List<Integer> edges : borderEdges.values()) {
			if (edges.size() != 2) {
				ModelPlugin.getDefault().logWarn(getClass(),
						"Found invalid border edges: " + edges);
				validEdges = false;
				break;

			}
		}
		if (borderEdges.isEmpty() || !validEdges) {
			// Unable to merge this
			for (final FaceId faceIndex : faces) {
				transferFace(target, source, faceIndex, vertexMap);
			}
			return;
		}

		final List<List<IVertex>> sourceStrips = new ArrayList<List<IVertex>>();
		Iterator<Map.Entry<Integer, List<Integer>>> it = borderEdges.entrySet()
				.iterator();

		while (it.hasNext()) {
			final Map.Entry<Integer, List<Integer>> startEdge = it.next();
			final List<IVertex> sourceStrip = new ArrayList<IVertex>();

			it.remove();
			sourceStrip.add(source.getVertex(startEdge.getKey()));
			sourceStrip.add(source.getVertex(startEdge.getValue().get(0)));

			boolean stripClosed = false;

			while (!stripClosed
					&& borderEdges.containsKey(sourceStrip.get(
							sourceStrip.size() - 1).getIndex())) {
				final List<Integer> nextVertices = borderEdges
						.remove(sourceStrip.get(sourceStrip.size() - 1)
								.getIndex());
				int nextVertex = nextVertices.get(0);
				if (sourceStrip.get(sourceStrip.size() - 2).getIndex() == nextVertex) {
					nextVertex = nextVertices.get(1);
				}
				if (sourceStrip.get(0).getIndex() == nextVertex) {
					stripClosed = true;
				} else {
					sourceStrip.add(source.getVertex(nextVertex));
				}
			}

			sourceStrips.add(sourceStrip);
			it = borderEdges.entrySet().iterator();
		}

		if (sourceStrips.isEmpty()) {
			ModelPlugin.getDefault().logWarn(getClass(),
					"Empty source strips. Should not happen");
			return;
		}

		for (final List<IVertex> sourceStrip : sourceStrips) {
			for (int i = sourceStrip.size() - 1; i >= 0; i--) {
				final Vector3f diff1 = sourceStrip
						.get((i + 1) % sourceStrip.size()).getPoint()
						.subtract(sourceStrip.get(i).getPoint());
				final Vector3f diff2 = sourceStrip
						.get((i + sourceStrip.size() - 1) % sourceStrip.size())
						.getPoint().subtract(sourceStrip.get(i).getPoint());
				diff2.crossLocal(diff1);
				if (FastMath.abs(diff2.x) < FastMath.ZERO_TOLERANCE
						&& FastMath.abs(diff2.y) < FastMath.ZERO_TOLERANCE
						&& FastMath.abs(diff2.z) < FastMath.ZERO_TOLERANCE) {
					sourceStrip.remove(i);
				}
			}
		}

		final Vector3f n = source.getFace(faces.iterator().next())
				.getMeanNormal();
		final Vector3f vx = sourceStrips.get(0).get(1).getPoint()
				.subtract(sourceStrips.get(0).get(0).getPoint());
		vx.normalizeLocal();
		final Vector3f vy = vx.cross(n).normalizeLocal();

		sortStrips(vx, vy, sourceStrips);

		for (int i = 0; i < sourceStrips.size(); i++) {
			final Vector3f pn = calculateMeanNormal(sourceStrips.get(i));

			if (n.dot(pn) < 0) {
				if (i == 0) {
					Collections.reverse(sourceStrips.get(i));
				}
			} else if (i > 0) {
				Collections.reverse(sourceStrips.get(i));
			}
		}

		final int targetStrips[][] = new int[sourceStrips.size()][];

		for (int i = 0; i < targetStrips.length; i++) {
			targetStrips[i] = new int[sourceStrips.get(i).size()];
			for (int j = 0; j < targetStrips[i].length; j++) {
				final IVertex sourceVertex = sourceStrips.get(i).get(j);
				Integer newIndex = vertexMap.get(sourceVertex.getIndex());

				if (newIndex == null) {
					newIndex = target.addVertex(sourceVertex.getPoint(),
							sourceVertex.isSmooth()).getIndex();
					vertexMap.put(sourceVertex.getIndex(), newIndex);
				}

				targetStrips[i][j] = newIndex;
			}
		}

		target.addFace(targetStrips);
	}

	private void collectCoplanarNeigbours(final IFace face,
			final Set<FaceId> coplanarFaces, final Set<FaceId> visitedFaces) {
		visitedFaces.add(face.getIndex());
		coplanarFaces.add(face.getIndex());

		for (final IFace neighbour : face.getNeighbours()) {
			if (!visitedFaces.contains(neighbour.getIndex())
					&& checkCoplanar(face, neighbour)) {
				collectCoplanarNeigbours(neighbour, coplanarFaces, visitedFaces);
			}
		}
	}

	private void sortStrips(final Vector3f vx, final Vector3f vy,
			final List<List<IVertex>> sourceStrips) {
		for (int i = 1; i < sourceStrips.size(); i++) {
			if (isInside(vx, vy, sourceStrips.get(0).get(0),
					sourceStrips.get(i))) {
				Collections.swap(sourceStrips, 0, i);
			}
		}
	}

	private boolean isInside(final Vector3f vx, final Vector3f vy,
			final IVertex vertex, final List<IVertex> polygon) {
		final int polySides = polygon.size();
		if (polySides == 0) {
			return false;
		}

		final Vector2f v = new Vector2f(vertex.getPoint().dot(vx), vertex
				.getPoint().dot(vy));
		int i, j = polySides - 1;

		boolean oddNodes = false;

		for (i = 0; i < polySides; i++) {
			final Vector3f vi = polygon.get(i).getPoint();
			final Vector3f vj = polygon.get(j).getPoint();
			final Vector2f pi = new Vector2f(vi.dot(vx), vi.dot(vy));
			final Vector2f pj = new Vector2f(vj.dot(vx), vj.dot(vy));

			if (pi.y < v.y && pj.y >= v.y || pj.y < v.y && pi.y >= v.y) {
				if (pi.x + (v.y - pi.y) / (pj.y - pi.y) * (pj.x - pi.x) < v.x) {
					oddNodes = !oddNodes;
				}
			}
			j = i;
		}

		return oddNodes;
	}

	private Vector3f calculateMeanNormal(final List<IVertex> sourceStrip) {
		final Vector3f meanNormal = new Vector3f(0, 0, 0);
		final int outerStripCount = sourceStrip.size();
		for (int i = 0; i < outerStripCount; i++) {
			final Vector3f v1 = sourceStrip.get(i).getPoint();
			final Vector3f v2 = sourceStrip.get((i + 1) % outerStripCount)
					.getPoint();
			meanNormal.addLocal(v1.cross(v2));
		}
		meanNormal.normalizeLocal();

		return meanNormal;
	}

	private boolean checkCoplanar(final IFace face1, final IFace face2) {
		final Vector3f cross = face1.getMeanNormal().cross(
				face2.getMeanNormal());

		return FastMath.abs(cross.x) < FastMath.ZERO_TOLERANCE
				&& FastMath.abs(cross.y) < FastMath.ZERO_TOLERANCE
				&& FastMath.abs(cross.z) < FastMath.ZERO_TOLERANCE;
	}
}
