package net.untoldwind.moredread.model.generator;

import java.io.IOException;

import net.untoldwind.moredread.model.state.IStateHolder;
import net.untoldwind.moredread.model.state.IStateReader;
import net.untoldwind.moredread.model.state.IStateWriter;

import org.eclipse.core.runtime.Platform;

import com.jme.math.Vector3f;

public abstract class AbstractCenterSizeGenerator implements IMeshGenerator,
		IStateHolder {
	protected Vector3f center = new Vector3f();
	protected float size = 1f;

	protected AbstractCenterSizeGenerator(final Vector3f center,
			final float size) {
		this.center = center;
		this.size = size;
	}

	public Vector3f getCenter() {
		return center;
	}

	public void setCenter(final Vector3f center) {
		this.center = center;
	}

	public float getSize() {
		return size;
	}

	public void setSize(final float size) {
		this.size = size;
	}

	@Override
	public void readState(final IStateReader reader) throws IOException {
		center = reader.readVector3f();
		size = reader.readFloat();
	}

	@Override
	public void writeState(final IStateWriter writer) throws IOException {
		writer.writeVector3f("center", center);
		writer.writeFloat("size", size);
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") final Class adapter) {
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}

}
