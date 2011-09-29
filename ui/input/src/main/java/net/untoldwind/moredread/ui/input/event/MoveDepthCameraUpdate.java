package net.untoldwind.moredread.ui.input.event;

import net.untoldwind.moredread.model.math.Camera;
import net.untoldwind.moredread.model.math.Vector3;

public class MoveDepthCameraUpdate implements ICameraUpdate {

	private final float amount;

	public MoveDepthCameraUpdate(final float amount) {
		this.amount = amount;
	}

	@Override
	public void updateComera(final Camera camera) {
		final Vector3 loc = camera.getLocation();
		if (!camera.isParallelProjection()) {
			camera.setLocation(loc.add(camera.getDirection().mult(amount)));
		} else {
			// move up instead of forward if in parallel mode
			camera.setLocation(loc.add(camera.getUp().mult(amount)));
		}
		camera.update();
	}
}
