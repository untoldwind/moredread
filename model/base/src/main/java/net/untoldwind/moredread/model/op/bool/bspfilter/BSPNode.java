package net.untoldwind.moredread.model.op.bool.bspfilter;

import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.math.Plane;
import net.untoldwind.moredread.model.op.utils.PlaneMap;

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
						inside.add(lpoint);
					} else {
						outside.add(lpoint);
					}
					if (ntag != VertexTag.ON && ntag != tstate) { // last, self
																	// in
						// different
						// half-spaces
						final BoolVertex mpoint = MathUtils.intersectLinePlane(
								nodePlane, lpoint, npoint);
						inside.add(mpoint);
						outside.add(mpoint);
						tstate = ntag;
					}
				} else { // last point on hyperplane, so we're switching
					// half-spaces
					// boundary point belong to both faces
					inside.add(lpoint);
					outside.add(lpoint);
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

	public void testFace(final BoolVertex[] vertices, final Plane plane,
			final PlaneMap<BoolFace> inFaces) {
		final VertexTag[] tags = new VertexTag[vertices.length];

		for (int i = 0; i < tags.length; i++) {
			tags[i] = MathUtils.testVertex(nodePlane, vertices[i]);
		}

		if (VertexTag.allOn(tags)) {
			if (plane.getNormal().dot(nodePlane.getNormal()) > 0) {
				testFaceIn(vertices, plane, inFaces);
			} else {
				testFaceOut(vertices, plane, inFaces);
			}
		} else if (VertexTag.allIn(tags)) {
			testFaceIn(vertices, plane, inFaces);
		} else if (VertexTag.allOut(tags)) {
			testFaceOut(vertices, plane, inFaces);
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
						inside.add(lpoint);
					} else {
						outside.add(lpoint);
					}
					if (ntag != VertexTag.ON && ntag != tstate) { // last, self
																	// in
						// different
						// half-spaces
						final BoolVertex mpoint = MathUtils.intersectLinePlane(
								nodePlane, lpoint, npoint);
						inside.add(mpoint);
						outside.add(mpoint);
						tstate = ntag;
					}
				} else { // last point on hyperplane, so we're switching
					// half-spaces
					// boundary point belong to both faces
					inside.add(lpoint);
					outside.add(lpoint);
					tstate = ntag; // state changes to new point tag
				}
				lpoint = npoint; // save point, tag for next iteration
				ltag = ntag;
			}

			testFaceIn(inside.toArray(new BoolVertex[inside.size()]), plane,
					inFaces);
			testFaceOut(outside.toArray(new BoolVertex[outside.size()]), plane,
					inFaces);
		}
	}

	private void testFaceIn(final BoolVertex[] vertices, final Plane plane,
			final PlaneMap<BoolFace> inFaces) {
		if (inChild != null) {
			inChild.testFace(vertices, plane, inFaces);
		} else {
			inFaces.add(plane, new BoolFace(vertices));
		}
	}

	private void testFaceOut(final BoolVertex[] vertices, final Plane plane,
			final PlaneMap<BoolFace> inFaces) {
		if (outChild != null) {
			outChild.testFace(vertices, plane, inFaces);
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
