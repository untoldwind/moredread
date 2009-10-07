package net.untoldwind.moredread.model.renderer;

import net.untoldwind.moredread.model.mesh.EdgeId;

import com.jme.renderer.ColorRGBA;

public class DefaultColorProvider implements IColorProvider {

	protected ColorRGBA defaultColor;

	public DefaultColorProvider(final ColorRGBA defaultColor) {
		this.defaultColor = defaultColor;
	}

	@Override
	public ColorRGBA getFaceColor(final int faceIndex) {
		return defaultColor;
	}

	@Override
	public ColorRGBA getEdgeColor(final EdgeId edgeIndex) {
		return defaultColor;
	}

}
