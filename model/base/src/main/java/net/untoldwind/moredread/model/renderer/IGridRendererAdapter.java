package net.untoldwind.moredread.model.renderer;

import net.untoldwind.moredread.model.mesh.IGrid;

import com.jme.scene.Geometry;

public interface IGridRendererAdapter {
	Geometry renderGrid(IGrid grid, final IColorProvider colorProvider);
}
