package net.untoldwind.moredread.ui.input.event;

import com.jme.math.Matrix3f;
import com.jme.renderer.Camera;

public class RotateZCameraUpdate implements ICameraUpdate {
	private final float amount;

	public RotateZCameraUpdate(final float amount) {
		this.amount = amount;
	}

	@Override
	public void updateComera(final Camera camera) {
		final Matrix3f incr = new Matrix3f();
		incr.fromAngleNormalAxis(amount, camera.getDirection());

		incr.mult(camera.getLeft(), camera.getLeft());
		incr.mult(camera.getDirection(), camera.getDirection());
		incr.mult(camera.getUp(), camera.getUp());
		camera.normalize();
		camera.update();
	}

}