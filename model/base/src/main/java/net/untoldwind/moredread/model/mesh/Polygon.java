package net.untoldwind.moredread.model.mesh;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.untoldwind.moredread.model.enums.GeometryType;
import net.untoldwind.moredread.model.state.IStateReader;
import net.untoldwind.moredread.model.state.IStateWriter;
import net.untoldwind.moredread.model.transform.ITransformation;

import com.jme.math.Vector3f;

public class Polygon implements IPolygon {
	private List<Vertex> vertices;
	private int[] stripCounts;
	private int[] contourCounts;
	private boolean closed;
	protected boolean dirty = false;
	protected Map<EdgeId, Edge> edges;

	protected Polygon() {
	}

	public Polygon(final IPoint[] points, final int[] stripCounts,
			final int[] contourCounts, final boolean closed) {
		this.vertices = new ArrayList<Vertex>();
		for (final IPoint point : points) {
			addVertex(point.getPoint(), false);
		}
		this.stripCounts = stripCounts;
		this.contourCounts = contourCounts;
		this.closed = closed;
		this.edges = new HashMap<EdgeId, Edge>();

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
		this.vertices = new ArrayList<Vertex>();
		for (final IPoint point : points) {
			addVertex(point.getPoint(), false);
		}
		this.stripCounts = stripCounts;
		this.contourCounts = contourCounts;
		this.closed = closed;
		this.edges = new HashMap<EdgeId, Edge>();

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

	@Override
	public List<? extends IVertex> getVertices() {
		return vertices;
	}

	@Override
	public IEdge getEdge(final EdgeId edgeIndex) {
		return edges.get(edgeIndex);
	}

	@Override
	public Collection<? extends IEdge> getEdges() {
		return edges.values();
	}

	public Vertex addVertex(final Vector3f point, final boolean smooth) {
		final Vertex vertex = new Vertex(this, vertices.size(), point);

		vertices.add(vertex);

		return vertex;
	}

	protected Edge addEdge(final Vertex vertex1, final Vertex vertex2) {
		Edge edge = edges
				.get(new EdgeId(vertex1.getIndex(), vertex2.getIndex()));

		if (edge == null) {
			edge = new Edge(this, vertex1, vertex2);

			edges.put(edge.getIndex(), edge);
		}

		return edge;
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

	void markDirty() {
		dirty = true;
	}

	public boolean clearDirty() {
		if (dirty) {
			dirty = false;
			return true;
		}
		return false;
	}

	@Override
	public void readState(final IStateReader reader) throws IOException {
		final List<IPoint> vertices = new ArrayList<IPoint>();
		final int numVerices = reader.readInt();

		for (int i = 0; i < numVerices; i++) {
			vertices.add(new Point(reader.readVector3f()));
		}
		stripCounts = reader.readIntArray();
		contourCounts = reader.readIntArray();
		closed = reader.readBoolean();
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
