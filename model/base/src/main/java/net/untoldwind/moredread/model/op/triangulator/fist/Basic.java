package net.untoldwind.moredread.model.op.triangulator.fist;

import net.untoldwind.moredread.model.math.Vector3;

import com.jme.math.Vector2f;

class Basic {

	static final double D_RND_MAX = 2147483647.0;

	static double detExp(final double u_x, final double u_y, final double u_z,
			final double v_x, final double v_y, final double v_z,
			final double w_x, final double w_y, final double w_z) {

		return ((u_x) * ((v_y) * (w_z) - (v_z) * (w_y)) - (u_y)
				* ((v_x) * (w_z) - (v_z) * (w_x)) + (u_z)
				* ((v_x) * (w_y) - (v_y) * (w_x)));
	}

	static double det3D(final Vector3 u, final Vector3 v, final Vector3 w) {
		return ((u).x * ((v).y * (w).z - (v).z * (w).y) - (u).y
				* ((v).x * (w).z - (v).z * (w).x) + (u).z
				* ((v).x * (w).y - (v).y * (w).x));
	}

	static double det2D(final Vector2f u, final Vector2f v, final Vector2f w) {
		return (((u).x - (v).x) * ((v).y - (w).y) + ((v).y - (u).y)
				* ((v).x - (w).x));
	}

	static double length2(final Vector3 u) {
		return (((u).x * (u).x) + ((u).y * (u).y) + ((u).z * (u).z));
	}

	static double lengthL1(final Vector3 u) {
		return (Math.abs((u).x) + Math.abs((u).y) + Math.abs((u).z));
	}

	static double lengthL2(final Vector3 u) {
		return Math.sqrt(((u).x * (u).x) + ((u).y * (u).y) + ((u).z * (u).z));
	}

	static double dotProduct(final Vector3 u, final Vector3 v) {
		return (((u).x * (v).x) + ((u).y * (v).y) + ((u).z * (v).z));
	}

	static double dotProduct2D(final Vector2f u, final Vector2f v) {
		return (((u).x * (v).x) + ((u).y * (v).y));
	}

	static void vectorProduct(final Vector3 p, final Vector3 q, final Vector3 r) {
		(r).x = (p).y * (q).z - (q).y * (p).z;
		(r).y = (q).x * (p).z - (p).x * (q).z;
		(r).z = (p).x * (q).y - (q).x * (p).y;
	}

	static void vectorAdd(final Vector3 p, final Vector3 q, final Vector3 r) {
		(r).x = (p).x + (q).x;
		(r).y = (p).y + (q).y;
		(r).z = (p).z + (q).z;
	}

	static void vectorSub(final Vector3 p, final Vector3 q, final Vector3 r) {
		(r).x = (p).x - (q).x;
		(r).y = (p).y - (q).y;
		(r).z = (p).z - (q).z;
	}

	static void vectorAdd2D(final Vector2f p, final Vector2f q, final Vector2f r) {
		(r).x = (p).x + (q).x;
		(r).y = (p).y + (q).y;
	}

	static void vectorSub2D(final Vector2f p, final Vector2f q, final Vector2f r) {
		(r).x = (p).x - (q).x;
		(r).y = (p).y - (q).y;
	}

	static void invertVector(final Vector3 p) {
		(p).x = -(p).x;
		(p).y = -(p).y;
		(p).z = -(p).z;
	}

	static void divScalar(final double scalar, final Vector3 u) {
		(u).x /= scalar;
		(u).y /= scalar;
		(u).z /= scalar;
	}

	static void multScalar2D(final double scalar, final Vector2f u) {
		(u).x *= scalar;
		(u).y *= scalar;
	}

	static int signEps(final double x, final double eps) {
		return ((x <= eps) ? ((x < -eps) ? -1 : 0) : 1);
	}
}
