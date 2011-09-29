package net.untoldwind.moredread.ui.input.event;

import net.untoldwind.moredread.model.math.Vector3;

import com.jme.math.Vector3f;
import com.jme.renderer.Camera;

public class MoveVerticalCameraUpdate implements ICameraUpdate {

	private Vector3 upVector;
	private final float amount;

	public MoveVerticalCameraUpdate(final float amount) {
		this.amount = amount;
	}

	@Override
	public void updateComera(final Camera camera) {
		final Vector3f loc = camera.getLocation();
		if (upVector != null) {
			loc.addLocal(upVector.mult(amount).toJME());
			camera.update();
		} else {
			loc.addLocal(camera.getUp().mult(amount));
			camera.update();
		}
	}
}