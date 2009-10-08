package net.untoldwind.moredread.model.op.bool;

import com.jme.math.Plane;
import com.jme.math.Vector3f;

public class MathUtils {
	private static final boolean VAR_EPSILON = true;
	private static final float EPSILON = 9.3132257461547852e-10f;

	/**
	 * Helper class to return result tuple of frexp()
	 */
	public static class FRExpResultf {
		/**
		 * normalised mantissa
		 */
		public float mantissa;
		/**
		 * exponent of floating point representation
		 */
		public int exponent;
	}

	/**
	 * An implementation of the C standard library frexp() function.
	 * 
	 * @param value
	 *            the number to split
	 * @param result
	 * 
	 * @return a touple of normalised mantissa and exponent
	 */
	public static final FRExpResultf frexp(float value,
			final FRExpResultf result) {
		int i = 0;
		if (value != 0.0f) {
			int sign = 1;
			if (value < 0f) {
				sign = -1;
				value = -value;
			}
			// slow...
			while (value < 0.5f) {
				value = value * 2.0f;
				i = i - 1;
			}
			while (value >= 1.0f) {
				value = value * 0.5f;
				i = i + 1;
			}
			value = value * sign;
		}

		result.mantissa = value;
		result.exponent = i;

		return (result);
	}

	/**
	 * An implementation of the C standard library frexp() function.
	 * 
	 * @param value
	 *            the number to split
	 * @return a touple of normalised mantissa and exponent
	 */
	public static final FRExpResultf frexp(final float value) {
		return (frexp(value, new FRExpResultf()));
	}

	/**
	 * Compares two scalars with EPSILON accuracy.
	 * 
	 * @param A
	 *            scalar
	 * @param B
	 *            scalar
	 * @return 1 if A > B, -1 if A < B, 0 otherwise
	 */

	public static int comp(final float A, final float B) {
		if (!VAR_EPSILON) {
			if (A >= B + EPSILON) {
				return 1;
			} else if (B >= A + EPSILON) {
				return -1;
			} else {
				return 0;
			}
		} else {
			FRExpResultf expA = frexp(A);
			final FRExpResultf expB = frexp(B);

			if (expA.exponent < expB.exponent) {
				expA = expB;
			}
			frexp(A - B, expB); /* get exponent of the difference */
			/*
			 * mantissa will only be zero is (A-B) is really zero; otherwise,
			 * also also allow a "reasonably" small exponent or
			 * "reasonably large" difference in exponents to be considers
			 * "close to zero"
			 */
			if (expB.mantissa == 0 || expB.exponent < -30
					|| expA.exponent - expB.exponent > 31) {
				return 0;
			} else if (expB.mantissa > 0) {
				return 1;
			} else {
				return -1;
			}
		}
	}

	/**
	 * Compares a scalar with EPSILON accuracy.
	 * 
	 * @param A
	 *            scalar
	 * @return 1 if A > 0, -1 if A < 0, 0 otherwise
	 */

	public static int comp0(final float A) {
		if (A >= EPSILON) {
			return 1;
		} else if (0 >= A + EPSILON) {
			return -1;
		} else {
			return 0;
		}
	}

	public static boolean fuzzyZero(final float x) {
		return comp0(x) == 0;
	}

	/**
	 * Classifies a point according to the specified plane with EPSILON
	 * accuracy.
	 * 
	 * @param p
	 *            point
	 * @param plane
	 *            plane
	 * @return >0 if the point is above (OUT), =0 if the point is on (ON), <0 if
	 *         the point is below (IN)
	 */
	public static int classify(final Vector3f p, final Plane plane) {
		// Compare plane - point distance with zero
		return comp0(plane.pseudoDistance(p));
	}

}
