package net.untoldwind.moredread.model.math;

public class Camera {
	com.jme.renderer.Camera delegate;

	public Camera(final com.jme.renderer.Camera delegate) {
		this.delegate = delegate;
	}

	public Vector3 getScreenCoordinates(final Vector3 worldPoint) {
		return new Vector3(delegate.getScreenCoordinates(worldPoint));
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
		delegate.setFrame(location, left, up, direction);
	}

	public Vector3 getLocation() {
		return new Vector3(delegate.getLocation());
	}

	public Vector3 getWorldCoordinates(final Vector2 scenePosition,
			final float zPos) {
		return new Vector3(delegate.getWorldCoordinates(scenePosition, zPos));
	}

	public Vector3 getLeft() {
		return new Vector3(delegate.getLeft());
	}

	public Vector3 getUp() {
		return new Vector3(delegate.getUp());
	}

	public void normalize() {
		delegate.normalize();
	}

	public void update() {
		delegate.update();
	}

	public void apply() {
		delegate.apply();
	}

	public boolean isParallelProjection() {
		return delegate.isParallelProjection();
	}

	public void setDirection(final Vector3 direction) {
		delegate.setDirection(direction);
	}

	public void setUp(final Vector3 up) {
		delegate.setUp(up);
	}

	public void setLeft(final Vector3 left) {
		delegate.setLeft(left);
	}

	public void setLocation(final Vector3 location) {
		delegate.setLocation(location);
	}

	public com.jme.renderer.Camera toJME() {
		return delegate;
	}

}
