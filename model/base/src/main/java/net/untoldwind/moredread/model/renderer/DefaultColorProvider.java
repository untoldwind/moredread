package net.untoldwind.moredread.model.renderer;

import net.untoldwind.moredread.model.mesh.EdgeId;
import net.untoldwind.moredread.model.mesh.FaceId;

import com.jme.renderer.ColorRGBA;

public class DefaultColorProvider implements IColorProvider {

	protected ColorRGBA defaultColor;

	public DefaultColorProvider(final ColorRGBA defaultColor) {
		this.defaultColor = defaultColor;
	}

	@Override
	public ColorRGBA getFaceColor(final FaceId faceIndex) {
		return defaultColor;
	}

	@Override
	public boolean isFaceVisible(final FaceId faceIndex) {
		return true;
	}

	@Override
	public ColorRGBA getEdgeColor(final EdgeId edgeIndex) {
		return defaultColor;
	}

	@Override
	public boolean isEdgeVisible(final EdgeId edgeIndex) {
		return true;
	}

	@Override
	public ColorRGBA getVertexColor(final int vertexIndex) {
		return defaultColor;
	}

	@Override
	public boolean isVertexVisible(final int vertexIndex) {
		return true;
	}

}
