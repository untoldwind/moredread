package net.untoldwind.moredread.model.generator;

import net.untoldwind.moredread.model.mesh.IGeometry;
import net.untoldwind.moredread.model.scene.INode;

public interface IGeneratorInput extends INode {
	IGeometry getRenderGeometry();
}
