package net.untoldwind.moredread.model.renderer;

import net.untoldwind.moredread.model.mesh.EdgeId;

import com.jme.renderer.ColorRGBA;

public interface IColorProvider {
	ColorRGBA getFaceColor(int faceIndex);

	ColorRGBA getEdgeColor(EdgeId edgeIndex);
}
