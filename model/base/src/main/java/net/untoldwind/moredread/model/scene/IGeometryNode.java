package net.untoldwind.moredread.model.scene;

import net.untoldwind.moredread.model.mesh.IGeometry;

import com.jme.renderer.ColorRGBA;

public interface IGeometryNode<RO_GEOMETRY extends IGeometry, RW_GEOMETRY extends IGeometry>
		extends ISpatialNode {
	RO_GEOMETRY getRenderGeometry();

	RO_GEOMETRY getGeometry();

	RW_GEOMETRY getEditableGeometry();

	ColorRGBA getModelColor(final float alpha);
}
