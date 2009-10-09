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

	public void addEdges(final BoolEdge edge) {
		edges.add(edge);
	}
}
