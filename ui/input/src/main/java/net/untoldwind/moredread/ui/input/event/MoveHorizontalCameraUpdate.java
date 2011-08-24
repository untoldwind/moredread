package net.untoldwind.moredread.ui.input.event;


import com.jme.math.Vector3f;
import com.jme.renderer.Camera;

public class MoveHorizontalCameraUpdate implements ICameraUpdate {

	private final float amount;

	public MoveHorizontalCameraUpdate(final float amount) {
		this.amount = amount;
	}

	@Override
	public void updateComera(final Camera camera) {
		final Vector3f loc = camera.getLocation();
		loc.addLocal(camera.getLeft().mult(amount));
		camera.update();

	}
}