package net.untoldwind.moredread.ui.controls.impl;

import java.util.List;

import net.untoldwind.moredread.model.enums.CartesianDirection;
import net.untoldwind.moredread.ui.controls.IControlHandle;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.controls.IViewport;
import net.untoldwind.moredread.ui.tools.spi.IToolAdapter;

import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Line;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.shape.Pyramid;

public class DirectionArrowModelControl extends Node implements IModelControl {
	private static final long serialVersionUID = 1L;

	private final CartesianDirection direction;

	private final IToolAdapter toolAdapter;
	private final ColorRGBA color;
	private final Line line;
	private final Pyramid pyramid;

	private transient LineControlHandle lineControlHandle;

	public DirectionArrowModelControl(final CartesianDirection direction,
			final IToolAdapter toolAdapter) {
		super(direction + "ArrowControl");

		this.toolAdapter = toolAdapter;
		this.direction = direction;

		switch (direction) {
		case X:
			color = ColorRGBA.red.clone();
			break;
		case Y:
			color = ColorRGBA.green.clone();
			break;
		case Z:
			color = ColorRGBA.blue.clone();
			break;
		default:
			color = ColorRGBA.white.clone();
			break;
		}

		line = new Line(direction + "Line", new Vector3f[] {
				new Vector3f(0, 0, 0), new Vector3f(0, 1, 0) }, null, null,
				null);
		line.setDefaultColor(color);
		line.setLocalRotation(direction.getStandardRotation());
		line.setLineWidth(2f);
		this.attachChild(line);

		pyramid = new Pyramid(direction + "Pyramid", 0.1f, 0.2f);
		pyramid.setDefaultColor(color);
		pyramid.setLocalRotation(direction.getStandardRotation());
		pyramid.setLocalTranslation(direction.getTranslation(1));
		this.attachChild(pyramid);
	}

	@Override
	public Spatial getSpatial() {
		return this;
	}

	@Override
	public void viewportChanged(final IViewport viewport) {
		updateHandle(viewport.getCamera());
	}

	@Override
	public void setActive(final boolean active) {
		if (active) {
			line.setLineWidth(4f);
			line.setDefaultColor(ColorRGBA.white.clone());
			pyramid.setDefaultColor(ColorRGBA.white.clone());
		} else {
			line.setLineWidth(2f);
			line.setDefaultColor(color);
			pyramid.setDefaultColor(color);
		}
	}

	@Override
	public void collectControlHandles(final List<IControlHandle> handles,
			final IViewport viewport) {
		updateHandle(viewport.getCamera());

		handles.add(lineControlHandle);
	}

	@Override
	public void updatePositions() {
	}

	@Override
	public IToolAdapter getToolAdapter() {
		return toolAdapter;
	}

	@Override
	public String toString() {
		return "DirectionArrowModelControl [direction=" + direction + "]";
	}

	void updateHandle(final Camera camera) {
		final Vector3f worldCenter = getWorldTranslation();

		if (lineControlHandle == null) {
			lineControlHandle = new LineControlHandle(this, camera,
					worldCenter, worldCenter.add(direction
							.project(getWorldScale())),
					IControlHandle.MAX_SALIENCE);
		} else {
			lineControlHandle.update(camera, worldCenter,
					worldCenter.add(direction.project(getWorldScale())));
		}
	}
}
