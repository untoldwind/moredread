package net.untoldwind.moredread.model.mesh;

import net.untoldwind.moredread.model.transform.ITransformable;

import com.jme.math.Vector3f;

public interface IPoint extends IGeometry, ITransformable<IPoint> {
	Vector3f getPoint();
}