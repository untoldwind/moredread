package net.untoldwind.moredread.model.enums;

import com.jme.math.Vector3f;

public enum StandardPlane {
	XY() {
		@Override
		public Vector3f getTranslation(final float u, final float v) {
			return new Vector3f(u, v, 0);
		}

		@Override
		public Vector3f project(final Vector3f vector) {
			return new Vector3f(vector.x, vector.y, 0);
		}
	},
	YZ() {
		@Override
		public Vector3f getTranslation(final float u, final float v) {
			return new Vector3f(0, u, v);
		}

		@Override
		public Vector3f project(final Vector3f vector) {
			return new Vector3f(0, vector.y, vector.z);
		}

	},
	XZ() {
		@Override
		public Vector3f getTranslation(final float u, final float v) {
			return new Vector3f(u, 0, v);
		}

		@Override
		public Vector3f project(final Vector3f vector) {
			return new Vector3f(vector.x, 0, vector.z);
		}
	};

	private StandardPlane() {
	}

	public abstract Vector3f project(Vector3f vector);

	public abstract Vector3f getTranslation(float u, float v);
}
