package net.untoldwind.moredread.model.op.bool;

import java.util.List;

import com.jme.math.Plane;
import com.jme.math.Vector3f;

public class BSPNode {
	BSPNode inChild;
	BSPNode outChild;
	Plane plane;
	int deep;

	BSPNode(final Plane plane) {
		this.plane = plane;
	}

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

	/**
	 * Adds a new face to this BSP tree.
	 * 
	 * @param points
	 *            vector containing face points
	 * @param plane
	 *            face plane.
	 */
	int addFace(final List<Vector3f> points, final Plane plane) {
		final int newDeep = 0;
		int tag = BoolTag.ON;

		// find out if any points on the "face" lie in either half-space
		for (final Vector3f itp : points) {
			tag = (tag | testPoint(itp));
		}

		if (tag == BoolTag.ON) {
			// face lies on hyperplane: do nothing
		}

		return 0;
	}

	int testPoint(final Vector3f p) {
		return BoolTag.createTAG(MathUtils.classify(p, plane));

	}
}
