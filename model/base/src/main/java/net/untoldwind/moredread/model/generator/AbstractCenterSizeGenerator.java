package net.untoldwind.moredread.model.generator;

import java.io.IOException;

import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.state.IStateHolder;
import net.untoldwind.moredread.model.state.IStateReader;
import net.untoldwind.moredread.model.state.IStateWriter;

import org.eclipse.core.runtime.Platform;

public abstract class AbstractCenterSizeGenerator implements IMeshGenerator,
		IStateHolder {
	protected Vector3 center = new Vector3();
	protected float size = 1f;

	protected AbstractCenterSizeGenerator(final Vector3 center, final float size) {
		this.center = center;
		this.size = size;
	}

	public Vector3 getCenter() {
		return center;
	}

	public void setCenter(final Vector3 center) {
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
		center = reader.readVector3();
		size = reader.readFloat();
	}

	@Override
	public void writeState(final IStateWriter writer) throws IOException {
		writer.writeVector3("center", center);
		writer.writeFloat("size", size);
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") final Class adapter) {
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}

}
