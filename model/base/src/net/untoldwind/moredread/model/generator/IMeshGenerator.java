package net.untoldwind.moredread.model.generator;

import java.util.List;

import net.untoldwind.moredread.model.mesh.Mesh;

public interface IMeshGenerator extends IGeometryGenerator {
	String getName();

	Mesh<?> generateMesh(final List<IGeneratorInput> generatorInputs);
}
