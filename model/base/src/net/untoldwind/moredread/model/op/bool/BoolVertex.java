package net.untoldwind.moredread.model.op.bool;

import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.mesh.Point;

import com.jme.math.Vector3f;

public class BoolVertex extends Point {
	int index;

	Vector3f point;
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
}
