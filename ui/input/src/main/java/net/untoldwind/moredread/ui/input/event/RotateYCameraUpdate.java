package net.untoldwind.moredread.ui.input.event;

import net.untoldwind.moredread.model.math.Camera;
import net.untoldwind.moredread.model.math.Matrix3;

public class RotateYCameraUpdate implements ICameraUpdate {
	private final float amount;

	public RotateYCameraUpdate(final float amount) {
		this.amount = amount;
	}

	@Override
	public void updateComera(final Camera camera) {
		final Matrix3 incr = new Matrix3();
		incr.fromAngleNormalAxis(amount, camera.getLeft());
		camera.setLeft(incr.mult(camera.getLeft()));
		camera.setDirection(incr.mult(camera.getDirection()));
		camera.setUp(incr.mult(camera.getUp()));
		camera.normalize();
		camera.update();
	}

}
