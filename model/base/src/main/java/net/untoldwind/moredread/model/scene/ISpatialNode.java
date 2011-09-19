package net.untoldwind.moredread.model.scene;

import net.untoldwind.moredread.model.mesh.IPoint;
import net.untoldwind.moredread.model.mesh.IPolygon;
import net.untoldwind.moredread.model.transform.ITransformation;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

/**
 * A generic node in the scene tree with spatial dimensions.
 * 
 * A nodes displayed in the 3D view have to be spatial nodes.
 */
public interface ISpatialNode extends INode {
	Quaternion getLocalRotation();

	Vector3f getLocalTranslation();

	Vector3f getLocalScale();

	Vector3f getWorldTranslation();

	ITransformation getLocalTransformation();

	BoundingBox getLocalBoundingBox();

	BoundingBox getWorldBoundingBox();

	Vector3f localToWorld(final Vector3f in, Vector3f store);

	IPoint localToWorld(final IPoint point);

	IPolygon localToWorld(final IPolygon polygon);

	Vector3f worldToLocal(final Vector3f in, final Vector3f store);
}
