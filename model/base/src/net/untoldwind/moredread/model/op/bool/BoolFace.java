package net.untoldwind.moredread.model.op.bool;

import java.util.List;

import com.jme.math.Plane;

public abstract class BoolFace {
	Plane plane;
	List<BoolVertex> vertices;
	int split;
	int tag;
	int originalFace;
	List<BoolEdge> edges;

	public BoolVertex getVertex(final int idx) {
		return vertices.get(idx);
	}

	public BoolEdge getEdge(final int idx) {
		return edges.get(idx);
	}

	public List<BoolVertex> getVertices() {
		return vertices;
	}

	public Plane getPlane() {
		return plane;
	}

	public void setSplit(final int split) {
		this.split = split;
	}

	public int getSplit() {
		return split;
	}

	public int size() {
		return vertices.size();
	}

	public int getTAG() {
		return tag;
	}

	public int getOriginalFace() {
		return originalFace;
	}

	public void setOriginalFace(final int originalFace) {
		this.originalFace = originalFace;
	}

}
