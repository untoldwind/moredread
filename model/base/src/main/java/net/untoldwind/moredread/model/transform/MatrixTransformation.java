package net.untoldwind.moredread.model.transform;

import com.jme.math.Matrix4f;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

public class MatrixTransformation implements ITransformation {
	private final Matrix4f transformation;
	private final Quaternion rotation;
	private final Vector3f translation;
	private final Vector3f scale;

	public MatrixTransformation(final Vector3f scale,
			final Quaternion rotation, final Vector3f translation) {
		transformation = new Matrix4f();

		this.rotation = rotation;
		this.translation = translation;
		this.scale = scale;

		transformation.multLocal(rotation);
		transformation.scale(scale);
		transformation.setTranslation(translation);
	}

	@Override
	public Vector3f transformPoint(final Vector3f vec) {
		return transformation.mult(vec);
	}

	@Override
	public Vector3f transformPoint(final Vector3f vec, final Vector3f store) {
		return transformation.mult(vec, store);
	}

	@Override
	public Vector3f transformVector(final Vector3f vec) {
		final Vector3f result = new Vector3f(vec);
		transformation.rotateVect(result);
		return result;
	}

	@Override
	public Vector3f transformVector(final Vector3f vec, final Vector3f store) {
		transformation.rotateVect(store);
		return store;
	}

	@Override
	public Quaternion getRotation() {
		return rotation;
	}

	@Override
	public Vector3f getTranslation() {
		return translation;
	}

	@Override
	public Vector3f getScale() {
		return scale;
	}

}
