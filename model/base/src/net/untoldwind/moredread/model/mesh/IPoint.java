package net.untoldwind.moredread.model.mesh;

import com.jme.math.Vector3f;

public interface IPoint extends IGeometry {
	int getIndex();

	Vector3f getPoint();
}
