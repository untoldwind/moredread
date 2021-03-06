package net.untoldwind.moredread.model.op.merge.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import net.untoldwind.moredread.model.enums.MeshType;
import net.untoldwind.moredread.model.generator.CubeMeshGenerator;
import net.untoldwind.moredread.model.generator.DodecahedronMeshGenerator;
import net.untoldwind.moredread.model.mesh.IFace;
import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.TriangleMesh;
import net.untoldwind.moredread.model.op.IUnaryOperation;
import net.untoldwind.moredread.model.op.UnaryOperationFactory;

import org.junit.Test;

import com.jme.math.Vector3;

public class CoplanarMergeTest {
	@Test
	public void testCubeMerge() throws Exception {
		final TriangleMesh cube = new CubeMeshGenerator(new Vector3(0, 0, 0),
				1f).generateMesh(null).toTriangleMesh();

		final IUnaryOperation mergeOperation = UnaryOperationFactory
				.createOperation(UnaryOperationFactory.Implementation.COPLANAR_MERGE);
		final IMesh result = mergeOperation.perform(cube);

		assertNotNull(result);
		assertEquals(MeshType.POLY, result.getMeshType());
		assertEquals(6, result.getFaces().size());

		for (final IFace face : result.getFaces()) {
			assertEquals(4, face.getVertexCount());
			assertEquals(1, face.getPolygonStripCounts().length);
		}
	}

	@Test
	public void testDodecahedronMerge() throws Exception {
		final TriangleMesh cube = new DodecahedronMeshGenerator(new Vector3(0,
				0, 0), 1f).generateMesh(null).toTriangleMesh();

		final IUnaryOperation mergeOperation = UnaryOperationFactory
				.createOperation(UnaryOperationFactory.Implementation.COPLANAR_MERGE);
		final IMesh result = mergeOperation.perform(cube);

		assertNotNull(result);
		assertEquals(MeshType.POLY, result.getMeshType());
		assertEquals(12, result.getFaces().size());

		for (final IFace face : result.getFaces()) {
			assertEquals(5, face.getVertexCount());
			assertEquals(1, face.getPolygonStripCounts().length);
		}
	}

}
