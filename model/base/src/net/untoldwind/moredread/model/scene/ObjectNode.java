package net.untoldwind.moredread.model.scene;

import com.jme.renderer.ColorRGBA;

public abstract class ObjectNode extends AbstractSpatialNode implements IMeshNode {

	private ColorRGBA modelColor;

	public ObjectNode(final Group parent, final String name) {
		super(parent, name);

		modelColor = ColorRGBA.red.clone();
	}

	public ColorRGBA getModelColor() {
		return modelColor;
	}

	public ColorRGBA getModelColor(final float alpha) {
		return new ColorRGBA(modelColor.r, modelColor.g, modelColor.b, alpha);
	}

	public void setModelColor(final ColorRGBA modelColor) {
		this.modelColor = modelColor;
	}

}
