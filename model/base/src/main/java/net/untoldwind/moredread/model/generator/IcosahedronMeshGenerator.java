package net.untoldwind.moredread.model.generator;

import java.util.List;

import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.mesh.TriangleMesh;

import com.jme.math.FastMath;

public class IcosahedronMeshGenerator extends AbstractCenterSizeGenerator {
	public IcosahedronMeshGenerator() {
		super(new Vector3(), 1.0f);
	}

	public IcosahedronMeshGenerator(final Vector3 center, final float size) {
		super(center, size);
	}

	@Override
	public String getName() {
		return "Icosahedron";
	}

	@Override
	public TriangleMesh generateGeometry(final List<IGeneratorInput> generatorInputs) {
		final float fGoldenRatio = 0.5f * (1.0f + FastMath.sqrt(5.0f));
		final float fInvRoot = 1.0f / FastMath.sqrt(1.0f + fGoldenRatio
				* fGoldenRatio);
		final float fU = fGoldenRatio * fInvRoot * size;
		final float fV = fInvRoot * size;

		final TriangleMesh mesh = new TriangleMesh();

		mesh.addVertex(new Vector3(fU, fV, 0.0f).addLocal(center));
		mesh.addVertex(new Vector3(-fU, fV, 0.0f).addLocal(center));
		mesh.addVertex(new Vector3(fU, -fV, 0.0f).addLocal(center));
		mesh.addVertex(new Vector3(-fU, -fV, 0.0f).addLocal(center));
		mesh.addVertex(new Vector3(fV, 0.0f, fU).addLocal(center));
		mesh.addVertex(new Vector3(fV, 0.0f, -fU).addLocal(center));
		mesh.addVertex(new Vector3(-fV, 0.0f, fU).addLocal(center));
		mesh.addVertex(new Vector3(-fV, 0.0f, -fU).addLocal(center));
		mesh.addVertex(new Vector3(0.0f, fU, fV).addLocal(center));
		mesh.addVertex(new Vector3(0.0f, -fU, fV).addLocal(center));
		mesh.addVertex(new Vector3(0.0f, fU, -fV).addLocal(center));
		mesh.addVertex(new Vector3(0.0f, -fU, -fV).addLocal(center));

		mesh.addFace(0, 8, 4);
		mesh.addFace(0, 5, 10);
		mesh.addFace(2, 4, 9);
		mesh.addFace(2, 11, 5);
		mesh.addFace(1, 6, 8);
		mesh.addFace(1, 10, 7);
		mesh.addFace(3, 9, 6);
		mesh.addFace(3, 7, 11);
		mesh.addFace(0, 10, 8);
		mesh.addFace(1, 8, 10);
		mesh.addFace(2, 9, 11);
		mesh.addFace(3, 11, 9);
		mesh.addFace(4, 2, 0);
		mesh.addFace(5, 0, 2);
		mesh.addFace(6, 1, 3);
		mesh.addFace(7, 3, 1);
		mesh.addFace(8, 6, 4);
		mesh.addFace(9, 4, 6);
		mesh.addFace(10, 5, 7);
		mesh.addFace(11, 7, 5);

		return mesh;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("IcosahedronMeshGenerator [center=");
		builder.append(center);
		builder.append(", size=");
		builder.append(size);
		builder.append("]");
		return builder.toString();
	}

}
