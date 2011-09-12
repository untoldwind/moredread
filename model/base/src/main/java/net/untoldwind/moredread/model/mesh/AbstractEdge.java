package net.untoldwind.moredread.model.mesh;

import java.util.HashSet;
import java.util.Set;

public class AbstractEdge implements IEdge {
	private final Mesh<?> ownerMesh;
	private final Polygon ownerPolygon;
	private final EdgeId index;
	private final Vertex vertex1;
	private final Vertex vertex2;
	private boolean smooth;
	private final Set<AbstractFace<?>> faces;

	AbstractEdge(final Mesh<?> owner, final Vertex vertex1, final Vertex vertex2) {
		this.ownerMesh = owner;
		this.ownerPolygon = null;
		this.index = new EdgeId(vertex1.getIndex(), vertex2.getIndex());
		this.vertex1 = vertex1;
		this.vertex2 = vertex2;
		this.faces = new HashSet<AbstractFace<?>>();

		vertex1.getEdges().add(this);
		vertex2.getEdges().add(this);
	}

	AbstractEdge(final Polygon owner, final Vertex vertex1, final Vertex vertex2) {
		this.ownerMesh = null;
		this.ownerPolygon = owner;
		this.index = new EdgeId(vertex1.getIndex(), vertex2.getIndex());
		this.vertex1 = vertex1;
		this.vertex2 = vertex2;
		this.faces = new HashSet<AbstractFace<?>>();

		vertex1.getEdges().add(this);
		vertex2.getEdges().add(this);
	}

	public Mesh<?> getOwnerMesh() {
		return ownerMesh;
	}

	public Polygon getOwnerPolygon() {
		return ownerPolygon;
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

	public Set<AbstractFace<?>> getFaces() {
		return faces;
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

		final AbstractEdge castObj = (AbstractEdge) obj;

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
