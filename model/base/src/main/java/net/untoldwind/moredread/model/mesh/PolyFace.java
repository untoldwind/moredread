package net.untoldwind.moredread.model.mesh;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.state.IStateReader;
import net.untoldwind.moredread.model.state.IStateWriter;
import net.untoldwind.moredread.model.transform.ITransformation;

import com.jme.math.Vector3f;

public class PolyFace extends Face<PolyFaceId, PolyFace, PolyMesh> {
	private final List<Vertex<PolyFace>> vertices;
	private final List<Edge<PolyFace>> edges;
	private final List<Integer> stripCounts;

	PolyFace(final PolyMesh owner, final PolyFaceId index,
			final List<Vertex<PolyFace>> vertices,
			final List<Edge<PolyFace>> edges) {
		super(owner, index);

		this.vertices = vertices;
		this.edges = edges;
		this.stripCounts = new ArrayList<Integer>();
		this.stripCounts.add(vertices.size());

		for (final Vertex<PolyFace> vertex : vertices) {
			vertex.getFaces().add(this);
		}

		for (final Edge<PolyFace> edge : edges) {
			edge.getFaces().add(this);
		}
	}

	PolyFace(final PolyMesh owner, final PolyFaceId index,
			final Vertex<PolyFace>[][] vertexStrips,
			final List<Edge<PolyFace>> edges) {
		super(owner, index);

		this.edges = edges;

		this.vertices = new ArrayList<Vertex<PolyFace>>();
		this.stripCounts = new ArrayList<Integer>();
		for (final Vertex<PolyFace>[] strip : vertexStrips) {
			this.stripCounts.add(strip.length);
			for (final Vertex<PolyFace> vertex : strip) {
				this.vertices.add(vertex);
			}
		}
		for (final Vertex<PolyFace> vertex : vertices) {
			vertex.getFaces().add(this);
		}

		for (final Edge<PolyFace> edge : edges) {
			edge.getFaces().add(this);
		}
	}

	@Override
	public int getVertexCount() {
		return vertices.size();
	}

	@Override
	public Vertex<PolyFace> getVertex(final int index) {
		return vertices.get(index);
	}

	@Override
	public List<Vertex<PolyFace>> getVertices() {
		return vertices;
	}

	@Override
	public IEdge getEdge(final EdgeId edgeIndex) {
		for (final Edge<PolyFace> edge : edges) {
			if (edge.getIndex().equals(edgeIndex)) {
				return edge;
			}
		}
		return null;
	}

	@Override
	public List<Edge<PolyFace>> getEdges() {
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

		for (final Vertex<PolyFace> vertex : vertices) {
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
		for (final Edge<PolyFace> edge : edges) {
			edge.removeFace(this);
		}
		for (final Vertex<PolyFace> vertex : vertices) {
			vertex.removeFace(this);
		}
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
		for (final Vertex<PolyFace> vertex : vertices) {
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
