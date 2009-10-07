package net.untoldwind.moredread.model.triangulator.fist;

import com.jme.math.Vector2f;
import com.jme.math.Vector3f;

class Basic {

	static final double D_RND_MAX = 2147483647.0;

	static double detExp(double u_x, double u_y, double u_z, double v_x,
			double v_y, double v_z, double w_x, double w_y, double w_z) {

		return ((u_x) * ((v_y) * (w_z) - (v_z) * (w_y)) - (u_y)
				* ((v_x) * (w_z) - (v_z) * (w_x)) + (u_z)
				* ((v_x) * (w_y) - (v_y) * (w_x)));
	}

	static double det3D(Vector3f u, Vector3f v, Vector3f w) {
		return ((u).x * ((v).y * (w).z - (v).z * (w).y) - (u).y
				* ((v).x * (w).z - (v).z * (w).x) + (u).z
				* ((v).x * (w).y - (v).y * (w).x));
	}

	static double det2D(Vector2f u, Vector2f v, Vector2f w) {
		return (((u).x - (v).x) * ((v).y - (w).y) + ((v).y - (u).y)
				* ((v).x - (w).x));
	}

	static double length2(Vector3f u) {
		return (((u).x * (u).x) + ((u).y * (u).y) + ((u).z * (u).z));
	}

	static double lengthL1(Vector3f u) {
		return (Math.abs((u).x) + Math.abs((u).y) + Math.abs((u).z));
	}

	static double lengthL2(Vector3f u) {
		return Math.sqrt(((u).x * (u).x) + ((u).y * (u).y) + ((u).z * (u).z));
	}

	static double dotProduct(Vector3f u, Vector3f v) {
		return (((u).x * (v).x) + ((u).y * (v).y) + ((u).z * (v).z));
	}

	static double dotProduct2D(Vector2f u, Vector2f v) {
		return (((u).x * (v).x) + ((u).y * (v).y));
	}

	static void vectorProduct(Vector3f p, Vector3f q, Vector3f r) {
		(r).x = (p).y * (q).z - (q).y * (p).z;
		(r).y = (q).x * (p).z - (p).x * (q).z;
		(r).z = (p).x * (q).y - (q).x * (p).y;
	}

	static void vectorAdd(Vector3f p, Vector3f q, Vector3f r) {
		(r).x = (p).x + (q).x;
		(r).y = (p).y + (q).y;
		(r).z = (p).z + (q).z;
	}

	static void vectorSub(Vector3f p, Vector3f q, Vector3f r) {
		(r).x = (p).x - (q).x;
		(r).y = (p).y - (q).y;
		(r).z = (p).z - (q).z;
	}

	static void vectorAdd2D(Vector2f p, Vector2f q, Vector2f r) {
		(r).x = (p).x + (q).x;
		(r).y = (p).y + (q).y;
	}

	static void vectorSub2D(Vector2f p, Vector2f q, Vector2f r) {
		(r).x = (p).x - (q).x;
		(r).y = (p).y - (q).y;
	}

	static void invertVector(Vector3f p) {
		(p).x = -(p).x;
		(p).y = -(p).y;
		(p).z = -(p).z;
	}

	static void divScalar(double scalar, Vector3f u) {
		(u).x /= scalar;
		(u).y /= scalar;
		(u).z /= scalar;
	}

	static void multScalar2D(double scalar, Vector2f u) {
		(u).x *= scalar;
		(u).y *= scalar;
	}

	static int signEps(double x, double eps) {
		return ((x <= eps) ? ((x < -eps) ? -1 : 0) : 1);
	}
}
