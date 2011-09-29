package net.untoldwind.moredread.ui.input.event;

import net.untoldwind.moredread.model.math.Camera;
import net.untoldwind.moredread.model.math.Matrix3;
import net.untoldwind.moredread.model.math.Vector3;

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
		final Matrix3 incr = new Matrix3();

		if (lockAxis == null) {
			incr.fromAngleNormalAxis(amount, camera.getUp());
		} else {
			incr.fromAngleNormalAxis(amount, lockAxis);
		}

		final Vector3 lookDir = camera.getLocation().subtract(rotateCenter);

		incr.mult(lookDir, lookDir);
		camera.setLocation(lookDir.add(rotateCenter));

		camera.setUp(incr.mult(camera.getUp()));
		camera.setLeft(incr.mult(camera.getLeft()));
		camera.setDirection(incr.mult(camera.getDirection()));
		camera.normalize();
		camera.update();
	}

}
