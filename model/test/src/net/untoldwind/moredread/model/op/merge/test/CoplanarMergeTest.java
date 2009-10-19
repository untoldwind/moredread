package net.untoldwind.moredread.model.op.merge.test;

import net.untoldwind.moredread.model.generator.CubeMeshGenerator;
import net.untoldwind.moredread.model.mesh.IFace;
import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.TriangleMesh;
import net.untoldwind.moredread.model.op.IUnaryOperation;
import net.untoldwind.moredread.model.op.UnaryOperationFactory;

import org.junit.Test;

import com.jme.math.Vector3f;

public class CoplanarMergeTest {
	@Test
	public void testCubeMerge() throws Exception {
		final TriangleMesh cube = new CubeMeshGenerator(new Vector3f(0, 0, 0),
				1f).generateMesh(null).toTriangleMesh();

		final IUnaryOperation mergeOperation = UnaryOperationFactory
				.createOperation(UnaryOperationFactory.Implementation.COPLANAR_MERGE);
		final IMesh result = mergeOperation.perform(cube);

		System.out.println(result.getFaces().size());
		for (final IFace face : result.getFaces()) {
			System.out.println(face.getVertices());
		}
	}

}
