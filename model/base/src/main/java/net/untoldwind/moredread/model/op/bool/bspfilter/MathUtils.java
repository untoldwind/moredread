package net.untoldwind.moredread.model.op.bool.bspfilter;

import net.untoldwind.moredread.model.math.Plane;
import net.untoldwind.moredread.model.math.Vector3;

import com.jme.math.FastMath;

public class MathUtils {
	public static Plane planeForTriangle(final Vector3 v1, final Vector3 v2,
			final Vector3 v3) {
		final Vector3 normal = v2.subtract(v1).normalizeLocal();
		final Vector3 other = v3.subtract(v1).normalizeLocal();
		normal.crossLocal(other);

		final float len = normal.length();

		if (len < FastMath.FLT_EPSILON) {
			return null;
		}
		normal.divideLocal(len);
		return new Plane(normal, normal.dot(v1));
	}

	public static VertexTag testVertex(final Plane plane, final BoolVertex v) {
		final float distance = plane.pseudoDistance(v.getPoint());

		if (distance < -1e-5f) {
			return VertexTag.IN;
		} else if (distance > 1e-5f) {
			return VertexTag.OUT;
		} else {
			return VertexTag.ON;
		}
	}

	public static BoolVertex intersectLinePlane(final Plane plane,
			final BoolVertex v1, final BoolVertex v2) {
		final Vector3 intersection = new Vector3(v1.getPoint());
		final Vector3 diff = v2.getPoint().subtract(v1.getPoint());

		final float den = plane.normal.dot(diff);

		final float lambda = (plane.constant - plane.normal.dot(v1.getPoint()))
				/ den;

		intersection.set(diff);
		intersection.multLocal(lambda);
		intersection.addLocal(v1.getPoint());

		return new BoolVertex(intersection, v1.getIndex(), v2.getIndex(), plane);
	}

	public static boolean isOnLine(final Vector3 p, final Vector3 v1,
			final Vector3 v2) {
		final Vector3 diff1 = v2.subtract(v1);
		final float l1 = diff1.lengthSquared();

		if (l1 < FastMath.FLT_EPSILON) {
			return false;
		}
		final Vector3 diff2 = p.subtract(v1);
		final float u = diff1.dot(diff2) / l1;

		if (u < 0.0 || u > 1.0) {
			return false;
		}

		final float dist = diff1.multLocal(u).addLocal(v1).subtractLocal(p)
				.length();

		return dist < 1e-5;
	}
}
