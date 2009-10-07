package net.untoldwind.moredread.model.mesh;

import com.jme.math.Vector3f;

public class Point implements IPoint {
	private final Vector3f point;

	public Point(final Vector3f point) {
		this.point = point;
	}

	public Point(final float x, final float y, final float z) {
		this.point = new Vector3f(x, y, z);
	}

	@Override
	public Vector3f getPoint() {
		return point;
	}
}
