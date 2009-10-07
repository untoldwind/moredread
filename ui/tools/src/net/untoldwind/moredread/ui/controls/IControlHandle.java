package net.untoldwind.moredread.ui.controls;

import java.util.EnumSet;

import com.jme.math.Vector2f;

public interface IControlHandle {
	float MAX_SALIENCE = 0.0f;
	float SELECT_SALIENCE = 1000.0f;

	/**
	 * Check if a certain screen coordinate is above the controller handle.
	 * 
	 * @param screenCoord
	 *            The screen coordinate to check
	 * @return If the handle does not match a value &lt; 0 is returned,
	 *         otherwise a salience &ge; 0. The handle with the lowest salience
	 *         will be considered valid, i.e. returning a 0 is considered to be
	 *         highest saliance of all.
	 */
	float matches(Vector2f screenCoord);

	void setActive(boolean active);

	boolean handleClick(Vector2f position, EnumSet<Modifier> modifiers);

	boolean handleDrag(Vector2f dragStart, Vector2f dragEnd,
			final EnumSet<Modifier> modifiers, boolean finished);
}
