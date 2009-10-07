package net.untoldwind.moredread.ui.controls;

import java.util.EnumSet;

import org.eclipse.swt.SWT;

public enum Modifier {
	SHIFT_KEY(SWT.SHIFT), ALT_KEY(SWT.ALT), CTRL_KEY(SWT.CTRL), COMMAND_KEY(
			SWT.COMMAND), LEFT_MOUSE_BUTTON(SWT.BUTTON1), MIDDLE_MOUSE_BUTTON(
			SWT.BUTTON2), RIGHT_MOUSE_BUTTON(SWT.BUTTON3);

	int swtMask;

	private Modifier(final int swtMask) {
		this.swtMask = swtMask;
	}

	public int getSWTMask() {
		return swtMask;
	}

	public static EnumSet<Modifier> fromStateMask(final int stateMask) {
		final EnumSet<Modifier> modifiers = EnumSet
				.<Modifier> noneOf(Modifier.class);

		for (final Modifier modifier : values()) {
			if ((stateMask & modifier.getSWTMask()) != 0) {
				modifiers.add(modifier);
			}
		}
		return modifiers;
	}
}
