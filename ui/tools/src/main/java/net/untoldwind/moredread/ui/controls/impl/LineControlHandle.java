package net.untoldwind.moredread.ui.controls.impl;

import java.util.EnumSet;

import net.untoldwind.moredread.ui.controls.IControlHandle;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.controls.Modifier;
import net.untoldwind.moredread.ui.tools.spi.IToolAdapter;

import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;

public class LineControlHandle implements IControlHandle {
	private final IModelControl modelControl;
	private Vector3f worldDirection;
	private float salience;
	private final Float fixedSalience;
	private Vector2f screenStart;
	private Vector2f screenDirection;

	private final IToolAdapter toolAdapter;

	private transient Vector3f moveStart;

	public LineControlHandle(final IModelControl modelControl,
			final IToolAdapter toolAdapter, final Camera camera,
			final Vector3f worldPoint1, final Vector3f worldPoint2) {
		this(modelControl, toolAdapter, camera, worldPoint1, worldPoint2, null);
	}

	public LineControlHandle(final IModelControl modelControl,
			final IToolAdapter toolAdapter, final Camera camera,
			final Vector3f worldPoint1, final Vector3f worldPoint2,
			final Float fixedSalience) {
		this.modelControl = modelControl;
		this.toolAdapter = toolAdapter;
		this.fixedSalience = fixedSalience;

		update(camera, worldPoint1, worldPoint2);
	}

	@Override
	public float matches(final Vector2f screenCoord) {
		if (screenDirection.lengthSquared() < 9) {
			return -1;
		}

		final Vector2f diff = screenCoord.subtract(screenStart);

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

	public void update(final Camera camera, final Vector3f worldPoint1,
			final Vector3f worldPoint2) {

		final Vector3f screenPoint1 = camera.getScreenCoordinates(worldPoint1);
		final Vector3f screenPoint2 = camera.getScreenCoordinates(worldPoint2);

		worldDirection = worldPoint2.subtract(worldPoint1);
		screenStart = new Vector2f(screenPoint1.x, screenPoint1.y);
		screenDirection = new Vector2f(screenPoint2.x - screenPoint1.x,
				screenPoint2.y - screenPoint1.y);
		salience = 0.5f * (screenPoint1.z + screenPoint2.z);
	}

	@Override
	public void setActive(final boolean active) {
		modelControl.setActive(active);
	}

	@Override
	public void handleClick(final Vector2f position,
			final EnumSet<Modifier> modifiers) {
		final Vector2f diff = position.subtract(screenStart);
		final float amount = diff.dot(screenDirection)
				/ screenDirection.lengthSquared();

		final Vector3f v = worldDirection.mult(amount).addLocal(moveStart);

		toolAdapter.handleClick(v, modifiers);
	}

	@Override
	public void handleDrag(final Vector2f dragStart, final Vector2f dragEnd,
			final EnumSet<Modifier> modifiers, final boolean finished) {
		final Vector2f diff = dragEnd.subtract(dragStart);
		final float amount = diff.dot(screenDirection)
				/ screenDirection.lengthSquared();

		if (moveStart == null) {
			moveStart = toolAdapter.getCenter().clone();
		}
		final Vector3f v = worldDirection.mult(amount).addLocal(moveStart);

		toolAdapter.handleDrag(v, modifiers, finished);

		if (finished) {
			moveStart = null;
		}
	}
}
