package net.untoldwind.moredread.model.op.bool.bspfilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.PolyMesh;
import net.untoldwind.moredread.model.mesh.TriangleFace;
import net.untoldwind.moredread.model.mesh.TriangleMesh;
import net.untoldwind.moredread.model.mesh.Vertex;
import net.untoldwind.moredread.model.op.IBooleanOperation;

public class BSPFilterBooleanOperation implements IBooleanOperation {

	@Override
	public IMesh performBoolean(final BoolOperation operation, final IMesh inA,
			final IMesh inB) {
		TriangleMesh meshA = inA.toTriangleMesh();
		TriangleMesh meshB = inB.toTriangleMesh();
		final boolean invertMeshA = (operation == BoolOperation.UNION);
		final boolean invertMeshB = (operation != BoolOperation.INTERSECTION);
		final boolean invertResult = (operation == BoolOperation.UNION);

		final Map<Integer, Integer> vertexMapA = new HashMap<Integer, Integer>();
		final Map<Integer, Integer> vertexMapB = new HashMap<Integer, Integer>();

		PolyMesh result = new PolyMesh();

		if (invertMeshA) {
			meshA = meshA.invert();
		}

		if (invertMeshB) {
			meshB = meshB.invert();
		}
		final UnitRescale unitRescale = new UnitRescale(meshA, meshB);
		unitRescale.rescaleInput(meshA);
		unitRescale.rescaleInput(meshB);

		final List<TriangleFace> facesA = new ArrayList<TriangleFace>(
				meshA.getFaces());
		final List<TriangleFace> facesB = new ArrayList<TriangleFace>(
				meshB.getFaces());
		final BSPTree bspA = new BSPTree();
		bspA.addMesh(meshA);
		final BSPTree bspB = new BSPTree();
		bspB.addMesh(meshB);

		bspFilter(result, bspA, facesB, vertexMapB);
		bspFilter(result, bspB, facesA, vertexMapA);

		if (invertResult) {
			result = result.invert();
		}
		unitRescale.rescaleOutput(result);

		return result;
	}

	private void bspFilter(final PolyMesh result, final BSPTree filter,
			final List<TriangleFace> source,
			final Map<Integer, Integer> vertexMap) {
		for (final TriangleFace face : source) {
			final Vertex[] verticies = face.getVertexArray();

			final List<BoolFace> inFaces = filter.testTriangle(verticies[0],
					verticies[1], verticies[2]);

			for (final BoolFace inFace : inFaces) {
				final BoolVertex[] vertices = inFace.getVertices();
				if (vertices.length < 3) {
					continue;
				}

				final int indices[] = new int[vertices.length];

				for (int i = 0; i < vertices.length; i++) {
					if (vertices[i].getOrginalIndex() != null) {
						indices[i] = transferredIndex(result, vertices[i],
								vertexMap);
					} else {
						indices[i] = result.addVertex(vertices[i].getPoint())
								.getIndex();
					}
				}
				result.addFace(indices);
			}
		}
	}

	private int transferredIndex(final PolyMesh result,
			final BoolVertex vertex, final Map<Integer, Integer> vertexMap) {
		Integer index = vertexMap.get(vertex.getOrginalIndex());

		if (index == null) {
			index = result.addVertex(vertex.getPoint()).getIndex();
			vertexMap.put(vertex.getOrginalIndex(), index);
		}
		return index;
	}
}
