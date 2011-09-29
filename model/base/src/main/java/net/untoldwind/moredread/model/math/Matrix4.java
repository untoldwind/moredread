package net.untoldwind.moredread.model.math;

import com.jme.math.FastMath;

public class Matrix4 {
	public float m00, m01, m02, m03;
	public float m10, m11, m12, m13;
	public float m20, m21, m22, m23;
	public float m30, m31, m32, m33;

	public Matrix4() {
		setIdentity();
	}

	public Matrix4(final float m00, final float m01, final float m02,
			final float m03, final float m10, final float m11, final float m12,
			final float m13, final float m20, final float m21, final float m22,
			final float m23, final float m30, final float m31, final float m32,
			final float m33) {

		this.m00 = m00;
		this.m01 = m01;
		this.m02 = m02;
		this.m03 = m03;
		this.m10 = m10;
		this.m11 = m11;
		this.m12 = m12;
		this.m13 = m13;
		this.m20 = m20;
		this.m21 = m21;
		this.m22 = m22;
		this.m23 = m23;
		this.m30 = m30;
		this.m31 = m31;
		this.m32 = m32;
		this.m33 = m33;
	}

	public Matrix4(final Matrix4 mat) {
		set(mat);
	}

	public Matrix4 zero() {
		m00 = m01 = m02 = m03 = m10 = m11 = m12 = m13 = m20 = m21 = m22 = m23 = m30 = m31 = m32 = m33 = 0.0f;
		return this;
	}

	public Matrix4 set(final Matrix4 matrix) {
		if (null == matrix) {
			setIdentity();
		} else {
			m00 = matrix.m00;
			m01 = matrix.m01;
			m02 = matrix.m02;
			m03 = matrix.m03;
			m10 = matrix.m10;
			m11 = matrix.m11;
			m12 = matrix.m12;
			m13 = matrix.m13;
			m20 = matrix.m20;
			m21 = matrix.m21;
			m22 = matrix.m22;
			m23 = matrix.m23;
			m30 = matrix.m30;
			m31 = matrix.m31;
			m32 = matrix.m32;
			m33 = matrix.m33;
		}
		return this;
	}

	public Matrix4 setIdentity() {
		m01 = m02 = m03 = m10 = m12 = m13 = m20 = m21 = m23 = m30 = m31 = m32 = 0.0f;
		m00 = m11 = m22 = m33 = 1.0f;
		return this;
	}

	public Vector3 mult(final Vector3 vec) {
		final float vx = vec.x;
		final float vy = vec.y;
		final float vz = vec.z;

		return new Vector3(m00 * vx + m01 * vy + m02 * vz + m03, m10 * vx + m11
				* vy + m12 * vz + m13, m20 * vx + m21 * vy + m22 * vz + m23);
	}

	public Vector3 mult(final Vector3 vec, final Vector3 result) {
		final float vx = vec.x;
		final float vy = vec.y;
		final float vz = vec.z;

		result.x = m00 * vx + m01 * vy + m02 * vz + m03;
		result.y = m10 * vx + m11 * vy + m12 * vz + m13;
		result.z = m20 * vx + m21 * vy + m22 * vz + m23;

		return result;
	}

	public void inverseRotateVect(final Vector3 vec) {
		final float vx = vec.x, vy = vec.y, vz = vec.z;

		vec.x = vx * m00 + vy * m10 + vz * m20;
		vec.y = vx * m01 + vy * m11 + vz * m21;
		vec.z = vx * m02 + vy * m12 + vz * m22;
	}

	public void rotateVect(final Vector3 vec) {
		final float vx = vec.x, vy = vec.y, vz = vec.z;

		vec.x = vx * m00 + vy * m01 + vz * m02;
		vec.y = vx * m10 + vy * m11 + vz * m12;
		vec.z = vx * m20 + vy * m21 + vz * m22;
	}

