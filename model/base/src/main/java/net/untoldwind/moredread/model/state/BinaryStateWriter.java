package net.untoldwind.moredread.model.state;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import com.jme.math.Quaternion;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;

public class BinaryStateWriter implements IStateWriter {
	private final DataOutputStream output;

	public BinaryStateWriter(final OutputStream out) {
		output = new DataOutputStream(out);
	}

	@Override
	public void writeBoolean(final String tag, final boolean value)
			throws IOException {
		output.writeBoolean(value);
	}

	@Override
	public void writeFloat(final String tag, final float value)
			throws IOException {
		output.writeFloat(value);
	}

	@Override
	public void writeInt(final String tag, final int value) throws IOException {
		output.writeInt(value);
	}

	@Override
	public void writeLong(final String tag, final long value)
			throws IOException {
		output.writeLong(value);
	}

	@Override
	public void writeIntArray(final String tag, final int[] values)
			throws IOException {
		output.writeInt(values.length);
		for (int i = 0; i < values.length; i++) {
			output.writeInt(values[i]);
		}
	}

	@Override
	public void writeString(final String tag, final String value)
			throws IOException {
		output.writeUTF(value);
	}

	@Override
	public void writeVector2f(final String tag, final Vector2f value)
			throws IOException {
		output.writeFloat(value.x);
		output.writeFloat(value.y);
	}

	@Override
	public void writeVector3f(final String tag, final Vector3f value)
			throws IOException {
		output.writeFloat(value.x);
		output.writeFloat(value.y);
		output.writeFloat(value.z);
	}

	@Override
	public void writeQuaternion(final String tag, final Quaternion quaterion)
			throws IOException {
		output.writeFloat(quaterion.x);
		output.writeFloat(quaterion.y);
		output.writeFloat(quaterion.z);
		output.writeFloat(quaterion.w);
	}

	@Override
	public void writeColor(final String tag, final ColorRGBA color)
			throws IOException {
		output.writeFloat(color.r);
		output.writeFloat(color.g);
		output.writeFloat(color.b);
		output.writeFloat(color.a);
	}

	@Override
	public void writeObject(final String tag, final IStateHolder obj)
			throws IOException {
		output.writeUTF(obj.getClass().getName());
		obj.writeState(this);
	}

	@Override
	public <T extends IStateHolder> void writeTypedArray(final String tag,
			final T[] arr) throws IOException {
		output.writeUTF(arr.getClass().getComponentType().getName());
		output.writeInt(arr.length);
		for (final T element : arr) {
			element.writeState(this);
		}
	}

	@Override
	public <T extends IStateHolder> void writeTypedList(final String tag,
			final Class<T> type, final List<T> collection) throws IOException {
		output.writeUTF(type.getName());
		output.writeInt(collection.size());
		for (final IStateHolder stateHolder : collection) {
			stateHolder.writeState(this);
		}
	}

	@Override
	public <T extends IStateHolder> void writeUntypedList(final String tag,
			final List<T> collection) throws IOException {
		output.writeInt(collection.size());
		for (final IStateHolder stateHolder : collection) {
			writeObject("element", stateHolder);
		}
	}

	// @Override
	// public void writeArray(final String tag, final IStateHolder[] arr)
	// throws IOException {
	// output.writeInt(arr.length);
	// for (final IStateHolder stateHolder : arr) {
	// stateHolder.writeState(this);
	// }
	// }

	public static byte[] toByteArray(final IStateHolder stateHolder) {
		try {
			final ByteArrayOutputStream out = new ByteArrayOutputStream();
			final BinaryStateWriter writer = new BinaryStateWriter(out);

			writer.writeObject("object", stateHolder);

			return out.toByteArray();
		} catch (final IOException e) {
			throw new RuntimeException("Very unexpected IOException", e);
		}
	}
}
