package net.untoldwind.moredread.model.generator;

import net.untoldwind.moredread.model.mesh.Mesh;

public interface IMeshGenerator extends IGeometryGenerator {
	String getName();

	Mesh<?> generateMesh();
}
