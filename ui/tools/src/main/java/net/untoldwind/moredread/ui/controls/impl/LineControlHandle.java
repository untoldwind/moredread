package net.untoldwind.moredread.ui.controls.impl;

import java.util.EnumSet;

import net.untoldwind.moredread.model.math.Camera;
import net.untoldwind.moredread.model.math.Vector2;
import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.ui.controls.IControlHandle;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.controls.Modifier;

public class LineControlHandle implements IControlHandle {
	private final IModelControl modelControl;
	private Vector3 worldDirection;
	private float salience;
	private final Float fixedSalience;
	private Vector2 screenStart;
	private Vector2 screenDirection;

	private volatile transient Vector2 dragScreenDirection;
	private volatile transient Vector3 moveStart;

	public LineControlHandle(final IModelControl modelControl,
			final Camera camera, final Vector3 worldPoint1,
			final Vector3 worldPoint2) {
		this(modelControl, camera, worldPoint1, worldPoint2, null);
	}

	public LineControlHandle(final IModelControl modelControl,
			final Camera camera, final Vector3 worldPoint1,
			final Vector3 worldPoint2, final Float fixedSalience) {
		this.modelControl = modelControl;
		this.fixedSalience = fixedSalience;

		update(camera, worldPoint1, worldPoint2);
	}

	@Override
	public float matches(final Vector2 screenCoord) {
		if (screenDirection.lengthSquared() < 9) {
			return -1;
		}

		final Vector2 diff = screenCoord.subtract(screenStart);

		final float s = diff.dot(screenDirection)
				/ screenDirection.lengthSquared();

		if (s >= 0.0 && s <= 1.0f) {
			final float l = diff.subtract(screenDirection.mult(s)).length();

			if (l < 6.0f) {
				return fixedSalience != null ? fixedSalience : salience;
			}
		}
		return -1;
	}

	public void update(final Camera camera, final Vector3 worldPoint1,
			final Vector3 worldPoint2) {

		final Vector3 screenPoint1 = camera.getScreenCoordinates(worldPoint1);
		final Vector3 screenPoint2 = camera.getScreenCoordinates(worldPoint2);

		worldDirection = worldPoint2.subtract(worldPoint1);
		screenStart = new Vector2(screenPoint1.x, screenPoint1.y);
		screenDirection = new Vector2(screenPoint2.x - screenPoint1.x,
				screenPoint2.y - screenPoint1.y);
		salience = 0.5f * (screenPoint1.z + screenPoint2.z);
	}

	@Override
	public void setActive(final boolean active) {
		modelControl.setActive(active);
	}

	@Override
	public boolean handleMove(final Vector2 position,
			final EnumSet<Modifier> modifiers) {
		final Vector2 diff = position.subtract(screenStart);
		final float amount = diff.dot(screenDirection)
				/ screenDirection.lengthSquared();

		final Vector3 v = new Vector3(worldDirection.mult(amount).addLocal(
				modelControl.getToolAdapter().getCenter()));

		return modelControl.getToolAdapter().handleMove(modelControl, v,
				modifiers);
	}

	@Override
	public boolean handleClick(final Vector2 position,
			final EnumSet<Modifier> modifiers) {
		final Vector2 diff = position.subtract(screenStart);
		final float amount = diff.dot(screenDirection)
				/ screenDirection.lengthSquared();

		final Vector3 v = new Vector3(worldDirection.mult(amount).addLocal(
				modelControl.getToolAdapter().getCenter()));

		return modelControl.getToolAdapter().handleClick(modelControl, v,
				modifiers);
	}

	@Override
	public boolean handleDragStart(final Vector2 dragStart,
			final EnumSet<Modifier> modifiers) {
		dragScreenDirection = screenDirection.clone();
		moveStart = modelControl.getToolAdapter().getCenter();

		final Vector2 diff = dragStart.subtract(dragStart);
		final float amount = diff.dot(dragScreenDirection)
				/ dragScreenDirection.lengthSquared();

		final Vector3 v = new Vector3(worldDirection.mult(amount).addLocal(
				moveStart));

		return modelControl.getToolAdapter().handleDragStart(modelControl, v,
				modifiers);
	}

	@Override
	public boolean handleDragMove(final Vector2 dragStart,
			final Vector2 dragEnd, final EnumSet<Modifier> modifiers) {
		final Vector2 diff = dragEnd.subtract(dragStart);
		final float amount = diff.dot(dragScreenDirection)
				/ dragScreenDirection.lengthSquared();

		final Vector3 v = new Vector3(worldDirection.mult(amount).addLocal(
				moveStart));

		return modelControl.getToolAdapter().handleDragMove(modelControl,
				new Vector3(moveStart), v, modifiers);
	}

	@Override
	public boolean handleDragEnd(final Vector2 dragStart,
			final Vector2 dragEnd, final EnumSet<Modifier> modifiers) {
		final Vector2 diff = dragEnd.subtract(dragStart);
		final float amount = diff.dot(dragScreenDirection)
				/ dragScreenDirection.lengthSquared();

		final Vector3 v1 = new Vector3(moveStart);
		final Vector3 v2 = new Vector3(worldDirection.mult(amount).addLocal(
				moveStart));

		moveStart = null;

		return modelControl.getToolAdapter().handleDragEnd(modelControl, v1,
				v2, modifiers);
	}
}
