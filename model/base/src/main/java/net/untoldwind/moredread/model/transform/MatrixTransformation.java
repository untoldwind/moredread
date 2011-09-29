package net.untoldwind.moredread.model.transform;

import net.untoldwind.moredread.model.math.Matrix4;
import net.untoldwind.moredread.model.math.Quaternion;
import net.untoldwind.moredread.model.math.Vector3;

public class MatrixTransformation implements ITransformation {
	private final Matrix4 transformation;
	private final Quaternion rotation;
	private final Vector3 translation;
	private final Vector3 scale;

	public MatrixTransformation(final Vector3 scale, final Quaternion rotation,
			final Vector3 translation) {
		transformation = new Matrix4();

		this.rotation = rotation;
		this.translation = translation;
		this.scale = scale;

		transformation.multLocal(rotation);
		transformation.scale(scale);
		transformation.setTranslation(translation);
	}

	@Override
	public Vector3 transformPoint(final Vector3 vec) {
		return transformation.mult(vec);
	}

	@Override
	public Vector3 transformPoint(final Vector3 vec, final Vector3 store) {
		return transformation.mult(vec, store);
	}

	@Override
	public Vector3 transformVector(final Vector3 vec) {
		final Vector3 result = new Vector3(vec);
		transformation.rotateVect(result);
		return result;
	}

	@Override
	public Vector3 transformVector(final Vector3 vec, final Vector3 store) {
		transformation.rotateVect(store);
		return store;
	}

	@Override
	public Quaternion getRotation() {
		return rotation;
	}

	@Override
	public Vector3 getTranslation() {
		return translation;
	}

	@Override
	public Vector3 getScale() {
		return scale;
	}

}
