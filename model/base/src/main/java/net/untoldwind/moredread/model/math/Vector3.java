package net.untoldwind.moredread.model.math;

import com.jme.math.Vector3f;

public class Vector3 implements Cloneable {
	public float x;
	public float y;
	public float z;

	public Vector3() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public Vector3(final float x, final float y, final float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3(final Vector3 v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}

	public Vector3(final Vector3f v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}

	public Vector3 set(final Vector3 other) {
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
		return this;
	}

	public Vector3 set(final float x, final float y, final float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public Vector3 add(final Vector3 other) {
		return new Vector3(x + other.x, y + other.y, z + other.z);
	}

	public Vector3 add(final Vector3 other, final Vector3 result) {
		result.x = x + other.x;
		result.y = y + other.y;
		result.z = z + other.z;
		return result;
	}

	public Vector3 addLocal(final Vector3 other) {
		x += other.x;
		y += other.y;
		z += other.z;
		return this;
	}

	public Vector3 subtract(final Vector3 other) {
		return new Vector3(x - other.x, y - other.y, z - other.z);
	}

	public Vector3 subtract(final Vector3 other, final Vector3 result) {
		result.x = x - other.x;
		result.y = y - other.y;
		result.z = z - other.z;
		return result;
	}

	public Vector3 subtractLocal(final Vector3 other) {
		x -= other.x;
		y -= other.y;
		z -= other.z;
		return this;
	}

	public Vector3 mult(final float scalar) {
		return new Vector3(x * scalar, y * scalar, z * scalar);
	}

	public Vector3 mult(final float scalar, final Vector3 result) {
		result.x = x * scalar;
		result.y = y * scalar;
		result.z = z * scalar;
		return result;
	}

	public Vector3 multLocal(final float scalar) {
		x *= scalar;
		y *= scalar;
		z *= scalar;
		return this;
	}

	public Vector3 mult(final Vector3 vec) {
		return new Vector3(x * vec.x, y * vec.y, z * vec.z);
	}

	public Vector3 mult(final Vector3 vec, final Vector3 result) {
		return result.set(x * vec.x, y * vec.y, z * vec.z);
	}

	public Vector3 multLocal(final Vector3 vec) {
		x *= vec.x;
		y *= vec.y;
		z *= vec.z;
		return this;
	}

	public Vector3 divide(float scalar) {
		scalar = 1f / scalar;
		return new Vector3(x * scalar, y * scalar, z * scalar);
	}

	public Vector3 divideLocal(float scalar) {
		scalar = 1f / scalar;
		x *= scalar;
		y *= scalar;
		z *= scalar;
		return this;
	}

	public Vector3 divideLocal(final Vector3 scalar) {
		x /= scalar.x;
		y /= scalar.y;
		z /= scalar.z;
		return this;
	}

	public float length() {
		return (float) Math.sqrt(lengthSquared());

	}

	public float lengthSquared() {
		return x * x + y * y + z * z;
	}

	public Vector3 normalize() {
		final float length = length();
		if (length != 0) {
			return divide(length);
		}

		return divide(1);
	}

	public Vector3 normalizeLocal() {
		final float length = length();
		if (length != 0) {
			return divideLocal(length);
		}

		return this;
	}

	public float dot(final Vector3 vec) {
		return x * vec.x + y * vec.y + z * vec.z;
	}

	public Vector3 cross(final Vector3 other) {
		final float resX = ((y * other.z) - (z * other.y));
		final float resY = ((z * other.x) - (x * other.z));
		final float resZ = ((x * other.y) - (y * other.x));
		return new Vector3(resX, resY, resZ);
	}

	public Vector3 crossLocal(final Vector3 other) {
		final float tempx = (y * other.z) - (z * other.y);
		final float tempy = (z * other.x) - (x * other.z);
		z = (x * other.y) - (y * other.x);
		x = tempx;
		y = tempy;
		return this;
	}

	public float distance(final Vector3 v) {
		return (float) Math.sqrt(distanceSquared(v));
	}

	public float distanceSquared(final Vector3 v) {
		final double dx = x - v.x;
		final double dy = y - v.y;
		final double dz = z - v.z;
		return (float) (dx * dx + dy * dy + dz * dz);
	}

	public void interpolate(final Vector3 finalVec, final float changeAmnt) {
		this.x = (1 - changeAmnt) * this.x + changeAmnt * finalVec.x;
		this.y = (1 - changeAmnt) * this.y + changeAmnt * finalVec.y;
		this.z = (1 - changeAmnt) * this.z + changeAmnt * finalVec.z;
	}

	public void interpolate(final Vector3 beginVec, final Vector3 finalVec,
			final float changeAmnt) {
		this.x = (1 - changeAmnt) * beginVec.x + changeAmnt * finalVec.x;
		this.y = (1 - changeAmnt) * beginVec.y + changeAmnt * finalVec.y;
		this.z = (1 - changeAmnt) * beginVec.z + changeAmnt * finalVec.z;
	}

	@Override
	public Vector3 clone() {
		try {
			return (Vector3) super.clone();
		} catch (final CloneNotSupportedException e) {
			throw new AssertionError(); // can not happen
		}
	}

	public Vector3f toJME() {
		return new Vector3f(x, y, z);
	}

	public static boolean isValidVector(final Vector3 vector) {
		if (vector == null) {
			return false;
		}
		if (Float.isNaN(vector.x) || Float.isNaN(vector.y)
				|| Float.isNaN(vector.z)) {
			return false;
		}
		if (Float.isInfinite(vector.x) || Float.isInfinite(vector.y)
				|| Float.isInfinite(vector.z)) {
			return false;
		}
		return true;
	}
}
