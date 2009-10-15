package net.untoldwind.moredread.model.generator;

import java.util.List;

import net.untoldwind.moredread.model.mesh.IMesh;

public interface IMeshGenerator extends IGeometryGenerator {
	String getName();

	IMesh generateMesh(final List<IGeneratorInput> generatorInputs);
}
