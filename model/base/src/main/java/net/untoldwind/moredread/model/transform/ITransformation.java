package net.untoldwind.moredread.model.transform;

import net.untoldwind.moredread.model.math.Quaternion;
import net.untoldwind.moredread.model.math.Vector3;

public interface ITransformation {
	Vector3 transformPoint(Vector3 vec);

	Vector3 transformPoint(Vector3 vec, Vector3 store);

	Vector3 transformVector(Vector3 vec);

	Vector3 transformVector(Vector3 vec, Vector3 store);

	Quaternion getRotation();

	Vector3 getTranslation();

	Vector3 getScale();
}
