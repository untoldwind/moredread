package net.untoldwind.moredread.model.state;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.jme.math.Quaternion;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;

public class BinaryStateReader implements IStateReader {
	private final DataInputStream input;

	public BinaryStateReader(final InputStream in) {
		input = new DataInputStream(in);
	}

	@Override
	public boolean readBoolean() throws IOException {
		return input.readBoolean();
	}

	@Override
	public float readFloat() throws IOException {
		return input.readFloat();
	}

	@Override
	public int readInt() throws IOException {
		return input.readInt();
	}

	@Override
	public String readString() throws IOException {
		return input.readUTF();
	}

	@Override
	public Vector2f readVector2f() throws IOException {
		return new Vector2f(input.readFloat(), input.readFloat());
	}

	@Override
	public Vector3f readVector3f() throws IOException {
		return new Vector3f(input.readFloat(), input.readFloat(), input
				.readFloat());
	}

	@Override
	public Quaternion readQuaternion() throws IOException {
		return new Quaternion(input.readFloat(), input.readFloat(), input
				.readFloat(), input.readFloat());
	}

	@Override
	public <T extends IStateHolder> T readObject(
			final InstanceCreator<T> instanceCreator) throws IOException {
		return instanceCreator.createInstance(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IStateHolder> T[] readArray(final Class<T> clazz,
			final InstanceCreator<T> instanceCreator) throws IOException {
		final int len = input.readInt();
		final T[] result = (T[]) Array.newInstance(clazz, len);

		for (int i = 0; i < len; i++) {
			result[i] = instanceCreator.createInstance(this);
		}

		return result;
	}

	@Override
	public <T extends IStateHolder> Collection<T> readCollection(
			final InstanceCreator<T> instanceCreator) throws IOException {
		final List<T> result = new ArrayList<T>();
		final int size = input.readInt();

		for (int i = 0; i < size; i++) {
			result.add(instanceCreator.createInstance(this));
		}

		return result;
	}

	public static <T extends IStateHolder> T fromByteArray(final byte[] data,
			final InstanceCreator<T> instanceCreator) {
		try {
			return instanceCreator.createInstance(new BinaryStateReader(
					new ByteArrayInputStream(data)));
		} catch (final IOException e) {
			throw new RuntimeException("Very unexpected IOException", e);
		}
	}
}
