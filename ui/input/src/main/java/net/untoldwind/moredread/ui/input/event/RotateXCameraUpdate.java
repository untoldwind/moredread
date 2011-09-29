package net.untoldwind.moredread.ui.input.event;

import net.untoldwind.moredread.model.math.Camera;
import net.untoldwind.moredread.model.math.Matrix3;
import net.untoldwind.moredread.model.math.Vector3;

public class RotateXCameraUpdate implements ICameraUpdate {
	private final float amount;

	private Vector3 lockAxis;

	public RotateXCameraUpdate(final float amount) {
		this.amount = amount;
	}

	public RotateXCameraUpdate(final float amount, final Vector3 lockAxis) {
		this.amount = amount;
		this.lockAxis = lockAxis;
	}

	@Override
	public void updateComera(final Camera camera) {
		final Matrix3 incr = new Matrix3();

		if (lockAxis == null) {
			incr.fromAngleNormalAxis(amount, camera.getUp());
		} else {
			incr.fromAngleNormalAxis(amount, lockAxis);
		}

		camera.setUp(incr.mult(camera.getUp()));
		camera.setLeft(incr.mult(camera.getLeft()));
		camera.setDirection(incr.mult(camera.getDirection()));
		camera.normalize();
		camera.update();
	}

}
