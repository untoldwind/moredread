package net.untoldwind.moredread.model.op.bool.bspfilter;

import java.util.ArrayList;
import java.util.List;

import com.jme.math.Plane;
import com.jme.math.Vector3f;

public class BSPNode {
	private final Plane nodePlane;
	BSPNode inChild;
	BSPNode outChild;

	BSPNode(final Plane nodePlane) {
		this.nodePlane = nodePlane;
	}

	public void addTriangle(final Vector3f[] vertices, final Plane plane) {
		final VertexTag[] tags = new VertexTag[vertices.length];

		for (int i = 0; i < tags.length; i++) {
			tags[i] = MathUtils.testPoint(nodePlane, vertices[i]);
		}

		if (VertexTag.allOn(tags)) {
			// Ignore
			return;
		} else if (VertexTag.allIn(tags)) {
			addInChild(vertices, plane);
			return;
		} else if (VertexTag.allOut(tags)) {
			addOutChild(vertices, plane);
			return;
		} else {
			final List<Vector3f> inside = new ArrayList<Vector3f>();
			final List<Vector3f> outside = new ArrayList<Vector3f>();
			Vector3f lpoint = vertices[vertices.length - 1];
			VertexTag ltag = MathUtils.testPoint(nodePlane, lpoint);
			VertexTag tstate = ltag;

			// classify each line segment, looking for endpoints which lie on
			// different
			// sides of the hyperplane.
			for (final Vector3f npoint : vertices) {
				final VertexTag ntag = MathUtils.testPoint(nodePlane, npoint);

				if (ltag != VertexTag.ON) { // last point not on hyperplane
					if (tstate == VertexTag.IN) {
						if (inChild != null) {
							inside.add(lpoint);
						}
					} else {
						if (outChild != null) {
							outside.add(lpoint);
						}
					}
					if (ntag != VertexTag.ON && ntag != tstate) { // last, self
																	// in
						// different
						// half-spaces
						final Vector3f mpoint = MathUtils.intersectLinePlane(
								nodePlane, lpoint, npoint);
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

			addInChild(inside.toArray(new Vector3f[inside.size()]), plane);
			addOutChild(outside.toArray(new Vector3f[outside.size()]), plane);
		}
	}

	public VertexTag testVertex(final Vector3f v) {
		final VertexTag tag = MathUtils.testPoint(nodePlane, v);
		switch (tag) {
		case ON:
			if (inChild != null) {
				return inChild.testVertex(v) == VertexTag.OUT ? VertexTag.OUT
						: VertexTag.ON;
			}
			return VertexTag.ON;
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

	public BooleanTag testTriangle(final Vector3f v1, final Vector3f v2,
			final Vector3f v3) {
		final TriangleTag tag = MathUtils.testTriangle(nodePlane, v1, v2, v3);
		if (MathUtils.planeForTriangle(v1, v2, v3) == null) {
			System.out.println(v1 + " " + v2 + " " + v3);
		}

		Vector3f v4, v5;
		switch (tag) {
		case IN_IN_IN:
		case IN_IN_ON:
		case IN_ON_IN:
		case ON_IN_IN:
		case IN_ON_ON:
		case ON_IN_ON:
		case ON_ON_IN:
			return testTriangleIn(v1, v2, v3);

		case OUT_OUT_OUT:
		case OUT_OUT_ON:
		case OUT_ON_OUT:
		case ON_OUT_OUT:
		case ON_ON_OUT:
		case ON_OUT_ON:
		case OUT_ON_ON:
			return testTriangleOut(v1, v2, v3);

		case ON_ON_ON:
			if (MathUtils.planeForTriangle(v1, v2, v3).getNormal()
					.dot(nodePlane.getNormal()) > 0) {
				return testTriangleIn(v1, v2, v3);
			} else {
				return testTriangleOut(v1, v2, v3);
			}

		case IN_OUT_ON:
			v4 = MathUtils.intersectLinePlane(nodePlane, v1, v2);
			return BooleanTag.combine(testTriangleIn(v1, v4, v3),
					testTriangleOut(v4, v2, v3));

		case OUT_IN_ON:
			v4 = MathUtils.intersectLinePlane(nodePlane, v1, v2);
			return BooleanTag.combine(testTriangleOut(v1, v4, v3),
					testTriangleIn(v4, v2, v3));

		case IN_ON_OUT:
			v4 = MathUtils.intersectLinePlane(nodePlane, v1, v3);
			return BooleanTag.combine(testTriangleIn(v1, v2, v4),
					testTriangleOut(v2, v3, v4));

		case OUT_ON_IN:
			v4 = MathUtils.intersectLinePlane(nodePlane, v1, v3);
			return BooleanTag.combine(testTriangleOut(v1, v2, v4),
					testTriangleIn(v2, v3, v4));

		case ON_IN_OUT:
			v4 = MathUtils.intersectLinePlane(nodePlane, v2, v3);
			return BooleanTag.combine(testTriangleIn(v1, v2, v4),
					testTriangleOut(v4, v3, v1));

		case ON_OUT_IN:
			v4 = MathUtils.intersectLinePlane(nodePlane, v2, v3);
			return BooleanTag.combine(testTriangleOut(v1, v2, v4),
					testTriangleIn(v4, v3, v1));

		case IN_OUT_OUT:
			v4 = MathUtils.intersectLinePlane(nodePlane, v1, v2);
			v5 = MathUtils.intersectLinePlane(nodePlane, v1, v3);

			return BooleanTag.combine(testTriangleIn(v1, v4, v5),
					testTriangleOut(v4, v2, v5), testTriangleOut(v2, v3, v5));

		case OUT_IN_IN:
			v4 = MathUtils.intersectLinePlane(nodePlane, v1, v2);
			v5 = MathUtils.intersectLinePlane(nodePlane, v1, v3);

			return BooleanTag.combine(testTriangleOut(v1, v4, v5),
					testTriangleIn(v4, v2, v5), testTriangleIn(v2, v3, v5));

		case OUT_IN_OUT:
			v4 = MathUtils.intersectLinePlane(nodePlane, v1, v2);
			v5 = MathUtils.intersectLinePlane(nodePlane, v2, v3);
			return BooleanTag.combine(testTriangleIn(v4, v2, v5),
					testTriangleOut(v1, v4, v5), testTriangleOut(v1, v5, v3));

		case IN_OUT_IN:
			v4 = MathUtils.intersectLinePlane(nodePlane, v1, v2);
			v5 = MathUtils.intersectLinePlane(nodePlane, v2, v3);
			return BooleanTag.combine(testTriangleOut(v4, v2, v5),
					testTriangleIn(v1, v4, v5), testTriangleIn(v1, v5, v3));

		case OUT_OUT_IN:
			v4 = MathUtils.intersectLinePlane(nodePlane, v1, v3);
			v5 = MathUtils.intersectLinePlane(nodePlane, v2, v3);
			return BooleanTag.combine(testTriangleIn(v4, v5, v3),
					testTriangleOut(v1, v2, v5), testTriangleOut(v1, v5, v4));

		case IN_IN_OUT:
			v4 = MathUtils.intersectLinePlane(nodePlane, v1, v3);
			v5 = MathUtils.intersectLinePlane(nodePlane, v2, v3);
			return BooleanTag.combine(testTriangleOut(v4, v5, v3),
					testTriangleIn(v1, v2, v5), testTriangleIn(v1, v5, v4));

		default:
			throw new IllegalStateException("Unhandled tag: " + tag);
		}
	}

	private BooleanTag testTriangleIn(final Vector3f v1, final Vector3f v2,
			final Vector3f v3) {
		if (inChild != null) {
			return inChild.testTriangle(v1, v2, v3);
		} else {
			return BooleanTag.IN;
		}
	}

	private BooleanTag testTriangleOut(final Vector3f v1, final Vector3f v2,
			final Vector3f v3) {
		if (outChild != null) {
			return outChild.testTriangle(v1, v2, v3);
		} else {
			return BooleanTag.OUT;
		}
	}

	private void addOutChild(final Vector3f[] vertices, final Plane plane) {
		if (outChild == null) {
			outChild = new BSPNode(plane);
		} else {
			outChild.addTriangle(vertices, plane);
		}
	}

	private void addInChild(final Vector3f[] vertices, final Plane plane) {
		if (inChild == null) {
			inChild = new BSPNode(plane);
		} else {
			inChild.addTriangle(vertices, plane);
		}
	}

}
