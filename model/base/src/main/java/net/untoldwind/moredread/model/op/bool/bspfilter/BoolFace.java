package net.untoldwind.moredread.model.op.bool.bspfilter;

public class BoolFace {
	private final BoolVertex[] vertices;

	public BoolFace(final BoolVertex[] vertices) {
		this.vertices = vertices;
	}

	public BoolVertex[] getVertices() {
		return vertices;
	}

}
