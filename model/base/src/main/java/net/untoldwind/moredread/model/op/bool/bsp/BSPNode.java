package net.untoldwind.moredread.model.op.bool.bsp;

import com.jme.math.Plane;
import com.jme.math.Vector3f;

public class BSPNode {
	private final Plane nodePlane;
	BSPNode inChild;
	BSPNode outChild;

	BSPNode(final Plane nodePlane) {
		this.nodePlane = nodePlane;
	}

	public void addTriangle(final Vector3f v1, final Vector3f v2,
			final Vector3f v3, final Plane plane) {
		final TriangleTag tag = MathUtils.testTriangle(nodePlane, v1, v2, v3);

		Vector3f v4, v5;

		switch (tag) {
		case ON_ON_ON:
			// All on => ignore
			return;
		case IN_IN_IN:
		case IN_IN_ON:
		case IN_ON_IN:
		case ON_IN_IN:
		case IN_ON_ON:
		case ON_IN_ON:
		case ON_ON_IN:
			// All in
			addInChild(v1, v2, v3, plane);
			return;
		case OUT_OUT_OUT:
		case OUT_OUT_ON:
		case OUT_ON_OUT:
		case ON_OUT_OUT:
		case OUT_ON_ON:
		case ON_OUT_ON:
		case ON_ON_OUT:
			// All out
			addOutChild(v1, v2, v3, plane);
			return;
		case IN_OUT_ON:
			v4 = MathUtils.intersectLinePlane(nodePlane, v1, v2);
			addInChild(v1, v4, v3, plane);
			addOutChild(v4, v2, v3, plane);
			return;
		case OUT_IN_ON:
			v4 = MathUtils.intersectLinePlane(nodePlane, v1, v2);
			addOutChild(v1, v4, v3, plane);
			addInChild(v4, v2, v3, plane);
			return;
		case IN_ON_OUT:
			v4 = MathUtils.intersectLinePlane(nodePlane, v1, v3);
			addInChild(v1, v2, v4, plane);
			addOutChild(v4, v2, v3, plane);
			return;
		case OUT_ON_IN:
			v4 = MathUtils.intersectLinePlane(nodePlane, v1, v3);
			addOutChild(v1, v2, v4, plane);
			addInChild(v4, v2, v3, plane);
			return;
		case ON_IN_OUT:
			v4 = MathUtils.intersectLinePlane(nodePlane, v2, v3);
			addInChild(v1, v2, v4, plane);
			addOutChild(v1, v4, v3, plane);
			return;
		case ON_OUT_IN:
			v4 = MathUtils.intersectLinePlane(nodePlane, v2, v3);
			addOutChild(v1, v2, v4, plane);
			addInChild(v1, v4, v3, plane);
			return;
		case IN_OUT_OUT:
			v4 = MathUtils.intersectLinePlane(nodePlane, v1, v2);
			v5 = MathUtils.intersectLinePlane(nodePlane, v1, v3);
			addInChild(v1, v4, v5, plane);
			addOutChild(v4, v2, v3, plane);
			addOutChild(v4, v3, v5, plane);
			return;
		case OUT_IN_IN:
			v4 = MathUtils.intersectLinePlane(nodePlane, v1, v2);
			v5 = MathUtils.intersectLinePlane(nodePlane, v1, v3);
			addOutChild(v1, v4, v5, plane);
			addInChild(v4, v2, v3, plane);
			addInChild(v4, v3, v5, plane);
			return;
		case OUT_IN_OUT:
			v4 = MathUtils.intersectLinePlane(nodePlane, v2, v1);
			v5 = MathUtils.intersectLinePlane(nodePlane, v2, v3);
			addInChild(v4, v2, v5, plane);
			addOutChild(v1, v4, v3, plane);
			addOutChild(v4, v5, v3, plane);
			return;
		case IN_OUT_IN:
			v4 = MathUtils.intersectLinePlane(nodePlane, v2, v1);
			v5 = MathUtils.intersectLinePlane(nodePlane, v2, v3);
			addOutChild(v4, v2, v5, plane);
			addInChild(v1, v4, v3, plane);
			addInChild(v4, v5, v3, plane);
			return;
		case OUT_OUT_IN:
			v4 = MathUtils.intersectLinePlane(nodePlane, v2, v3);
			v5 = MathUtils.intersectLinePlane(nodePlane, v1, v3);
			addInChild(v4, v3, v5, plane);
			addOutChild(v1, v2, v4, plane);
			addOutChild(v4, v5, v1, plane);
			return;
		case IN_IN_OUT:
			v4 = MathUtils.intersectLinePlane(nodePlane, v2, v3);
			v5 = MathUtils.intersectLinePlane(nodePlane, v1, v3);
			addOutChild(v4, v3, v5, plane);
			addInChild(v1, v2, v4, plane);
			addInChild(v4, v5, v1, plane);
			return;
		default:
			throw new IllegalStateException("Unhandled tag: " + tag);
		}
	}

	public VertexTag testVertex(final Vector3f v) {
		final VertexTag tag = MathUtils.testPoint(nodePlane, v);
		switch (tag) {
		case ON:
		case IN:
			if (inChild != null) {
				return inChild.testVertex(v);
			}
			return VertexTag.IN;
		case OUT:
			if (outChild != null) {
				return outChild.testVertex(v);
			}
			return VertexTag.OUT;
		default:
			throw new IllegalStateException("Unhandled tag: " + tag);
		}
	}

	private void addOutChild(final Vector3f v1, final Vector3f v2,
			final Vector3f v3, final Plane plane) {
		if (outChild == null) {
			outChild = new BSPNode(plane);
		} else {
			outChild.addTriangle(v1, v2, v3, plane);
		}
	}

	private void addInChild(final Vector3f v1, final Vector3f v2,
			final Vector3f v3, final Plane plane) {
		if (inChild == null) {
			inChild = new BSPNode(plane);
		} else {
			inChild.addTriangle(v1, v2, v3, plane);
		}
	}
}
