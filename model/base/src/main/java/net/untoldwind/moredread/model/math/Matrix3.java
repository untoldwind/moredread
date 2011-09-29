package net.untoldwind.moredread.model.math;

import com.jme.math.Matrix3f;

public class Matrix3 extends Matrix3f {
	private static final long serialVersionUID = 1L;

	public Matrix3() {
		super();
	}

	public Matrix3(final float m00, final float m01, final float m02,
			final float m10, final float m11, final float m12, final float m20,
			final float m21, final float m22) {
		super(m00, m01, m02, m10, m11, m12, m20, m21, m22);
	}

	public Matrix3(final Matrix3f mat) {
		super(mat);
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

}
