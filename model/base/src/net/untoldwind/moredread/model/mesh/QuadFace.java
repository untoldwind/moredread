package net.untoldwind.moredread.model.mesh;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import net.untoldwind.moredread.model.state.IStateWriter;

import com.jme.math.Vector3f;

public class QuadFace extends Face<QuadMesh> {
	private final Vertex[] vertices;
	private final Edge[] edges;

	QuadFace(final QuadMesh owner, final int index, final Vertex[] vertices,
			final Edge[] edges) {
		super(owner, index);

		this.vertices = vertices;
		this.edges = edges;

		for (final Vertex vertex : vertices) {
			vertex.getFaces().add(this);
		}

		for (final Edge edge : edges) {
			edge.getFaces().add(this);
		}
	}

	@Override
	public List<Vertex> getVertices() {
		return Arrays.asList(vertices);
	}

	public Vertex[] getVertexArray() {
		return vertices;
	}

	public Edge[] getEdges() {
		return edges;
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

		for (final Vertex vertex : vertices) {
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
	public void writeState(final IStateWriter writer) throws IOException {
		for (final Vertex vertex : vertices) {
			writer.writeInt("vertexIndex", vertex.getIndex());
		}
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		final QuadFace castObj = (QuadFace) obj;

		return index == castObj.index
				&& Arrays.equals(vertices, castObj.vertices)
				&& Arrays.equals(edges, castObj.edges);
	}

	@Override
	public int hashCode() {
		return index;
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
