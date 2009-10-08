package net.untoldwind.moredread.model.mesh;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import net.untoldwind.moredread.model.enums.GeometryType;
import net.untoldwind.moredread.model.state.IStateWriter;

import com.jme.math.Vector3f;

public class Polygon implements IPolygon {
	private final List<? extends IPoint> vertices;
	private final int[] stripCounts;
	private final int[] contourCounts;
	private final boolean closed;

	public Polygon(final IPoint[] vertices, final Vector3f[] normals,
			final int[] stripCounts, final int[] contourCounts,
			final boolean closed) {
		this.vertices = Arrays.asList(vertices);
		this.stripCounts = stripCounts;
		this.contourCounts = contourCounts;
		this.closed = closed;
	}

	public Polygon(final List<? extends IPoint> vertices,
			final Vector3f[] normals, final int[] stripCounts,
			final int[] contourCounts, final boolean closed) {
		this.vertices = vertices;
		this.stripCounts = stripCounts;
		this.contourCounts = contourCounts;
		this.closed = closed;
	}

	@Override
	public GeometryType getGeometryType() {
		return GeometryType.POLYGON;
	}

	@Override
	public List<? extends IPoint> getPolygonPoints() {
		return vertices;
	}

	@Override
	public Vector3f getMeanNormal() {
		final Vector3f meanNormal = new Vector3f(0, 0, 0);
		for (int i = 0; i < vertices.size(); i++) {
			final Vector3f v1 = vertices.get(i).getPoint();
			final Vector3f v2 = vertices.get((i + 1) % vertices.size())
					.getPoint();
			meanNormal.addLocal(v1.cross(v2));
		}
		final float len = meanNormal.length();

		if (len < 1e-6) {
			meanNormal.set(0, 0, 1);
		} else {
			meanNormal.divideLocal(len);
		}
		return meanNormal;
	}

	@Override
	public int[] getPolygonStripCounts() {
		return stripCounts;
	}

	@Override
	public int[] getPolygonContourCounts() {
		return contourCounts;
	}

	@Override
	public boolean isClosed() {
		return closed;
	}

	@Override
	public Vector3f getCenter() {
		final Vector3f center = new Vector3f(0, 0, 0);

		for (final IPoint vertex : vertices) {
			center.addLocal(vertex.getPoint());
		}

		center.divideLocal(vertices.size());

		return center;
	}

	@Override
	public void writeState(final IStateWriter writer) throws IOException {
		writer.writeCollection("vertices", vertices);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Polygon [closed=");
		builder.append(closed);
		builder.append(", contourCounts=");
		builder.append(Arrays.toString(contourCounts));
		builder.append(", stripCounts=");
		builder.append(Arrays.toString(stripCounts));
		builder.append(", vertices=");
		builder.append(vertices);
		builder.append("]");
		return builder.toString();
	}

}
