package net.untoldwind.moredread.ui.controls.impl;

import java.util.EnumSet;

import net.untoldwind.moredread.ui.controls.IControlHandle;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.controls.Modifier;

import com.jme.math.Vector2f;
import com.jme.math.Vector3f;

public class FullScreenControlHandle implements IControlHandle {
	private final float salience;
	private final IModelControl modelControl;

	public FullScreenControlHandle(final float salience,
			final IModelControl modelControl) {
		this.salience = salience;
		this.modelControl = modelControl;
	}

	@Override
	public float matches(final Vector2f screenCoord) {
		return salience;
	}

	@Override
	public boolean handleMove(final Vector2f position,
			final EnumSet<Modifier> modifiers) {
		return modelControl.getToolAdapter().handleMove(modelControl,
				new Vector3f(position.x, position.y, 0), modifiers);
	}

	@Override
	public void handleClick(final Vector2f position,
			final EnumSet<Modifier> modifiers) {
		modelControl.getToolAdapter().handleClick(
				new Vector3f(position.x, position.y, 0), modifiers);
	}

	@Override
	public void handleDragStart(final Vector2f dragStart,
			final EnumSet<Modifier> modifiers) {
		modelControl.getToolAdapter().handleDragStart(
				new Vector3f(dragStart.x, dragStart.y, 0), modifiers);
	}

	@Override
	public void handleDragMove(final Vector2f dragStart,
			final Vector2f dragEnd, final EnumSet<Modifier> modifiers) {
		modelControl.getToolAdapter().handleDragMove(
				new Vector3f(dragEnd.x, dragEnd.y, 0), modifiers);
	}

	@Override
	public void handleDragEnd(final Vector2f dragStart, final Vector2f dragEnd,
			final EnumSet<Modifier> modifiers) {
		modelControl.getToolAdapter().handleDragEnd(
				new Vector3f(dragEnd.x, dragEnd.y, 0), modifiers);
	}

	@Override
	public void setActive(final boolean active) {
		// Do nothing
	}

}
