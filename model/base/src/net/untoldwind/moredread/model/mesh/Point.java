package net.untoldwind.moredread.model.mesh;

import java.io.IOException;

import net.untoldwind.moredread.model.enums.GeometryType;
import net.untoldwind.moredread.model.state.IStateWriter;

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
	public GeometryType getGeometryType() {
		return GeometryType.POINT;
	}

	@Override
	public Vector3f getPoint() {
		return point;
	}

	@Override
	public void writeState(final IStateWriter writer) throws IOException {
		writer.writeVector3f("point", point);
	}

}
