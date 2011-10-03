package net.untoldwind.moredread.model.op.bool.bspfilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.mesh.IFace;
import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.IVertex;
import net.untoldwind.moredread.model.mesh.PolyMesh;
import net.untoldwind.moredread.model.mesh.TriangleFace;
import net.untoldwind.moredread.model.mesh.TriangleMesh;
import net.untoldwind.moredread.model.op.IBooleanOperation;
import net.untoldwind.moredread.model.op.ITriangulator;
import net.untoldwind.moredread.model.op.TriangulatorFactory;
import net.untoldwind.moredread.model.op.utils.IndexList;
import net.untoldwind.moredread.model.op.utils.PlaneMap;
import net.untoldwind.moredread.model.op.utils.UnitRescale;
import net.untoldwind.moredread.model.op.utils.VertexSet;

import com.jme.math.FastMath;

/**
 * Implementation of IBooleanOperation using BSP tree filtering.
 */
public class BSPFilterBooleanOperation implements IBooleanOperation {

	@Override
	public IMesh performBoolean(final BoolOperation operation, final IMesh inA,
			final IMesh inB) {
		final boolean invertMeshA = (operation == BoolOperation.UNION);
		final boolean invertMeshB = (operation != BoolOperation.INTERSECTION);
		final boolean invertResult = (operation == BoolOperation.UNION);

		final TriangleMesh meshA = triangulate(inA, invertMeshA);
		final TriangleMesh meshB = triangulate(inB, invertMeshB);

		final UnitRescale unitRescale = new UnitRescale(meshA, meshB);
		unitRescale.rescaleInput(meshA);
		unitRescale.rescaleInput(meshB);

		final List<TriangleFace> facesA = new ArrayList<TriangleFace>(
				meshA.getFaces());
		final List<TriangleFace> facesB = new ArrayList<TriangleFace>(
				meshB.getFaces());
		final BSPTree bspA = new BSPTree(meshA);
		final BSPTree bspB = new BSPTree(meshB);

		final PlaneMap<BoolFace> faces = new PlaneMap<BoolFace>();

		bspFilter(bspA, facesB, meshA.getVertexCount(), faces);
		bspFilter(bspB, facesA, 0, faces);

		final PolyMesh result = new PolyMesh();

		transferVertices(faces, result);
		mergeMidpoints(faces, result);
		mergeFaces(faces);
		cleanMidpoints(faces, result);
		transferFaces(faces, invertResult, result);

		final Set<Integer> obsoleteVertices = new HashSet<Integer>();

		for (final IVertex vertex : result.getVertices()) {
			if (vertex.getFaces().isEmpty()) {
				obsoleteVertices.add(vertex.getIndex());
			}
		}
		result.removeVertices(obsoleteVertices);

		unitRescale.rescaleOutput(result);

		return result;
	}

	private TriangleMesh triangulate(final IMesh mesh, final boolean invert) {
		final TriangleMesh triangleMesh = new TriangleMesh();
		final ITriangulator triangulator = TriangulatorFactory.createDefault();

		for (final IVertex vertex : mesh.getVertices()) {
			triangleMesh.addVertex(vertex.getPoint(), vertex.isSmooth());
		}

		for (final IFace face : mesh.getFaces()) {
			final List<? extends IVertex> vertices = face.getVertices();
			final int[] indices = triangulator.triangulate(face);

			for (int i = 0; i < indices.length; i += 3) {
				if (invert) {
					triangleMesh.addFace(vertices.get(indices[i + 2])
							.getIndex(), vertices.get(indices[i + 1])
							.getIndex(), vertices.get(indices[i]).getIndex());
				} else {
					triangleMesh.addFace(vertices.get(indices[i]).getIndex(),
							vertices.get(indices[i + 1]).getIndex(), vertices
									.get(indices[i + 2]).getIndex());
				}
			}
		}
		return triangleMesh;
	}

	private void bspFilter(final BSPTree filter,
			final List<TriangleFace> source, final int offset,
			final PlaneMap<BoolFace> faces) {
		for (final TriangleFace face : source) {
			final IVertex[] verticies = face.getVertexArray();

			filter.testTriangle(offset, verticies[0], verticies[1],
					verticies[2], faces);
		}
	}

	private void transferVertices(final PlaneMap<BoolFace> faces,
			final PolyMesh result) {
		final VertexSet vertexSet = new VertexSet();
		final Map<BoolVertex.IBoolIndex, Integer> vertexMap = new HashMap<BoolVertex.IBoolIndex, Integer>();

		for (final BoolFace face : faces.allValues()) {
			final BoolVertex[] vertices = face.getVertices();
			if (vertices.length < 3) {
				continue;
			}

			final IndexList indices = new IndexList(vertices.length);

			for (int i = 0; i < vertices.length; i++) {
				indices.add(transferredIndex(result, vertexSet, vertices[i],
						vertexMap));
			}
			face.setResultIndices(indices);
		}
	}

