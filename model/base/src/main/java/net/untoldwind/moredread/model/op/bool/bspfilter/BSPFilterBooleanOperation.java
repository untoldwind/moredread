package net.untoldwind.moredread.model.op.bool.bspfilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.untoldwind.moredread.model.mesh.TriangleFace;
import net.untoldwind.moredread.model.mesh.TriangleMesh;
import net.untoldwind.moredread.model.mesh.Vertex;
import net.untoldwind.moredread.model.op.IBooleanOperation;

public class BSPFilterBooleanOperation implements IBooleanOperation {

	@Override
	public TriangleMesh performBoolean(final BoolOperation operation,
			final TriangleMesh meshA, final TriangleMesh meshB) {
		final boolean invertMeshA = (operation == BoolOperation.UNION);
		final boolean invertMeshB = (operation != BoolOperation.INTERSECTION);
		final boolean invertResult = (operation == BoolOperation.UNION);

		final Map<Integer, Integer> vertexMapA = new HashMap<Integer, Integer>();
		final Map<Integer, Integer> vertexMapB = new HashMap<Integer, Integer>();

		final TriangleMesh result = new TriangleMesh();

		if (invertMeshA) {
			meshA.invert();
		}

		if (invertMeshB) {
			meshB.invert();
		}

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
			result.invert();
		}

		return result;
	}

	private void bspFilter(final TriangleMesh result, final BSPTree filter,
			final List<TriangleFace> source,
			final Map<Integer, Integer> vertexMap) {
		final Iterator<TriangleFace> it = source.iterator();

		while (it.hasNext()) {
			final TriangleFace face = it.next();
			final Vertex[] verticies = face.getVertexArray();

			switch (filter.testTriangle(verticies[0].getPoint(),
					verticies[1].getPoint(), verticies[2].getPoint())) {
			case IN:
				final int i1 = transferredIndex(result, verticies[0], vertexMap);
				final int i2 = transferredIndex(result, verticies[1], vertexMap);
				final int i3 = transferredIndex(result, verticies[2], vertexMap);

				result.addFace(i1, i2, i3);
				it.remove();
				break;
			case OUT:
				it.remove();
				break;
			}
		}
	}

	private int transferredIndex(final TriangleMesh result,
			final Vertex vertex, final Map<Integer, Integer> vertexMap) {
		Integer index = vertexMap.get(vertex.getIndex());

		if (index == null) {
			index = result.addVertex(vertex.getPoint()).getIndex();
			vertexMap.put(vertex.getIndex(), index);
		}
		return index;
	}
}
