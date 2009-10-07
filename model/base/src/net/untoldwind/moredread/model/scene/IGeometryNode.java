package net.untoldwind.moredread.model.scene;

import net.untoldwind.moredread.model.mesh.IGeometry;
import net.untoldwind.moredread.model.mesh.IPoint;
import net.untoldwind.moredread.model.mesh.IPolygon;

import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;

public interface IGeometryNode<RO_GEOMETRY extends IGeometry, RW_GEOMETRY extends IGeometry>
		extends INode {
	RO_GEOMETRY getRenderGeometry();

	RW_GEOMETRY getEditableGeometry(boolean forChange);

	ColorRGBA getModelColor(final float alpha);

	Vector3f localToWorld(final Vector3f in, Vector3f store);

	IPoint localToWorld(final IPoint point);

	IPolygon localToWorld(final IPolygon polygon);

	Vector3f worldToLocal(final Vector3f in, final Vector3f store);

}
