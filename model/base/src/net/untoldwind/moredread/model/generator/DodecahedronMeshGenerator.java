package net.untoldwind.moredread.model.generator;

import java.util.List;

import net.untoldwind.moredread.model.mesh.PolyMesh;

import com.jme.math.FastMath;
import com.jme.math.Vector3f;

public class DodecahedronMeshGenerator implements IMeshGenerator {
	private final Vector3f center;
	private final float size;

	public DodecahedronMeshGenerator() {
		this(new Vector3f(0, 0, 0), 1f);
	}

	public DodecahedronMeshGenerator(final Vector3f center, final float size) {
		this.center = center;
		this.size = size;
	}

	public String getName() {
		return "Dodecahedron";
	}

	@Override
	public PolyMesh generateMesh(final List<IGeneratorInput> generatorInputs) {
		final PolyMesh mesh = new PolyMesh();

		float fA = 1.0f / FastMath.sqrt(3.0f);
		float fB = FastMath.sqrt((3.0f - FastMath.sqrt(5.0f)) / 6.0f);
		float fC = FastMath.sqrt((3.0f + FastMath.sqrt(5.0f)) / 6.0f);
		fA *= size;
		fB *= size;
		fC *= size;

		mesh.addVertex(new Vector3f(fA, fA, fA).addLocal(center));
		mesh.addVertex(new Vector3f(fA, fA, -fA).addLocal(center));
		mesh.addVertex(new Vector3f(fA, -fA, fA).addLocal(center));
		mesh.addVertex(new Vector3f(fA, -fA, -fA).addLocal(center));
		mesh.addVertex(new Vector3f(-fA, fA, fA).addLocal(center));
		mesh.addVertex(new Vector3f(-fA, fA, -fA).addLocal(center));
		mesh.addVertex(new Vector3f(-fA, -fA, fA).addLocal(center));
		mesh.addVertex(new Vector3f(-fA, -fA, -fA).addLocal(center));
		mesh.addVertex(new Vector3f(fB, fC, 0.0f).addLocal(center));
		mesh.addVertex(new Vector3f(-fB, fC, 0.0f).addLocal(center));
		mesh.addVertex(new Vector3f(fB, -fC, 0.0f).addLocal(center));
		mesh.addVertex(new Vector3f(-fB, -fC, 0.0f).addLocal(center));
		mesh.addVertex(new Vector3f(fC, 0.0f, fB).addLocal(center));
		mesh.addVertex(new Vector3f(fC, 0.0f, -fB).addLocal(center));
		mesh.addVertex(new Vector3f(-fC, 0.0f, fB).addLocal(center));
		mesh.addVertex(new Vector3f(-fC, 0.0f, -fB).addLocal(center));
		mesh.addVertex(new Vector3f(0.0f, fB, fC).addLocal(center));
		mesh.addVertex(new Vector3f(0.0f, -fB, fC).addLocal(center));
		mesh.addVertex(new Vector3f(0.0f, fB, -fC).addLocal(center));
		mesh.addVertex(new Vector3f(0.0f, -fB, -fC).addLocal(center));

		mesh.addFace(0, 8, 9, 4, 16);
		mesh.addFace(0, 12, 13, 1, 8);
		mesh.addFace(0, 16, 17, 2, 12);
		mesh.addFace(8, 1, 18, 5, 9);
		mesh.addFace(12, 2, 10, 3, 13);
		mesh.addFace(16, 4, 14, 6, 17);
		mesh.addFace(9, 5, 15, 14, 4);
		mesh.addFace(6, 11, 10, 2, 17);
		mesh.addFace(3, 19, 18, 1, 13);
		mesh.addFace(7, 15, 5, 18, 19);
		mesh.addFace(7, 11, 6, 14, 15);
		mesh.addFace(7, 19, 3, 10, 11);

		return mesh;
	}

}
