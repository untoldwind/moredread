package net.untoldwind.moredread.ui.tools.spi;

import java.util.EnumSet;

import net.untoldwind.moredread.ui.controls.Modifier;

import com.jme.math.Vector3f;

public interface IToolAdapter {
	Vector3f getCenter();

	boolean handleClick(final Vector3f point, final EnumSet<Modifier> modifiers);

	boolean handleDrag(final Vector3f point, final EnumSet<Modifier> modifiers,
			final boolean finished);
}
