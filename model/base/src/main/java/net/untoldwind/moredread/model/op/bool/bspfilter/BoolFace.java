package net.untoldwind.moredread.model.op.bool.bspfilter;

import net.untoldwind.moredread.model.op.utils.IndexList;

public class BoolFace {
	private final BoolVertex[] vertices;
	private IndexList resultIndices;

	public BoolFace(final BoolVertex[] vertices) {
		this.vertices = vertices;
	}

	public BoolVertex[] getVertices() {
		return vertices;
	}

	public IndexList getResultIndices() {
		return resultIndices;
	}

	public void setResultIndices(final IndexList resultIndices) {
		this.resultIndices = resultIndices;
	}

}
