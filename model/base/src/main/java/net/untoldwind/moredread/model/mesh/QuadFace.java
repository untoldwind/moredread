package net.untoldwind.moredread.model.mesh;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.untoldwind.moredread.model.state.IStateReader;
import net.untoldwind.moredread.model.state.IStateWriter;
import net.untoldwind.moredread.model.transform.ITransformation;

import com.jme.math.Vector3f;

public class QuadFace extends Face<QuadFaceId, QuadFace, QuadMesh> {
	private final Vertex<QuadFace>[] vertices;
	private final Edge<QuadFace>[] edges;

	QuadFace(final QuadMesh owner, final QuadFaceId index,
			final Vertex<QuadFace>[] vertices, final Edge<QuadFace>[] edges) {
		super(owner, index);

		this.vertices = vertices;
		this.edges = edges;

		for (final Vertex<QuadFace> vertex : vertices) {
			vertex.getFaces().add(this);
		}

		for (final Edge<QuadFace> edge : edges) {
			edge.getFaces().add(this);
		}
	}

	@Override
	public int getVertexCount() {
		return 4;
	}

	public Vertex<QuadFace> getVertex(final int index) {
		return vertices[index];
	}

	@Override
	public List<Vertex<QuadFace>> getVertices() {
		return Arrays.asList(vertices);
	}

	public Vertex<QuadFace>[] getVertexArray() {
		return vertices;
	}

	@Override
	public IEdge getEdge(final EdgeId edgeIndex) {
		for (final Edge<QuadFace> edge : edges) {
			if (edge.getIndex().equals(edgeIndex)) {
				return edge;
			}
		}
		return null;
	}

	@Override
	public List<Edge<QuadFace>> getEdges() {
		return Arrays.asList(edges);
	}

	@Override
	public int[] getPolygonContourCounts() {
		return new int[] { 1 };
	}

	@Override
	public int[] getPolygonStripCounts() {
		return new int[] { 4 };
	}

	@Override
	public boolean isClosed() {
		return true;
	}

	@Override
	public void updateCenter() {
		center = new Vector3f(0, 0, 0);

		for (final Vertex<QuadFace> vertex : vertices) {
			center.addLocal(vertex.getPoint());
		}

		center.divideLocal(4);
	}

	@Override
	public void updateMeanNormal() {
		final Vector3f v1 = vertices[1].getPoint().subtract(
				vertices[0].getPoint());
		final Vector3f v2 = vertices[2].getPoint().subtract(
				vertices[0].getPoint());
		final Vector3f v3 = vertices[3].getPoint().subtract(
				vertices[0].getPoint());
		meanNormal = v1.cross(v2).addLocal(v1.cross(v3));
		final float len = meanNormal.length();

		if (len < 1e-6) {
			meanNormal.set(0, 0, 1);
		} else {
			meanNormal.divideLocal(len);
		}
	}

	@Override
	void remove() {
		for (final Edge<QuadFace> edge : edges) {
			edge.removeFace(this);
		}
		for (final Vertex<QuadFace> vertex : vertices) {
			vertex.removeFace(this);
		}
	}

	@Override
	public IPolygon transform(final ITransformation transformation) {
		final List<IPoint> new_vertices = new ArrayList<IPoint>(vertices.length);

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

	@Override
	public void writeState(final IStateWriter writer) throws IOException {
		for (final Vertex<QuadFace> vertex : vertices) {
			writer.writeInt("vertexIndex", vertex.getIndex());
		}
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("QuadFace [edges=");
		builder.append(Arrays.toString(edges));
		builder.append(", vertices=");
		builder.append(Arrays.toString(vertices));
		builder.append("]");
		return builder.toString();
	}

}
