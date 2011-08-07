package net.untoldwind.moredread.model.transform;

import com.jme.math.Vector3f;

public interface ITransformation {
	Vector3f transform(Vector3f vec);

	Vector3f transform(Vector3f vec, Vector3f store);
}
