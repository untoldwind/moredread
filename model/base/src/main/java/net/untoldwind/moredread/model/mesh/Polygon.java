package net.untoldwind.moredread.model.mesh;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.untoldwind.moredread.model.enums.GeometryType;
import net.untoldwind.moredread.model.state.IStateReader;
import net.untoldwind.moredread.model.state.IStateWriter;
import net.untoldwind.moredread.model.transform.ITransformation;

import com.jme.math.Vector3f;

public class Polygon extends EdgeGeometry<IPolygon> implements IPolygon {
	private int[] stripCounts;
	private int[] contourCounts;
	private boolean closed;

	public Polygon() {
		this.stripCounts = new int[] { 0 };
		this.contourCounts = new int[] { 1 };
		this.closed = false;
	}

	public Polygon(final IPoint[] points, final int[] stripCounts,
			final int[] contourCounts, final boolean closed) {
		for (final IPoint point : points) {
			addVertex(point.getPoint(), false);
		}
		this.stripCounts = stripCounts;
		this.contourCounts = contourCounts;
		this.closed = closed;

		int k = 0;
		final Iterator<Vertex> it = vertices.iterator();
		for (int i = 0; i < contourCounts.length; i++) {
			for (int j = 0; j < contourCounts[i]; j++) {
				final int stripCount = stripCounts[k++];

				final Vertex first = it.next();
				Vertex last = first;

				for (int l = 1; l < stripCount; l++) {
					final Vertex vertex = it.next();

					addEdge(vertex, last);

					last = vertex;
				}
				if (closed) {
					addEdge(last, first);
				}
			}
		}
	}

	public Polygon(final List<? extends IPoint> points,
			final int[] stripCounts, final int[] contourCounts,
			final boolean closed) {
		for (final IPoint point : points) {
			addVertex(point.getPoint(), false);
		}
		this.stripCounts = stripCounts;
		this.contourCounts = contourCounts;
		this.closed = closed;

		int k = 0;
		final Iterator<Vertex> it = vertices.iterator();
		for (int i = 0; i < contourCounts.length; i++) {
			for (int j = 0; j < contourCounts[i]; j++) {
				final int stripCount = stripCounts[k++];

				final Vertex first = it.next();
				Vertex last = first;

				for (int l = 1; l < stripCount; l++) {
					final Vertex vertex = it.next();

					addEdge(vertex, last);

					last = vertex;
				}
				if (closed) {
					addEdge(last, first);
				}
			}
		}
	}

	@Override
	public GeometryType getGeometryType() {
		return GeometryType.POLYGON;
	}

	@Override
	public int getVertexCount() {
		return vertices.size();
	}

	public Vertex appendVertex(final Vector3f point, final boolean smooth) {
		final Vertex vertex = new Vertex(this, vertices.size(), point);

		vertices.add(vertex);

		stripCounts[stripCounts.length - 1] += 1;
		if (vertex.getIndex() > 0) {
			addEdge(vertices.get(vertex.getIndex() - 1), vertex);
		}

		return vertex;
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
	public IPolygon transform(final ITransformation transformation) {
		final List<IPoint> new_vertices = new ArrayList<IPoint>(vertices.size());

		for (final IVertex vertex : vertices) {
			new_vertices.add(vertex.transform(transformation));
		}

		return new Polygon(new_vertices, stripCounts, contourCounts, closed);
	}

	@Override
	public void readState(final IStateReader reader) throws IOException {
		final int numVerices = reader.readInt();

		for (int i = 0; i < numVerices; i++) {
			addVertex(reader.readVector3f(), false);
		}
		stripCounts = reader.readIntArray();
		contourCounts = reader.readIntArray();
		closed = reader.readBoolean();

		int k = 0;
		final Iterator<Vertex> it = vertices.iterator();
		for (int i = 0; i < contourCounts.length; i++) {
			for (int j = 0; j < contourCounts[i]; j++) {
				final int stripCount = stripCounts[k++];

				final Vertex first = it.next();
				Vertex last = first;

				for (int l = 1; l < stripCount; l++) {
					final Vertex vertex = it.next();

					addEdge(vertex, last);

					last = vertex;
				}
				if (closed) {
					addEdge(last, first);
				}
			}
		}
	}

	@Override
	public void writeState(final IStateWriter writer) throws IOException {
		writer.writeInt("numVerices", vertices.size());
		for (final IPoint vertex : vertices) {
			writer.writeVector3f("vertex", vertex.getPoint());
		}
		writer.writeIntArray("stripCounts", stripCounts);
		writer.writeIntArray("contourCounts", contourCounts);
		writer.writeBoolean("cloded", closed);
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
