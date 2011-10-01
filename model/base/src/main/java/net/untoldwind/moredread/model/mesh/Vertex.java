package net.untoldwind.moredread.model.mesh;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.untoldwind.moredread.model.enums.GeometryType;
import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.state.IStateHolder;
import net.untoldwind.moredread.model.state.IStateReader;
import net.untoldwind.moredread.model.state.IStateWriter;
import net.untoldwind.moredread.model.transform.ITransformation;

public class Vertex implements IStateHolder, IVertex {
	private final VertexGeometry<?> owner;
	private int index;
	private Vector3 point;
	private boolean smooth;
	private final Set<Edge> edges;
	private final Set<Face<?, ?>> faces;
	private Vector3 meanNormal;

	Vertex(final VertexGeometry<?> owner, final int index, final Vector3 point) {
		this.owner = owner;
		this.index = index;
		this.point = point.clone();
		this.edges = new HashSet<Edge>();
		this.faces = new HashSet<Face<?, ?>>();
	}

	@Override
	public GeometryType getGeometryType() {
		return GeometryType.POINT;
	}

	public VertexGeometry<?> getOwner() {
		return owner;
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
	public Vector3 getPoint() {
		return point;
	}

	public void setPoint(final Vector3 point) {
		this.point.set(point);
		for (final Face<?, ?> face : faces) {
			face.markDirty();
		}
		owner.markDirty();
	}

	public Set<Edge> getEdges() {
		return edges;
	}

	public Set<Face<?, ?>> getFaces() {
		return faces;
	}

	@Override
	public Vector3 getMeanNormal() {
		if (meanNormal == null) {
			updateMeanNormal();
		}
		return meanNormal;
	}

	void remove() {
		if (owner instanceof EdgeGeometry<?>) {
			final Set<EdgeId> edgeIds = new HashSet<EdgeId>();
			for (final Edge edge : edges) {
				edgeIds.add(edge.getIndex());
			}
			((EdgeGeometry<?>) owner).removeEdges(edgeIds);
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

	void markDirty() {
		meanNormal = null;
	}

	public void updateMeanNormal() {
		final Vector3 normal = new Vector3(0, 0, 0);

		for (final IFace face : faces) {
			normal.addLocal(face.getMeanNormal());
		}
		final float len = normal.length();

		if (len < 1e-6) {
			normal.set(0, 0, 1);
		} else {
			normal.divideLocal(len);
		}

		this.meanNormal = normal;
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

		final Vertex castObj = (Vertex) obj;

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
		point = reader.readVector3();
	}

	public void writeState(final IStateWriter writer) throws IOException {
		writer.writeBoolean("smooth", smooth);
		writer.writeVector3("point", point);
	}
}
