package net.untoldwind.moredread.model.op.bool;

import com.jme.math.Plane;

public class BSPNode {
	BSPNode inChild;
	BSPNode outChild;
	Plane plane;
	int deep;

	public Plane getPlane() {
		return plane;
	}

	public void setPlane(final Plane plane) {
		this.plane = plane;
	}

	public void setInChild(final BSPNode inChild) {
		this.inChild = inChild;
	}

	public void setOutChild(final BSPNode outChild) {
		this.outChild = outChild;
	}

	public BSPNode getInChild() {
		return inChild;
	}

	public BSPNode getOutChild() {
		return outChild;
	}

	public int getDeep() {
		return deep;
	}

	boolean isLeaf() {
		return inChild == null && outChild == null;
	}

}
