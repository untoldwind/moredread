package net.untoldwind.moredread.ui.input.impl;

import java.util.Map;
import java.util.TimerTask;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.untoldwind.moredread.ui.input.UIInputPlugin;
import net.untoldwind.moredread.ui.input.event.IUIInputReceiver;

public class ControllerPollTask extends TimerTask {
	private final Controller controller;
	private final Map<Component.Identifier, IComponentAdapter> componentAdapters;
	IUIInputReceiver inputReceiver;

	public ControllerPollTask(final IUIInputReceiver inputReceiver,
			final Controller controller,
			final Map<Component.Identifier, IComponentAdapter> componentAdapters) {
		this.inputReceiver = inputReceiver;
		this.controller = controller;
		this.componentAdapters = componentAdapters;
	}

	@Override
	public void run() {
		try {
			controller.poll();

			for (final Map.Entry<Component.Identifier, IComponentAdapter> entry : componentAdapters
					.entrySet()) {
				final Component component = controller.getComponent(entry
						.getKey());
				final float amount = component.getPollData()
						- component.getDeadZone();
				final float minAmount = entry.getValue().getMinAmount();

				if (amount <= -minAmount || amount >= minAmount) {
					entry.getValue()
							.handleComponentInput(amount, inputReceiver);
				}
			}
		} catch (final Exception e) {
			UIInputPlugin.getDefault().log(e);
		}
	}
}
