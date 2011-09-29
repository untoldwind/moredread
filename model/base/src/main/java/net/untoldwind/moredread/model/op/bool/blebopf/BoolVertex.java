package net.untoldwind.moredread.model.op.bool.blebopf;

import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.mesh.Point;

public class BoolVertex extends Point {
	int index;

	List<BoolEdge> edges;
	int tag;

	public BoolVertex(final Vector3 point, final int index) {
		super(point);
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