	public Matrix4 mult(final Matrix4 in2, final Matrix4 result) {
		float temp00, temp01, temp02, temp03;
		float temp10, temp11, temp12, temp13;
		float temp20, temp21, temp22, temp23;
		float temp30, temp31, temp32, temp33;

		temp00 = m00 * in2.m00 + m01 * in2.m10 + m02 * in2.m20 + m03 * in2.m30;
		temp01 = m00 * in2.m01 + m01 * in2.m11 + m02 * in2.m21 + m03 * in2.m31;
		temp02 = m00 * in2.m02 + m01 * in2.m12 + m02 * in2.m22 + m03 * in2.m32;
		temp03 = m00 * in2.m03 + m01 * in2.m13 + m02 * in2.m23 + m03 * in2.m33;

		temp10 = m10 * in2.m00 + m11 * in2.m10 + m12 * in2.m20 + m13 * in2.m30;
		temp11 = m10 * in2.m01 + m11 * in2.m11 + m12 * in2.m21 + m13 * in2.m31;
		temp12 = m10 * in2.m02 + m11 * in2.m12 + m12 * in2.m22 + m13 * in2.m32;
		temp13 = m10 * in2.m03 + m11 * in2.m13 + m12 * in2.m23 + m13 * in2.m33;

		temp20 = m20 * in2.m00 + m21 * in2.m10 + m22 * in2.m20 + m23 * in2.m30;
		temp21 = m20 * in2.m01 + m21 * in2.m11 + m22 * in2.m21 + m23 * in2.m31;
		temp22 = m20 * in2.m02 + m21 * in2.m12 + m22 * in2.m22 + m23 * in2.m32;
		temp23 = m20 * in2.m03 + m21 * in2.m13 + m22 * in2.m23 + m23 * in2.m33;

		temp30 = m30 * in2.m00 + m31 * in2.m10 + m32 * in2.m20 + m33 * in2.m30;
		temp31 = m30 * in2.m01 + m31 * in2.m11 + m32 * in2.m21 + m33 * in2.m31;
		temp32 = m30 * in2.m02 + m31 * in2.m12 + m32 * in2.m22 + m33 * in2.m32;
		temp33 = m30 * in2.m03 + m31 * in2.m13 + m32 * in2.m23 + m33 * in2.m33;

		result.m00 = temp00;
		result.m01 = temp01;
		result.m02 = temp02;
		result.m03 = temp03;
		result.m10 = temp10;
		result.m11 = temp11;
		result.m12 = temp12;
		result.m13 = temp13;
		result.m20 = temp20;
		result.m21 = temp21;
		result.m22 = temp22;
		result.m23 = temp23;
		result.m30 = temp30;
		result.m31 = temp31;
		result.m32 = temp32;
		result.m33 = temp33;

		return result;
	}

	public Matrix4 multLocal(final Matrix4 in2) {

		return mult(in2, this);
	}

	public Matrix4 scale(final Vector3 scale) {
		m00 *= scale.x;
		m10 *= scale.x;
		m20 *= scale.x;
		m30 *= scale.x;
		m01 *= scale.y;
		m11 *= scale.y;
		m21 *= scale.y;
		m31 *= scale.y;
		m02 *= scale.z;
		m12 *= scale.z;
		m22 *= scale.z;
		m32 *= scale.z;
		return this;
	}

	public Matrix4 setTranslation(final Vector3 translation) {
		m03 = translation.x;
		m13 = translation.y;
		m23 = translation.z;
		return this;
	}

	public void fromAngleAxis(final float angle, final Vector3 axis) {
		final Vector3 normAxis = axis.normalize();
		fromAngleNormalAxis(angle, normAxis);
	}

	public void fromAngleNormalAxis(final float angle, final Vector3 axis) {
		zero();
		m33 = 1;

		final float fCos = FastMath.cos(angle);
		final float fSin = FastMath.sin(angle);
		final float fOneMinusCos = ((float) 1.0) - fCos;
		final float fX2 = axis.x * axis.x;
		final float fY2 = axis.y * axis.y;
		final float fZ2 = axis.z * axis.z;
		final float fXYM = axis.x * axis.y * fOneMinusCos;
		final float fXZM = axis.x * axis.z * fOneMinusCos;
		final float fYZM = axis.y * axis.z * fOneMinusCos;
		final float fXSin = axis.x * fSin;
		final float fYSin = axis.y * fSin;
		final float fZSin = axis.z * fSin;

		m00 = fX2 * fOneMinusCos + fCos;
		m01 = fXYM - fZSin;
		m02 = fXZM + fYSin;
		m10 = fXYM + fZSin;
		m11 = fY2 * fOneMinusCos + fCos;
		m12 = fYZM - fXSin;
		m20 = fXZM - fYSin;
		m21 = fYZM + fXSin;
		m22 = fZ2 * fOneMinusCos + fCos;
	}

	public void multLocal(final Quaternion rotation) {
		final Vector3 axis = new Vector3();
		final float angle = rotation.toAngleAxis(axis);
		final Matrix4 matrix4 = new Matrix4();
		matrix4.fromAngleAxis(angle, axis);
		multLocal(matrix4);
	}
}
