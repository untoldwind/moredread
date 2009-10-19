package net.untoldwind.moredread.model.op.merge.coplanar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.untoldwind.moredread.model.ModelPlugin;
import net.untoldwind.moredread.model.mesh.IEdge;
import net.untoldwind.moredread.model.mesh.IFace;
import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.IVertex;
import net.untoldwind.moredread.model.mesh.Mesh;
import net.untoldwind.moredread.model.mesh.PolyMesh;
import net.untoldwind.moredread.model.op.IUnaryOperation;

import com.jme.math.FastMath;
import com.jme.math.Vector3f;

public class CoplanarMergeOperation implements IUnaryOperation {
	@Override
	public Mesh<?> perform(final IMesh mesh) {
		final Set<Integer> visitedFaces = new HashSet<Integer>();
		final List<Integer> unmergableFaces = new ArrayList<Integer>();
		final List<Set<Integer>> facesToMerge = new ArrayList<Set<Integer>>();

		for (final IFace face : mesh.getFaces()) {
			if (visitedFaces.contains(face.getIndex())) {
				continue;
			}
			final Set<Integer> coplanarFaces = new HashSet<Integer>();

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
		if (facesToMerge.isEmpty() && (mesh instanceof Mesh<?>)) {
			return (Mesh<?>) mesh;
		}

		final PolyMesh result = new PolyMesh();

		final Map<Integer, Integer> vertexMap = new HashMap<Integer, Integer>();

		for (final int faceIndex : unmergableFaces) {
			transferFace(result, mesh, faceIndex, vertexMap);
		}
		for (final Set<Integer> faces : facesToMerge) {
			mergeFaces(result, mesh, faces, vertexMap);
		}

		return result;
	}

	private void transferFace(final PolyMesh target, final IMesh source,
			final int faceIndex, final Map<Integer, Integer> vertexMap) {
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
			final Set<Integer> faces, final Map<Integer, Integer> vertexMap) {
		final Map<Integer, List<Integer>> borderEdges = new HashMap<Integer, List<Integer>>();

		for (final int faceIndex : faces) {
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
			for (final int faceIndex : faces) {
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
					&& borderEdges.containsKey(sourceStrip.get(0).getIndex())) {
				final List<Integer> nextVertices = borderEdges
						.remove(sourceStrip.get(0).getIndex());
				int nextVertex = nextVertices.get(0);
				if (sourceStrip.get(1).getIndex() == nextVertex) {
					nextVertex = nextVertices.get(1);
				}
				if (sourceStrip.get(sourceStrip.size() - 1).getIndex() == nextVertex) {
					stripClosed = true;
				} else {
					sourceStrip.add(0, source.getVertex(nextVertex));
				}
			}

			sourceStrips.add(sourceStrip);
			it = borderEdges.entrySet().iterator();
		}

	}

	private void collectCoplanarNeigbours(final IFace face,
			final Set<Integer> coplanarFaces, final Set<Integer> visitedFaces) {
		visitedFaces.add(face.getIndex());
		coplanarFaces.add(face.getIndex());
		for (final IFace neighbour : face.getNeighbours()) {
			if (!visitedFaces.contains(neighbour.getIndex())
					&& checkCoplanar(face, neighbour)) {
				collectCoplanarNeigbours(neighbour, coplanarFaces, visitedFaces);
			}
		}
	}

	private boolean checkCoplanar(final IFace face1, final IFace face2) {
		// Using angleBetween might be a better (more user-friendly) approach
		final Vector3f diff = face1.getMeanNormal().subtract(
				face2.getMeanNormal());

		return FastMath.abs(diff.x) < FastMath.FLT_EPSILON
				&& FastMath.abs(diff.y) < FastMath.FLT_EPSILON
				&& FastMath.abs(diff.z) < FastMath.FLT_EPSILON;
	}
}
