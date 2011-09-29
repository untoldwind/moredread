package net.untoldwind.moredread.ui.input.event;

import net.untoldwind.moredread.model.math.Vector3;

import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;

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
		final Matrix3f incr = new Matrix3f();
		incr.fromAngleNormalAxis(amount, camera.getLeft());

		final Vector3f lookDir = camera.getLocation().subtract(
				rotateCenter.toJME());

		incr.mult(lookDir, lookDir);
		lookDir.add(rotateCenter.toJME(), camera.getLocation());

		incr.mult(camera.getLeft(), camera.getLeft());
		incr.mult(camera.getDirection(), camera.getDirection());
		incr.mult(camera.getUp(), camera.getUp());
		camera.normalize();
		camera.update();
	}

}
