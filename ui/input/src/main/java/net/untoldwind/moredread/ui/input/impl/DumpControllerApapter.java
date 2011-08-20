package net.untoldwind.moredread.ui.input.impl;

import net.untoldwind.moredread.ui.input.event.IUIInputReceiver;

public class DumpControllerApapter implements IComponentAdapter {

	public float getMinAmount() {
		return 0.01f;
	}

	@Override
	public void handleComponentInput(final float amount,
			final IUIInputReceiver inputReceiver) {
		System.out.println(">>> " + amount);
	}

}
