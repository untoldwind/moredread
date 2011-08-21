package net.untoldwind.moredread.model.mesh;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import net.untoldwind.moredread.model.enums.GeometryType;
import net.untoldwind.moredread.model.state.IStateHolder;
import net.untoldwind.moredread.model.state.IStateWriter;
import net.untoldwind.moredread.model.transform.ITransformation;

import com.jme.math.Vector3f;

public class Vertex implements IStateHolder, IVertex {
	private final Mesh<?> owner;
	private final int index;
	private Vector3f point;
	private boolean smooth;
	private final Set<AbstractEdge> edges;
	private final Set<AbstractFace<?>> faces;

	Vertex(final Mesh<?> owner, final int index, final Vector3f point) {
		this.owner = owner;
		this.index = index;
		this.point = point;
		this.edges = new HashSet<AbstractEdge>();
		this.faces = new HashSet<AbstractFace<?>>();
	}

	@Override
	public GeometryType getGeometryType() {
		return GeometryType.POINT;
	}

	public Mesh<?> getOwner() {
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

	@Override
	public Vector3f getPoint() {
		return point;
	}

	public void setPoint(final Vector3f point) {
		this.point = point;
		for (final AbstractFace<?> face : faces) {
			face.markDirty();
		}
		owner.markDirty();
	}

	public Set<AbstractEdge> getEdges() {
		return edges;
	}

	public Set<AbstractFace<?>> getFaces() {
		return faces;
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

	public void writeState(final IStateWriter writer) throws IOException {
		writer.writeBoolean("smooth", smooth);
		writer.writeVector3f("point", point);
	}
}