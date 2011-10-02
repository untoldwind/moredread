package net.untoldwind.moredread.model.op.bool.bspfilter;

import net.untoldwind.moredread.model.math.Plane;

public class BoolFace {
	private final Plane plane;
	private final BoolVertex[] vertices;

	public BoolFace(final BoolVertex[] vertices, final Plane plane) {
		this.plane = plane;
		this.vertices = vertices;
	}

	public Plane getPlane() {
		return plane;
	}

	public BoolVertex[] getVertices() {
		return vertices;
	}

}
