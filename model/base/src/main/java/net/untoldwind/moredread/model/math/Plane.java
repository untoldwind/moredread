package net.untoldwind.moredread.model.math;

public class Plane {
	public Vector3 normal;

	public float constant;

	public Plane() {
		normal = new Vector3();
	}

	public Plane(final Vector3 normal, final float constant) {
		this.normal = normal;
		this.constant = constant;
	}

	public float pseudoDistance(final Vector3 point) {
		return normal.dot(point) - constant;
	}

	public Vector3 getNormal() {
		return normal;
	}

	public void setNormal(final Vector3 normal) {
		this.normal = normal;
	}

	public float getConstant() {
		return constant;
	}

	public void setConstant(final float constant) {
		this.constant = constant;
	}

}
