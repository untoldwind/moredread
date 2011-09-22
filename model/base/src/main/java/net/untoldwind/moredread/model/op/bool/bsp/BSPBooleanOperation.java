package net.untoldwind.moredread.model.op.bool.bsp;

import java.util.HashMap;
import java.util.Map;

import net.untoldwind.moredread.model.mesh.TriangleFace;
import net.untoldwind.moredread.model.mesh.TriangleMesh;
import net.untoldwind.moredread.model.op.IBooleanOperation;

public class BSPBooleanOperation implements IBooleanOperation {

	@Override
	public TriangleMesh performBoolean(final BoolOperation operation,
			final TriangleMesh meshA, final TriangleMesh meshB) {
		final boolean invertMeshA = (operation == BoolOperation.UNION);
		final boolean invertMeshB = (operation != BoolOperation.INTERSECTION);
		final boolean invertResult = (operation == BoolOperation.UNION);

		final Map<Integer, Integer> vertexMapA = new HashMap<Integer, Integer>();
		final Map<Integer, Integer> vertexMapB = new HashMap<Integer, Integer>();

		final TriangleMesh result = new TriangleMesh();

		bspFilter(result, invertResult, meshA, invertMeshA, meshB, vertexMapB);
		bspFilter(result, invertResult, meshB, invertMeshB, meshA, vertexMapA);

		return result;
	}

	private void bspFilter(final TriangleMesh result,
			final boolean invertResult, final TriangleMesh filter,
			final boolean invertFilter, final TriangleMesh source,
			final Map<Integer, Integer> vertexMap) {
		final BSPTree bsp = new BSPTree();
		bsp.addMesh(filter, invertFilter);

		for (final TriangleFace face : source.getFaces()) {

		}
	}

}
