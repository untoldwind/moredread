package net.untoldwind.moredread.model.op.merge.test;

import static net.untoldwind.moredread.model.test.TestHelper.readString;
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
import net.untoldwind.moredread.model.state.XMLStateReader;

import org.junit.Ignore;
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
		final TriangleMesh cube = new DodecahedronMeshGenerator(new Vector3f(0,
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

	@Test
	public void testBooleanResult1() throws Exception {
		final String xml = readString(getClass().getResourceAsStream(
				"bool-result1.xml"));

		final IMesh mesh = XMLStateReader.fromXML(xml);
		final IUnaryOperation mergeOperation = UnaryOperationFactory
				.createOperation(UnaryOperationFactory.Implementation.COPLANAR_MERGE);
		final IMesh result = mergeOperation.perform(mesh);

		assertNotNull(result);
		assertEquals(MeshType.POLY, result.getMeshType());
	}

	@Test
	@Ignore
	public void testBooleanResult2() throws Exception {
		final String xml = readString(getClass().getResourceAsStream(
				"bool-result2.xml"));

		final IMesh mesh = XMLStateReader.fromXML(xml);
		final IUnaryOperation mergeOperation = UnaryOperationFactory
				.createOperation(UnaryOperationFactory.Implementation.COPLANAR_MERGE);
		final IMesh result = mergeOperation.perform(mesh);

		assertNotNull(result);
		assertEquals(MeshType.POLY, result.getMeshType());

	}
}
