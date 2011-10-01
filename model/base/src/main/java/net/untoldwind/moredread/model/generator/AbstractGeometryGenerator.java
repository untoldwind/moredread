package net.untoldwind.moredread.model.generator;

import net.untoldwind.moredread.model.mesh.IGeometry;
import net.untoldwind.moredread.model.scene.GeneratorNode;

import org.eclipse.core.runtime.Platform;

public abstract class AbstractGeometryGenerator<T extends IGeometry<?>>
		implements IGeometryGenerator<T> {
	protected GeneratorNode generatorNode;

	@Override
	public GeneratorNode getGeneratorNode() {
		return generatorNode;
	}

	public void setGeneratorNode(final GeneratorNode generatorNode) {
		this.generatorNode = generatorNode;
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") final Class adapter) {
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}

}
