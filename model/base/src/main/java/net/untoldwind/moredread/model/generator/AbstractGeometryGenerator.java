package net.untoldwind.moredread.model.generator;

import org.eclipse.core.runtime.Platform;

public abstract class AbstractGeometryGenerator implements IGeometryGenerator {

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") final Class adapter) {
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}

}
