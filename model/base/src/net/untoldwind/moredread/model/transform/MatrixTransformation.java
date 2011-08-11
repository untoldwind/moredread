package net.untoldwind.moredread.model.transform;

import com.jme.math.Matrix4f;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

public class MatrixTransformation implements ITransformation {
	private final Matrix4f transformation;

	public MatrixTransformation(final Vector3f scale,
			final Quaternion rotation, final Vector3f translation) {
		transformation = new Matrix4f();

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
}
