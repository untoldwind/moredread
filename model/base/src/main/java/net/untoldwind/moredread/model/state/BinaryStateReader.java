package net.untoldwind.moredread.model.state;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import com.jme.math.Quaternion;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;

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
		return new Vector3f(input.readFloat(), input.readFloat(),
				input.readFloat());
	}

	@Override
	public Quaternion readQuaternion() throws IOException {
		return new Quaternion(input.readFloat(), input.readFloat(),
				input.readFloat(), input.readFloat());
	}

	@Override
	public ColorRGBA readColor() throws IOException {
		return new ColorRGBA(input.readFloat(), input.readFloat(),
				input.readFloat(), input.readFloat());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IStateHolder> T readObject() throws IOException {
		final String className = input.readUTF();
		try {
			final Class<T> clazz = (Class<T>) Class.forName(className);
			final Constructor<T> constructor = clazz.getDeclaredConstructor();
			constructor.setAccessible(true);
			final T instance = constructor.newInstance();
			instance.readState(this);

			return instance;
		} catch (final Exception e) {
			throw new IOException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IStateHolder> T readObject(
			final IInstanceCreator<T> creator) throws IOException {
		final String className = input.readUTF();
		try {
			final T instance = creator.createInstance((Class<T>) Class
					.forName(className));
			instance.readState(this);

			return instance;
		} catch (final Exception e) {
			throw new IOException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IStateHolder> T[] readTypedArray() throws IOException {
		try {
			final String className = input.readUTF();
			final int len = input.readInt();
			final T[] result = (T[]) Array.newInstance(
					Class.forName(className), len);

			for (int i = 0; i < len; i++) {
				final Class<T> clazz = (Class<T>) Class.forName(className);
				final Constructor<T> constructor = clazz
						.getDeclaredConstructor();
				constructor.setAccessible(true);
				final T instance = constructor.newInstance();
				instance.readState(this);
				result[i] = instance;
			}

			return result;
		} catch (final Exception e) {
			throw new IOException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IStateHolder> List<T> readTypedList() throws IOException {
		try {
			final List<T> result = new ArrayList<T>();
			final String className = input.readUTF();
			final int size = input.readInt();

			for (int i = 0; i < size; i++) {
				final Class<T> clazz = (Class<T>) Class.forName(className);
				final Constructor<T> constructor = clazz
						.getDeclaredConstructor();
				constructor.setAccessible(true);
				final T instance = constructor.newInstance();
				instance.readState(this);
				result.add(instance);
			}

			return result;
		} catch (final Exception e) {
			throw new IOException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IStateHolder> List<T> readUntypedList()
			throws IOException {
		final int size = input.readInt();
		final List<T> result = new ArrayList<T>();
		for (int i = 0; i < size; i++) {
			result.add((T) readObject());
		}

		return result;
	}

	@Override
	public <T extends IStateHolder> List<T> readUntypedList(
			final IInstanceCreator<T> creator) throws IOException {
		final int size = input.readInt();
		final List<T> result = new ArrayList<T>();
		for (int i = 0; i < size; i++) {
			result.add(readObject(creator));
		}

		return result;
	}

	public static <T extends IStateHolder> T fromByteArray(final byte[] data) {
		try {
			return new BinaryStateReader(new ByteArrayInputStream(data))
					.readObject();
		} catch (final IOException e) {
			throw new RuntimeException("Very unexpected IOException", e);
		}
	}

	public static <T extends IStateHolder> T fromByteArray(final byte[] data,
			final IInstanceCreator<T> creator) {
		try {
			return new BinaryStateReader(new ByteArrayInputStream(data))
					.readObject(creator);
		} catch (final IOException e) {
			throw new RuntimeException("Very unexpected IOException", e);
		}
	}
}
