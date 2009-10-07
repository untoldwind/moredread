package net.untoldwind.moredread.ui.input.impl;

import net.untoldwind.moredread.ui.input.event.IUIInputReceiver;

public interface IComponentAdapter {
	float getMinAmount();

	void handleComponentInput(float amount, IUIInputReceiver inputReceiver);
}
