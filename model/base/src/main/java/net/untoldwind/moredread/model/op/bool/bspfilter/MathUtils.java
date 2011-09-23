package net.untoldwind.moredread.model.op.bool.bspfilter;

import com.jme.math.FastMath;
import com.jme.math.Plane;
import com.jme.math.Vector3f;

public class MathUtils {
	public static Plane planeForTriangle(final Vector3f v1, final Vector3f v2,
			final Vector3f v3) {
		final Vector3f normal = v2.subtract(v1).normalizeLocal();
		final Vector3f other = v3.subtract(v1).normalizeLocal();
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
		final Vector3f intersection = new Vector3f(v1.getPoint());
		final Vector3f diff = v2.getPoint().subtract(v1.getPoint());

		final float den = plane.normal.dot(diff);

		if (den != 0) {
			final float lambda = (plane.constant - plane.normal.dot(v1
					.getPoint())) / den;

			intersection.set(diff);
			intersection.multLocal(lambda);
			intersection.addLocal(v1.getPoint());

			return new BoolVertex(intersection);
		}
		return new BoolVertex(intersection);
	}
}
