package net.untoldwind.moredread.ui.input.event;

import net.untoldwind.moredread.model.math.Camera;
import net.untoldwind.moredread.model.math.Vector3;

public class MoveVerticalCameraUpdate implements ICameraUpdate {

	private Vector3 upVector;
	private final float amount;

	public MoveVerticalCameraUpdate(final float amount) {
		this.amount = amount;
	}

	@Override
	public void updateComera(final Camera camera) {
		final Vector3 loc = camera.getLocation();
		if (upVector != null) {
			camera.setLocation(loc.add(upVector.mult(amount)));
			camera.update();
		} else {
			camera.setLocation(loc.add(camera.getUp().mult(amount)));
			camera.update();
		}
	}
}