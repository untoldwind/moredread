package net.untoldwind.moredread.model.enums;

import net.untoldwind.moredread.model.math.Vector3;

public enum StandardPlane {
	XY() {
		@Override
		public Vector3 getTranslation(final float u, final float v) {
			return new Vector3(u, v, 0);
		}

		@Override
		public Vector3 project(final Vector3 vector) {
			return new Vector3(vector.x, vector.y, 0);
		}
	},
	YZ() {
		@Override
		public Vector3 getTranslation(final float u, final float v) {
			return new Vector3(0, u, v);
		}

		@Override
		public Vector3 project(final Vector3 vector) {
			return new Vector3(0, vector.y, vector.z);
		}

	},
	XZ() {
		@Override
		public Vector3 getTranslation(final float u, final float v) {
			return new Vector3(u, 0, v);
		}

		@Override
		public Vector3 project(final Vector3 vector) {
			return new Vector3(vector.x, 0, vector.z);
		}
	};

	private StandardPlane() {
	}

	public abstract Vector3 project(Vector3 vector);

	public abstract Vector3 getTranslation(float u, float v);
}
