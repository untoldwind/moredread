package net.untoldwind.moredread.ui.controls.impl;

import java.util.EnumSet;

import net.untoldwind.moredread.ui.controls.IControlHandle;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.controls.Modifier;

import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;

public class PointControlHandle implements IControlHandle {
	private final IModelControl modelControl;
	private Vector3f worldPosition;
	private Vector2f screenPosition;
	private float salience;

	public PointControlHandle(final IModelControl modelControl,
			final Camera camera, final Vector3f worldPosition) {
		this.modelControl = modelControl;

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
	public boolean handleMove(final Vector2f position,
			final EnumSet<Modifier> modifiers) {
		// TODO: Project this?
		return modelControl.getToolAdapter().handleMove(modelControl,
				new Vector3f(position.x, position.y, 0), modifiers);
	}

	@Override
	public boolean handleClick(final Vector2f position,
			final EnumSet<Modifier> modifiers) {
		return modelControl.getToolAdapter().handleClick(modelControl,
				worldPosition, modifiers);
	}

	@Override
	public boolean handleDragStart(final Vector2f dragStart,
			final EnumSet<Modifier> modifiers) {
		// TODO: Project this?
		return modelControl.getToolAdapter().handleDragStart(modelControl,
				new Vector3f(dragStart.x, dragStart.y, 0), modifiers);

	}

	@Override
	public boolean handleDragMove(final Vector2f dragStart,
			final Vector2f dragEnd, final EnumSet<Modifier> modifiers) {
		// TODO: Project this?
		return modelControl.getToolAdapter().handleDragMove(modelControl,
				new Vector3f(dragStart.x, dragStart.y, 0),
				new Vector3f(dragEnd.x, dragEnd.y, 0), modifiers);

	}

	@Override
	public boolean handleDragEnd(final Vector2f dragStart,
			final Vector2f dragEnd, final EnumSet<Modifier> modifiers) {
		// TODO: Project this?
		return modelControl.getToolAdapter().handleDragEnd(modelControl,
				new Vector3f(dragStart.x, dragStart.y, 0),
				new Vector3f(dragEnd.x, dragEnd.y, 0), modifiers);
	}

}
