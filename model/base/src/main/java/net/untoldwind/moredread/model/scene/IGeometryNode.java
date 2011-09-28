package net.untoldwind.moredread.model.scene;

import net.untoldwind.moredread.model.enums.GeometryType;
import net.untoldwind.moredread.model.mesh.IGeometry;

import com.jme.renderer.ColorRGBA;

public interface IGeometryNode<RO_GEOMETRY extends IGeometry<?>, RW_GEOMETRY extends IGeometry<?>>
		extends ISpatialNode {
	GeometryType getGeometryType();

	RO_GEOMETRY getRenderGeometry();

	RO_GEOMETRY getGeometry();

	RW_GEOMETRY getEditableGeometry();

	void setGeometry(RW_GEOMETRY geometry);

	ColorRGBA getModelColor(final float alpha);
}
