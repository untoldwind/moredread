package net.untoldwind.moredread.model.mesh;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.state.IStateReader;
import net.untoldwind.moredread.model.state.IStateWriter;
import net.untoldwind.moredread.model.transform.ITransformation;

import com.jme.math.Vector3f;

public class PolyFace extends Face<PolyFaceId, PolyFace> {
	private final List<Vertex> vertices;
	private final List<Edge> edges;
	private final List<Integer> stripCounts;

	PolyFace(final PolyMesh owner, final PolyFaceId index,
			final List<Vertex> vertices, final List<Edge> edges) {
		super(owner, index);

		this.vertices = vertices;
		this.edges = edges;
		this.stripCounts = new ArrayList<Integer>();
		this.stripCounts.add(vertices.size());

		for (final Vertex vertex : vertices) {
			vertex.getFaces().add(this);
		}

		for (final Edge edge : edges) {
			edge.getFaces().add(this);
		}
	}

	PolyFace(final PolyMesh owner, final PolyFaceId index,
			final Vertex[][] vertexStrips, final List<Edge> edges) {
		super(owner, index);

		this.edges = edges;

		this.vertices = new ArrayList<Vertex>();
		this.stripCounts = new ArrayList<Integer>();
		for (final Vertex[] strip : vertexStrips) {
			this.stripCounts.add(strip.length);
			for (final Vertex vertex : strip) {
				this.vertices.add(vertex);
			}
		}
		for (final Vertex vertex : vertices) {
			vertex.getFaces().add(this);
		}

		for (final Edge edge : edges) {
			edge.getFaces().add(this);
		}
	}

	@Override
	public int getVertexCount() {
		return vertices.size();
	}

	@Override
	public Vertex getVertex(final int index) {
		return vertices.get(index);
	}

	@Override
	public List<Vertex> getVertices() {
		return vertices;
	}

	@Override
	public IEdge getEdge(final EdgeId edgeIndex) {
		for (final Edge edge : edges) {
			if (edge.getIndex().equals(edgeIndex)) {
				return edge;
			}
		}
		return null;
	}

	@Override
	public List<Edge> getEdges() {
		return edges;
	}

	public List<Integer> getStripCounts() {
		return stripCounts;
	}

	@Override
	public int[] getPolygonContourCounts() {
		return new int[] { stripCounts.size() };
	}

	@Override
	public int[] getPolygonStripCounts() {
		final int[] stripCountArray = new int[stripCounts.size()];

		for (int i = 0; i < stripCountArray.length; i++) {
			stripCountArray[i] = stripCounts.get(i);
		}

		return stripCountArray;
	}

	@Override
	public boolean isClosed() {
		return true;
	}

	@Override
	public void updateCenter() {
		center = new Vector3f(0, 0, 0);

		for (final Vertex vertex : vertices) {
			center.addLocal(vertex.getPoint());
		}

		center.divideLocal(vertices.size());
	}

	@Override
	public void updateMeanNormal() {
		meanNormal = new Vector3f(0, 0, 0);
		final int outerStripCount = stripCounts.get(0);
		for (int i = 0; i < outerStripCount; i++) {
			final Vector3f v1 = vertices.get(i).getPoint();
			final Vector3f v2 = vertices.get((i + 1) % outerStripCount)
					.getPoint();
			meanNormal.addLocal(v1.cross(v2));
		}
		final float len = meanNormal.length();

		if (len < 1e-6) {
			meanNormal.set(0, 0, 1);
		} else {
			meanNormal.divideLocal(len);
		}
	}

	@Override
	void remove() {
		for (final Edge edge : edges) {
			edge.getFaces().remove(this);
		}
		for (final Vertex vertex : vertices) {
			vertex.getFaces().remove(this);
		}
	}

	void addMidpoint(final Edge edge, final Vertex midpoint,
			final Edge newEdge1, final Edge newEdge2) {
		int offset = 0;
		for (int i = 0; i < stripCounts.size(); i++) {
			final int stripCount = stripCounts.get(i);

			for (int j = 0; j < stripCount; j++) {
				final Vertex v1 = vertices.get(j + offset);
				final Vertex v2 = vertices.get(((j + 1) % stripCount) + offset);

				if (edge.isConnection(v1, v2)) {
					edges.remove(edge);
					edge.getFaces().remove(this);
					edges.add(newEdge1);
					newEdge1.getFaces().add(this);
					edges.add(newEdge2);
					newEdge2.getFaces().add(this);
					midpoint.getFaces().add(this);

					vertices.add(j + offset + 1, midpoint);
					stripCounts.set(i, stripCount + 1);

					return;
				}
			}
			offset += stripCount;
		}
		throw new IllegalStateException("Edge " + edge + " not found in face "
				+ this);
	}

	@Override
	public IPolygon transform(final ITransformation transformation) {
		final List<IPoint> new_vertices = new ArrayList<IPoint>(vertices.size());

		for (final IPoint point : vertices) {
			new_vertices.add(point.transform(transformation));
		}

		return new Polygon(new_vertices, getPolygonStripCounts(),
				getPolygonContourCounts(), true);
	}

	@Override
	public void readState(final IStateReader reader) throws IOException {
		// TODO Auto-generated method stub

	}

	public void writeState(final IStateWriter writer) throws IOException {
		writer.writeInt("vertexNumber", vertices.size());
		for (final Vertex vertex : vertices) {
			writer.writeInt("vertexIndex", vertex.getIndex());
		}
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer("PolyFace(");
		buffer.append("index=").append(index);
		buffer.append(", vertices=").append(vertices);
		buffer.append(", edges=").append(edges);
		buffer.append(", stripCounts=").append(stripCounts);

		return buffer.toString();
	}
}
