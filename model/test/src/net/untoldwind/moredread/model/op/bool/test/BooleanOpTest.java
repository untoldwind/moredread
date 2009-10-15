package net.untoldwind.moredread.model.op.bool.test;

import net.untoldwind.moredread.model.generator.CubeMeshGenerator;
import net.untoldwind.moredread.model.mesh.TriangleMesh;
import net.untoldwind.moredread.model.op.BooleanOperationFactory;
import net.untoldwind.moredread.model.op.IBooleanOperation;

import org.junit.Test;

import com.jme.math.Vector3f;

public class BooleanOpTest {
	@Test
	public void testTwoCubes() throws Exception {
		final TriangleMesh cube1 = new CubeMeshGenerator(new Vector3f(0, 0, 0),
				1f).generateMesh(null).toTriangleMesh();
		// Clean intersection on each triangle (i.e. no edge/edge intersection
		final TriangleMesh cube2 = new CubeMeshGenerator(new Vector3f(0.1f,
				0.2f, 0.3f), 1f).generateMesh(null).toTriangleMesh();

		final IBooleanOperation booleanOperation = BooleanOperationFactory
				.createBooleanOperation(BooleanOperationFactory.Implementation.BSP);

		booleanOperation.performBoolean(
				IBooleanOperation.BoolOperation.INTERSECTION, cube1, cube2);
	}

	public static void main(final String[] args) {
		try {
			new BooleanOpTest().testTwoCubes();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
