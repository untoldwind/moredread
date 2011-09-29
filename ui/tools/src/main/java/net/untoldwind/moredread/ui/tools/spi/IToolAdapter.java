package net.untoldwind.moredread.ui.tools.spi;

import java.util.EnumSet;

import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.controls.Modifier;

/**
 * Interface of all tool adapters.
 * 
 * Tool adapters act as a link between model controls and the tool. Model
 * controls are supposed to be reusable, i.e. the same model control may be used
 * by different tools, but lead to different actions.
 */
public interface IToolAdapter {
	/**
	 * Get a contextual center.
	 * 
	 * This is mostly used for 'rotate around this'.
	 */
	Vector3 getCenter();

	Vector3 getFeedbackPoint();

	boolean handleMove(IModelControl modelControl, final Vector3 point,
			final EnumSet<Modifier> modifiers);

	/**
	 * Handle/react to a click event.
	 * 
	 * @param point
	 *            The point clicked (note that its jobs of the model control to
	 *            do all 3D-transformation stuff)
	 * @param modifiers
	 *            Modifier (mouse-button, keyboard ...)
	 */
	boolean handleClick(IModelControl modelControl, final Vector3 point,
			final EnumSet<Modifier> modifiers);

	boolean handleDragStart(IModelControl modelControl, Vector3 dragStart,
			final EnumSet<Modifier> modifiers);

	boolean handleDragMove(IModelControl modelControl, Vector3 dragStart,
			Vector3 dragEnd, final EnumSet<Modifier> modifiers);

	boolean handleDragEnd(IModelControl modelControl, Vector3 dragStart,
			Vector3 dragEnd, final EnumSet<Modifier> modifiers);
}
