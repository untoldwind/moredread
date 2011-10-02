package net.untoldwind.moredread.model.op.bool.bspfilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.untoldwind.moredread.model.mesh.IFace;
import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.IVertex;
import net.untoldwind.moredread.model.mesh.PolyMesh;
import net.untoldwind.moredread.model.mesh.TriangleFace;
import net.untoldwind.moredread.model.mesh.TriangleMesh;
import net.untoldwind.moredread.model.op.IBooleanOperation;
import net.untoldwind.moredread.model.op.ITriangulator;
import net.untoldwind.moredread.model.op.TriangulatorFactory;
import net.untoldwind.moredread.model.op.utils.PlaneMap;
import net.untoldwind.moredread.model.op.utils.UnitRescale;
import net.untoldwind.moredread.model.op.utils.VertexSet;

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
		// mergeFaces(faces);
		transferFaces(faces, invertResult, result);

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

			final int indices[] = new int[vertices.length];

			for (int i = 0; i < vertices.length; i++) {
				indices[i] = transferredIndex(result, vertexSet, vertices[i],
						vertexMap);
			}
			face.setResultIndices(indices);
		}
	}

	private void transferFaces(final PlaneMap<BoolFace> faces,
			final boolean invert, final PolyMesh result) {
		for (final BoolFace face : faces.allValues()) {
			final int[] resultIndices = face.getResultIndices();
			final int[] indices = new int[resultIndices.length];

			for (int i = 0; i < indices.length; i++) {
				if (invert) {
					indices[indices.length - i - 1] = resultIndices[i];
				} else {
					indices[i] = resultIndices[i];
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
				int[] resultIndices = face.getResultIndices();
				IVertex prev = result
						.getVertex(resultIndices[resultIndices.length - 1]);
				for (int i = 0; i < resultIndices.length; i++) {
					final IVertex next = result.getVertex(resultIndices[i]);

					if (vertex.getIndex() != prev.getIndex()
							&& vertex.getIndex() != next.getIndex()
							&& MathUtils.isOnLine(vertex.getPoint(),
									prev.getPoint(), next.getPoint())) {

						final int[] newIndices = new int[resultIndices.length + 1];

						System.arraycopy(resultIndices, 0, newIndices, 0, i);
						newIndices[i] = vertex.getIndex();
						System.arraycopy(resultIndices, i, newIndices, i + 1,
								resultIndices.length - i);

						resultIndices = newIndices;
						face.setResultIndices(resultIndices);

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
			for (int i = 0; i < faceList.size(); i++) {
				final BoolFace face1 = faceList.get(i);
				for (int j = i + 1; j < faceList.size(); j++) {
					final BoolFace face2 = faceList.get(j);

					if (checkFaceMerge(face1, face2)) {
						faceList.remove(j);
						j--;
					}
				}
			}
		}
	}

	private boolean checkFaceMerge(final BoolFace face1, final BoolFace face2) {
		final int[] vertices1 = face1.getResultIndices();
		final int[] vertices2 = face2.getResultIndices();

		for (int i = 0; i < vertices1.length; i++) {
			final int idx1a = vertices1[i];
			final int idx1b = vertices1[(i + 1) % vertices1.length];

			for (int j = 0; j < vertices2.length; j++) {
				final int idx2a = vertices2[j];
				final int idx2b = vertices2[(j + 1) % vertices2.length];

				if (idx1a == idx2b && idx1b == idx2a) {
					final int[] newVertices = new int[vertices1.length
							+ vertices2.length - 2];

					int l = 0;
					for (int k = 0; k <= i; k++) {
						newVertices[l++] = vertices1[k];
					}
					for (int k = 0; k < vertices2.length - 2; k++) {
						newVertices[l++] = vertices2[(j + 2 + k)
								% vertices2.length];
					}
					for (int k = i + 1; k < vertices1.length; k++) {
						newVertices[l++] = vertices1[k];
					}

					face1.setResultIndices(newVertices);

					return true;
				}
			}
		}

		return false;
	}
}
