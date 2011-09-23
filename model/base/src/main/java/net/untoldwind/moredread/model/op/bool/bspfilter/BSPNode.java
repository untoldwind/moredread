package net.untoldwind.moredread.model.op.bool.bspfilter;

import java.util.ArrayList;
import java.util.List;

import com.jme.math.Plane;

public class BSPNode {
	private final Plane nodePlane;
	BSPNode inChild;
	BSPNode outChild;

	BSPNode(final Plane nodePlane) {
		this.nodePlane = nodePlane;
	}

	public void addFace(final BoolVertex[] vertices, final Plane plane) {
		final VertexTag[] tags = new VertexTag[vertices.length];

		for (int i = 0; i < tags.length; i++) {
			tags[i] = MathUtils.testVertex(nodePlane, vertices[i]);
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
			final List<BoolVertex> inside = new ArrayList<BoolVertex>();
			final List<BoolVertex> outside = new ArrayList<BoolVertex>();
			BoolVertex lpoint = vertices[vertices.length - 1];
			VertexTag ltag = MathUtils.testVertex(nodePlane, lpoint);
			VertexTag tstate = ltag;

			// classify each line segment, looking for endpoints which lie on
			// different
			// sides of the hyperplane.
			for (final BoolVertex npoint : vertices) {
				final VertexTag ntag = MathUtils.testVertex(nodePlane, npoint);

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
						final BoolVertex mpoint = MathUtils.intersectLinePlane(
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

			addInChild(inside.toArray(new BoolVertex[inside.size()]), plane);
			addOutChild(outside.toArray(new BoolVertex[outside.size()]), plane);
		}
	}

	public VertexTag testVertex(final BoolVertex v) {
		final VertexTag tag = MathUtils.testVertex(nodePlane, v);
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

	public BooleanTag testFace(final BoolVertex[] vertices, final Plane plane) {
		final VertexTag[] tags = new VertexTag[vertices.length];

		for (int i = 0; i < tags.length; i++) {
			tags[i] = MathUtils.testVertex(nodePlane, vertices[i]);
		}

		if (VertexTag.allOn(tags)) {
			if (plane.getNormal().dot(nodePlane.getNormal()) > 0) {
				return testFaceIn(vertices, plane);
			} else {
				return testFaceOut(vertices, plane);
			}
		} else if (VertexTag.allIn(tags)) {
			return testFaceIn(vertices, plane);
		} else if (VertexTag.allOut(tags)) {
			return testFaceOut(vertices, plane);
		} else {
			final List<BoolVertex> inside = new ArrayList<BoolVertex>();
			final List<BoolVertex> outside = new ArrayList<BoolVertex>();
			BoolVertex lpoint = vertices[vertices.length - 1];
			VertexTag ltag = MathUtils.testVertex(nodePlane, lpoint);
			VertexTag tstate = ltag;

			// classify each line segment, looking for endpoints which lie on
			// different
			// sides of the hyperplane.
			for (final BoolVertex npoint : vertices) {
				final VertexTag ntag = MathUtils.testVertex(nodePlane, npoint);

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
						final BoolVertex mpoint = MathUtils.intersectLinePlane(
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

			return BooleanTag.combine(
					testFaceIn(inside.toArray(new BoolVertex[inside.size()]),
							plane),
					testFaceOut(
							outside.toArray(new BoolVertex[outside.size()]),
							plane));
		}
	}

	private BooleanTag testFaceIn(final BoolVertex[] vertices, final Plane plane) {
		if (inChild != null) {
			return inChild.testFace(vertices, plane);
		} else {
			return BooleanTag.IN;
		}
	}

	private BooleanTag testFaceOut(final BoolVertex[] vertices,
			final Plane plane) {
		if (outChild != null) {
			return outChild.testFace(vertices, plane);
		} else {
			return BooleanTag.OUT;
		}
	}

	private void addOutChild(final BoolVertex[] vertices, final Plane plane) {
		if (outChild == null) {
			outChild = new BSPNode(plane);
		} else {
			outChild.addFace(vertices, plane);
		}
	}

	private void addInChild(final BoolVertex[] vertices, final Plane plane) {
		if (inChild == null) {
			inChild = new BSPNode(plane);
		} else {
			inChild.addFace(vertices, plane);
		}
	}

	public void dump(final String prefix) {
		System.out.println(prefix + nodePlane);
		if (inChild != null) {
			System.out.println(prefix + "In");
			inChild.dump(prefix + "   ");
		}
		if (outChild != null) {
			System.out.println(prefix + "Out");
			outChild.dump(prefix + "   ");
		}
	}
}
