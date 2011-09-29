package net.untoldwind.moredread.ui.input.event;

import net.untoldwind.moredread.model.math.Camera;
import net.untoldwind.moredread.model.math.Matrix3;
import net.untoldwind.moredread.model.math.Vector3;

public class RotateAroundYCameraUpdate implements ICameraUpdate {
	private final float amount;

	private final Vector3 rotateCenter;

	public RotateAroundYCameraUpdate(final float amount) {
		this(amount, new Vector3());
	}

	public RotateAroundYCameraUpdate(final float amount,
			final Vector3 rotateCenter) {
		this.amount = amount;
		this.rotateCenter = rotateCenter;
	}

	@Override
	public void updateComera(final Camera camera) {
		final Matrix3 incr = new Matrix3();
		incr.fromAngleNormalAxis(amount, camera.getLeft());

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
