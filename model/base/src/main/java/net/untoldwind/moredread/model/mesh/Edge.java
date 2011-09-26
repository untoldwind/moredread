package net.untoldwind.moredread.model.mesh;

import java.util.HashSet;
import java.util.Set;

public class Edge<FaceT extends Face<?, ?, ?>> implements IEdge {
	private final Mesh<?, ?> ownerMesh;
	private final Polygon ownerPolygon;
	private final EdgeId index;
	private final Vertex<FaceT> vertex1;
	private final Vertex<FaceT> vertex2;
	private boolean smooth;

	private final Set<FaceT> faces;

	Edge(final Mesh<?, ?> owner, final Vertex<FaceT> vertex1,
			final Vertex<FaceT> vertex2) {
		this.ownerMesh = owner;
		this.ownerPolygon = null;
		this.index = new EdgeId(vertex1.getIndex(), vertex2.getIndex());
		this.vertex1 = vertex1;
		this.vertex2 = vertex2;
		this.faces = new HashSet<FaceT>();

		vertex1.getEdges().add(this);
		vertex2.getEdges().add(this);
	}

	Edge(final Polygon owner, final Vertex<FaceT> vertex1,
			final Vertex<FaceT> vertex2) {
		this.ownerMesh = null;
		this.ownerPolygon = owner;
		this.index = new EdgeId(vertex1.getIndex(), vertex2.getIndex());
		this.vertex1 = vertex1;
		this.vertex2 = vertex2;
		this.faces = new HashSet<FaceT>();

		vertex1.getEdges().add(this);
		vertex2.getEdges().add(this);
	}

	public Mesh<?, ?> getOwnerMesh() {
		return ownerMesh;
	}

	public Polygon getOwnerPolygon() {
		return ownerPolygon;
	}

	public EdgeId getIndex() {
		return index;
	}

	public Vertex<FaceT> getVertex1() {
		return vertex1;
	}

	public Vertex<FaceT> getVertex2() {
		return vertex2;
	}

	public boolean isSmooth() {
		return smooth;
	}

	public void setSmooth(final boolean smooth) {
		this.smooth = smooth;
	}

	public Set<FaceT> getFaces() {
		return faces;
	}

	void removeFace(final FaceT face) {
		faces.remove(face);
	}

	void remove() {
		if (ownerMesh != null) {
			final Set<FaceId> faceIds = new HashSet<FaceId>();
			for (final FaceT face : faces) {
				faceIds.add(face.getIndex());
			}
			ownerMesh.removeFaces(faceIds);
		}
		vertex1.removeEdge(this);
		vertex2.removeEdge(this);
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

		final Edge<?> castObj = (Edge<?>) obj;

		return index.equals(castObj.index) && smooth == castObj.smooth
				&& vertex1.equals(castObj.vertex1)
				&& vertex2.equals(castObj.vertex2);
	}

	@Override
	public int hashCode() {
		return index.hashCode();
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer("Edge(");
		buffer.append("index=").append(index);
		buffer.append(", smooth=").append(smooth);
		buffer.append(", vertex1=").append(vertex1);
		buffer.append(", vertex2=").append(vertex2);

		return buffer.toString();
	}
}
