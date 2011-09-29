package net.untoldwind.moredread.model.math;

import com.jme.math.FastMath;

public class Matrix3 {
	public float m00, m01, m02;
	public float m10, m11, m12;
	public float m20, m21, m22;

	public Matrix3() {
		setIdentity();
	}

	public Matrix3 setIdentity() {
		m01 = m02 = m10 = m12 = m20 = m21 = 0;
		m00 = m11 = m22 = 1;
		return this;
	}

	public void set(final Quaternion quaternion) {
		quaternion.toRotationMatrix(this);
	}

	public Matrix3 mult(final Matrix3 mat) {
		return mult(mat, new Matrix3());
	}

	public Matrix3 mult(final Matrix3 mat, final Matrix3 product) {
		float temp00, temp01, temp02;
		float temp10, temp11, temp12;
		float temp20, temp21, temp22;

		temp00 = m00 * mat.m00 + m01 * mat.m10 + m02 * mat.m20;
		temp01 = m00 * mat.m01 + m01 * mat.m11 + m02 * mat.m21;
		temp02 = m00 * mat.m02 + m01 * mat.m12 + m02 * mat.m22;
		temp10 = m10 * mat.m00 + m11 * mat.m10 + m12 * mat.m20;
		temp11 = m10 * mat.m01 + m11 * mat.m11 + m12 * mat.m21;
		temp12 = m10 * mat.m02 + m11 * mat.m12 + m12 * mat.m22;
		temp20 = m20 * mat.m00 + m21 * mat.m10 + m22 * mat.m20;
		temp21 = m20 * mat.m01 + m21 * mat.m11 + m22 * mat.m21;
		temp22 = m20 * mat.m02 + m21 * mat.m12 + m22 * mat.m22;

		product.m00 = temp00;
		product.m01 = temp01;
		product.m02 = temp02;
		product.m10 = temp10;
		product.m11 = temp11;
		product.m12 = temp12;
		product.m20 = temp20;
		product.m21 = temp21;
		product.m22 = temp22;

		return product;
	}

	public Vector3 mult(final Vector3 vec) {
		return mult(vec, new Vector3());
	}

	public Vector3 mult(final Vector3 vec, final Vector3 product) {
		final float x = vec.x;
		final float y = vec.y;
		final float z = vec.z;

		product.x = m00 * x + m01 * y + m02 * z;
		product.y = m10 * x + m11 * y + m12 * z;
		product.z = m20 * x + m21 * y + m22 * z;
		return product;
	}

	public void fromAngleAxis(final float angle, final Vector3 axis) {
		final Vector3 normAxis = axis.normalize();
		fromAngleNormalAxis(angle, normAxis);
	}

	public void fromAngleNormalAxis(final float angle, final Vector3 axis) {
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

	public void scale(final Vector3 scale) {
		m00 *= scale.x;
		m10 *= scale.x;
		m20 *= scale.x;
		m01 *= scale.y;
		m11 *= scale.y;
		m21 *= scale.y;
		m02 *= scale.z;
		m12 *= scale.z;
		m22 *= scale.z;
	}

}
