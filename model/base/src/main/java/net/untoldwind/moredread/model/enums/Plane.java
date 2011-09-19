package net.untoldwind.moredread.model.enums;

import com.jme.math.FastMath;
import com.jme.math.Vector3f;

public enum Plane {
	XY(0, 0, 1) {
		@Override
		public Vector3f project(final Vector3f center, final Vector3f position,
				final float u, final float v) {
			return new Vector3f(center.x + u, center.y + v, position.z);
		}
	},
	XZ(0, -1, 0) {
		@Override
		public Vector3f project(final Vector3f center, final Vector3f position,
				final float u, final float v) {
			return new Vector3f(center.x + u, position.y, center.z + v);
		}

	},
	YZ(1, 0, 0) {
		@Override
		public Vector3f project(final Vector3f center, final Vector3f position,
				final float u, final float v) {
			return new Vector3f(position.x, center.y + u, center.z + v);
		}
	};

	private Vector3f normal;

	private Plane(final float x, final float y, final float z) {
		this.normal = new Vector3f(x, y, z);
	}

	public Vector3f getNormal() {
		return normal;
	}

	public abstract Vector3f project(Vector3f center, Vector3f position,
			float u, float v);

	public static Plane choose(final Vector3f direction) {
		final float x = FastMath.abs(direction.x);
		final float y = FastMath.abs(direction.y);
		final float z = FastMath.abs(direction.z);

		if (x >= y && x >= z) {
			return Plane.YZ;
		} else if (y >= x && y >= z) {
			return Plane.XZ;
		} else {
			return Plane.XY;
		}
	}
}
