package net.untoldwind.moredread.model.op.bool;

import java.util.ArrayList;
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
		int newDeep = 0;
		int tag = BoolTag.ON;

		// find out if any points on the "face" lie in either half-space
		for (final Vector3f itp : points) {
			tag = (tag | testPoint(itp));
		}

		if (tag == BoolTag.ON) {
			// face lies on hyperplane: do nothing
		} else if ((tag & BoolTag.IN) != 0 && (tag & BoolTag.OUT) == 0) { // face
			// is
			// entirely
			// on
			// inside
			if (inChild != null) {
				newDeep = inChild.addFace(points, plane) + 1;
			} else {
				inChild = new BSPNode(plane);
				newDeep = 2;
			}
		} else if ((tag & BoolTag.OUT) != 0 && (tag & BoolTag.IN) == 0) { // face
			// is
			// entirely
			// on
			// outside
			if (outChild != null) {
				newDeep = outChild.addFace(points, plane) + 1;
			} else {
				outChild = new BSPNode(plane);
				newDeep = 2;
			}
		} else { // face lies in both half-spaces: split it
			final List<Vector3f> inside = new ArrayList<Vector3f>();
			final List<Vector3f> outside = new ArrayList<Vector3f>();
			Vector3f lpoint = points.get(points.size() - 1);
			int ltag = testPoint(lpoint);
			int tstate = ltag;

			// classify each line segment, looking for endpoints which lie on
			// different
			// sides of the hyperplane.

			for (final Vector3f npoint : points) {
				final int ntag = testPoint(npoint);

				if (ltag != BoolTag.ON) { // last point not on hyperplane
					if (tstate == BoolTag.IN) {
						if (inChild != null) {
							inside.add(lpoint);
						}
					} else {
						if (outChild != null) {
							outside.add(lpoint);
						}
					}
					if (ntag != BoolTag.ON && ntag != tstate) { // last, self in
						// different
						// half-spaces
						final Vector3f mpoint = MathUtils.intersectPlane(plane,
								lpoint, npoint);
						if (inChild != null) {
							inside.add(mpoint);
						}
						if (outChild != null) {
							outside.add(mpoint);
						}
						tstate = ntag;
					}
				} else { // last point on hyperplane, so we're switching
					// half-spaces
					// boundary point belong to both faces
					if (inChild != null) {
						inside.add(lpoint);
					}
					if (outChild != null) {
						outside.add(lpoint);
					}
					tstate = ntag; // state changes to new point tag
				}
				lpoint = npoint; // save point, tag for next iteration
				ltag = ntag;
			}

			if (inChild != null) {
				newDeep = inChild.addFace(inside, plane) + 1;
			} else {
				inChild = new BSPNode(plane);
				newDeep = 2;
			}
			if (outChild != null) {
				newDeep = Math.max(newDeep,
						outChild.addFace(outside, plane) + 1);
			} else {
				outChild = new BSPNode(plane);
				newDeep = Math.max(newDeep, 2);
			}
		}

		// update the deep attribute
		deep = Math.max(deep, newDeep);

		return deep;
	}

	int testPoint(final Vector3f p) {
		return BoolTag.createTAG(MathUtils.classify(p, plane));
	}
}
