package net.untoldwind.moredread.model.op.bool.blebopd;

import java.util.logging.Logger;

public class Plane3d {

	private static final Logger logger = Logger.getLogger(Plane3d.class
			.getName());

	/** Vector normal to the Plane3d. */
	public Vector3d normal;

	/** Constant of the Plane3d. See formula in class definition. */
	public double constant;

	/**
	 * Constructor instantiates a new <code>Plane3d</code> object. This is the
	 * default object and contains a normal of (0,0,0) and a constant of 0.
	 */
	public Plane3d() {
		normal = new Vector3d();
	}

	/**
	 * Constructor instantiates a new <code>Plane3d</code> object. The normal
	 * and constant values are set at creation.
	 * 
	 * @param normal
	 *            the normal of the Plane3d.
	 * @param constant
	 *            the constant of the Plane3d.
	 */
	public Plane3d(Vector3d normal, final double constant) {
		if (normal == null) {
			logger.warning("Normal was null, created default normal.");
			normal = new Vector3d();
		}
		this.normal = normal;
		this.constant = constant;
	}

	/**
	 * <code>setNormal</code> sets the normal of the Plane3d.
	 * 
	 * @param normal
	 *            the new normal of the Plane3d.
	 */
	public void setNormal(Vector3d normal) {
		if (normal == null) {
			logger.warning("Normal was null, created default normal.");
			normal = new Vector3d();
		}
		this.normal = normal;
	}

	/**
	 * <code>getNormal</code> retrieves the normal of the Plane3d.
	 * 
	 * @return the normal of the Plane3d.
	 */
	public Vector3d getNormal() {
		return normal;
	}

	/**
	 * <code>setConstant</code> sets the constant value that helps define the
	 * Plane3d.
	 * 
	 * @param constant
	 *            the new constant value.
	 */
	public void setConstant(final double constant) {
		this.constant = constant;
	}

	/**
	 * <code>getConstant</code> returns the constant of the Plane3d.
	 * 
	 * @return the constant of the Plane3d.
	 */
	public double getConstant() {
		return constant;
	}

	/**
	 * <code>pseudoDistance</code> calculates the distance from this Plane3d to
	 * a provided point. If the point is on the negative side of the Plane3d the
	 * distance returned is negative, otherwise it is positive. If the point is
	 * on the Plane3d, it is zero.
	 * 
	 * @param point
	 *            the point to check.
	 * @return the signed distance from the Plane3d to a point.
	 */
	public double pseudoDistance(final Vector3d point) {
		return normal.dot(point) - constant;
	}

	/**
	 * Determine on which side of this Plane3d the point {@code p} lies.
	 * 
	 * @param p
	 *            the point to check.
	 * @return the side at which the point lies.
	 */
	public Side whichSide(final Vector3d p) {
		final double dis = pseudoDistance(p);
		if (dis < 0) {
			return Side.NEGATIVE;
		}
		if (dis > 0) {
			return Side.POSITIVE;
		}
		return Side.NONE;
	}

	/**
	 * Initialize the Plane3d using the given 3 points as coplanar.
	 * 
	 * @param v1
	 *            the first point
	 * @param v2
	 *            the second point
	 * @param v3
	 *            the third point
	 */
	public void setPlane3dPoints(final Vector3d v1, final Vector3d v2,
			final Vector3d v3) {
		normal.set(v2).subtractLocal(v1);
		normal.crossLocal(v3.x - v1.x, v3.y - v1.y, v3.z - v1.z)
				.normalizeLocal();
		constant = normal.dot(v1);
	}

	/**
	 * <code>toString</code> returns a string thta represents the string
	 * representation of this Plane3d. It represents the normal as a
	 * <code>Vector3d</code> object, so the format is the following:
	 * com.jme.math.Plane3d [Normal: org.jme.math.Vector3d [X=XX.XXXX,
	 * Y=YY.YYYY, Z=ZZ.ZZZZ] - Constant: CC.CCCCC]
	 * 
	 * @return the string representation of this Plane3d.
	 */
	@Override
	public String toString() {
		return "com.jme.math.Plane3d [Normal: " + normal + " - Constant: "
				+ constant + "]";
	}

	@Override
	public Plane3d clone() {
		try {
			final Plane3d p = (Plane3d) super.clone();
			p.normal = normal.clone();
			return p;
		} catch (final CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}

	public static enum Side {
		/** A point on the side opposite the normal to the Plane3d. */
		NEGATIVE,
		/** A point on the Plane3d itself. */
		NONE,
		/** A point on the side of the normal to the Plane3d. */
		POSITIVE
	}

}
