package net.untoldwind.moredread.model.op.bool.bsp.test;

import static org.junit.Assert.assertEquals;
import net.untoldwind.moredread.model.generator.CubeMeshGenerator;
import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.TriangleFace;
import net.untoldwind.moredread.model.mesh.TriangleMesh;
import net.untoldwind.moredread.model.mesh.Vertex;
import net.untoldwind.moredread.model.op.bool.bspfilter.BSPTree;
import net.untoldwind.moredread.model.op.bool.bspfilter.UnitRescale;
import net.untoldwind.moredread.model.op.bool.bspfilter.VertexTag;
import net.untoldwind.moredread.model.state.XMLStateReader;
import net.untoldwind.moredread.model.transform.MatrixTransformation;

import org.junit.Test;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

public class BSPTreeTest {
	@Test
	public void testInOutCube() throws Exception {
		final IMesh mesh = new CubeMeshGenerator().generateMesh(null);

		final BSPTree tree = new BSPTree();

		tree.addMesh(mesh.toTriangleMesh());

		assertEquals(VertexTag.IN, tree.testPoint(new Vector3f(0, 0, 0)));
		assertEquals(VertexTag.IN, tree.testPoint(new Vector3f(0.9f, 0, 0)));
		assertEquals(VertexTag.OUT, tree.testPoint(new Vector3f(1.1f, 0, 0)));
		assertEquals(VertexTag.OUT, tree.testPoint(new Vector3f(0, 1.1f, 0)));
		assertEquals(VertexTag.OUT, tree.testPoint(new Vector3f(0, 0, 1.1f)));
		assertEquals(VertexTag.OUT, tree.testPoint(new Vector3f(-1.1f, 0, 0)));
		assertEquals(VertexTag.OUT, tree.testPoint(new Vector3f(0, -1.1f, 0)));
		assertEquals(VertexTag.OUT, tree.testPoint(new Vector3f(0, 0, -1.1f)));
		assertEquals(VertexTag.OUT, tree.testPoint(new Vector3f(0, 1, 1.1f)));
		assertEquals(VertexTag.ON, tree.testPoint(new Vector3f(1, 0.5f, 0)));
	}

	@Test
	public void testInOutCubeInverted() throws Exception {
		final IMesh mesh = new CubeMeshGenerator().generateMesh(null);
		final TriangleMesh triMesh = mesh.toTriangleMesh().invert();
		final BSPTree tree = new BSPTree();

		tree.addMesh(triMesh);

		assertEquals(VertexTag.OUT, tree.testPoint(new Vector3f(0, 0, 0)));
		assertEquals(VertexTag.OUT, tree.testPoint(new Vector3f(0.9f, 0, 0)));
		assertEquals(VertexTag.IN, tree.testPoint(new Vector3f(1.1f, 0, 0)));
		assertEquals(VertexTag.IN, tree.testPoint(new Vector3f(0, 1.1f, 0)));
		assertEquals(VertexTag.IN, tree.testPoint(new Vector3f(0, 0, 1.1f)));
		assertEquals(VertexTag.IN, tree.testPoint(new Vector3f(-1.1f, 0, 0)));
		assertEquals(VertexTag.IN, tree.testPoint(new Vector3f(0, -1.1f, 0)));
		assertEquals(VertexTag.IN, tree.testPoint(new Vector3f(0, 0, -1.1f)));
		assertEquals(VertexTag.IN, tree.testPoint(new Vector3f(0, 1, 1.1f)));
		assertEquals(VertexTag.ON, tree.testPoint(new Vector3f(1, 0.5f, 0)));

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

		assertEquals(VertexTag.OUT, tree.testPoint(new Vector3f(0, 0, 0)));
		assertEquals(VertexTag.OUT, tree.testPoint(new Vector3f(0.5f, 0, 0)));
		assertEquals(VertexTag.OUT, tree.testPoint(new Vector3f(-0.5f, 0, 0)));
		assertEquals(VertexTag.OUT, tree.testPoint(new Vector3f(0, 0.5f, 0)));
		assertEquals(VertexTag.OUT, tree.testPoint(new Vector3f(0, -0.5f, 0)));
		assertEquals(VertexTag.ON, tree.testPoint(new Vector3f(0.75f, 0, 0)));
		assertEquals(VertexTag.IN, tree.testPoint(new Vector3f(0.8f, 0, 0)));
		assertEquals(VertexTag.ON, tree.testPoint(new Vector3f(-0.75f, 0, 0)));
		assertEquals(VertexTag.IN, tree.testPoint(new Vector3f(-0.8f, 0, 0)));
		assertEquals(VertexTag.ON, tree.testPoint(new Vector3f(0, 0.75f, 0)));
		assertEquals(VertexTag.IN, tree.testPoint(new Vector3f(0, 0.8f, 0)));
		assertEquals(VertexTag.ON, tree.testPoint(new Vector3f(0, -0.75f, 0)));
		assertEquals(VertexTag.IN, tree.testPoint(new Vector3f(0, -0.8f, 0)));
		assertEquals(VertexTag.ON, tree.testPoint(new Vector3f(0.75f, 0.5f, 0)));
		assertEquals(VertexTag.ON,
				tree.testPoint(new Vector3f(0.75f, 0.75f, 0)));
		assertEquals(VertexTag.IN, tree.testPoint(new Vector3f(0.8f, 0.8f, 0)));
		assertEquals(VertexTag.ON,
				tree.testPoint(new Vector3f(-0.75f, -0.5f, 0)));
		assertEquals(VertexTag.ON,
				tree.testPoint(new Vector3f(-0.75f, -0.75f, 0)));
		assertEquals(VertexTag.IN,
				tree.testPoint(new Vector3f(-0.8f, -0.8f, 0)));
		assertEquals(VertexTag.OUT, tree.testPoint(new Vector3f(1.1f, 0, 0)));
		assertEquals(VertexTag.OUT, tree.testPoint(new Vector3f(-1.1f, 0, 0)));
		assertEquals(VertexTag.OUT, tree.testPoint(new Vector3f(0, 1.1f, 0)));
		assertEquals(VertexTag.OUT, tree.testPoint(new Vector3f(0, -1.1f, 0)));
	}

