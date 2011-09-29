package net.untoldwind.moredread.model.enums;

import net.untoldwind.moredread.model.math.Vector3;

import com.jme.math.FastMath;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

/**
 * A direction in the Cartesian coordinate system.
 */
public enum CartesianDirection {
	X(new Quaternion(new float[] { 0, 0, -FastMath.HALF_PI })) {
		@Override
		public float getValue(final Vector3 vector) {
			return vector.x;
		}

		@Override
		public Vector3f getTranslation(final float length) {
			return new Vector3f(length, 0, 0);
		}

		@Override
		public Vector3 project(final Vector3 vector) {
			return new Vector3(vector.x, 0, 0);
		}

		@Override
		public Vector3f project(final Vector3f vector) {
			return new Vector3f(vector.x, 0, 0);
		}

		@Override
		public CartesianPlane getOrthogonalPlane() {
			return CartesianPlane.YZ;
		}
	},
	Y(new Quaternion(new float[] { 0, 0, 0 })) {
		@Override
		public float getValue(final Vector3 vector) {
			return vector.y;
		}

		@Override
		public Vector3f getTranslation(final float length) {
			return new Vector3f(0, length, 0);
		}

		@Override
		public Vector3 project(final Vector3 vector) {
			return new Vector3(0, vector.y, 0);
		}

		@Override
		public Vector3f project(final Vector3f vector) {
			return new Vector3f(0, vector.y, 0);
		}

		@Override
		public CartesianPlane getOrthogonalPlane() {
			return CartesianPlane.XZ;
		}
	},
	Z(new Quaternion(new float[] { FastMath.HALF_PI, 0, 0 })) {
		@Override
		public float getValue(final Vector3 vector) {
			return vector.z;
		}

		@Override
		public Vector3f getTranslation(final float length) {
			return new Vector3f(0, 0, length);
		}

		@Override
		public Vector3 project(final Vector3 vector) {
			return new Vector3(0, 0, vector.z);
		}

		@Override
		public Vector3f project(final Vector3f vector) {
			return new Vector3f(0, 0, vector.z);
		}

		@Override
		public CartesianPlane getOrthogonalPlane() {
			return CartesianPlane.XY;
		}
	};

	private final Quaternion standardRotation;

	private CartesianDirection(final Quaternion standardRotation) {
		this.standardRotation = standardRotation;
	}

	public Quaternion getStandardRotation() {
		return standardRotation;
	}

	public abstract Vector3 project(Vector3 vector);

	public abstract Vector3f project(Vector3f vector);

	public abstract Vector3f getTranslation(float length);

	public abstract float getValue(Vector3 vector);

	/**
	 * Get the corresponding orthogonal/perpendicular plane.
	 */
	public abstract CartesianPlane getOrthogonalPlane();
}
