package net.untoldwind.moredread.model.state;

import java.io.IOException;
import java.util.Collection;

import com.jme.math.Vector2f;
import com.jme.math.Vector3f;

public interface IStateReader {
	boolean readBoolean() throws IOException;

	int readInt() throws IOException;

	float readFloat() throws IOException;

	Vector2f readVector2f() throws IOException;

	Vector3f readVector3f() throws IOException;

	<T extends IStateHolder> T readObject(InstanceCreator<T> instanceCreator)
			throws IOException;

	<T extends IStateHolder> T[] readArray(Class<T> clazz,
			InstanceCreator<T> instanceCreator) throws IOException;

	<T extends IStateHolder> Collection<T> readCollection(
			InstanceCreator<T> instanceCreator) throws IOException;

	public static interface InstanceCreator<T extends IStateHolder> {
		T createInstance(IStateReader reader) throws IOException;
	}
}
