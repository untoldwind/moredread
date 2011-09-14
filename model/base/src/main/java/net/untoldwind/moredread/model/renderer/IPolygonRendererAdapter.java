package net.untoldwind.moredread.model.renderer;

import net.untoldwind.moredread.model.mesh.IPolygon;

import com.jme.scene.Geometry;

public interface IPolygonRendererAdapter {
	Geometry renderPolygon(IPolygon polygon, final IColorProvider colorProvider);
}
