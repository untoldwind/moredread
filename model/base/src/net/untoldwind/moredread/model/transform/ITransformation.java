package net.untoldwind.moredread.model.transform;

import com.jme.math.Vector3f;

public interface ITransformation {
	Vector3f transformPoint(Vector3f vec);

	Vector3f transformPoint(Vector3f vec, Vector3f store);

	Vector3f transformVector(Vector3f vec);

	Vector3f transformVector(Vector3f vec, Vector3f store);
}
