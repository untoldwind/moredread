package net.untoldwind.moredread.model.op.bool.bspfilter;

public class BoolFace {
	private final BoolVertex[] vertices;
	private int[] resultIndices;

	public BoolFace(final BoolVertex[] vertices) {
		this.vertices = vertices;
	}

	public BoolVertex[] getVertices() {
		return vertices;
	}

	public int[] getResultIndices() {
		return resultIndices;
	}

	public void setResultIndices(final int[] resultIndices) {
		this.resultIndices = resultIndices;
	}

}
