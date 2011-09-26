package net.untoldwind.moredread.model.mesh;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.untoldwind.moredread.model.enums.GeometryType;
import net.untoldwind.moredread.model.state.IStateHolder;
import net.untoldwind.moredread.model.state.IStateReader;
import net.untoldwind.moredread.model.state.IStateWriter;
import net.untoldwind.moredread.model.transform.ITransformation;

import com.jme.math.Vector3f;

public class Vertex<FaceT extends Face<?, ?, ?>> implements IStateHolder,
		IVertex {
	private final Mesh<?, ?> ownerMesh;
	private final Polygon ownerPolygon;
	private int index;
	private Vector3f point;
	private boolean smooth;
	private final Set<Edge<FaceT>> edges;
	private final Set<FaceT> faces;

	Vertex(final Mesh<?, ?> owner, final int index, final Vector3f point) {
		this.ownerMesh = owner;
		this.ownerPolygon = null;
		this.index = index;
		this.point = point.clone();
		this.edges = new HashSet<Edge<FaceT>>();
		this.faces = new HashSet<FaceT>();
	}

	Vertex(final Polygon owner, final int index, final Vector3f point) {
		this.ownerMesh = null;
		this.ownerPolygon = owner;
		this.index = index;
		this.point = point.clone();
		this.edges = new HashSet<Edge<FaceT>>();
		this.faces = new HashSet<FaceT>();
	}

	@Override
	public GeometryType getGeometryType() {
		return GeometryType.POINT;
	}

	public Mesh<?, ?> getOwnerMesh() {
		return ownerMesh;
	}

	public Polygon getOwnerPolygon() {
		return ownerPolygon;
	}

	public boolean isSmooth() {
		return smooth;
	}

	public void setSmooth(final boolean smooth) {
		this.smooth = smooth;
	}

	public int getIndex() {
		return index;
	}

	void setIndex(final int index) {
		this.index = index;
	}

	@Override
	public Vector3f getPoint() {
		return point;
	}

	public void setPoint(final Vector3f point) {
		this.point.set(point);
		for (final FaceT face : faces) {
			face.markDirty();
		}
		if (ownerMesh != null) {
			ownerMesh.markDirty();
		}
		if (ownerPolygon != null) {
			ownerPolygon.markDirty();
		}
	}

	public Set<Edge<FaceT>> getEdges() {
		return edges;
	}

	public Set<FaceT> getFaces() {
		return faces;
	}

	void remove() {
		if (ownerMesh != null) {
			final Set<EdgeId> edgeIds = new HashSet<EdgeId>();
			for (final Edge<FaceT> edge : edges) {
				edgeIds.add(edge.getIndex());
			}
			ownerMesh.removeEdges(edgeIds);
		}
	}

	@Override
	public List<? extends IVertex> getNeighbours() {
		final List<IVertex> neighbours = new ArrayList<IVertex>();

		for (final IEdge edge : edges) {
			if (edge.getVertex1() != this) {
				neighbours.add(edge.getVertex2());
			} else {
				neighbours.add(edge.getVertex1());
			}
		}
		return neighbours;
	}

	@Override
	public IPoint transform(final ITransformation transformation) {
		return new Point(transformation.transformPoint(point));
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

		final Vertex<?> castObj = (Vertex<?>) obj;

		return index == castObj.index && smooth == castObj.smooth
				&& point.equals(castObj.point);
	}

	@Override
	public int hashCode() {
		return index;
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer("Vertex(");
		buffer.append("index=").append(index);
		buffer.append(", smooth=").append(smooth);
		buffer.append(", point=").append(point);
		buffer.append(")");

		return buffer.toString();
	}

	@Override
	public void readState(final IStateReader reader) throws IOException {
		smooth = reader.readBoolean();
		point = reader.readVector3f();
	}

	public void writeState(final IStateWriter writer) throws IOException {
		writer.writeBoolean("smooth", smooth);
		writer.writeVector3f("point", point);
	}
}
