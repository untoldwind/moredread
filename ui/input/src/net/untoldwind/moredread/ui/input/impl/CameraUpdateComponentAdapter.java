package net.untoldwind.moredread.ui.input.impl;

import java.lang.reflect.Constructor;

import net.untoldwind.moredread.ui.input.UIInputPlugin;
import net.untoldwind.moredread.ui.input.event.ICameraUpdate;
import net.untoldwind.moredread.ui.input.event.IUIInputReceiver;

public class CameraUpdateComponentAdapter implements IComponentAdapter {
	private Constructor<? extends ICameraUpdate> cameraUpdateConstructor;
	private final float minAmount;
	private final float multiplicator;

	public CameraUpdateComponentAdapter(
			final Class<? extends ICameraUpdate> cameraUpdateClass,
			final float minAmount, final float multiplicator) {
		try {
			this.cameraUpdateConstructor = cameraUpdateClass
					.getConstructor(Float.TYPE);
		} catch (final Exception e) {
			UIInputPlugin.getDefault().log(e);
		}
		this.minAmount = minAmount;
		this.multiplicator = multiplicator;
	}

	@Override
	public float getMinAmount() {
		return minAmount;
	}

	@Override
	public void handleComponentInput(final float amount,
			final IUIInputReceiver inputReceiver) {
		try {
			final ICameraUpdate cameraUpdate = cameraUpdateConstructor
					.newInstance(multiplicator * amount);

			inputReceiver.queueCameraUpdate(cameraUpdate);
		} catch (final Exception e) {
			UIInputPlugin.getDefault().log(e);
		}
	}
}
