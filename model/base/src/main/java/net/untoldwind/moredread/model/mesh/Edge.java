package net.untoldwind.moredread.model.mesh;

import java.util.HashSet;
import java.util.Set;

public class Edge implements IEdge {
	private final EdgeGeometry<?> owner;
	private final EdgeId index;
	private final Vertex vertex1;
	private final Vertex vertex2;
	private boolean smooth;

	private final Set<Face<?, ?>> faces;

	Edge(final EdgeGeometry<?> owner, final Vertex vertex1, final Vertex vertex2) {
		this.owner = owner;
		this.index = new EdgeId(vertex1.getIndex(), vertex2.getIndex());
		this.vertex1 = vertex1;
		this.vertex2 = vertex2;
		this.faces = new HashSet<Face<?, ?>>();

		vertex1.getEdges().add(this);
		vertex2.getEdges().add(this);
	}

	public EdgeGeometry<?> getOwner() {
		return owner;
	}

	public EdgeId getIndex() {
		return index;
	}

	public Vertex getVertex1() {
		return vertex1;
	}

	public Vertex getVertex2() {
		return vertex2;
	}

	public boolean isSmooth() {
		return smooth;
	}

	public void setSmooth(final boolean smooth) {
		this.smooth = smooth;
	}

	public Set<Face<?, ?>> getFaces() {
		return faces;
	}

	@Override
	public boolean isConnection(final IVertex vertex1, final IVertex vertex2) {
		return (vertex1.getIndex() == this.vertex1.getIndex() && vertex2
				.getIndex() == this.vertex2.getIndex())
				|| (vertex1.getIndex() == this.vertex2.getIndex() && vertex2
						.getIndex() == this.vertex1.getIndex());

	}

	void remove() {
		if (owner instanceof Mesh) {
			final Set<FaceId> faceIds = new HashSet<FaceId>();
			for (final Face<?, ?> face : faces) {
				faceIds.add(face.getIndex());
			}
			((Mesh<?, ?>) owner).removeFaces(faceIds);
		}
		vertex1.getEdges().remove(this);
		vertex2.getEdges().remove(this);
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

		final Edge castObj = (Edge) obj;

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