	@Test
	public void testCube2Cube() throws Exception {
		final IMesh meshA = XMLStateReader.fromXML(getClass()
				.getResourceAsStream("boolIn1_1.xml"));
		final IMesh meshB = XMLStateReader.fromXML(getClass()
				.getResourceAsStream("boolIn1_2.xml"));

		final TriangleMesh triMeshA = meshA.toTriangleMesh();
		final TriangleMesh triMeshB = meshB.toTriangleMesh().invert();

		// final UnitRescale unitRescale = new UnitRescale(triMeshA, triMeshB);
		// unitRescale.rescaleInput(triMeshA);
		// unitRescale.rescaleInput(triMeshB);

		final BSPTree bspB = new BSPTree();
		bspB.addMesh(triMeshB);

		int count = 0;
		for (final TriangleFace face : triMeshA.getFaces()) {
			final Vertex[] verticies = face.getVertexArray();

			count += bspB
					.testTriangle(verticies[0], verticies[1], verticies[2])
					.size();
		}
		assertEquals(22, count);
	}

	@Test
	public void testDodecaeder2Cube() throws Exception {
		TriangleMesh meshA = XMLStateReader.fromXML(getClass()
				.getResourceAsStream("boolIn2_1.xml"));
		TriangleMesh meshB = XMLStateReader.fromXML(getClass()
				.getResourceAsStream("boolIn2_2.xml"));

		meshA = (TriangleMesh) meshA.transform(new MatrixTransformation(
				new Vector3f(3, 3, 3), new Quaternion(),
				new Vector3f(-10, 0, 0)));
		meshB = meshB.invert();

		final UnitRescale unitRescale = new UnitRescale(meshA, meshB);
		unitRescale.rescaleInput(meshA);
		unitRescale.rescaleInput(meshB);

		final BSPTree bspA = new BSPTree();
		bspA.addMesh(meshA);

		int count = 0;
		for (final TriangleFace face : meshB.getFaces()) {
			final Vertex[] verticies = face.getVertexArray();

			count += bspA
					.testTriangle(verticies[0], verticies[1], verticies[2])
					.size();
		}
		assertEquals(0, count);
	}

	@Test
	public void testDodecaeder2Cube2() throws Exception {
		TriangleMesh meshA = XMLStateReader.fromXML(getClass()
				.getResourceAsStream("boolIn2_1.xml"));
		TriangleMesh meshB = XMLStateReader.fromXML(getClass()
				.getResourceAsStream("boolIn2_2.xml"));

		meshA = (TriangleMesh) meshA.transform(new MatrixTransformation(
				new Vector3f(3, 3, 3), new Quaternion(), new Vector3f(
						-7.31787f, 0, 0)));
		meshB = meshB.invert();

		final UnitRescale unitRescale = new UnitRescale(meshA, meshB);
		unitRescale.rescaleInput(meshA);
		unitRescale.rescaleInput(meshB);

		final BSPTree bspA = new BSPTree();
		bspA.addMesh(meshA);

		int count = 0;
		for (final TriangleFace face : meshB.getFaces()) {
			final Vertex[] verticies = face.getVertexArray();

			count += bspA
					.testTriangle(verticies[0], verticies[1], verticies[2])
					.size();
		}
		assertEquals(0, count);
	}
}
