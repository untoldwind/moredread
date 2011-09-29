package net.untoldwind.moredread.ui.input.event;

import net.untoldwind.moredread.model.math.Camera;
import net.untoldwind.moredread.model.math.Vector3;

public class MoveHorizontalCameraUpdate implements ICameraUpdate {

	private final float amount;

	public MoveHorizontalCameraUpdate(final float amount) {
		this.amount = amount;
	}

	@Override
	public void updateComera(final Camera camera) {
		final Vector3 loc = camera.getLocation();
		camera.setLocation(loc.add(camera.getLeft().mult(amount)));
		camera.update();

	}
}