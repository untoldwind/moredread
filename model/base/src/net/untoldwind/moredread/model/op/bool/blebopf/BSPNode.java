package net.untoldwind.moredread.model.op.bool.blebopf;

import java.io.PrintStream;
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
		} else if ((tag & BoolTag.IN) != 0 && (tag & BoolTag.OUT) == 0) {
			// face is entirely on inside
			if (inChild != null) {
				newDeep = inChild.addFace(points, plane) + 1;
			} else {
				inChild = new BSPNode(plane);
				newDeep = 2;
			}
		} else if ((tag & BoolTag.OUT) != 0 && (tag & BoolTag.IN) == 0) {
			// face is entirely on outside
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

	/**
	 * Classifies a face using its coordinates and plane.
	 * 
	 * @param p1
	 *            first point.
	 * @param p2
	 *            second point.
	 * @param p3
	 *            third point.
	 * @param plane
	 *            face plane.
	 * @return TAG result: IN, OUT or IN&OUT.
	 */
	int classifyFace(final Vector3f p1, final Vector3f p2, final Vector3f p3,
			final Plane plane) {
		// local variables
		Vector3f auxp1, auxp2;
		int auxtag1, auxtag2, auxtag3;

		switch (BoolTag.createTAG(testPoint(p1), testPoint(p2), testPoint(p3))) {
		// Classify the face on the IN side
		case BoolTag.IN_IN_IN:
			return classifyFaceIN(p1, p2, p3, plane);
		case BoolTag.IN_IN_ON:
		case BoolTag.IN_ON_IN:
		case BoolTag.ON_IN_IN:
		case BoolTag.IN_ON_ON:
		case BoolTag.ON_IN_ON:
		case BoolTag.ON_ON_IN:
			return BoolTag.addON(classifyFaceIN(p1, p2, p3, plane));

			// Classify the face on the OUT side
		case BoolTag.OUT_OUT_OUT:
			return classifyFaceOUT(p1, p2, p3, plane);
		case BoolTag.OUT_OUT_ON:
		case BoolTag.OUT_ON_OUT:
		case BoolTag.ON_OUT_OUT:
		case BoolTag.ON_ON_OUT:
		case BoolTag.ON_OUT_ON:
		case BoolTag.OUT_ON_ON:
			return BoolTag.addON(classifyFaceOUT(p1, p2, p3, plane));

			// Classify the ON face depending on it plane normal
		case BoolTag.ON_ON_ON:
			if (hasSameOrientation(plane)) {
				return BoolTag.addON(classifyFaceIN(p1, p2, p3, plane));
			} else {
				return BoolTag.addON(classifyFaceOUT(p1, p2, p3, plane));
			}

			// Classify the face IN/OUT and one vertex ON
			// becouse only one ON, only one way to subdivide the face
		case BoolTag.IN_OUT_ON:
			auxp1 = MathUtils.intersectPlane(plane, p1, p2);
			auxtag1 = classifyFaceIN(p1, auxp1, p3, plane);
			auxtag2 = classifyFaceOUT(auxp1, p2, p3, plane);
			return (BoolTag.compTAG(auxtag1, auxtag2) ? BoolTag.addON(auxtag1)
					: BoolTag.INOUT);

		case BoolTag.OUT_IN_ON:
			auxp1 = MathUtils.intersectPlane(plane, p1, p2);
			auxtag1 = classifyFaceOUT(p1, auxp1, p3, plane);
			auxtag2 = classifyFaceIN(auxp1, p2, p3, plane);
			return (BoolTag.compTAG(auxtag1, auxtag2) ? BoolTag.addON(auxtag1)
					: BoolTag.INOUT);

		case BoolTag.IN_ON_OUT:
			auxp1 = MathUtils.intersectPlane(plane, p1, p3);
			auxtag1 = classifyFaceIN(p1, p2, auxp1, plane);
			auxtag2 = classifyFaceOUT(p2, p3, auxp1, plane);
			return (BoolTag.compTAG(auxtag1, auxtag2) ? BoolTag.addON(auxtag1)
					: BoolTag.INOUT);

		case BoolTag.OUT_ON_IN:
			auxp1 = MathUtils.intersectPlane(plane, p1, p3);
			auxtag1 = classifyFaceOUT(p1, p2, auxp1, plane);
			auxtag2 = classifyFaceIN(p2, p3, auxp1, plane);
			return (BoolTag.compTAG(auxtag1, auxtag2) ? BoolTag.addON(auxtag1)
					: BoolTag.INOUT);

		case BoolTag.ON_IN_OUT:
			auxp1 = MathUtils.intersectPlane(plane, p2, p3);
			auxtag1 = classifyFaceIN(p1, p2, auxp1, plane);
			auxtag2 = classifyFaceOUT(auxp1, p3, p1, plane);
			return (BoolTag.compTAG(auxtag1, auxtag2) ? BoolTag.addON(auxtag1)
					: BoolTag.INOUT);

		case BoolTag.ON_OUT_IN:
			auxp1 = MathUtils.intersectPlane(plane, p2, p3);
			auxtag1 = classifyFaceOUT(p1, p2, auxp1, plane);
			auxtag2 = classifyFaceIN(auxp1, p3, p1, plane);
			return (BoolTag.compTAG(auxtag1, auxtag2) ? BoolTag.addON(auxtag1)
					: BoolTag.INOUT);

			// Classify IN/OUT face without ON vertices.
			// Two ways to divide the triangle,
			// will chose the least degenerated sub-triangles.
		case BoolTag.IN_OUT_OUT:
			auxp1 = MathUtils.intersectPlane(plane, p1, p2);
			auxp2 = MathUtils.intersectPlane(plane, p1, p3);

			// f1: p1 auxp1 , auxp1 auxp2
			auxtag1 = classifyFaceIN(p1, auxp1, auxp2, plane);

			// f2: auxp1 p2 , p2 auxp2; f3: p2 p3 , p3 auxp2 ||
			// f2: auxp1 p3, p3 auxp2; f3: p2 p3 , p3 auxp1
			if (MathUtils.isInsideCircle(p2, p3, auxp1, auxp2)) {
				auxtag2 = classifyFaceOUT(auxp1, p2, auxp2, plane);
				auxtag3 = classifyFaceOUT(p2, p3, auxp2, plane);
			} else {
				auxtag2 = classifyFaceOUT(auxp1, p3, auxp2, plane);
				auxtag3 = classifyFaceOUT(p2, p3, auxp1, plane);
			}
			return (BoolTag.compTAG(auxtag1, auxtag2)
					&& BoolTag.compTAG(auxtag2, auxtag3) ? auxtag1
					: BoolTag.INOUT);

		case BoolTag.OUT_IN_IN:
			auxp1 = MathUtils.intersectPlane(plane, p1, p2);
			auxp2 = MathUtils.intersectPlane(plane, p1, p3);

			// f1: p1 auxp1 , auxp1 auxp2
			auxtag1 = classifyFaceOUT(p1, auxp1, auxp2, plane);

			// f2: auxp1 p2 , p2 auxp2; f3: p2 p3 , p3 auxp2 ||
			// f2: auxp1 p3, p3 auxp2; f3: p2 p3 , p3 auxp1
			if (MathUtils.isInsideCircle(p2, p3, auxp1, auxp2)) {
				auxtag2 = classifyFaceIN(auxp1, p2, auxp2, plane);
				auxtag3 = classifyFaceIN(p2, p3, auxp2, plane);
			} else {
				auxtag2 = classifyFaceIN(auxp1, p3, auxp2, plane);
				auxtag3 = classifyFaceIN(p2, p3, auxp1, plane);
			}
			return (BoolTag.compTAG(auxtag1, auxtag2)
					&& BoolTag.compTAG(auxtag2, auxtag3) ? auxtag1
					: BoolTag.INOUT);

		case BoolTag.OUT_IN_OUT:
			auxp1 = MathUtils.intersectPlane(plane, p2, p1);
			auxp2 = MathUtils.intersectPlane(plane, p2, p3);

			// f1: auxp1 p2 , p2 auxp2
			auxtag1 = classifyFaceIN(auxp1, p2, auxp2, plane);

			// f2: p1 auxp1 , auxp1 auxp2; f3: p1 auxp2 , auxp2 p3 ||
			// f2: p3 auxp1, auxp1 auxp2 f3:p1 auxp1, auxp1 p3
			if (MathUtils.isInsideCircle(p1, p3, auxp1, auxp2)) {
				auxtag2 = classifyFaceOUT(p1, auxp1, auxp2, plane);
				auxtag3 = classifyFaceOUT(p1, auxp2, p3, plane);
			} else {
				auxtag2 = classifyFaceOUT(p3, auxp1, auxp2, plane);
				auxtag3 = classifyFaceOUT(p1, auxp1, p3, plane);
			}
			return (BoolTag.compTAG(auxtag1, auxtag2)
					&& BoolTag.compTAG(auxtag2, auxtag3) ? auxtag1
					: BoolTag.INOUT);

		case BoolTag.IN_OUT_IN:
			auxp1 = MathUtils.intersectPlane(plane, p2, p1);
			auxp2 = MathUtils.intersectPlane(plane, p2, p3);

			// f1: auxp1 p2 , p2 auxp2
			auxtag1 = classifyFaceOUT(auxp1, p2, auxp2, plane);

			// f2: p1 auxp1 , auxp1 auxp2; f3: p1 auxp2 , auxp2 p3 ||
			// f2: p3 auxp1, auxp1 auxp2 f3:p1 auxp1, auxp1 p3
			if (MathUtils.isInsideCircle(p1, p3, auxp1, auxp2)) {
				auxtag2 = classifyFaceIN(p1, auxp1, auxp2, plane);
				auxtag3 = classifyFaceIN(p1, auxp2, p3, plane);
			} else {
				auxtag2 = classifyFaceIN(p3, auxp1, auxp2, plane);
				auxtag3 = classifyFaceIN(p1, auxp1, p3, plane);
			}
			return (BoolTag.compTAG(auxtag1, auxtag2)
					&& BoolTag.compTAG(auxtag2, auxtag3) ? auxtag1
					: BoolTag.INOUT);

		case BoolTag.OUT_OUT_IN:
			auxp1 = MathUtils.intersectPlane(plane, p3, p1);
			auxp2 = MathUtils.intersectPlane(plane, p3, p2);

			// f1: auxp1 auxp2 , auxp2 p3
			auxtag1 = classifyFaceIN(auxp1, auxp2, p3, plane);

			// f2: p1 p2 , p2 auxp2; f3:p1 auxp2 , auxp2 auxp1 ||
			// f2: p1 p2, p2 auxp1; f3:p2 auxp2, auxp2 auxp1
			if (MathUtils.isInsideCircle(p1, p2, auxp1, auxp2)) {
				auxtag2 = classifyFaceOUT(p1, p2, auxp2, plane);
				auxtag3 = classifyFaceOUT(p1, auxp2, auxp1, plane);
			} else {
				auxtag2 = classifyFaceOUT(p1, p2, auxp1, plane);
				auxtag3 = classifyFaceOUT(p2, auxp2, auxp1, plane);
			}
			return (BoolTag.compTAG(auxtag1, auxtag2)
					&& BoolTag.compTAG(auxtag2, auxtag3) ? auxtag1
					: BoolTag.INOUT);

		case BoolTag.IN_IN_OUT:
			auxp1 = MathUtils.intersectPlane(plane, p3, p1);
			auxp2 = MathUtils.intersectPlane(plane, p3, p2);

			// f1: auxp1 auxp2 , auxp2 p3
			auxtag1 = classifyFaceOUT(auxp1, auxp2, p3, plane);

			// f2: p1 p2 , p2 auxp2; f3:p1 auxp2 , auxp2 auxp1 ||
			// f2: p1 p2, p2 auxp1; f3:p2 auxp2, auxp2 auxp1
			if (MathUtils.isInsideCircle(p1, p2, auxp1, auxp2)) {
				auxtag2 = classifyFaceIN(p1, p2, auxp2, plane);
				auxtag3 = classifyFaceIN(p1, auxp2, auxp1, plane);
			} else {
				auxtag2 = classifyFaceIN(p1, p2, auxp1, plane);
				auxtag3 = classifyFaceIN(p2, auxp2, auxp1, plane);
			}
			return (BoolTag.compTAG(auxtag1, auxtag2)
					&& BoolTag.compTAG(auxtag2, auxtag3) ? auxtag1
					: BoolTag.INOUT);

		default:
			return BoolTag.UNCLASSIFIED;
		}
	}

	/**
	 * Classifies a face through IN subtree.
	 * 
	 * @param p1
	 *            firts face vertex.
	 * @param p2
	 *            second face vertex.
	 * @param p3
	 *            third face vertex.
	 * @param plane
	 *            face plane.
	 */
	int classifyFaceIN(final Vector3f p1, final Vector3f p2, final Vector3f p3,
			final Plane plane) {
		if (inChild != null) {
			return inChild.classifyFace(p1, p2, p3, plane);
		} else {
			return BoolTag.IN;
		}
	}

	/**
	 * Classifies a face through OUT subtree.
	 * 
	 * @param p1
	 *            firts face vertex.
	 * @param p2
	 *            second face vertex.
	 * @param p3
	 *            third face vertex.
	 * @param plane
	 *            face plane.
	 */
	int classifyFaceOUT(final Vector3f p1, final Vector3f p2,
			final Vector3f p3, final Plane plane) {
		if (outChild != null) {
			return outChild.classifyFace(p1, p2, p3, plane);
		} else {
			return BoolTag.OUT;
		}
	}

	/**
	 * Determine if the input plane have the same orientation of the node plane.
	 * 
	 * @param plane
	 *            plane to test.
	 * @return TRUE if have the same orientation, FALSE otherwise.
	 */
	boolean hasSameOrientation(final Plane otherPlane) {
		return (plane.getNormal().dot(otherPlane.getNormal()) > 0);
	}

	/**
	 * Simplified classification (optimized but requires that the face is not
	 * INOUT; only works correctly with faces completely IN or OUT).
	 * 
	 * @param p1
	 *            firts face vertex.
	 * @param p2
	 *            second face vertex.
	 * @param p3
	 *            third face vertex.
	 * @param plane
	 *            face plane.
	 * @return TAG result: IN or OUT.
	 */
	int simplifiedClassifyFace(final Vector3f p1, final Vector3f p2,
			final Vector3f p3, final Plane plane) {
		final Vector3f ret[] = new Vector3f[3];

		final int tag = BoolTag.createTAG(testPoint(p1), testPoint(p2),
				testPoint(p3));

		if ((tag & BoolTag.IN_IN_IN) != 0) {
			if ((tag & BoolTag.OUT_OUT_OUT) != 0) {
				if (splitTriangle(ret, plane, p1, p2, p3, tag) < 0) {
					return simplifiedClassifyFaceIN(ret[0], ret[1], ret[2],
							plane);
				} else {
					return simplifiedClassifyFaceOUT(ret[0], ret[1], ret[2],
							plane);
				}
			} else {
				return simplifiedClassifyFaceIN(p1, p2, p3, plane);
			}
		} else {
			if ((tag & BoolTag.OUT_OUT_OUT) != 0) {
				return simplifiedClassifyFaceOUT(p1, p2, p3, plane);
			} else {
				if (hasSameOrientation(plane)) {
					return simplifiedClassifyFaceIN(p1, p2, p3, plane);
				} else {
					return simplifiedClassifyFaceOUT(p1, p2, p3, plane);
				}
			}
		}
	}

	/**
	 * Simplified classify through IN subtree.
	 * 
	 * @param p1
	 *            firts face vertex.
	 * @param p2
	 *            second face vertex.
	 * @param p3
	 *            third face vertex.
	 * @param plane
	 *            face plane.
	 */
	int simplifiedClassifyFaceIN(final Vector3f p1, final Vector3f p2,
			final Vector3f p3, final Plane plane) {
		if (inChild != null) {
			return inChild.simplifiedClassifyFace(p1, p2, p3, plane);
		} else {
			return BoolTag.IN;
		}
	}

	/**
	 * Simplified classify through OUT subtree.
	 * 
	 * @param p1
	 *            firts face vertex.
	 * @param p2
	 *            second face vertex.
	 * @param p3
	 *            third face vertex.
	 * @param plane
	 *            face plane.
	 */
	int simplifiedClassifyFaceOUT(final Vector3f p1, final Vector3f p2,
			final Vector3f p3, final Plane plane) {
		if (outChild != null) {
			return outChild.simplifiedClassifyFace(p1, p2, p3, plane);
		} else {
			return BoolTag.OUT;
		}
	}

	/**
	 * Comparation between both childrens.
	 * 
	 * @return 0 equal deep, 1 inChild more deep than outChild and -1 otherwise.
	 */
	int compChildren() {
		final int deep1 = (inChild == null ? 0 : inChild.getDeep());
		final int deep2 = (outChild == null ? 0 : outChild.getDeep());

		if (deep1 == deep2) {
			return 0;
		} else if (deep1 < deep2) {
			return -1;
		} else {
			return 1;
		}
	}

	/**
	 * Extract a subtriangle from input triangle, is used for simplified
	 * classification. The subtriangle is obtained spliting the input triangle
	 * by input plane.
	 * 
	 * @param res
	 *            output subtriangle result.
	 * @param plane
	 *            spliter plane.
	 * @param p1
	 *            first triangle point.
	 * @param p2
	 *            second triangle point.
	 * @param p3
	 *            third triangle point.
	 * @param tag
	 *            triangle orientation respect the plane.
	 */
	int splitTriangle(final Vector3f[] res, final Plane plane,
			final Vector3f p1, final Vector3f p2, final Vector3f p3,
			final int tag) {
		switch (tag) {
		case BoolTag.IN_OUT_ON:
			if (compChildren() < 0) {
				// f1: p1 new p3 || new = splitedge(p1,p2)
				res[0] = p1;
				res[1] = MathUtils.intersectPlane(plane, p1, p2);
				res[2] = p3;
				return -1;
			} else {
				// f1: p2 new p3 || new = splitedge(p1,p2)
				res[0] = p2;
				res[1] = p3;
				res[2] = MathUtils.intersectPlane(plane, p1, p2);
				return 1;
			}
		case BoolTag.OUT_IN_ON:
			if (compChildren() < 0) {
				// f1: p2 new p3 || new = splitedge(p1,p2)
				res[0] = p2;
				res[1] = p3;
				res[2] = MathUtils.intersectPlane(plane, p1, p2);
				return -1;
			} else {
				// f1: p1 new p3 || new = splitedge(p1,p2)
				res[0] = p1;
				res[1] = MathUtils.intersectPlane(plane, p1, p2);
				res[2] = p3;
				return 1;
			}
		case BoolTag.IN_ON_OUT:
			if (compChildren() < 0) {
				// f1: p1 p2 new || new = splitedge(p1,p3)
				res[0] = p1;
				res[1] = p2;
				res[2] = MathUtils.intersectPlane(plane, p1, p3);
				return -1;
			} else {
				// f1: p2 p3 new || new = splitedge(p1,p3)
				res[0] = p2;
				res[1] = p3;
				res[2] = MathUtils.intersectPlane(plane, p1, p3);
				return 1;
			}
		case BoolTag.OUT_ON_IN:
			if (compChildren() < 0) {
				// f1: p2 p3 new || new = splitedge(p1,p3)
				res[0] = p2;
				res[1] = p3;
				res[2] = MathUtils.intersectPlane(plane, p1, p3);
				return -1;
			} else {
				// f1: p1 p2 new || new = splitedge(p1,p3)
				res[0] = p1;
				res[1] = p2;
				res[2] = MathUtils.intersectPlane(plane, p1, p3);
				return 1;
			}
		case BoolTag.ON_IN_OUT:
			if (compChildren() < 0) {
				// f1: p1 p2 new || new = splitedge(p2,p3)
				res[0] = p1;
				res[1] = p2;
				res[2] = MathUtils.intersectPlane(plane, p2, p3);
				return -1;
			} else {
				// f1: p1 p3 new || new = splitedge(p2,p3)
				res[0] = p1;
				res[1] = MathUtils.intersectPlane(plane, p2, p3);
				res[2] = p3;
				return 1;
			}
		case BoolTag.ON_OUT_IN:
			if (compChildren() < 0) {
				// f1: p1 p2 new || new = splitedge(p2,p3)
				res[0] = p1;
				res[1] = MathUtils.intersectPlane(plane, p2, p3);
				res[2] = p3;
				return -1;
			} else {
				// f1: p1 p2 new || new = splitedge(p2,p3)
				res[0] = p1;
				res[1] = p2;
				res[2] = MathUtils.intersectPlane(plane, p2, p3);
				return 1;
			}
		case BoolTag.IN_OUT_OUT:
			if (compChildren() <= 0) {
				// f1: p1 new1 new2 || new1 = splitedge(p1,p2) new2 =
				// splitedge(p1,p3)
				res[0] = p1;
				res[1] = MathUtils.intersectPlane(plane, p1, p2);
				res[2] = MathUtils.intersectPlane(plane, p1, p3);
				return -1;
			} else {
				// f1: p1 new1 new2 || new1 = splitedge(p1,p2) new2 =
				// splitedge(p1,p3)
				res[0] = MathUtils.intersectPlane(plane, p1, p2);
				res[1] = p2;
				res[2] = p3;
				return 1;
			}
		case BoolTag.OUT_IN_IN:
			if (compChildren() < 0) {
				// f1: p1 new1 new2 || new1 = splitedge(p1,p2) new2 =
				// splitedge(p1,p3)
				res[0] = MathUtils.intersectPlane(plane, p1, p2);
				res[1] = p2;
				res[2] = p3;
				return -1;
			} else {
				// f1: p1 new1 new2 || new1 = splitedge(p1,p2) new2 =
				// splitedge(p1,p3)
				res[0] = p1;
				res[1] = MathUtils.intersectPlane(plane, p1, p2);
				res[2] = MathUtils.intersectPlane(plane, p1, p3);
				return 1;
			}
		case BoolTag.OUT_IN_OUT:
			if (compChildren() <= 0) {
				// f1: new1 p2 new2 || new1 = splitedge(p2,p1) new2 =
				// splitedge(p2,p3)
				res[0] = MathUtils.intersectPlane(plane, p2, p1);
				res[1] = p2;
				res[2] = MathUtils.intersectPlane(plane, p2, p3);
				return -1;
			} else {
				// f1: new1 p2 new2 || new1 = splitedge(p2,p1) new2 =
				// splitedge(p2,p3)
				res[0] = p1;
				res[1] = MathUtils.intersectPlane(plane, p2, p1);
				res[2] = MathUtils.intersectPlane(plane, p2, p3);
				return 1;
			}
		case BoolTag.IN_OUT_IN:
			if (compChildren() < 0) {
				// f1: new1 p2 new2 || new1 = splitedge(p2,p1) new2 =
				// splitedge(p2,p3)
				res[0] = p1;
				res[1] = MathUtils.intersectPlane(plane, p2, p1);
				res[2] = MathUtils.intersectPlane(plane, p2, p3);
				return -1;
			} else {
				// f1: new1 p2 new2 || new1 = splitedge(p2,p1) new2 =
				// splitedge(p2,p3)
				res[0] = MathUtils.intersectPlane(plane, p2, p1);
				res[1] = p2;
				res[2] = MathUtils.intersectPlane(plane, p2, p3);
				return 1;
			}
		case BoolTag.OUT_OUT_IN:
			if (compChildren() <= 0) {
				// f1: new1 new2 p2 || new1 = splitedge(p3,p1) new2 =
				// splitedge(p3,p2)
				res[0] = MathUtils.intersectPlane(plane, p3, p1);
				res[1] = MathUtils.intersectPlane(plane, p3, p2);
				res[2] = p3;
				return -1;
			} else {
				// f1: new1 new2 p2 || new1 = splitedge(p3,p1) new2 =
				// splitedge(p3,p2)
				res[0] = MathUtils.intersectPlane(plane, p3, p1);
				res[1] = p1;
				res[2] = p2;
				return 1;
			}
		case BoolTag.IN_IN_OUT:
			if (compChildren() < 0) {
				// f1: new1 new2 p2 || new1 = splitedge(p3,p1) new2 =
				// splitedge(p3,p2)
				res[0] = MathUtils.intersectPlane(plane, p3, p1);
				res[1] = p1;
				res[2] = p2;
				return -1;
			} else {
				// f1: new1 new2 p2 || new1 = splitedge(p3,p1) new2 =
				// splitedge(p3,p2)
				res[0] = MathUtils.intersectPlane(plane, p3, p1);
				res[1] = MathUtils.intersectPlane(plane, p3, p2);
				res[2] = p3;
				return 1;
			}
		default:
			return 0;
		}
	}

	public void dumpNode(final String ident, final PrintStream out) {
		out.print(ident);
		out.print(deep);
		out.print(" ");
		out.println(plane);
		if (inChild != null) {
			out.print(ident);
			out.println("IN");
			inChild.dumpNode(ident + "  ", out);
		}
		if (outChild != null) {
			out.print(ident);
			out.println("OUT");
			outChild.dumpNode(ident + "  ", out);
		}
	}
}
