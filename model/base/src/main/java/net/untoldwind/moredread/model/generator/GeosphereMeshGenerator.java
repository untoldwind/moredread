package net.untoldwind.moredread.model.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.mesh.TriangleMesh;
import net.untoldwind.moredread.model.state.IStateReader;
import net.untoldwind.moredread.model.state.IStateWriter;

import com.jme.math.FastMath;

public class GeosphereMeshGenerator extends AbstractCenterSizeGenerator
		implements IMeshGenerator {
	private int numLevels;

	public GeosphereMeshGenerator() {
		super(new Vector3(0, 0, 0), 1f);
	}

	public GeosphereMeshGenerator(final int numLevels, final Vector3 center,
			final float size) {
		super(center, size);

		this.numLevels = numLevels;
	}

	@Override
	public String getName() {
		return "Geosphere";
	}

	public int getNumLevels() {
		return numLevels;
	}

	public void setNumLevels(final int numLevels) {
		registerParameterChange();

		this.numLevels = numLevels;
	}

	@Override
	public TriangleMesh generateGeometry(
			final List<IGeneratorInput> generatorInputs) {
		final TriangleMesh mesh = new TriangleMesh();
		List<Triangle> triangles = new ArrayList<Triangle>();

		if (numLevels % 2 == 0) {
			mesh.addVertex(new Vector3(center.x + size, center.y, center.z));
			mesh.addVertex(new Vector3(center.x - size, center.y, center.z));
			mesh.addVertex(new Vector3(center.x, center.y + size, center.z));
			mesh.addVertex(new Vector3(center.x, center.y - size, center.z));
			mesh.addVertex(new Vector3(center.x, center.y, center.z + size));
			mesh.addVertex(new Vector3(center.x, center.y, center.z - size));

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

			mesh.addVertex(new Vector3(center.x + fU, center.y + fV, center.z));
			mesh.addVertex(new Vector3(center.x - fU, center.y + fV, center.z));
			mesh.addVertex(new Vector3(center.x + fU, center.y - fV, center.z));
			mesh.addVertex(new Vector3(center.x - fU, center.y - fV, center.z));
			mesh.addVertex(new Vector3(center.x + fV, center.y, center.z + fU));
			mesh.addVertex(new Vector3(center.x + fV, center.y, center.z - fU));
			mesh.addVertex(new Vector3(center.x - fV, center.y, center.z + fU));
			mesh.addVertex(new Vector3(center.x - fV, center.y, center.z - fU));
			mesh.addVertex(new Vector3(center.x, center.y + fU, center.z + fV));
			mesh.addVertex(new Vector3(center.x, center.y - fU, center.z + fV));
			mesh.addVertex(new Vector3(center.x, center.y + fU, center.z - fV));
			mesh.addVertex(new Vector3(center.x, center.y - fU, center.z - fV));

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

		Vector3 pt0;
		Vector3 pt1;
		Vector3 pt2;
		for (int level = 1; level < numLevels; level += 2) {
			final List<Triangle> nextTriangles = new ArrayList<Triangle>(
					triangles.size() * 4);

			for (final Triangle old : triangles) {
				pt0 = mesh.getVertex(old.i1).getPoint();
				pt1 = mesh.getVertex(old.i2).getPoint();
				pt2 = mesh.getVertex(old.i3).getPoint();
				final Vector3 av = createMidpoint(pt0, pt2)
						.subtractLocal(center).normalizeLocal().multLocal(size)
						.addLocal(center);
				final Vector3 bv = createMidpoint(pt0, pt1)
						.subtractLocal(center).normalizeLocal().multLocal(size)
						.addLocal(center);
				final Vector3 cv = createMidpoint(pt1, pt2)
						.subtractLocal(center).normalizeLocal().multLocal(size)
						.addLocal(center);
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

	@Override
	public void readState(final IStateReader reader) throws IOException {
		super.readState(reader);
		numLevels = reader.readInt();
	}

	@Override
	public void writeState(final IStateWriter writer) throws IOException {
		super.writeState(writer);
		writer.writeInt("levels", numLevels);
	}

	private Vector3 createMidpoint(final Vector3 a, final Vector3 b) {
		return new Vector3((a.x + b.x) * 0.5f, (a.y + b.y) * 0.5f,
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
