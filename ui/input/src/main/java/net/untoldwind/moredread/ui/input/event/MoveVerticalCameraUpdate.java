package net.untoldwind.moredread.ui.input.event;


import com.jme.math.Vector3f;
import com.jme.renderer.Camera;

public class MoveVerticalCameraUpdate implements ICameraUpdate {

	private Vector3f upVector;
	private final float amount;

	public MoveVerticalCameraUpdate(final float amount) {
		this.amount = amount;
	}

	@Override
	public void updateComera(final Camera camera) {
		final Vector3f loc = camera.getLocation();
		if (upVector != null) {
			loc.addLocal(upVector.mult(amount));
			camera.update();
		} else {
			loc.addLocal(camera.getUp().mult(amount));
			camera.update();
		}
	}
}