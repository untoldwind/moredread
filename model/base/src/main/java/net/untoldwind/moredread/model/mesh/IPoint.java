package net.untoldwind.moredread.model.mesh;

import net.untoldwind.moredread.model.math.Vector3;

public interface IPoint extends IGeometry<IPoint> {
	Vector3 getPoint();
}