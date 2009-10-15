package net.untoldwind.moredread.model.op.bool.blebopd;


public class MathUtils {
	// private static final boolean VAR_EPSILON = true;
	// private static final double EPSILON = 1e-7f;

	private static final boolean VAR_EPSILON = true;
	private static final double EPSILON = 9.3132257461547852e-10f;

	/**
	 * Helper class to return result tuple of frexp()
	 */
	public static class FRExpResultf {
		/**
		 * normalised mantissa
		 */
		public double mantissa;
		/**
		 * exponent of doubleing point representation
		 */
		public int exponent;
	}

	/**
	 * An implementation of the C standard library frexp() function.
	 * 
	 * @param value
	 *            the number to split
	 * @param result
	 * 
	 * @return a touple of normalised mantissa and exponent
	 */
	public static final FRExpResultf frexp(double value,
			final FRExpResultf result) {
		int i = 0;
		if (value != 0.0f) {
			int sign = 1;
			if (value < 0f) {
				sign = -1;
				value = -value;
			}
			// slow...
			while (value < 0.5f) {
				value = value * 2.0f;
				i = i - 1;
			}
			while (value >= 1.0f) {
				value = value * 0.5f;
				i = i + 1;
			}
			value = value * sign;
		}

		result.mantissa = value;
		result.exponent = i;

		return (result);
	}

	/**
	 * An implementation of the C standard library frexp() function.
	 * 
	 * @param value
	 *            the number to split
	 * @return a touple of normalised mantissa and exponent
	 */
	public static final FRExpResultf frexp(final double value) {
		return (frexp(value, new FRExpResultf()));
	}

	/**
	 * Compares two scalars with EPSILON accuracy.
	 * 
	 * @param A
	 *            scalar
	 * @param B
	 *            scalar
	 * @return 1 if A > B, -1 if A < B, 0 otherwise
	 */

	public static int comp(final double A, final double B) {
		if (!VAR_EPSILON) {
			if (A >= B + EPSILON) {
				return 1;
			} else if (B >= A + EPSILON) {
				return -1;
			} else {
				return 0;
			}
		} else {
			FRExpResultf expA = frexp(A);
			final FRExpResultf expB = frexp(B);

			if (expA.exponent < expB.exponent) {
				expA = expB;
			}
			frexp(A - B, expB); /* get exponent of the difference */
			/*
			 * mantissa will only be zero is (A-B) is really zero; otherwise,
			 * also also allow a "reasonably" small exponent or
			 * "reasonably large" difference in exponents to be considers
			 * "close to zero"
			 */
			if (expB.mantissa == 0 || expB.exponent < -30
					|| expA.exponent - expB.exponent > 31) {
				return 0;
			} else if (expB.mantissa > 0) {
				return 1;
			} else {
				return -1;
			}
		}
	}

	/**
	 * Compares a scalar with EPSILON accuracy.
	 * 
	 * @param A
	 *            scalar
	 * @return 1 if A > 0, -1 if A < 0, 0 otherwise
	 */

	public static int comp0(final double A) {
		if (A >= EPSILON) {
			return 1;
		} else if (0 >= A + EPSILON) {
			return -1;
		} else {
			return 0;
		}
	}

	/**
	 * Compares two scalar triplets with EPSILON accuracy.
	 * 
	 * @param A
	 *            scalar triplet
	 * @param B
	 *            scalar triplet
	 * @return 1 if A > B, -1 if A < B, 0 otherwise
	 */
	public static int comp(final Vector3d A, final Vector3d B) {
		if (!VAR_EPSILON) {
			if (A.x >= (B.x + EPSILON)) {
				return 1;
			} else if (B.x >= (A.x + EPSILON)) {
				return -1;
			} else if (A.y >= (B.y + EPSILON)) {
				return 1;
			} else if (B.y >= (A.y + EPSILON)) {
				return -1;
			} else if (A.z >= (B.z + EPSILON)) {
				return 1;
			} else if (B.z >= (A.z + EPSILON)) {
				return -1;
			} else {
				return 0;
			}
		} else {
			int result = comp(A.x, B.x);
			if (result != 0) {
				return result;
			}
			result = comp(A.y, B.y);
			if (result != 0) {
				return result;
			}
			return comp(A.z, B.z);
		}
	}

