package net.untoldwind.moredread.model.math;

import com.jme.math.FastMath;

public class Quaternion {
	public float x;
	public float y;
	public float z;
	public float w;

	public Quaternion() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.w = 1;
	}

	public Quaternion(final Quaternion q) {
		this.x = q.x;
		this.y = q.y;
		this.z = q.z;
		this.w = q.w;
	}

	public Quaternion(final float x, final float y, final float z, final float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public void set(final float x, final float y, final float z, final float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public Quaternion set(final Quaternion other) {
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
		this.w = other.w;
		return this;
	}

	public void setIdentity() {
		x = y = z = 0;
		w = 1;
	}

	public Vector3 mult(final Vector3 v) {
		return mult(v, new Vector3());
	}

	public Vector3 mult(final Vector3 v, final Vector3 result) {
		if (v.x == 0 && v.y == 0 && v.z == 0) {
			result.set(0, 0, 0);
		} else {
			final float vx = v.x, vy = v.y, vz = v.z;
			result.x = w * w * vx + 2 * y * w * vz - 2 * z * w * vy + x * x
					* vx + 2 * y * x * vy + 2 * z * x * vz - z * z * vx - y * y
					* vx;
			result.y = 2 * x * y * vx + y * y * vy + 2 * z * y * vz + 2 * w * z
					* vx - z * z * vy + w * w * vy - 2 * x * w * vz - x * x
					* vy;
			result.z = 2 * x * z * vx + 2 * y * z * vy + z * z * vz - 2 * w * y
					* vx - y * y * vz + 2 * w * x * vy - x * x * vz + w * w
					* vz;
		}
		return result;
	}

	public Quaternion mult(final Quaternion q) {
		return mult(q, new Quaternion());
	}

	public Quaternion mult(final Quaternion q, final Quaternion res) {
		final float qw = q.w, qx = q.x, qy = q.y, qz = q.z;
		res.x = x * qw + y * qz - z * qy + w * qx;
		res.y = -x * qz + y * qw + z * qx + w * qy;
		res.z = x * qy - y * qx + z * qw + w * qz;
		res.w = -x * qx - y * qy - z * qz + w * qw;
		return res;
	}

	public void normalize() {
		final float n = FastMath.invSqrt(norm());
		x *= n;
		y *= n;
		z *= n;
		w *= n;
	}

	public float norm() {
		return w * w + x * x + y * y + z * z;
	}

	public Quaternion inverse() {
		final float norm = norm();
		if (norm > 0.0) {
			final float invNorm = 1.0f / norm;
			return new Quaternion(-x * invNorm, -y * invNorm, -z * invNorm, w
					* invNorm);
		}
		// return an invalid result to flag the error
		return null;
	}

	public float toAngleAxis(final Vector3 axisResult) {
		final float sqrLength = x * x + y * y + z * z;
		float angle;
		if (sqrLength == 0.0f) {
			angle = 0.0f;
			if (axisResult != null) {
				axisResult.x = 1.0f;
				axisResult.y = 0.0f;
				axisResult.z = 0.0f;
			}
		} else {
			angle = (2.0f * FastMath.acos(w));
			if (axisResult != null) {
				final float invLength = (1.0f / FastMath.sqrt(sqrLength));
				axisResult.x = x * invLength;
				axisResult.y = y * invLength;
				axisResult.z = z * invLength;
			}
		}

		return angle;
	}

	public Matrix3 toRotationMatrix(final Matrix3 result) {

		final float norm = norm();
		// we explicitly test norm against one here, saving a division
		// at the cost of a test and branch. Is it worth it?
		final float s = (norm == 1f) ? 2f : (norm > 0f) ? 2f / norm : 0;

		// compute xs/ys/zs first to save 6 multiplications, since xs/ys/zs
		// will be used 2-4 times each.
		final float xs = x * s;
		final float ys = y * s;
		final float zs = z * s;
		final float xx = x * xs;
		final float xy = x * ys;
		final float xz = x * zs;
		final float xw = w * xs;
		final float yy = y * ys;
		final float yz = y * zs;
		final float yw = w * ys;
		final float zz = z * zs;
		final float zw = w * zs;

		// using s=2/norm (instead of 1/norm) saves 9 multiplications by 2 here
		result.m00 = 1 - (yy + zz);
		result.m01 = (xy - zw);
		result.m02 = (xz + yw);
		result.m10 = (xy + zw);
		result.m11 = 1 - (xx + zz);
		result.m12 = (yz - xw);
		result.m20 = (xz - yw);
		result.m21 = (yz + xw);
		result.m22 = 1 - (xx + yy);

		return result;
	}

	public Matrix4 toRotationMatrix(final Matrix4 result) {

		final float norm = norm();
		// we explicitly test norm against one here, saving a division
		// at the cost of a test and branch. Is it worth it?
		final float s = (norm == 1f) ? 2f : (norm > 0f) ? 2f / norm : 0;

		// compute xs/ys/zs first to save 6 multiplications, since xs/ys/zs
		// will be used 2-4 times each.
		final float xs = x * s;
		final float ys = y * s;
		final float zs = z * s;
		final float xx = x * xs;
		final float xy = x * ys;
		final float xz = x * zs;
		final float xw = w * xs;
		final float yy = y * ys;
		final float yz = y * zs;
		final float yw = w * ys;
		final float zz = z * zs;
		final float zw = w * zs;

		// using s=2/norm (instead of 1/norm) saves 9 multiplications by 2 here
		result.m00 = 1 - (yy + zz);
		result.m01 = (xy - zw);
		result.m02 = (xz + yw);
		result.m10 = (xy + zw);
		result.m11 = 1 - (xx + zz);
		result.m12 = (yz - xw);
		result.m20 = (xz - yw);
		result.m21 = (yz + xw);
		result.m22 = 1 - (xx + yy);

		return result;
	}

	public void fromAngles(final float[] angles) {
		if (angles.length != 3) {
			throw new IllegalArgumentException(
					"Angles array must have three elements");
		}

		fromAngles(angles[0], angles[1], angles[2]);
	}

	public Quaternion fromAngles(final float yaw, final float roll,
			final float pitch) {
		float angle;
		float sinRoll, sinPitch, sinYaw, cosRoll, cosPitch, cosYaw;
		angle = pitch * 0.5f;
		sinPitch = FastMath.sin(angle);
		cosPitch = FastMath.cos(angle);
		angle = roll * 0.5f;
		sinRoll = FastMath.sin(angle);
		cosRoll = FastMath.cos(angle);
		angle = yaw * 0.5f;
		sinYaw = FastMath.sin(angle);
		cosYaw = FastMath.cos(angle);

		// variables used to reduce multiplication calls.
		final float cosRollXcosPitch = cosRoll * cosPitch;
		final float sinRollXsinPitch = sinRoll * sinPitch;
		final float cosRollXsinPitch = cosRoll * sinPitch;
		final float sinRollXcosPitch = sinRoll * cosPitch;

		w = (cosRollXcosPitch * cosYaw - sinRollXsinPitch * sinYaw);
		x = (cosRollXcosPitch * sinYaw + sinRollXsinPitch * cosYaw);
		y = (sinRollXcosPitch * cosYaw + cosRollXsinPitch * sinYaw);
		z = (cosRollXsinPitch * cosYaw - sinRollXcosPitch * sinYaw);

		normalize();
		return this;
	}

	public float[] toAngles(float[] angles) {
		if (angles == null) {
			angles = new float[3];
		} else if (angles.length != 3) {
			throw new IllegalArgumentException(
					"Angles array must have three elements");
		}

		final float sqw = w * w;
		final float sqx = x * x;
		final float sqy = y * y;
		final float sqz = z * z;
		final float unit = sqx + sqy + sqz + sqw; // if normalized is one,
													// otherwise
		// is correction factor
		final float test = x * y + z * w;
		if (test > 0.499 * unit) { // singularity at north pole
			angles[1] = 2 * FastMath.atan2(x, w);
			angles[2] = FastMath.HALF_PI;
			angles[0] = 0;
		} else if (test < -0.499 * unit) { // singularity at south pole
			angles[1] = -2 * FastMath.atan2(x, w);
			angles[2] = -FastMath.HALF_PI;
			angles[0] = 0;
		} else {
			angles[1] = FastMath.atan2(2 * y * w - 2 * x * z, sqx - sqy - sqz
					+ sqw); // roll or heading
			angles[2] = FastMath.asin(2 * test / unit); // pitch or attitude
			angles[0] = FastMath.atan2(2 * x * w - 2 * y * z, -sqx + sqy - sqz
					+ sqw); // yaw or bank
		}
		return angles;
	}

	public Quaternion fromAngleAxis(final float angle, final Vector3 axis) {
		final Vector3 normAxis = axis.normalize();
		fromAngleNormalAxis(angle, normAxis);
		return this;
	}

	public Quaternion fromAngleNormalAxis(final float angle, final Vector3 axis) {
		if (axis.x == 0 && axis.y == 0 && axis.z == 0) {
			setIdentity();
		} else {
			final float halfAngle = 0.5f * angle;
			final float sin = FastMath.sin(halfAngle);
			w = FastMath.cos(halfAngle);
			x = sin * axis.x;
			y = sin * axis.y;
			z = sin * axis.z;
		}
		return this;
	}

	public com.jme.math.Quaternion toJME() {
		return new com.jme.math.Quaternion(x, y, z, w);
	}
}
