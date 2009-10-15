package net.untoldwind.moredread.model.op.bool.blebopd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.enums.GeometryType;
import net.untoldwind.moredread.model.mesh.IPoint;
import net.untoldwind.moredread.model.state.IStateWriter;

import com.jme.math.Vector3f;

public class BoolVertex implements IPoint {
	private final Vector3d point;

	int index;

	List<BoolEdge> edges;
	int tag;

	public BoolVertex(final Vector3d point, final int index) {
		this.point = point;
		this.index = index;

		edges = new ArrayList<BoolEdge>();
		tag = BoolTag.UNCLASSIFIED;
	}

	public int getIndex() {
		return index;
	}

	public void addEdge(final BoolEdge edge) {
		edges.add(edge);
	}

	public void removeEdge(final BoolEdge edge) {
		edges.remove(edge);
	}

	public List<BoolEdge> getEdges() {
		return edges;
	}

	public int getTAG() {
		return tag;
	}

	public void setTAG(final int tag) {
		this.tag = tag;
	}

	public Vector3f getPoint() {
		return point.toVector3f();
	}

	public Vector3d getPoint3d() {
		return point;
	}

	@Override
	public GeometryType getGeometryType() {
		return GeometryType.POINT;
	}

	@Override
	public void writeState(final IStateWriter writer) throws IOException {
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("BoolVertex [index=");
		builder.append(index);
		builder.append(", getPoint()=");
		builder.append(getPoint());
		builder.append(", edges=");
		builder.append(edges);
		builder.append(", tag=");
		builder.append(tag);
		builder.append("]");
		return builder.toString();
	}

}
