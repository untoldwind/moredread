package net.untoldwind.moredread.model.generator;

import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.mesh.TriangleMesh;

import com.jme.math.FastMath;
import com.jme.math.Vector3f;

public class GeosphereMeshGenerator implements IMeshGenerator {
	private final Vector3f center = new Vector3f();
	private final float size = 1f;
	private final int numLevels;

	public GeosphereMeshGenerator(final int numLevels) {
		this.numLevels = numLevels;
	}

	@Override
	public String getName() {
		return "Geosphere";
	}

	@Override
	public TriangleMesh generateMesh(final List<IGeneratorInput> generatorInputs) {
		final TriangleMesh mesh = new TriangleMesh();
		List<Triangle> triangles = new ArrayList<Triangle>();

		if (numLevels % 2 == 0) {
			mesh.addVertex(new Vector3f(size, 0, 0));
			mesh.addVertex(new Vector3f(-size, 0, 0));
			mesh.addVertex(new Vector3f(0, size, 0));
			mesh.addVertex(new Vector3f(0, -size, 0));
			mesh.addVertex(new Vector3f(0, 0, size));
			mesh.addVertex(new Vector3f(0, 0, -size));

			triangles.add(new Triangle(4, 0, 2));
			triangles.add(new Triangle(4, 2, 1));
			triangles.add(new Triangle(4, 1, 3));
			triangles.add(new Triangle(4, 3, 0));
			triangles.add(new Triangle(5, 2, 0));
			triangles.add(new Triangle(5, 1, 2));
			triangles.add(new Triangle(5, 3, 1));
			triangles.add(new Triangle(5, 0, 3));
		} else {
			final float fGoldenRatio = 0.5f * (1.0f + FastMath.sqrt(5.0f));
			final float fInvRoot = 1.0f / FastMath.sqrt(1.0f + fGoldenRatio
					* fGoldenRatio);
			final float fU = fGoldenRatio * fInvRoot * size;
			final float fV = fInvRoot * size;

			mesh.addVertex(new Vector3f(fU, fV, 0.0f));
			mesh.addVertex(new Vector3f(-fU, fV, 0.0f));
			mesh.addVertex(new Vector3f(fU, -fV, 0.0f));
			mesh.addVertex(new Vector3f(-fU, -fV, 0.0f));
			mesh.addVertex(new Vector3f(fV, 0.0f, fU));
			mesh.addVertex(new Vector3f(fV, 0.0f, -fU));
			mesh.addVertex(new Vector3f(-fV, 0.0f, fU));
			mesh.addVertex(new Vector3f(-fV, 0.0f, -fU));
			mesh.addVertex(new Vector3f(0.0f, fU, fV));
			mesh.addVertex(new Vector3f(0.0f, -fU, fV));
			mesh.addVertex(new Vector3f(0.0f, fU, -fV));
			mesh.addVertex(new Vector3f(0.0f, -fU, -fV));

			triangles.add(new Triangle(0, 8, 4));
			triangles.add(new Triangle(0, 5, 10));
			triangles.add(new Triangle(2, 4, 9));
			triangles.add(new Triangle(2, 11, 5));
			triangles.add(new Triangle(1, 6, 8));
			triangles.add(new Triangle(1, 10, 7));
			triangles.add(new Triangle(3, 9, 6));
			triangles.add(new Triangle(3, 7, 11));
			triangles.add(new Triangle(0, 10, 8));
			triangles.add(new Triangle(1, 8, 10));
			triangles.add(new Triangle(2, 9, 11));
			triangles.add(new Triangle(3, 11, 9));
			triangles.add(new Triangle(4, 2, 0));
			triangles.add(new Triangle(5, 0, 2));
			triangles.add(new Triangle(6, 1, 3));
			triangles.add(new Triangle(7, 3, 1));
			triangles.add(new Triangle(8, 6, 4));
			triangles.add(new Triangle(9, 4, 6));
			triangles.add(new Triangle(10, 5, 7));
			triangles.add(new Triangle(11, 7, 5));
		}

		Vector3f pt0;
		Vector3f pt1;
		Vector3f pt2;
		for (int level = 1; level < numLevels; level += 2) {
			final List<Triangle> nextTriangles = new ArrayList<Triangle>(
					triangles.size() * 4);

			for (final Triangle old : triangles) {
				pt0 = mesh.getVertex(old.i1).getPoint();
				pt1 = mesh.getVertex(old.i2).getPoint();
				pt2 = mesh.getVertex(old.i3).getPoint();
				final Vector3f av = createMidpoint(pt0, pt2).normalizeLocal()
						.multLocal(size);
				final Vector3f bv = createMidpoint(pt0, pt1).normalizeLocal()
						.multLocal(size);
				final Vector3f cv = createMidpoint(pt1, pt2).normalizeLocal()
						.multLocal(size);
				final int a = mesh.addVertex(av).getIndex();
				final int b = mesh.addVertex(bv).getIndex();
				final int c = mesh.addVertex(cv).getIndex();

				nextTriangles.add(new Triangle(old.i1, b, a));
				nextTriangles.add(new Triangle(b, old.i2, c));
				nextTriangles.add(new Triangle(a, b, c));
				nextTriangles.add(new Triangle(a, c, old.i3));
			}
			triangles = nextTriangles;
		}

		for (final Triangle triangle : triangles) {
			mesh.addFace(triangle.i1, triangle.i2, triangle.i3);
		}

		return mesh;
	}

	private Vector3f createMidpoint(final Vector3f a, final Vector3f b) {
		return new Vector3f((a.x + b.x) * 0.5f, (a.y + b.y) * 0.5f,
				(a.z + b.z) * 0.5f);
	}

	static class Triangle {
		final int i1;
		final int i2;
		final int i3;

		public Triangle(final int i1, final int i2, final int i3) {
			super();
			this.i1 = i1;
			this.i2 = i2;
			this.i3 = i3;
		}

	}

}
