package net.untoldwind.moredread.model.generator;

import net.untoldwind.moredread.model.mesh.Mesh;
import net.untoldwind.moredread.model.mesh.TriangleMesh;

import com.jme.math.Vector3f;

public class OctahedronMeshGenerator implements IMeshGenerator {
	private final Vector3f center = new Vector3f();
	private final float size = 1f;

	public String getName() {
		return "Octahedron";
	}

	public Mesh<?> generateMesh() {
		final TriangleMesh mesh = new TriangleMesh();

		mesh.addVertex(new Vector3f(size, 0, 0).addLocal(center));
		mesh.addVertex(new Vector3f(-size, 0, 0).addLocal(center));
		mesh.addVertex(new Vector3f(0, size, 0).addLocal(center));
		mesh.addVertex(new Vector3f(0, -size, 0).addLocal(center));
		mesh.addVertex(new Vector3f(0, 0, size).addLocal(center));
		mesh.addVertex(new Vector3f(0, 0, -size).addLocal(center));

		mesh.addFace(4, 0, 2);
		mesh.addFace(4, 2, 1);
		mesh.addFace(4, 1, 3);
		mesh.addFace(4, 3, 0);
		mesh.addFace(5, 2, 0);
		mesh.addFace(5, 1, 2);
		mesh.addFace(5, 3, 1);
		mesh.addFace(5, 0, 3);

		return mesh;
	}

}
