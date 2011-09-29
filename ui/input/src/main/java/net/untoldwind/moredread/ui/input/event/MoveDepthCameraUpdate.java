package net.untoldwind.moredread.ui.input.event;

import com.jme.math.Vector3f;
import com.jme.renderer.Camera;

public class MoveDepthCameraUpdate implements ICameraUpdate {

	private final float amount;

	public MoveDepthCameraUpdate(final float amount) {
		this.amount = amount;
	}

	@Override
	public void updateComera(final Camera camera) {
		final Vector3f loc = camera.getLocation();
		if (!camera.isParallelProjection()) {
			loc.addLocal(camera.getDirection().mult(amount));
		} else {
			// move up instead of forward if in parallel mode
			loc.addLocal(camera.getUp().mult(amount));
		}
		camera.update();
	}

}