	private void transferFaces(final PlaneMap<BoolFace> faces,
			final boolean invert, final PolyMesh result) {
		for (final BoolFace face : faces.allValues()) {
			final IndexList resultIndices = face.getResultIndices();

			if (resultIndices.size() < 3) {
				continue;
			}

			final int[] indices = new int[resultIndices.size()];

			for (int i = 0; i < indices.length; i++) {
				if (invert) {
					indices[indices.length - i - 1] = resultIndices.get(i);
				} else {
					indices[i] = resultIndices.get(i);
				}
			}

			result.addFace(indices);
		}
	}

	private int transferredIndex(final PolyMesh result,
			final VertexSet vertexSet, final BoolVertex vertex,
			final Map<BoolVertex.IBoolIndex, Integer> vertexMap) {
		Integer index = vertexMap.get(vertex.getIndex());

		if (index == null) {
			IVertex newVertex = vertexSet.findVertex(vertex.getPoint());

			if (newVertex == null) {
				newVertex = result.addVertex(vertex.getPoint());
				vertexSet.addVertex(newVertex);
			}
			index = newVertex.getIndex();
			vertexMap.put(vertex.getIndex(), index);
		}
		return index;
	}

	private void mergeMidpoints(final PlaneMap<BoolFace> faces,
			final PolyMesh result) {
		for (final IVertex vertex : result.getVertices()) {
			for (final BoolFace face : faces.allValues()) {
				final IndexList resultIndices = face.getResultIndices();
				IVertex prev = result.getVertex(resultIndices.get(-1));
				for (int i = 0; i < resultIndices.size(); i++) {
					final IVertex next = result.getVertex(resultIndices.get(i));

					if (vertex.getIndex() != prev.getIndex()
							&& vertex.getIndex() != next.getIndex()
							&& MathUtils.isOnLine(vertex.getPoint(),
									prev.getPoint(), next.getPoint())) {

						resultIndices.add(i, vertex.getIndex());

						prev = vertex;
					} else {
						prev = next;
					}
				}
			}
		}
	}

	private void mergeFaces(final PlaneMap<BoolFace> faces) {
		for (final List<BoolFace> faceList : faces.valueSets()) {
			boolean found = true;
			while (found) {
				found = false;
				for (int i = 0; i < faceList.size(); i++) {
					final BoolFace face1 = faceList.get(i);
					for (int j = i + 1; j < faceList.size(); j++) {
						final BoolFace face2 = faceList.get(j);

						if (checkFaceMerge(face1, face2)) {
							faceList.remove(j);
							found = true;
							j--;
						}
					}
				}
			}
		}
	}

	private boolean checkFaceMerge(final BoolFace face1, final BoolFace face2) {
		final IndexList vertices1 = face1.getResultIndices();
		final IndexList vertices2 = face2.getResultIndices();

		for (int i = 0; i < vertices1.size(); i++) {
			final int idx1a = vertices1.get(i);
			final int idx1b = vertices1.get(i + 1);

			for (int j = 0; j < vertices2.size(); j++) {
				final int idx2a = vertices2.get(j);
				final int idx2b = vertices2.get(j + 1);

				if (idx1a == idx2b && idx1b == idx2a) {
					for (int k = 0; k < vertices2.size() - 2; k++) {
						vertices1.add(i + 1 + k, vertices2.get(j + 2 + k));
					}

					for (int k = 0; k < vertices1.size(); k++) {
						final int idx1 = vertices1.get(k - 1);
						final int idx2 = vertices1.get(k + 1);

						if (idx1 == idx2) {
							vertices1.remove(k, k + 1);
							k = -1;
						}
					}

					return true;
				}
			}
		}

		return false;
	}

	private void cleanMidpoints(final PlaneMap<BoolFace> faces,
			final PolyMesh result) {
		for (final BoolFace face : faces.allValues()) {
			final IndexList vertices = face.getResultIndices();

			for (int k = 0; k < vertices.size(); k++) {
				final Vector3 p = result.getVertex(vertices.get(k)).getPoint();
				final Vector3 diff1 = result.getVertex(vertices.get(k - 1))
						.getPoint().subtract(p);
				final Vector3 diff2 = result.getVertex(vertices.get(k + 1))
						.getPoint().subtract(p);
				diff2.crossLocal(diff1);

				if (FastMath.abs(diff2.x) < 1e-6
						&& FastMath.abs(diff2.y) < 1e-6
						&& FastMath.abs(diff2.z) < 1e-6) {
					vertices.remove(k);
					k--;
				}
			}
		}
	}
}
