package net.untoldwind.moredread.model.generator;

import java.io.IOException;

import net.untoldwind.moredread.model.state.IStateHolder;
import net.untoldwind.moredread.model.state.IStateWriter;

import com.jme.math.Vector3f;

public class AbstractCenterSizeGenerator implements IStateHolder {
	protected Vector3f center = new Vector3f();
	protected float size = 1f;

	protected AbstractCenterSizeGenerator(final Vector3f center,
			final float size) {
		this.center = center;
		this.size = size;
	}

	@Override
	public void writeState(final IStateWriter writer) throws IOException {
		writer.writeVector3f("center", center);
		writer.writeFloat("size", size);
	}

}
