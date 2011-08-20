package net.untoldwind.moredread.ui.controls.impl;

import java.util.EnumSet;

import net.untoldwind.moredread.ui.controls.IControlHandle;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.controls.Modifier;
import net.untoldwind.moredread.ui.tools.spi.IToolAdapter;

import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;

public class PointControlHandle implements IControlHandle {
	private final IModelControl modelControl;
	private final IToolAdapter toolAdapter;
	private Vector3f worldPosition;
	private Vector2f screenPosition;
	private float salience;

	public PointControlHandle(final IModelControl modelControl,
			final IToolAdapter toolAdapter, final Camera camera,
			final Vector3f worldPosition) {
		this.modelControl = modelControl;
		this.toolAdapter = toolAdapter;

		update(camera, worldPosition);
	}

	@Override
	public float matches(final Vector2f screenCoord) {
		if (screenPosition.distanceSquared(screenCoord) <= 25f) {
			return salience;
		}

		return -1;
	}

	public void update(final Camera camera, final Vector3f worldPosition) {
		this.worldPosition = worldPosition;
		final Vector3f screenPosition = camera
				.getScreenCoordinates(worldPosition);

		this.screenPosition = new Vector2f(screenPosition.x, screenPosition.y);
		this.salience = screenPosition.z;
	}

	@Override
	public void setActive(final boolean active) {
		modelControl.setActive(active);
	}

	@Override
	public boolean handleClick(final Vector2f position,
			final EnumSet<Modifier> modifiers) {
		return toolAdapter.handleClick(worldPosition, modifiers);
	}

	@Override
	public boolean handleDrag(final Vector2f dragStart, final Vector2f dragEnd,
			final EnumSet<Modifier> modifiers, final boolean finished) {
		// TODO: Project this?
		return toolAdapter.handleDrag(new Vector3f(dragEnd.x, dragEnd.y, 0),
				modifiers, finished);

	}

}
