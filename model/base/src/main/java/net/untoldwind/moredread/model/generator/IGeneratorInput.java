package net.untoldwind.moredread.model.generator;

import net.untoldwind.moredread.model.mesh.IGeometry;
import net.untoldwind.moredread.model.scene.ISpatialNode;

public interface IGeneratorInput extends ISpatialNode {
	IGeometry<?> getRenderGeometry();
}
