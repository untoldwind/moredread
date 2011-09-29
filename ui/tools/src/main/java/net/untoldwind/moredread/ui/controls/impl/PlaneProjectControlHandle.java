package net.untoldwind.moredread.ui.controls.impl;

import java.util.EnumSet;

import net.untoldwind.moredread.model.enums.CartesianPlane;
import net.untoldwind.moredread.model.math.Camera;
import net.untoldwind.moredread.model.math.Vector2;
import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.ui.controls.IControlHandle;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.controls.Modifier;

public class PlaneProjectControlHandle implements IControlHandle {
	private final IModelControl modelControl;
	private final float salience;
	private CartesianPlane plane;
	private Camera camera;
	private final Vector3 position;

	public PlaneProjectControlHandle(final IModelControl modelControl,
			final float salience) {
		this.modelControl = modelControl;
		this.salience = salience;
		this.position = new Vector3();
	}

	@Override
	public float matches(final Vector2 screenCoord) {
		return salience;
	}

	@Override
	public void setActive(final boolean active) {
		// Do nothing
	}

	@Override
	public boolean handleClick(final Vector2 position,
			final EnumSet<Modifier> modifiers) {
		return modelControl.getToolAdapter().handleClick(modelControl,
				project(position), modifiers);
	}

	@Override
	public boolean handleMove(final Vector2 position,
			final EnumSet<Modifier> modifiers) {
		return modelControl.getToolAdapter().handleMove(modelControl,
				project(position), modifiers);
	}

	@Override
	public boolean handleDragStart(final Vector2 dragStart,
			final EnumSet<Modifier> modifiers) {
		return modelControl.getToolAdapter().handleDragStart(modelControl,
				project(dragStart), modifiers);
	}

	@Override
	public boolean handleDragMove(final Vector2 dragStart,
			final Vector2 dragEnd, final EnumSet<Modifier> modifiers) {
		return modelControl.getToolAdapter().handleDragMove(modelControl,
				project(dragStart), project(dragEnd), modifiers);
	}

	@Override
	public boolean handleDragEnd(final Vector2 dragStart,
			final Vector2 dragEnd, final EnumSet<Modifier> modifiers) {
		return modelControl.getToolAdapter().handleDragEnd(modelControl,
				project(dragStart), project(dragEnd), modifiers);
	}

	void setProjection(final CartesianPlane plane, final Camera camera) {
		this.plane = plane;
		this.camera = camera;
	}

	void setPosition(final Vector3 position) {
		this.position.set(position);
	}

	private Vector3 project(final Vector2 scenePosition) {
		final Vector3 origin = camera.getWorldCoordinates(scenePosition, 0);
		final Vector3 direction = camera
				.getWorldCoordinates(scenePosition, 0.3f).subtractLocal(origin)
				.normalizeLocal();

		float dist = plane.getNormal().dot(origin);
		dist -= plane.getNormal().dot(position);
		dist /= plane.getNormal().dot(direction);

		direction.multLocal(-dist);
		return new Vector3(origin.addLocal(direction));
	}
}