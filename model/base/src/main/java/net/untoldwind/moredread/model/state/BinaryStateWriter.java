package net.untoldwind.moredread.model.state;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

import com.jme.math.Quaternion;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;

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
	public void writeObject(final String tag, final IStateHolder obj)
			throws IOException {
		obj.writeState(this);
	}

	@Override
	public void writeCollection(final String tag,
			final Collection<? extends IStateHolder> collection)
			throws IOException {
		output.writeInt(collection.size());
		for (final IStateHolder stateHolder : collection) {
			stateHolder.writeState(this);
		}
	}

	@Override
	public void writeArray(final String tag, final IStateHolder[] arr)
			throws IOException {
		output.writeInt(arr.length);
		for (final IStateHolder stateHolder : arr) {
			stateHolder.writeState(this);
		}
	}

	public static byte[] toByteArray(final IStateHolder stateHolder) {
		try {
			final ByteArrayOutputStream out = new ByteArrayOutputStream();

			stateHolder.writeState(new BinaryStateWriter(out));

			return out.toByteArray();
		} catch (final IOException e) {
			throw new RuntimeException("Very unexpected IOException", e);
		}
	}
}
