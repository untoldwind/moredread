package net.untoldwind.moredread.model.renderer;

import net.untoldwind.moredread.model.mesh.Mesh;

import com.jme.scene.Geometry;

public interface IMeshRendererAdapter {
	Geometry renderMesh(Mesh<?> mesh, final IColorProvider colorProvider);
}