	public static boolean fuzzyZero(final double x) {
		return comp0(x) == 0;
	}

	/**
	 * Classifies a point according to the specified Plane3d with EPSILON
	 * accuracy.
	 * 
	 * @param p
	 *            point
	 * @param Plane3d
	 *            Plane3d
	 * @return >0 if the point is above (OUT), =0 if the point is on (ON), <0 if
	 *         the point is below (IN)
	 */
	public static int classify(final Vector3d p, final Plane3d Plane3d) {
		// Compare Plane3d - point distance with zero
		return comp0(Plane3d.pseudoDistance(p));
	}

	/**
	 * Returns if three points lay on the same line (are collinears).
	 * 
	 * @param p1
	 *            point
	 * @param p2
	 *            point
	 * @param p3
	 *            point
	 * @return true if the three points lay on the same line, false otherwise
	 */
	public static boolean collinear(final Vector3d p1, final Vector3d p2,
			final Vector3d p3) {
		if (comp(p1, p2) == 0 || comp(p2, p3) == 0) {
			return true;
		}

		final Vector3d v1 = p2.subtract(p1);
		final Vector3d v2 = p3.subtract(p2);

		/*
		 * normalize vectors before taking their cross product, so its length
		 * has some actual meaning
		 */
		// if(MT_fuzzyZero(v1.length()) || MT_fuzzyZero(v2.length())) return
		// true;
		v1.normalize();
		v2.normalize();

		final Vector3d w = v1.cross(v2);

		return (fuzzyZero(w.x) && fuzzyZero(w.y) && fuzzyZero(w.z));
	}

	/**
	 * Returns if a quad (coplanar) is convex.
	 * 
	 * @return true if the quad is convex, false otherwise
	 */
	public static boolean convex(final Vector3d p1, final Vector3d p2,
			final Vector3d p3, final Vector3d p4) {
		final Vector3d v1 = p3.subtract(p1);
		final Vector3d v2 = p4.subtract(p2);
		final Vector3d quadPlane3d = v1.cross(v2);
		// Plane3d1 is the perpendicular Plane3d that contains the quad diagonal
		// (p2,p4)
		final Plane3d Plane3d1 = createPlane3d(quadPlane3d.cross(v2), p2);
		// if p1 and p3 are classified in the same region, the quad is not
		// convex
		if (classify(p1, Plane3d1) == classify(p3, Plane3d1)) {
			return false;
		} else {
			// Test the other quad diagonal (p1,p3) and perpendicular Plane3d
			final Plane3d Plane3d2 = createPlane3d(quadPlane3d.cross(v1), p1);
			// if p2 and p4 are classified in the same region, the quad is not
			// convex
			return (classify(p2, Plane3d2) != classify(p4, Plane3d2));
		}
	}

	/**
	 * Returns if a quad (coplanar) is concave and where is the split edge.
	 * 
	 * @return 0 if is convex, 1 if is concave and split edge is p1-p3 and -1 if
	 *         is cancave and split edge is p2-p4.
	 */
	public static int concave(final Vector3d p1, final Vector3d p2,
			final Vector3d p3, final Vector3d p4) {
		final Vector3d v1 = p3.subtract(p1);
		final Vector3d v2 = p4.subtract(p2);
		final Vector3d quadPlane3d = v1.cross(v2);
		// Plane3d1 is the perpendicular Plane3d that contains the quad diagonal
		// (p2,p4)
		final Plane3d Plane3d1 = createPlane3d(quadPlane3d.cross(v2), p2);
		// if p1 and p3 are classified in the same region, the quad is not
		// convex
		if (classify(p1, Plane3d1) == classify(p3, Plane3d1)) {
			return 1;
		} else {
			// Test the other quad diagonal (p1,p3) and perpendicular Plane3d
			final Plane3d Plane3d2 = createPlane3d(quadPlane3d.cross(v1), p1);
			// if p2 and p4 are classified in the same region, the quad is not
			// convex
			if (classify(p2, Plane3d2) == classify(p4, Plane3d2)) {
				return -1;
			} else {
				return 0;
			}
		}
	}

