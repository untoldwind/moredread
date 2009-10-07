package net.untoldwind.moredread.model.renderer;

import net.untoldwind.moredread.model.mesh.IMesh;

import com.jme.scene.Geometry;

public interface IMeshRendererAdapter {
	Geometry renderMesh(IMesh mesh, final IColorProvider colorProvider);
}
