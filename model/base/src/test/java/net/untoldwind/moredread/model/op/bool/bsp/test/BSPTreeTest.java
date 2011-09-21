package net.untoldwind.moredread.model.op.bool.bsp.test;

import static org.junit.Assert.assertEquals;
import net.untoldwind.moredread.model.generator.CubeMeshGenerator;
import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.op.bool.bsp.BSPTree;
import net.untoldwind.moredread.model.op.bool.bsp.VertexTag;
import net.untoldwind.moredread.model.state.XMLStateReader;

import org.junit.Test;

import com.jme.math.Vector3f;

public class BSPTreeTest {
	@Test
	public void testInOutCube() throws Exception {
		final IMesh mesh = new CubeMeshGenerator().generateMesh(null);

		final BSPTree tree = new BSPTree();

		tree.addMesh(mesh.toTriangleMesh());

		assertEquals(VertexTag.IN, tree.testVertex(new Vector3f(0, 0, 0)));
		assertEquals(VertexTag.IN, tree.testVertex(new Vector3f(0.9f, 0, 0)));
		assertEquals(VertexTag.OUT, tree.testVertex(new Vector3f(1.1f, 0, 0)));
		assertEquals(VertexTag.OUT, tree.testVertex(new Vector3f(0, 1.1f, 0)));
		assertEquals(VertexTag.OUT, tree.testVertex(new Vector3f(0, 0, 1.1f)));
		assertEquals(VertexTag.OUT, tree.testVertex(new Vector3f(-1.1f, 0, 0)));
		assertEquals(VertexTag.OUT, tree.testVertex(new Vector3f(0, -1.1f, 0)));
		assertEquals(VertexTag.OUT, tree.testVertex(new Vector3f(0, 0, -1.1f)));
		assertEquals(VertexTag.OUT, tree.testVertex(new Vector3f(0, 1, 1.1f)));
		assertEquals(VertexTag.IN, tree.testVertex(new Vector3f(1, 0.5f, 0)));

	}

	@Test
	public void testInOutCubeWithHole() throws Exception {
		final IMesh mesh = XMLStateReader
				.fromXML(getClass()
						.getClassLoader()
						.getResourceAsStream(
								"net/untoldwind/moredread/model/mesh/test/cube-with-hole-state.xml"));
		final BSPTree tree = new BSPTree();

		tree.addMesh(mesh.toTriangleMesh());

		assertEquals(VertexTag.OUT, tree.testVertex(new Vector3f(0, 0, 0)));
		assertEquals(VertexTag.OUT, tree.testVertex(new Vector3f(0.5f, 0, 0)));
		assertEquals(VertexTag.OUT, tree.testVertex(new Vector3f(-0.5f, 0, 0)));
		assertEquals(VertexTag.OUT, tree.testVertex(new Vector3f(0, 0.5f, 0)));
		assertEquals(VertexTag.OUT, tree.testVertex(new Vector3f(0, -0.5f, 0)));
		assertEquals(VertexTag.IN, tree.testVertex(new Vector3f(0.75f, 0, 0)));
		assertEquals(VertexTag.IN, tree.testVertex(new Vector3f(0.8f, 0, 0)));
		assertEquals(VertexTag.IN, tree.testVertex(new Vector3f(-0.75f, 0, 0)));
		assertEquals(VertexTag.IN, tree.testVertex(new Vector3f(-0.8f, 0, 0)));
		assertEquals(VertexTag.IN, tree.testVertex(new Vector3f(0, 0.75f, 0)));
		assertEquals(VertexTag.IN, tree.testVertex(new Vector3f(0, 0.8f, 0)));
		assertEquals(VertexTag.IN, tree.testVertex(new Vector3f(0, -0.75f, 0)));
		assertEquals(VertexTag.IN, tree.testVertex(new Vector3f(0, -0.8f, 0)));
		assertEquals(VertexTag.IN,
				tree.testVertex(new Vector3f(0.75f, 0.5f, 0)));
		assertEquals(VertexTag.IN,
				tree.testVertex(new Vector3f(0.75f, 0.75f, 0)));
		assertEquals(VertexTag.IN, tree.testVertex(new Vector3f(0.8f, 0.8f, 0)));
		assertEquals(VertexTag.IN,
				tree.testVertex(new Vector3f(-0.75f, -0.5f, 0)));
		assertEquals(VertexTag.IN,
				tree.testVertex(new Vector3f(-0.75f, -0.75f, 0)));
		assertEquals(VertexTag.IN,
				tree.testVertex(new Vector3f(-0.8f, -0.8f, 0)));
		assertEquals(VertexTag.OUT, tree.testVertex(new Vector3f(1.1f, 0, 0)));
		assertEquals(VertexTag.OUT, tree.testVertex(new Vector3f(-1.1f, 0, 0)));
		assertEquals(VertexTag.OUT, tree.testVertex(new Vector3f(0, 1.1f, 0)));
		assertEquals(VertexTag.OUT, tree.testVertex(new Vector3f(0, -1.1f, 0)));
	}
}