	/**
	 * Computes the intersection between two lines (on the same Plane3d).
	 * 
	 * @param vL1
	 *            first line vector
	 * @param pL1
	 *            first line point
	 * @param vL2
	 *            second line vector
	 * @param pL2
	 *            second line point
	 * @param intersection
	 *            intersection point (if exists)
	 * @return false if lines are parallels, true otherwise
	 */
	public static boolean intersect(final Vector3d vL1, final Vector3d pL1,
			final Vector3d vL2, final Vector3d pL2, final Vector3d intersection) {
		// NOTE:
		// If the lines aren't on the same Plane3d, the intersection point will
		// not be valid.
		// So be careful !!

		double t = -1;
		double den = (vL1.y * vL2.x - vL1.x * vL2.y);

		if (!fuzzyZero(den)) {
			t = (pL2.y * vL1.x - vL1.y * pL2.x + pL1.x * vL1.y - pL1.y * vL1.x)
					/ den;
		} else {
			den = (vL1.y * vL2.z - vL1.z * vL2.y);
			if (!fuzzyZero(den)) {
				t = (pL2.y * vL1.z - vL1.y * pL2.z + pL1.z * vL1.y - pL1.y
						* vL1.z)
						/ den;
			} else {
				den = (vL1.x * vL2.z - vL1.z * vL2.x);
				if (!fuzzyZero(den)) {
					t = (pL2.x * vL1.z - vL1.x * pL2.z + pL1.z * vL1.x - pL1.x
							* vL1.z)
							/ den;
				} else {
					return false;
				}
			}
		}

		intersection.set(vL2.x * t + pL2.x, vL2.y * t + pL2.y, vL2.z * t
				+ pL2.z);

		return true;
	}

	/**
	 * Returns the center of the circle defined by three points.
	 * 
	 * @param p1
	 *            point
	 * @param p2
	 *            point
	 * @param p3
	 *            point
	 * @param center
	 *            circle center
	 * @return false if points are collinears, true otherwise
	 */
	public static boolean getCircleCenter(final Vector3d p1, final Vector3d p2,
			final Vector3d p3, final Vector3d center) {
		// Compute quad Plane3d
		final Vector3d p1p2 = p2.subtract(p1);
		final Vector3d p1p3 = p3.subtract(p1);
		final Plane3d Plane3d1 = createPlane3d(p1, p2, p3);
		final Vector3d Plane3d = Plane3d1.getNormal();

		// Compute first line vector, perpendicular to Plane3d vector and edge
		// (p1,p2)
		final Vector3d vL1 = p1p2.cross(Plane3d);
		if (fuzzyZero(vL1.length())) {
			return false;
		}
		vL1.normalize();

		// Compute first line point, middle point of edge (p1,p2)
		final Vector3d pL1 = new Vector3d();
		pL1.interpolate(p1, p2, 0.5f);

		// Compute second line vector, perpendicular to Plane3d vector and edge
		// (p1,p3)
		final Vector3d vL2 = p1p3.cross(Plane3d);
		if (fuzzyZero(vL2.length())) {
			return false;
		}
		vL2.normalize();

		// Compute second line point, middle point of edge (p1,p3)
		final Vector3d pL2 = new Vector3d();
		pL2.interpolate(p1, p2, 0.5f);

		// Compute intersection (the lines lay on the same Plane3d, so the
		// intersection exists
		// only if they are not parallel!!)
		return intersect(vL1, pL1, vL2, pL2, center);
	}

