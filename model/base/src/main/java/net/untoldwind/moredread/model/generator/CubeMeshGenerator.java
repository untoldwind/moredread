package net.untoldwind.moredread.model.generator;

import java.util.List;

import net.untoldwind.moredread.model.mesh.QuadMesh;

import com.jme.math.Vector3f;

public class CubeMeshGenerator extends AbstractCenterSizeGenerator implements
		IMeshGenerator {

	public CubeMeshGenerator() {
		super(new Vector3f(0, 0, 0), 1f);
	}

	public CubeMeshGenerator(final Vector3f center, final float size) {
		super(center, size);
	}

	public String getName() {
		return "Cube";
	}

	public QuadMesh generateMesh(final List<IGeneratorInput> generatorInputs) {
		final QuadMesh mesh = new QuadMesh();

		mesh.addVertex(new Vector3f(-size, -size, -size).addLocal(center));
		mesh.addVertex(new Vector3f(size, -size, -size).addLocal(center));
		mesh.addVertex(new Vector3f(size, size, -size).addLocal(center));
		mesh.addVertex(new Vector3f(-size, size, -size).addLocal(center));
		mesh.addVertex(new Vector3f(-size, -size, size).addLocal(center));
		mesh.addVertex(new Vector3f(size, -size, size).addLocal(center));
		mesh.addVertex(new Vector3f(size, size, size).addLocal(center));
		mesh.addVertex(new Vector3f(-size, size, size).addLocal(center));

		mesh.addFace(3, 2, 1, 0);
		mesh.addFace(4, 5, 6, 7);
		mesh.addFace(4, 7, 3, 0);
		mesh.addFace(1, 2, 6, 5);
		mesh.addFace(0, 1, 5, 4);
		mesh.addFace(2, 3, 7, 6);

		return mesh;
	}
}
