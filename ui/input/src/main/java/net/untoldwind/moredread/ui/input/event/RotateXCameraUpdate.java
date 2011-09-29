package net.untoldwind.moredread.ui.input.event;

import net.untoldwind.moredread.model.math.Vector3;

import com.jme.math.Matrix3f;
import com.jme.renderer.Camera;

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
		final Matrix3f incr = new Matrix3f();

		if (lockAxis == null) {
			incr.fromAngleNormalAxis(amount, camera.getUp());
		} else {
			incr.fromAngleNormalAxis(amount, lockAxis.toJME());
		}

		incr.mult(camera.getUp(), camera.getUp());
		incr.mult(camera.getLeft(), camera.getLeft());
		incr.mult(camera.getDirection(), camera.getDirection());
		camera.normalize();
		camera.update();
	}

}
