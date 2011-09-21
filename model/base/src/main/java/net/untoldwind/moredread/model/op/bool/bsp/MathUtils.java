package net.untoldwind.moredread.model.op.bool.bsp;

import com.jme.math.FastMath;
import com.jme.math.Plane;
import com.jme.math.Vector3f;

public class MathUtils {
	public static Plane planeForTriangle(final Vector3f v1, final Vector3f v2,
			final Vector3f v3) {
		final Vector3f normal = v2.subtract(v1);
		normal.crossLocal(v3.x - v1.x, v3.y - v1.y, v3.z - v1.z);

		final float len = normal.length();

		if (len < FastMath.FLT_EPSILON) {
			return null;
		}
		normal.divideLocal(len);
		return new Plane(normal, normal.dot(v1));
	}

	public static TriangleTag testTriangle(final Plane plane,
			final Vector3f v1, final Vector3f v2, final Vector3f v3) {
		return TriangleTag.fromVertexTags(testPoint(plane, v1),
				testPoint(plane, v3), testPoint(plane, v3));
	}

	public static VertexTag testPoint(final Plane plane, final Vector3f v) {
		final float distance = plane.pseudoDistance(v);

		if (distance < -FastMath.FLT_EPSILON) {
			return VertexTag.IN;
		} else if (distance > FastMath.FLT_EPSILON) {
			return VertexTag.OUT;
		} else {
			return VertexTag.ON;
		}
	}

	public static Vector3f intersectLinePlane(final Plane plane,
			final Vector3f p1, final Vector3f p2) {
		final Vector3f intersection = new Vector3f(p1);
		final Vector3f diff = p2.subtract(p1);

		final float den = plane.normal.dot(diff);

		if (den != 0) {
			final float lambda = (plane.constant - plane.normal.dot(p1)) / den;

			intersection.set(diff);
			intersection.multLocal(lambda);
			intersection.addLocal(p1);

			return intersection;
		}
		return intersection;
	}
}
