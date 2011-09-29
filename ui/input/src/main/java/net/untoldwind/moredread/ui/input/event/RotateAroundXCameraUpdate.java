package net.untoldwind.moredread.ui.input.event;

import net.untoldwind.moredread.model.math.Vector3;

import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;

public class RotateAroundXCameraUpdate implements ICameraUpdate {
	private final float amount;

	private final Vector3 rotateCenter;
	private final Vector3 lockAxis;

	public RotateAroundXCameraUpdate(final float amount) {
		this(amount, new Vector3(), new Vector3(0, 1, 0));
	}

	public RotateAroundXCameraUpdate(final float amount,
			final Vector3 rotateCenter) {
		this.amount = amount;
		this.rotateCenter = rotateCenter;
		this.lockAxis = null;
	}

	public RotateAroundXCameraUpdate(final float amount,
			final Vector3 rotateCenter, final Vector3 lockAxis) {
		this.amount = amount;
		this.rotateCenter = rotateCenter;
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

		final Vector3f lookDir = camera.getLocation().subtract(
				rotateCenter.toJME());

		incr.mult(lookDir, lookDir);
		lookDir.add(rotateCenter.toJME(), camera.getLocation());

		incr.mult(camera.getUp(), camera.getUp());
		incr.mult(camera.getLeft(), camera.getLeft());
		incr.mult(camera.getDirection(), camera.getDirection());
		camera.normalize();
		camera.update();
	}

}
