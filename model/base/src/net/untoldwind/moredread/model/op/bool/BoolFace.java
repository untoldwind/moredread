package net.untoldwind.moredread.model.op.bool;

import java.util.Arrays;
import java.util.List;

import com.jme.math.Plane;

public class BoolFace {
	Plane plane;
	List<BoolVertex> vertices;
	int split;
	int tag;
	BoolFace originalFace;

	public BoolFace(final BoolVertex v1, final BoolVertex v2,
			final BoolVertex v3, final Plane plane, final BoolFace originalFace) {
		this.vertices = Arrays.asList(v1, v2, v2);
		this.plane = plane;
		this.originalFace = originalFace;
	}

	public BoolVertex getVertex(final int idx) {
		return vertices.get(idx);
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

	public void setTAG(final int tag) {
		this.tag = tag;
	}

	public BoolFace getOriginalFace() {
		return originalFace;
	}

	public void setOriginalFace(final BoolFace originalFace) {
		this.originalFace = originalFace;
	}

}
