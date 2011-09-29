package net.untoldwind.moredread.model.scene;

import net.untoldwind.moredread.model.math.Quaternion;
import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.mesh.IPoint;
import net.untoldwind.moredread.model.mesh.IPolygon;
import net.untoldwind.moredread.model.transform.ITransformation;

/**
 * A generic node in the scene tree with spatial dimensions.
 * 
 * A nodes displayed in the 3D view have to be spatial nodes.
 */
public interface ISpatialNode extends INode {
	Quaternion getLocalRotation();

	Vector3 getLocalTranslation();

	Vector3 getLocalScale();

	Vector3 getWorldTranslation();

	ITransformation getLocalTransformation();

	BoundingBox getLocalBoundingBox();

	BoundingBox getWorldBoundingBox();

	Vector3 localToWorld(final Vector3 in, Vector3 store);

	IPoint localToWorld(final IPoint point);

	IPolygon localToWorld(final IPolygon polygon);

	Vector3 worldToLocal(final Vector3 in, final Vector3 store);
}
