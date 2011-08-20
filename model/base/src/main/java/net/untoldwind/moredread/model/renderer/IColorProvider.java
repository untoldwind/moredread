package net.untoldwind.moredread.model.renderer;

import net.untoldwind.moredread.model.mesh.EdgeId;

import com.jme.renderer.ColorRGBA;

public interface IColorProvider {
	boolean isFaceVisible(int faceIndex);

	ColorRGBA getFaceColor(int faceIndex);

	boolean isEdgeVisible(EdgeId edgeIndex);

	ColorRGBA getEdgeColor(EdgeId edgeIndex);
}
