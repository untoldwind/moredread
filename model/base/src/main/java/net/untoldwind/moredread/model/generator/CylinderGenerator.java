package net.untoldwind.moredread.model.generator;

import java.io.IOException;
import java.util.List;

import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.Mesh;
import net.untoldwind.moredread.model.mesh.PolyMesh;
import net.untoldwind.moredread.model.mesh.QuadMesh;
import net.untoldwind.moredread.model.state.IStateReader;
import net.untoldwind.moredread.model.state.IStateWriter;

import com.jme.math.FastMath;

public class CylinderGenerator extends AbstractGeometryGenerator implements
		IMeshGenerator {

	private Vector3 startPoint;
	private Vector3 endPoint;
	private float radius;
	private int numSections;
	private int pointsPerSection;
	private boolean closed;

	public CylinderGenerator() {
		startPoint = new Vector3(0, 0, 0);
		endPoint = new Vector3(0, 0, 1);
		radius = 1.0f;
		numSections = 1;
		pointsPerSection = 8;
		closed = true;
	}

	@Override
	public String getName() {
		return "Cylinder";
	}

	public Vector3 getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(final Vector3 startPoint) {
		this.startPoint = startPoint;
	}

	public Vector3 getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(final Vector3 endPoint) {
		this.endPoint = endPoint;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(final float radius) {
		this.radius = radius;
	}

	public int getNumSections() {
		return numSections;
	}

	public void setNumSections(final int numSections) {
		this.numSections = numSections;
	}

	public int getPointsPerSection() {
		return pointsPerSection;
	}

	public void setPointsPerSection(final int pointsPerSection) {
		this.pointsPerSection = pointsPerSection;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(final boolean closed) {
		this.closed = closed;
	}

	@Override
	public IMesh generateMesh(final List<IGeneratorInput> generatorInputs) {
		final Vector3 dir = endPoint.subtract(startPoint);
		Vector3 radiusU = dir.cross(Vector3.UNIT_Y);
		float len = radiusU.length();

		if (len < 1e-6) {
			radiusU = dir.cross(Vector3.UNIT_Z);
			len = radiusU.length();
		}
		radiusU.multLocal(radius / len);
		final Vector3 radiusV = dir.cross(radiusU);
		len = radiusV.length();
		radiusV.multLocal(radius / len);

		if (closed) {
			final PolyMesh result = new PolyMesh();

			int[] prevSection = generateSectionVertices(result, radiusU,
					radiusV, 0);

			for (int i = 1; i <= numSections; i++) {
				final int[] nextSection = generateSectionVertices(result,
						radiusU, radiusV, i);

				for (int j = 0, k = pointsPerSection - 1; j < pointsPerSection; k = j++) {
					result.addFace(prevSection[j], prevSection[k],
							nextSection[k], nextSection[j]);
				}

				prevSection = nextSection;
			}

			return result;
		} else {
			final QuadMesh result = new QuadMesh();

			int[] prevSection = generateSectionVertices(result, radiusU,
					radiusV, 0);

			for (int i = 1; i <= numSections; i++) {
				final int[] nextSection = generateSectionVertices(result,
						radiusU, radiusV, i);

				for (int j = 0, k = pointsPerSection - 1; j < pointsPerSection; k = j++) {
					result.addFace(prevSection[j], prevSection[k],
							nextSection[k], nextSection[j]);
				}

				prevSection = nextSection;
			}

			return result;
		}
	}

	private int[] generateSectionVertices(final Mesh<?, ?> mesh,
			final Vector3 radiusU, final Vector3 radiusV, final int section) {
		final int[] result = new int[pointsPerSection];
		final Vector3 mid = new Vector3();

		mid.interpolate(startPoint, endPoint, section / numSections);

		for (int i = 0; i < pointsPerSection; i++) {
			final Vector3 p = new Vector3(mid);

			p.addLocal(radiusU.mult(FastMath.sin(i * FastMath.TWO_PI
					/ pointsPerSection)));
			p.addLocal(radiusV.mult(FastMath.cos(i * FastMath.TWO_PI
					/ pointsPerSection)));

			result[i] = mesh.addVertex(p, true).getIndex();
		}

		return result;
	}

	@Override
	public void readState(final IStateReader reader) throws IOException {
		startPoint = reader.readVector3();
		endPoint = reader.readVector3();
		radius = reader.readFloat();
		numSections = reader.readInt();
		pointsPerSection = reader.readInt();
		closed = reader.readBoolean();
	}

	@Override
	public void writeState(final IStateWriter writer) throws IOException {
		writer.writeVector3("startPoint", startPoint);
		writer.writeVector3("endPoint", endPoint);
		writer.writeFloat("radius", radius);
		writer.writeInt("numSections", numSections);
		writer.writeInt("pointsPerSection", pointsPerSection);
		writer.writeBoolean("closed", closed);
	}

}
