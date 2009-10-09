package net.untoldwind.moredread.model.op.bool;

import java.util.List;

import net.untoldwind.moredread.model.mesh.Point;

import com.jme.math.Vector3f;

public class BoolVertex extends Point {
	Vector3f point;
	List<BoolEdge> edges;
	int tag;

	public BoolVertex(final Vector3f point) {
		super(point);
	}

}
