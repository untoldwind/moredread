package net.untoldwind.moredread.model.generator;

import net.untoldwind.moredread.model.mesh.Mesh;

public interface IMeshGenerator {
	String getName();

	Mesh<?> generateMesh();
}
