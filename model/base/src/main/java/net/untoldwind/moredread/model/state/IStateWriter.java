package net.untoldwind.moredread.model.state;

import java.io.IOException;
import java.util.List;

import com.jme.math.Quaternion;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;

public interface IStateWriter {
	void writeBoolean(String tag, boolean value) throws IOException;

	void writeInt(String tag, int value) throws IOException;

	void writeFloat(String tag, float value) throws IOException;

	void writeString(String tag, String value) throws IOException;

	void writeVector2f(String tag, Vector2f value) throws IOException;

	void writeVector3f(String tag, Vector3f value) throws IOException;

	void writeQuaternion(String tag, Quaternion quaterion) throws IOException;

	void writeObject(String tag, IStateHolder obj) throws IOException;

	<T extends IStateHolder> void writeTypedArray(String tag, T[] arr)
			throws IOException;

	// void writeArray(String tag, IStateHolder[] arr) throws IOException;

	<T extends IStateHolder> void writeTypedList(String tag, Class<T> type,
			List<T> collection) throws IOException;

	<T extends IStateHolder> void writeUntypedList(String tag,
			List<T> collection) throws IOException;

}