	/**
	 * Returns if points q is inside the circle defined by p1, p2 and p3.
	 * 
	 * @param p1
	 *            point
	 * @param p2
	 *            point
	 * @param p3
	 *            point
	 * @param q
	 *            point
	 * @return true if p4 or p5 are inside the circle, false otherwise. If the
	 *         circle does not exist (p1, p2 and p3 are collinears) returns true
	 */
	public static boolean isInsideCircle(final Vector3d p1, final Vector3d p2,
			final Vector3d p3, final Vector3d q) {
		final Vector3d center = new Vector3d();

		// Compute circle center
		final boolean ok = getCircleCenter(p1, p2, p3, center);

		if (!ok) {
			return true; // p1,p2 and p3 are collinears
		}

		// Check if q is inside the circle
		final double r = p1.distance(center);
		final double d = q.distance(center);
		return (comp(d, r) <= 0);
	}

	/**
	 * Returns if points p4 or p5 is inside the circle defined by p1, p2 and p3.
	 * 
	 * @param p1
	 *            point
	 * @param p2
	 *            point
	 * @param p3
	 *            point
	 * @param p4
	 *            point
	 * @param p5
	 *            point
	 * @return true if p4 or p5 is inside the circle, false otherwise. If the
	 *         circle does not exist (p1, p2 and p3 are collinears) returns true
	 */
	public static boolean isInsideCircle(final Vector3d p1, final Vector3d p2,
			final Vector3d p3, final Vector3d p4, final Vector3d p5) {
		final Vector3d center = new Vector3d();
		final boolean ok = getCircleCenter(p1, p2, p3, center);

		if (!ok) {
			return true; // Collinear points!
		}

		// Check if p4 or p5 is inside the circle
		final double r = p1.distance(center);
		final double d1 = p4.distance(center);
		final double d2 = p5.distance(center);
		return (comp(d1, r) <= 0 || comp(d2, r) <= 0);
	}

	/**
	 * Intersects a Plane3d with the line that contains the specified points.
	 * 
	 * @param Plane3d
	 *            split Plane3d
	 * @param p1
	 *            first line point
	 * @param p2
	 *            second line point
	 * @return intersection between Plane3d and line that contains p1 and p2
	 */
	public static Vector3d intersectPlane3d(final Plane3d Plane3d,
			final Vector3d p1, final Vector3d p2) {
		// Compute intersection between Plane3d and line ...
		//
		// L: (p2-p1)lambda + p1
		//
		// supposes resolve equation ...
		//
		// coefA*((p2.x - p1.y)*lambda + p1.x) + ... + coefD = 0

		final Vector3d intersection = new Vector3d(0, 0, 0);
		final Vector3d diff = p2.subtract(p1);

		final double den = Plane3d.getNormal().dot(diff);

		if (den != 0) {
			final double lambda = (Plane3d.constant - Plane3d.getNormal().dot(
					p1))
					/ den;

			intersection.set(diff);
			intersection.multLocal(lambda);
			intersection.addLocal(p1);

			return intersection;
		}
		return intersection;
	}

	/**
	 * Returns if a Plane3d contains a point with EPSILON accuracy.
	 * 
	 * @param Plane3d
	 *            Plane3d
	 * @param point
	 *            point
	 * @return true if the point is on the Plane3d, false otherwise
	 */
	public static boolean containsPoint(final Plane3d Plane3d,
			final Vector3d point) {
		return fuzzyZero(Plane3d.pseudoDistance(point));
	}

	public static Plane3d createPlane3d(final Vector3d n, final Vector3d p) {
		final Vector3d mn = n.normalize();
		final double md = mn.dot(p);

		return new Plane3d(mn, md);
	}

	public static Plane3d createPlane3d(final Vector3d a, final Vector3d b,
			final Vector3d c) {
		final Vector3d l1 = b.subtract(a);
		final Vector3d l2 = c.subtract(b);

		Vector3d n = l1.cross(l2);
		n = n.normalize();
		final double d = n.dot(a);

		return new Plane3d(n, d);
	}
}
