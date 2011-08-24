package net.untoldwind.moredread.model.op.bool.blebopd;

import java.util.logging.Logger;

import com.jme.math.Vector3f;

public class Vector3d {
	private static final Logger logger = Logger.getLogger(Vector3d.class
			.getName());

	public final static Vector3d ZERO = new Vector3d(0, 0, 0);

	public final static Vector3d UNIT_X = new Vector3d(1, 0, 0);
	public final static Vector3d UNIT_Y = new Vector3d(0, 1, 0);
	public final static Vector3d UNIT_Z = new Vector3d(0, 0, 1);
	public final static Vector3d UNIT_XYZ = new Vector3d(1, 1, 1);

	/**
	 * the x value of the vector.
	 */
	public double x;

	/**
	 * the y value of the vector.
	 */
	public double y;

	/**
	 * the z value of the vector.
	 */
	public double z;

	public Vector3d(final Vector3f v) {
		x = v.x;
		y = v.y;
		z = v.z;
	}

	/**
	 * Constructor instantiates a new <code>Vector3d</code> with default values
	 * of (0,0,0).
	 * 
	 */
	public Vector3d() {
		x = y = z = 0;
	}

	/**
	 * Constructor instantiates a new <code>Vector3d</code> with provides
	 * values.
	 * 
	 * @param x
	 *            the x value of the vector.
	 * @param y
	 *            the y value of the vector.
	 * @param z
	 *            the z value of the vector.
	 */
	public Vector3d(final double x, final double y, final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Constructor instantiates a new <code>Vector3d</code> that is a copy of
	 * the provided vector
	 * 
	 * @param copy
	 *            The Vector3d to copy
	 */
	public Vector3d(final Vector3d copy) {
		this.set(copy);
	}

	/**
	 * <code>set</code> sets the x,y,z values of the vector based on passed
	 * parameters.
	 * 
	 * @param x
	 *            the x value of the vector.
	 * @param y
	 *            the y value of the vector.
	 * @param z
	 *            the z value of the vector.
	 * @return this vector
	 */
	public Vector3d set(final double x, final double y, final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	/**
	 * <code>set</code> sets the x,y,z values of the vector by copying the
	 * supplied vector.
	 * 
	 * @param vect
	 *            the vector to copy.
	 * @return this vector
	 */
	public Vector3d set(final Vector3d vect) {
		this.x = vect.x;
		this.y = vect.y;
		this.z = vect.z;
		return this;
	}

	/**
	 * 
	 * <code>add</code> adds a provided vector to this vector creating a
	 * resultant vector which is returned. If the provided vector is null, null
	 * is returned.
	 * 
	 * Neither 'this' nor 'vec' are modified.
	 * 
	 * @param vec
	 *            the vector to add to this.
	 * @return the resultant vector.
	 */
	public Vector3d add(final Vector3d vec) {
		if (null == vec) {
			logger.warning("Provided vector is null, null returned.");
			return null;
		}
		return new Vector3d(x + vec.x, y + vec.y, z + vec.z);
	}

	/**
	 * 
	 * <code>add</code> adds the values of a provided vector storing the values
	 * in the supplied vector.
	 * 
	 * @param vec
	 *            the vector to add to this
	 * @param result
	 *            the vector to store the result in
	 * @return result returns the supplied result vector.
	 */
	public Vector3d add(final Vector3d vec, final Vector3d result) {
		result.x = x + vec.x;
		result.y = y + vec.y;
		result.z = z + vec.z;
		return result;
	}

	/**
	 * <code>addLocal</code> adds a provided vector to this vector internally,
	 * and returns a handle to this vector for easy chaining of calls. If the
	 * provided vector is null, null is returned.
	 * 
	 * @param vec
	 *            the vector to add to this vector.
	 * @return this
	 */
	public Vector3d addLocal(final Vector3d vec) {
		if (null == vec) {
			logger.warning("Provided vector is null, null returned.");
			return null;
		}
		x += vec.x;
		y += vec.y;
		z += vec.z;
		return this;
	}

	/**
	 * 
	 * <code>add</code> adds the provided values to this vector, creating a new
	 * vector that is then returned.
	 * 
	 * @param addX
	 *            the x value to add.
	 * @param addY
	 *            the y value to add.
	 * @param addZ
	 *            the z value to add.
	 * @return the result vector.
	 */
	public Vector3d add(final double addX, final double addY, final double addZ) {
		return new Vector3d(x + addX, y + addY, z + addZ);
	}

	/**
	 * <code>addLocal</code> adds the provided values to this vector internally,
	 * and returns a handle to this vector for easy chaining of calls.
	 * 
	 * @param addX
	 *            value to add to x
	 * @param addY
	 *            value to add to y
	 * @param addZ
	 *            value to add to z
	 * @return this
	 */
	public Vector3d addLocal(final double addX, final double addY,
			final double addZ) {
		x += addX;
		y += addY;
		z += addZ;
		return this;
	}

	/**
	 * 
	 * <code>scaleAdd</code> multiplies this vector by a scalar then adds the
	 * given Vector3d.
	 * 
	 * @param scalar
	 *            the value to multiply this vector by.
	 * @param add
	 *            the value to add
	 */
	public void scaleAdd(final double scalar, final Vector3d add) {
		x = x * scalar + add.x;
		y = y * scalar + add.y;
		z = z * scalar + add.z;
	}

	/**
	 * 
	 * <code>scaleAdd</code> multiplies the given vector by a scalar then adds
	 * the given vector.
	 * 
	 * @param scalar
	 *            the value to multiply this vector by.
	 * @param mult
	 *            the value to multiply the scalar by
	 * @param add
	 *            the value to add
	 */
	public void scaleAdd(final double scalar, final Vector3d mult,
			final Vector3d add) {
		this.x = mult.x * scalar + add.x;
		this.y = mult.y * scalar + add.y;
		this.z = mult.z * scalar + add.z;
	}

	/**
	 * 
	 * <code>dot</code> calculates the dot product of this vector with a
	 * provided vector. If the provided vector is null, 0 is returned.
	 * 
	 * @param vec
	 *            the vector to dot with this vector.
	 * @return the resultant dot product of this vector and a given vector.
	 */
	public double dot(final Vector3d vec) {
		if (null == vec) {
			logger.warning("Provided vector is null, 0 returned.");
			return 0;
		}
		return x * vec.x + y * vec.y + z * vec.z;
	}

	/**
	 * Returns a new vector which is the cross product of this vector with the
	 * specified vector.
	 * <P>
	 * Neither 'this' nor v are modified. The starting value of 'result'
	 * </P>
	 * 
	 * @param v
	 *            the vector to take the cross product of with this.
	 * @return the cross product vector.
	 */
	public Vector3d cross(final Vector3d v) {
		return cross(v, null);
	}

	/**
	 * <code>cross</code> calculates the cross product of this vector with a
	 * parameter vector v. The result is stored in <code>result</code>
	 * <P>
	 * Neither 'this' nor v are modified. The starting value of 'result' (if
	 * any) is ignored.
	 * </P>
	 * 
	 * @param v
	 *            the vector to take the cross product of with this.
	 * @param result
	 *            the vector to store the cross product result.
	 * @return result, after recieving the cross product vector.
	 */
	public Vector3d cross(final Vector3d v, final Vector3d result) {
		return cross(v.x, v.y, v.z, result);
	}

	/**
	 * <code>cross</code> calculates the cross product of this vector with a
	 * Vector comprised of the specified other* elements. The result is stored
	 * in <code>result</code>, without modifying either 'this' or the 'other*'
	 * values.
	 * 
	 * @param otherX
	 *            x component of the vector to take the cross product of with
	 *            this.
	 * @param otherY
	 *            y component of the vector to take the cross product of with
	 *            this.
	 * @param otherZ
	 *            z component of the vector to take the cross product of with
	 *            this.
	 * @param result
	 *            the vector to store the cross product result.
	 * @return result, after recieving the cross product vector.
	 */
	public Vector3d cross(final double otherX, final double otherY,
			final double otherZ, Vector3d result) {
		if (result == null) {
			result = new Vector3d();
		}
		final double resX = ((y * otherZ) - (z * otherY));
		final double resY = ((z * otherX) - (x * otherZ));
		final double resZ = ((x * otherY) - (y * otherX));
		result.set(resX, resY, resZ);
		return result;
	}

	/**
	 * <code>crossLocal</code> calculates the cross product of this vector with
	 * a parameter vector v.
	 * 
	 * @param v
	 *            the vector to take the cross product of with this.
	 * @return this.
	 */
	public Vector3d crossLocal(final Vector3d v) {
		return crossLocal(v.x, v.y, v.z);
	}

	/**
	 * <code>crossLocal</code> calculates the cross product of this vector with
	 * a parameter vector v.
	 * 
	 * @param otherX
	 *            x component of the vector to take the cross product of with
	 *            this.
	 * @param otherY
	 *            y component of the vector to take the cross product of with
	 *            this.
	 * @param otherZ
	 *            z component of the vector to take the cross product of with
	 *            this.
	 * @return this.
	 */
	public Vector3d crossLocal(final double otherX, final double otherY,
			final double otherZ) {
		final double tempx = (y * otherZ) - (z * otherY);
		final double tempy = (z * otherX) - (x * otherZ);
		z = (x * otherY) - (y * otherX);
		x = tempx;
		y = tempy;
		return this;
	}

	/**
	 * <code>length</code> calculates the magnitude of this vector.
	 * 
	 * @return the length or magnitude of the vector.
	 */
	public double length() {
		return Math.sqrt(lengthSquared());
	}

	/**
	 * <code>lengthSquared</code> calculates the squared value of the magnitude
	 * of the vector.
	 * 
	 * @return the magnitude squared of the vector.
	 */
	public double lengthSquared() {
		return x * x + y * y + z * z;
	}

	/**
	 * <code>distanceSquared</code> calculates the distance squared between this
	 * vector and vector v.
	 * 
	 * @param v
	 *            the second vector to determine the distance squared.
	 * @return the distance squared between the two vectors.
	 */
	public double distanceSquared(final Vector3d v) {
		final double dx = x - v.x;
		final double dy = y - v.y;
		final double dz = z - v.z;
		return (dx * dx + dy * dy + dz * dz);
	}

	/**
	 * <code>distance</code> calculates the distance between this vector and
	 * vector v.
	 * 
	 * @param v
	 *            the second vector to determine the distance.
	 * @return the distance between the two vectors.
	 */
	public double distance(final Vector3d v) {
		return Math.sqrt(distanceSquared(v));
	}

	/**
	 * <code>mult</code> multiplies this vector by a scalar. The resultant
	 * vector is returned. "this" is not modified.
	 * 
	 * @param scalar
	 *            the value to multiply this vector by.
	 * @return the new vector.
	 */
	public Vector3d mult(final double scalar) {
		return new Vector3d(x * scalar, y * scalar, z * scalar);
	}

	/**
	 * 
	 * <code>mult</code> multiplies this vector by a scalar. The resultant
	 * vector is supplied as the second parameter and returned. "this" is not
	 * modified.
	 * 
	 * @param scalar
	 *            the scalar to multiply this vector by.
	 * @param product
	 *            the product to store the result in.
	 * @return product
	 */
	public Vector3d mult(final double scalar, Vector3d product) {
		if (null == product) {
			product = new Vector3d();
		}

		product.x = x * scalar;
		product.y = y * scalar;
		product.z = z * scalar;
		return product;
	}

	/**
	 * <code>multLocal</code> multiplies this vector by a scalar internally, and
	 * returns a handle to this vector for easy chaining of calls.
	 * 
	 * @param scalar
	 *            the value to multiply this vector by.
	 * @return this
	 */
	public Vector3d multLocal(final double scalar) {
		x *= scalar;
		y *= scalar;
		z *= scalar;
		return this;
	}

	/**
	 * <code>multLocal</code> multiplies a provided vector to this vector
	 * internally, and returns a handle to this vector for easy chaining of
	 * calls. If the provided vector is null, null is returned. The provided
	 * 'vec' is not modified.
	 * 
	 * @param vec
	 *            the vector to mult to this vector.
	 * @return this
	 */
	public Vector3d multLocal(final Vector3d vec) {
		if (null == vec) {
			logger.warning("Provided vector is null, null returned.");
			return null;
		}
		x *= vec.x;
		y *= vec.y;
		z *= vec.z;
		return this;
	}

	/**
	 * Returns a new Vector instance comprised of elements which are the product
	 * of the corresponding vector elements. (N.b. this is not a cross product).
	 * <P>
	 * Neither 'this' nor 'vec' are modified.
	 * </P>
	 * 
	 * @param vec
	 *            the vector to mult to this vector.
	 */
	public Vector3d mult(final Vector3d vec) {
		if (null == vec) {
			logger.warning("Provided vector is null, null returned.");
			return null;
		}
		return mult(vec, null);
	}

	/**
	 * Multiplies a provided 'vec' vector with this vector. If the specified
	 * 'store' is null, then a new Vector instance is returned. Otherwise,
	 * 'store' with replaced values will be returned, to facilitate chaining.
	 * </P>
	 * <P>
	 * 'This' is not modified; and the starting value of 'store' (if any) is
	 * ignored (and over-written).
	 * <P>
	 * The resultant Vector is comprised of elements which are the product of
	 * the corresponding vector elements. (N.b. this is not a cross product).
	 * </P>
	 * 
	 * @param vec
	 *            the vector to mult to this vector.
	 * @param store
	 *            result vector (null to create a new vector)
	 * @return 'store', or a new Vector3d
	 */
	public Vector3d mult(final Vector3d vec, Vector3d store) {
		if (null == vec) {
			logger.warning("Provided vector is null, null returned.");
			return null;
		}
		if (store == null) {
			store = new Vector3d();
		}
		return store.set(x * vec.x, y * vec.y, z * vec.z);
	}

	/**
	 * <code>divide</code> divides the values of this vector by a scalar and
	 * returns the result. The values of this vector remain untouched.
	 * 
	 * @param scalar
	 *            the value to divide this vectors attributes by.
	 * @return the result <code>Vector</code>.
	 */
	public Vector3d divide(double scalar) {
		scalar = 1f / scalar;
		return new Vector3d(x * scalar, y * scalar, z * scalar);
	}

	/**
	 * <code>divideLocal</code> divides this vector by a scalar internally, and
	 * returns a handle to this vector for easy chaining of calls. Dividing by
	 * zero will result in an exception.
	 * 
	 * @param scalar
	 *            the value to divides this vector by.
	 * @return this
	 */
	public Vector3d divideLocal(double scalar) {
		scalar = 1f / scalar;
		x *= scalar;
		y *= scalar;
		z *= scalar;
		return this;
	}

	/**
	 * <code>divide</code> divides the values of this vector by a scalar and
	 * returns the result. The values of this vector remain untouched.
	 * 
	 * @param scalar
	 *            the value to divide this vectors attributes by.
	 * @return the result <code>Vector</code>.
	 */
	public Vector3d divide(final Vector3d scalar) {
		return new Vector3d(x / scalar.x, y / scalar.y, z / scalar.z);
	}

	/**
	 * <code>divideLocal</code> divides this vector by a scalar internally, and
	 * returns a handle to this vector for easy chaining of calls. Dividing by
	 * zero will result in an exception.
	 * 
	 * @param scalar
	 *            the value to divides this vector by.
	 * @return this
	 */
	public Vector3d divideLocal(final Vector3d scalar) {
		x /= scalar.x;
		y /= scalar.y;
		z /= scalar.z;
		return this;
	}

	/**
	 * 
	 * <code>negate</code> returns the negative of this vector. All values are
	 * negated and set to a new vector.
	 * 
	 * @return the negated vector.
	 */
	public Vector3d negate() {
		return new Vector3d(-x, -y, -z);
	}

	/**
	 * 
	 * <code>negateLocal</code> negates the internal values of this vector.
	 * 
	 * @return this.
	 */
	public Vector3d negateLocal() {
		x = -x;
		y = -y;
		z = -z;
		return this;
	}

	/**
	 * 
	 * <code>subtract</code> subtracts the values of a given vector from those
	 * of this vector creating a new vector object. If the provided vector is
	 * null, null is returned.
	 * 
	 * @param vec
	 *            the vector to subtract from this vector.
	 * @return the result vector.
	 */
	public Vector3d subtract(final Vector3d vec) {
		return new Vector3d(x - vec.x, y - vec.y, z - vec.z);
	}

	/**
	 * <code>subtractLocal</code> subtracts a provided vector to this vector
	 * internally, and returns a handle to this vector for easy chaining of
	 * calls. If the provided vector is null, null is returned.
	 * 
	 * @param vec
	 *            the vector to subtract
	 * @return this
	 */
	public Vector3d subtractLocal(final Vector3d vec) {
		if (null == vec) {
			logger.warning("Provided vector is null, null returned.");
			return null;
		}
		x -= vec.x;
		y -= vec.y;
		z -= vec.z;
		return this;
	}

	/**
	 * 
	 * <code>subtract</code>
	 * 
	 * @param vec
	 *            the vector to subtract from this
	 * @param result
	 *            the vector to store the result in
	 * @return result
	 */
	public Vector3d subtract(final Vector3d vec, Vector3d result) {
		if (result == null) {
			result = new Vector3d();
		}
		result.x = x - vec.x;
		result.y = y - vec.y;
		result.z = z - vec.z;
		return result;
	}

	/**
	 * 
	 * <code>subtract</code> subtracts the provided values from this vector,
	 * creating a new vector that is then returned.
	 * 
	 * @param subtractX
	 *            the x value to subtract.
	 * @param subtractY
	 *            the y value to subtract.
	 * @param subtractZ
	 *            the z value to subtract.
	 * @return the result vector.
	 */
	public Vector3d subtract(final double subtractX, final double subtractY,
			final double subtractZ) {
		return new Vector3d(x - subtractX, y - subtractY, z - subtractZ);
	}

	/**
	 * <code>subtractLocal</code> subtracts the provided values from this vector
	 * internally, and returns a handle to this vector for easy chaining of
	 * calls.
	 * 
	 * @param subtractX
	 *            the x value to subtract.
	 * @param subtractY
	 *            the y value to subtract.
	 * @param subtractZ
	 *            the z value to subtract.
	 * @return this
	 */
	public Vector3d subtractLocal(final double subtractX,
			final double subtractY, final double subtractZ) {
		x -= subtractX;
		y -= subtractY;
		z -= subtractZ;
		return this;
	}

	/**
	 * <code>normalize</code> returns the unit vector of this vector.
	 * 
	 * @return unit vector of this vector.
	 */
	public Vector3d normalize() {
		final double length = length();
		if (length != 0) {
			return divide(length);
		}

		return divide(1);
	}

	/**
	 * <code>normalizeLocal</code> makes this vector into a unit vector of
	 * itself.
	 * 
	 * @return this.
	 */
	public Vector3d normalizeLocal() {
		final double length = length();
		if (length != 0) {
			return divideLocal(length);
		}

		return this;
	}

	/**
	 * <code>zero</code> resets this vector's data to zero internally.
	 */
	public void zero() {
		x = y = z = 0;
	}

	/**
	 * <code>angleBetween</code> returns (in radians) the angle between two
	 * vectors. It is assumed that both this vector and the given vector are
	 * unit vectors (iow, normalized).
	 * 
	 * @param otherVector
	 *            a unit vector to find the angle against
	 * @return the angle in radians.
	 */
	public double angleBetween(final Vector3d otherVector) {
		final double dotProduct = dot(otherVector);
		final double angle = Math.acos(dotProduct);
		return angle;
	}

	/**
	 * Sets this vector to the interpolation by changeAmnt from this to the
	 * finalVec this=(1-changeAmnt)*this + changeAmnt * finalVec
	 * 
	 * @param finalVec
	 *            The final vector to interpolate towards
	 * @param changeAmnt
	 *            An amount between 0.0 - 1.0 representing a precentage change
	 *            from this towards finalVec
	 */
	public void interpolate(final Vector3d finalVec, final double changeAmnt) {
		this.x = (1 - changeAmnt) * this.x + changeAmnt * finalVec.x;
		this.y = (1 - changeAmnt) * this.y + changeAmnt * finalVec.y;
		this.z = (1 - changeAmnt) * this.z + changeAmnt * finalVec.z;
	}

	/**
	 * Sets this vector to the interpolation by changeAmnt from beginVec to
	 * finalVec this=(1-changeAmnt)*beginVec + changeAmnt * finalVec
	 * 
	 * @param beginVec
	 *            the beging vector (changeAmnt=0)
	 * @param finalVec
	 *            The final vector to interpolate towards
	 * @param changeAmnt
	 *            An amount between 0.0 - 1.0 representing a precentage change
	 *            from beginVec towards finalVec
	 */
	public void interpolate(final Vector3d beginVec, final Vector3d finalVec,
			final double changeAmnt) {
		this.x = (1 - changeAmnt) * beginVec.x + changeAmnt * finalVec.x;
		this.y = (1 - changeAmnt) * beginVec.y + changeAmnt * finalVec.y;
		this.z = (1 - changeAmnt) * beginVec.z + changeAmnt * finalVec.z;
	}

	/**
	 * Check a vector... if it is null or its doubles are NaN or infinite,
	 * return false. Else return true.
	 * 
	 * @param vector
	 *            the vector to check
	 * @return true or false as stated above.
	 */
	public static boolean isValidVector(final Vector3d vector) {
		if (vector == null) {
			return false;
		}
		if (Double.isNaN(vector.x) || Double.isNaN(vector.y)
				|| Double.isNaN(vector.z)) {
			return false;
		}
		if (Double.isInfinite(vector.x) || Double.isInfinite(vector.y)
				|| Double.isInfinite(vector.z)) {
			return false;
		}
		return true;
	}

	public static void generateOrthonormalBasis(final Vector3d u,
			final Vector3d v, final Vector3d w) {
		w.normalizeLocal();
		generateComplementBasis(u, v, w);
	}

	public static void generateComplementBasis(final Vector3d u,
			final Vector3d v, final Vector3d w) {
		double fInvLength;

		if (Math.abs(w.x) >= Math.abs(w.y)) {
			// w.x or w.z is the largest magnitude component, swap them
			fInvLength = (1.0 / Math.sqrt(w.x * w.x + w.z * w.z));
			u.x = -w.z * fInvLength;
			u.y = 0.0f;
			u.z = +w.x * fInvLength;
			v.x = w.y * u.z;
			v.y = w.z * u.x - w.x * u.z;
			v.z = -w.y * u.x;
		} else {
			// w.y or w.z is the largest magnitude component, swap them
			fInvLength = (1.0 / Math.sqrt(w.y * w.y + w.z * w.z));
			u.x = 0.0f;
			u.y = +w.z * fInvLength;
			u.z = -w.y * fInvLength;
			v.x = w.y * u.z - w.z * u.y;
			v.y = -w.x * u.z;
			v.z = w.x * u.y;
		}
	}

	@Override
	public Vector3d clone() {
		try {
			return (Vector3d) super.clone();
		} catch (final CloneNotSupportedException e) {
			throw new AssertionError(); // can not happen
		}
	}

	/**
	 * Saves this Vector3d into the given double[] object.
	 * 
	 * @param doubles
	 *            The double[] to take this Vector3d. If null, a new double[3]
	 *            is created.
	 * @return The array, with X, Y, Z double values in that order
	 */
	public double[] toArray(double[] doubles) {
		if (doubles == null) {
			doubles = new double[3];
		}
		doubles[0] = x;
		doubles[1] = y;
		doubles[2] = z;
		return doubles;
	}

	/**
	 * are these two vectors the same? they are is they both have the same x,y,
	 * and z values.
	 * 
	 * @param o
	 *            the object to compare for equality
	 * @return true if they are equal
	 */
	@Override
	public boolean equals(final Object o) {
		if (!(o instanceof Vector3d)) {
			return false;
		}

		if (this == o) {
			return true;
		}

		final Vector3d comp = (Vector3d) o;
		if (Double.compare(x, comp.x) != 0) {
			return false;
		}
		if (Double.compare(y, comp.y) != 0) {
			return false;
		}
		if (Double.compare(z, comp.z) != 0) {
			return false;
		}
		return true;
	}

	/**
	 * <code>hashCode</code> returns a unique code for this vector object based
	 * on it's values. If two vectors are logically equivalent, they will return
	 * the same hash code value.
	 * 
	 * @return the hash code value of this vector.
	 */
	@Override
	public int hashCode() {
		long hash = 37;
		hash += 37 * hash + Double.doubleToLongBits(x);
		hash += 37 * hash + Double.doubleToLongBits(y);
		hash += 37 * hash + Double.doubleToLongBits(z);
		return (int) (hash ^ (hash >>> 32));
	}

	/**
	 * <code>toString</code> returns the string representation of this vector.
	 * The format is:
	 * 
	 * org.jme.math.Vector3d [X=XX.XXXX, Y=YY.YYYY, Z=ZZ.ZZZZ]
	 * 
	 * @return the string representation of this vector.
	 */
	@Override
	public String toString() {
		return Vector3d.class.getName() + " [X=" + x + ", Y=" + y + ", Z=" + z
				+ "]";
	}

	public double getX() {
		return x;
	}

	public void setX(final double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(final double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(final double z) {
		this.z = z;
	}

	/**
	 * @param index
	 * @return x value if index == 0, y value if index == 1 or z value if index
	 *         == 2
	 * @throws IllegalArgumentException
	 *             if index is not one of 0, 1, 2.
	 */
	public double get(final int index) {
		switch (index) {
		case 0:
			return x;
		case 1:
			return y;
		case 2:
			return z;
		}
		throw new IllegalArgumentException("index must be either 0, 1 or 2");
	}

	/**
	 * @param index
	 *            which field index in this vector to set.
	 * @param value
	 *            to set to one of x, y or z.
	 * @throws IllegalArgumentException
	 *             if index is not one of 0, 1, 2.
	 */
	public void set(final int index, final double value) {
		switch (index) {
		case 0:
			x = value;
			return;
		case 1:
			y = value;
			return;
		case 2:
			z = value;
			return;
		}
		throw new IllegalArgumentException("index must be either 0, 1 or 2");
	}

	public Vector3f toVector3f() {
		return new Vector3f((float) x, (float) y, (float) z);
	}
}
