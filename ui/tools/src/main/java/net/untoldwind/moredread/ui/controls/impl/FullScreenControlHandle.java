package net.untoldwind.moredread.ui.controls.impl;

import java.util.EnumSet;

import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.ui.controls.IControlHandle;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.controls.Modifier;

import com.jme.math.Vector2f;

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
				new Vector3(position.x, position.y, 0), modifiers);
	}

	@Override
	public boolean handleClick(final Vector2f position,
			final EnumSet<Modifier> modifiers) {
		return modelControl.getToolAdapter().handleClick(modelControl,
				new Vector3(position.x, position.y, 0), modifiers);
	}

	@Override
	public boolean handleDragStart(final Vector2f dragStart,
			final EnumSet<Modifier> modifiers) {
		return modelControl.getToolAdapter().handleDragStart(modelControl,
				new Vector3(dragStart.x, dragStart.y, 0), modifiers);
	}

	@Override
	public boolean handleDragMove(final Vector2f dragStart,
			final Vector2f dragEnd, final EnumSet<Modifier> modifiers) {
		return modelControl.getToolAdapter().handleDragMove(modelControl,
				new Vector3(dragStart.x, dragStart.y, 0),
				new Vector3(dragEnd.x, dragEnd.y, 0), modifiers);
	}

	@Override
	public boolean handleDragEnd(final Vector2f dragStart,
			final Vector2f dragEnd, final EnumSet<Modifier> modifiers) {
		return modelControl.getToolAdapter().handleDragEnd(modelControl,
				new Vector3(dragStart.x, dragStart.y, 0),
				new Vector3(dragEnd.x, dragEnd.y, 0), modifiers);
	}

	@Override
	public void setActive(final boolean active) {
		// Do nothing
	}

}
