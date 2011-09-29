package net.untoldwind.moredread.ui.controls.impl;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.ui.controls.IControlHandle;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.controls.Modifier;

import com.jme.math.FastMath;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;

public class PolyLineControlHandle implements IControlHandle {
	private final IModelControl modelControl;
	private float salience;
	private final Float fixedSalience;
	private List<Vector3f> worldPoints;
	private List<Vector2f> screenPoints;
	private Vector2f center;

	public PolyLineControlHandle(final IModelControl modelControl,
			final Camera camera, final List<Vector3f> points) {
		this(modelControl, camera, points, null);
	}

	public PolyLineControlHandle(final IModelControl modelControl,
			final Camera camera, final List<Vector3f> points,
			final Float fixedSalience) {
		this.modelControl = modelControl;
		this.fixedSalience = fixedSalience;

		update(camera, points);
	}

	@Override
	public float matches(final Vector2f screenCoord) {
		for (int i = 1; i < screenPoints.size(); i++) {
			final Vector2f previous = screenPoints.get(i - 1);
			final Vector2f current = screenPoints.get(i);
			final Vector2f direction = current.subtract(previous);
			final Vector2f diff = screenCoord.subtract(center);

			final float s = intersection(previous, direction, center, diff);

			if (s >= 0.0 && s <= 1.0f) {
				final float l = screenCoord.subtract(
						direction.mult(s).addLocal(previous)).length();

				if (l < 6.0f) {
					return fixedSalience != null ? fixedSalience : salience;
				}
			}
		}
		return -1;
	}

	@Override
	public void setActive(final boolean active) {
		modelControl.setActive(active);
	}

	@Override
	public boolean handleMove(final Vector2f position,
			final EnumSet<Modifier> modifiers) {
		final Vector3 point = project(position);

		if (point == null) {
			return false;
		}
		return modelControl.getToolAdapter().handleMove(modelControl, point,
				modifiers);
	}

	@Override
	public boolean handleClick(final Vector2f position,
			final EnumSet<Modifier> modifiers) {
		final Vector3 point = project(position);

		if (point == null) {
			return false;
		}
		return modelControl.getToolAdapter().handleClick(modelControl, point,
				modifiers);
	}

	@Override
	public boolean handleDragStart(final Vector2f dragStart,
			final EnumSet<Modifier> modifiers) {
		final Vector3 point = project(dragStart);

		if (point == null) {
			return false;
		}
		return modelControl.getToolAdapter().handleDragStart(modelControl,
				point, modifiers);
	}

	@Override
	public boolean handleDragMove(final Vector2f dragStart,
			final Vector2f dragEnd, final EnumSet<Modifier> modifiers) {
		final Vector3 point1 = project(dragStart);
		final Vector3 point2 = project(dragEnd);

		if (point1 == null || point2 == null) {
			return false;
		}
		return modelControl.getToolAdapter().handleDragMove(modelControl,
				point1, point2, modifiers);
	}

	@Override
	public boolean handleDragEnd(final Vector2f dragStart,
			final Vector2f dragEnd, final EnumSet<Modifier> modifiers) {
		final Vector3 point1 = project(dragStart);
		final Vector3 point2 = project(dragEnd);

		if (point1 == null || point2 == null) {
			return false;
		}
		return modelControl.getToolAdapter().handleDragEnd(modelControl,
				point1, point2, modifiers);
	}

	public void update(final Camera camera, final List<Vector3f> points) {
		this.worldPoints = points;
		this.screenPoints = new ArrayList<Vector2f>(points.size());
		int count = 0;

		this.salience = 0;

		this.center = new Vector2f();
		for (final Vector3f point : points) {
			final Vector3f screenPoint3 = camera.getScreenCoordinates(point);
			final Vector2f screenPoint = new Vector2f(screenPoint3.x,
					screenPoint3.y);
			this.screenPoints.add(screenPoint);
			this.center.addLocal(screenPoint);
			this.salience += screenPoint3.z;
			count++;
		}
		if (count > 0) {
			this.salience /= count;
			this.center.divideLocal(count);
		}
	}

	private Vector3 project(final Vector2f screenCoord) {
		for (int i = 1; i < screenPoints.size(); i++) {
			final Vector2f previous = screenPoints.get(i - 1);
			final Vector2f current = screenPoints.get(i);
			final Vector2f direction = current.subtract(previous);
			final Vector2f diff = screenCoord.subtract(center);

			final float s = intersection(previous, direction, center, diff);

			if (s >= 0.0 && s <= 1.0f) {
				return new Vector3(worldPoints.get(i)
						.subtract(worldPoints.get(i - 1)).multLocal(s)
						.addLocal(worldPoints.get(i - 1)));
			}
		}
		return null;
	}

	private float intersection(final Vector2f v1, final Vector2f dir1,
			final Vector2f v2, final Vector2f dir2) {
		final float det = dir1.x * dir2.y - dir1.y * dir2.x;

		if (FastMath.abs(det) < FastMath.ZERO_TOLERANCE) {
			return Float.NaN;
		}

		return (dir2.x * (v1.y - v2.y) + dir2.y * (v2.x - v1.x)) / det;
	}
}
