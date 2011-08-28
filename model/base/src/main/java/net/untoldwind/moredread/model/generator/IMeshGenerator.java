package net.untoldwind.moredread.model.generator;

import java.util.List;

import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.state.IStateHolder;

public interface IMeshGenerator extends IGeometryGenerator, IStateHolder {
	String getName();

	IMesh generateMesh(final List<IGeneratorInput> generatorInputs);
}
