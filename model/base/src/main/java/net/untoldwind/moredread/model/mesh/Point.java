package net.untoldwind.moredread.model.mesh;

import java.io.IOException;

import net.untoldwind.moredread.model.enums.GeometryType;
import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.state.IStateReader;
import net.untoldwind.moredread.model.state.IStateWriter;
import net.untoldwind.moredread.model.transform.ITransformation;

public class Point implements IPoint {
	private Vector3 point;

	public Point(final Vector3 point) {
		this.point = point;
	}

	public Point(final float x, final float y, final float z) {
		this.point = new Vector3(x, y, z);
	}

	@Override
	public GeometryType getGeometryType() {
		return GeometryType.POINT;
	}

	@Override
	public Vector3 getPoint() {
		return point;
	}

	@Override
	public IPoint transform(final ITransformation transformation) {
		return new Point(transformation.transformPoint(point));
	}

	@Override
	public void readState(final IStateReader reader) throws IOException {
		point = reader.readVector3();
	}

	@Override
	public void writeState(final IStateWriter writer) throws IOException {
		writer.writeVector3("point", point);
	}

}
