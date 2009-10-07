package net.untoldwind.moredread.model.mesh;

import net.untoldwind.moredread.model.enums.GeometryType;

import com.jme.math.Vector3f;

public class Point implements IPoint {
	private final int index;
	private final Vector3f point;

	public Point(final int index, final Vector3f point) {
		this.index = index;
		this.point = point;
	}

	public Point(final int index, final float x, final float y, final float z) {
		this.index = index;
		this.point = new Vector3f(x, y, z);
	}

	@Override
	public GeometryType getGeometryType() {
		return GeometryType.POINT;
	}

	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public Vector3f getPoint() {
		return point;
	}
}
