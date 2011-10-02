package net.untoldwind.moredread.model.op.utils;

import net.untoldwind.moredread.model.math.Vector3;

import com.jme.math.FastMath;

public class VectorKey {
	private final float TOLERANCE = 1e-5f;

	private final Vector3 point;
	private final int hashCode;

	VectorKey(final Vector3 point) {
		this.point = point;
		this.hashCode = 31 * 31 * Math.round(point.x / TOLERANCE / 2) + 31
				* Math.round(point.y / TOLERANCE / 2)
				+ Math.round(point.z / TOLERANCE / 2);
	}

	@Override
	public int hashCode() {
		return hashCode;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final VectorKey other = (VectorKey) obj;

		return FastMath.abs(point.x - other.point.x) < TOLERANCE
				&& FastMath.abs(point.y - other.point.y) < TOLERANCE
				&& FastMath.abs(point.z - other.point.z) < TOLERANCE;
	}

}
