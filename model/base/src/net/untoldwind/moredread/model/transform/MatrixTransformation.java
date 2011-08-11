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
	public Vector3f transform(final Vector3f vec) {
		return transformation.mult(vec);
	}

	@Override
	public Vector3f transform(final Vector3f vec, final Vector3f store) {
		return transform(vec, store);
	}
}
