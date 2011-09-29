package net.untoldwind.moredread.ui.controls.impl;

import java.util.EnumSet;

import net.untoldwind.moredread.model.math.Camera;
import net.untoldwind.moredread.model.math.Vector2;
import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.ui.controls.IControlHandle;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.controls.Modifier;

public class PointControlHandle implements IControlHandle {
	private final IModelControl modelControl;
	private Vector3 worldPosition;
	private Vector2 screenPosition;
	private float salience;

	public PointControlHandle(final IModelControl modelControl,
			final Camera camera, final Vector3 worldPosition) {
		this.modelControl = modelControl;

		update(camera, worldPosition);
	}

	@Override
	public float matches(final Vector2 screenCoord) {
		if (screenPosition.distanceSquared(screenCoord) <= 25f) {
			return salience;
		}

		return -1;
	}

	public void update(final Camera camera, final Vector3 worldPosition) {
		this.worldPosition = worldPosition;
		final Vector3 screenPosition = camera
				.getScreenCoordinates(worldPosition);

		this.screenPosition = new Vector2(screenPosition.x, screenPosition.y);
		this.salience = screenPosition.z;
	}

	@Override
	public void setActive(final boolean active) {
		modelControl.setActive(active);
	}

	@Override
	public boolean handleMove(final Vector2 position,
			final EnumSet<Modifier> modifiers) {
		// TODO: Project this?
		return modelControl.getToolAdapter().handleMove(modelControl,
				new Vector3(position.x, position.y, 0), modifiers);
	}

	@Override
	public boolean handleClick(final Vector2 position,
			final EnumSet<Modifier> modifiers) {
		return modelControl.getToolAdapter().handleClick(modelControl,
				new Vector3(worldPosition), modifiers);
	}

	@Override
	public boolean handleDragStart(final Vector2 dragStart,
			final EnumSet<Modifier> modifiers) {
		// TODO: Project this?
		return modelControl.getToolAdapter().handleDragStart(modelControl,
				new Vector3(dragStart.x, dragStart.y, 0), modifiers);

	}

	@Override
	public boolean handleDragMove(final Vector2 dragStart,
			final Vector2 dragEnd, final EnumSet<Modifier> modifiers) {
		// TODO: Project this?
		return modelControl.getToolAdapter().handleDragMove(modelControl,
				new Vector3(dragStart.x, dragStart.y, 0),
				new Vector3(dragEnd.x, dragEnd.y, 0), modifiers);

	}

	@Override
	public boolean handleDragEnd(final Vector2 dragStart,
			final Vector2 dragEnd, final EnumSet<Modifier> modifiers) {
		// TODO: Project this?
		return modelControl.getToolAdapter().handleDragEnd(modelControl,
				new Vector3(dragStart.x, dragStart.y, 0),
				new Vector3(dragEnd.x, dragEnd.y, 0), modifiers);
	}

}
