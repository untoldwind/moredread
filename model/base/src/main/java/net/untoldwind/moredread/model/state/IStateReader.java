package net.untoldwind.moredread.model.state;

import java.io.IOException;
import java.util.List;

import com.jme.math.Quaternion;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;

public interface IStateReader {
	boolean readBoolean() throws IOException;

	int readInt() throws IOException;

	float readFloat() throws IOException;

	String readString() throws IOException;

	Vector2f readVector2f() throws IOException;

	Vector3f readVector3f() throws IOException;

	Quaternion readQuaternion() throws IOException;

	ColorRGBA readColor() throws IOException;

	<T extends IStateHolder> T readObject() throws IOException;

	<T extends IStateHolder> T readObject(IInstanceCreator<T> creator)
			throws IOException;

	<T extends IStateHolder> T[] readTypedArray() throws IOException;

	<T extends IStateHolder> List<T> readTypedList() throws IOException;

	<T extends IStateHolder> List<T> readUntypedList() throws IOException;

	<T extends IStateHolder> List<T> readUntypedList(IInstanceCreator<T> creator)
			throws IOException;

	public interface IInstanceCreator<T> {
		T createInstance(Class<T> clazz);
	}
}
