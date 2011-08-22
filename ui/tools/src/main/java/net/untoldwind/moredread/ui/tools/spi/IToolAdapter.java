package net.untoldwind.moredread.ui.tools.spi;

import java.util.EnumSet;

import net.untoldwind.moredread.ui.controls.Modifier;

import com.jme.math.Vector3f;

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
	Vector3f getCenter();

	/**
	 * Handle/react to a click event.
	 * 
	 * @param point
	 *            The point clicked (note that its jobs of the model control to
	 *            do all 3D-transformation stuff)
	 * @param modifiers
	 *            Modifier (mouse-button, keyboard ...)
	 */
	void handleClick(final Vector3f point, final EnumSet<Modifier> modifiers);

	/**
	 * Handle/react to a drap event
	 * 
	 * @param point
	 *            The point clicked (note that its jobs of the model control to
	 *            do all 3D-transformation stuff)
	 * @param modifiers
	 *            Modifier (mouse-button, keyboard ...)
	 * @param finished
	 *            <tt>true</tt> if the drag is completed, <tt>false</tt> if drag
	 *            is still ongoing
	 */
	void handleDrag(final Vector3f point, final EnumSet<Modifier> modifiers,
			final boolean finished);
}
