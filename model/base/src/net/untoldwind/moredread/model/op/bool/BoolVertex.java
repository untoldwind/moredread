package net.untoldwind.moredread.model.op.bool;

import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.mesh.Point;

import com.jme.math.Vector3f;

public class BoolVertex extends Point {
	int index;

	List<BoolEdge> edges;
	int tag;

	public BoolVertex(final Vector3f point, final int index) {
		super(point);
		this.index = index;

		edges = new ArrayList<BoolEdge>();
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
