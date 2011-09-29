package net.untoldwind.moredread.ui.controls.impl;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import net.untoldwind.moredread.model.math.Camera;
import net.untoldwind.moredread.model.math.Vector2;
import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.ui.controls.IControlHandle;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.controls.Modifier;

public class PolygonControlHandle implements IControlHandle {
	private final IModelControl modelControl;
	private float salience;
	private List<Vector2> screenPoints;

	public PolygonControlHandle(final IModelControl modelControl,
			final Camera camera, final List<Vector3> points) {
		this.modelControl = modelControl;

		update(camera, points);
	}

	@Override
	public float matches(final Vector2 screenCoord) {
		final int polySides = screenPoints.size();

		if (polySides == 0) {
			return -1;
		}

		int i, j = polySides - 1;

		boolean oddNodes = false;

		for (i = 0; i < polySides; i++) {
			final Vector2 pi = screenPoints.get(i);
			final Vector2 pj = screenPoints.get(j);

			if (pi.y < screenCoord.y && pj.y >= screenCoord.y
					|| pj.y < screenCoord.y && pi.y >= screenCoord.y) {
				if (pi.x + (screenCoord.y - pi.y) / (pj.y - pi.y)
						* (pj.x - pi.x) < screenCoord.x) {
					oddNodes = !oddNodes;
				}
			}
			j = i;
		}

		return oddNodes ? salience : -1;
	}

	public void update(final Camera camera, final List<Vector3> points) {
		this.screenPoints = new ArrayList<Vector2>(points.size());
		int count = 0;

		this.salience = 0;

		for (final Vector3 point : points) {
			final Vector3 screenPoint = camera.getScreenCoordinates(point);
			this.screenPoints.add(new Vector2(screenPoint.x, screenPoint.y));
			this.salience += screenPoint.z;
			count++;
		}
		if (count > 0) {
			this.salience /= count;
		}
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
		// TODO: Project this?
		return modelControl.getToolAdapter().handleClick(modelControl,
				new Vector3(position.x, position.y, 0), modifiers);
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
