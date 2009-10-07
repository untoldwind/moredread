package net.untoldwind.moredread.ui.input.event;

import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;

public class RotateAroundXCameraUpdate implements ICameraUpdate {
	private final float amount;

	private final Vector3f rotateCenter;
	private final Vector3f lockAxis;

	public RotateAroundXCameraUpdate(final float amount) {
		this(amount, new Vector3f(), new Vector3f(0, 1, 0));
	}

	public RotateAroundXCameraUpdate(final float amount,
			final Vector3f rotateCenter) {
		this.amount = amount;
		this.rotateCenter = rotateCenter;
		this.lockAxis = null;
	}

	public RotateAroundXCameraUpdate(final float amount,
			final Vector3f rotateCenter, final Vector3f lockAxis) {
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
			incr.fromAngleNormalAxis(amount, lockAxis);
		}

		final Vector3f lookDir = camera.getLocation().subtract(rotateCenter);

		incr.mult(lookDir, lookDir);
		lookDir.add(rotateCenter, camera.getLocation());

		incr.mult(camera.getUp(), camera.getUp());
		incr.mult(camera.getLeft(), camera.getLeft());
		incr.mult(camera.getDirection(), camera.getDirection());
		camera.normalize();
		camera.update();
	}

}
