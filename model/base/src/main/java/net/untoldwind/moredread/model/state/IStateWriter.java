package net.untoldwind.moredread.model.state;

import java.io.IOException;
import java.util.List;

import net.untoldwind.moredread.model.math.Quaternion;
import net.untoldwind.moredread.model.math.Vector3;

import com.jme.math.Vector2f;
import com.jme.renderer.ColorRGBA;

public interface IStateWriter {
	void writeBoolean(String tag, boolean value) throws IOException;

	void writeInt(String tag, int value) throws IOException;

	void writeLong(String tag, long value) throws IOException;

	void writeIntArray(String tag, int[] values) throws IOException;

	void writeFloat(String tag, float value) throws IOException;

	void writeString(String tag, String value) throws IOException;

	void writeVector2f(String tag, Vector2f value) throws IOException;

	void writeVector3(String tag, Vector3 value) throws IOException;

	void writeQuaternion(String tag, Quaternion quaterion) throws IOException;

	void writeColor(String tag, ColorRGBA color) throws IOException;

	void writeObject(String tag, IStateHolder obj) throws IOException;

	<T extends IStateHolder> void writeTypedArray(String tag, T[] arr)
			throws IOException;

	// void writeArray(String tag, IStateHolder[] arr) throws IOException;

	<T extends IStateHolder> void writeTypedList(String tag, Class<T> type,
			List<T> collection) throws IOException;

	<T extends IStateHolder> void writeUntypedList(String tag,
			List<T> collection) throws IOException;

}
