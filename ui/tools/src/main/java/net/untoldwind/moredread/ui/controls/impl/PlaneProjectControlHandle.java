package net.untoldwind.moredread.ui.controls.impl;

import java.util.EnumSet;

import net.untoldwind.moredread.ui.controls.IControlHandle;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.controls.Modifier;

import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;

public class PlaneProjectControlHandle implements IControlHandle {
	private final IModelControl modelControl;
	private final float salience;
	private Plane plane;
	private Camera camera;
	private final Vector3f position;

	public PlaneProjectControlHandle(final IModelControl modelControl,
			final float salience) {
		this.modelControl = modelControl;
		this.salience = salience;
		this.position = new Vector3f();
	}

	@Override
	public float matches(final Vector2f screenCoord) {
		return salience;
	}

	@Override
	public void setActive(final boolean active) {
		// Do nothing
	}

	@Override
	public boolean handleClick(final Vector2f position,
			final EnumSet<Modifier> modifiers) {
		return modelControl.getToolAdapter().handleClick(modelControl,
				project(position), modifiers);
	}

	@Override
	public boolean handleMove(final Vector2f position,
			final EnumSet<Modifier> modifiers) {
		return modelControl.getToolAdapter().handleMove(modelControl,
				project(position), modifiers);
	}

	@Override
	public boolean handleDragStart(final Vector2f dragStart,
			final EnumSet<Modifier> modifiers) {
		return modelControl.getToolAdapter().handleDragStart(modelControl,
				project(dragStart), modifiers);
	}

	@Override
	public boolean handleDragMove(final Vector2f dragStart,
			final Vector2f dragEnd, final EnumSet<Modifier> modifiers) {
		return modelControl.getToolAdapter().handleDragMove(modelControl,
				project(dragStart), project(dragEnd), modifiers);
	}

	@Override
	public boolean handleDragEnd(final Vector2f dragStart,
			final Vector2f dragEnd, final EnumSet<Modifier> modifiers) {
		return modelControl.getToolAdapter().handleDragEnd(modelControl,
				project(dragStart), project(dragEnd), modifiers);
	}

	void setProjection(final Plane plane, final Camera camera) {
		this.plane = plane;
		this.camera = camera;
	}

	void setPosition(final Vector3f position) {
		this.position.set(position);
	}

	private Vector3f project(final Vector2f scenePosition) {
		final Vector3f origin = camera.getWorldCoordinates(scenePosition, 0);
		final Vector3f direction = camera
				.getWorldCoordinates(scenePosition, 0.3f).subtractLocal(origin)
				.normalizeLocal();

		float dist = plane.getNormal().dot(origin);
		dist -= plane.getNormal().dot(position);
		dist /= plane.getNormal().dot(direction);

		direction.multLocal(-dist);
		return origin.addLocal(direction);
	}
}