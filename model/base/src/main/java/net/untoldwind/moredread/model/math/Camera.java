package net.untoldwind.moredread.model.math;

public class Camera {
	com.jme.renderer.Camera delegate;

	public Camera(final com.jme.renderer.Camera delegate) {
		this.delegate = delegate;
	}

	public Vector3 getScreenCoordinates(final Vector3 worldPoint) {
		return new Vector3(delegate.getScreenCoordinates(worldPoint.toJME()));
	}

	public Vector3 getDirection() {
		return new Vector3(delegate.getDirection());
	}

	public void setFrustumPerspective(final float fovY, final float aspect,
			final float near, final float far) {
		delegate.setFrustumPerspective(fovY, aspect, near, far);
	}

	public void setFrame(final Vector3 location, final Vector3 left,
			final Vector3 up, final Vector3 direction) {
		delegate.setFrame(location.toJME(), left.toJME(), up.toJME(),
				direction.toJME());
	}

	public void update() {
		delegate.update();
	}

	public Vector3 getUp() {
		return new Vector3(delegate.getUp());
	}

	public com.jme.renderer.Camera toJME() {
		return delegate;
	}

	public Vector3 getWorldCoordinates(final Vector2 scenePosition,
			final float zPos) {
		return new Vector3(delegate.getWorldCoordinates(scenePosition.toJME(),
				zPos));
	}
}
