package net.untoldwind.moredread.model.enums;

import net.untoldwind.moredread.model.math.Vector3;

import com.jme.math.FastMath;

/**
 * A plane in the Cartesian coordinate system.
 */
public enum CartesianPlane {
	XY(0, 0, 1) {
		@Override
		public Vector3 project(final Vector3 center, final Vector3 position,
				final float u, final float v) {
			return new Vector3(center.x + u, center.y + v, position.z);
		}
	},
	XZ(0, -1, 0) {
		@Override
		public Vector3 project(final Vector3 center, final Vector3 position,
				final float u, final float v) {
			return new Vector3(center.x + u, position.y, center.z + v);
		}

	},
	YZ(1, 0, 0) {
		@Override
		public Vector3 project(final Vector3 center, final Vector3 position,
				final float u, final float v) {
			return new Vector3(position.x, center.y + u, center.z + v);
		}
	};

	private Vector3 normal;

	private CartesianPlane(final float x, final float y, final float z) {
		this.normal = new Vector3(x, y, z);
	}

	public Vector3 getNormal() {
		return normal;
	}

	public abstract Vector3 project(Vector3 center, Vector3 position, float u,
			float v);

	public static CartesianPlane choose(final Vector3 direction) {
		final float x = FastMath.abs(direction.x);
		final float y = FastMath.abs(direction.y);
		final float z = FastMath.abs(direction.z);

		if (x >= y && x >= z) {
			return CartesianPlane.YZ;
		} else if (y >= x && y >= z) {
			return CartesianPlane.XZ;
		} else {
			return CartesianPlane.XY;
		}
	}
}
