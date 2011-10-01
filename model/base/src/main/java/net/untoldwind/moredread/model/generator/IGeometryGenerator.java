package net.untoldwind.moredread.model.generator;

import java.util.List;

import net.untoldwind.moredread.model.enums.GeometryType;
import net.untoldwind.moredread.model.mesh.IGeometry;
import net.untoldwind.moredread.model.scene.GeneratorNode;
import net.untoldwind.moredread.model.state.IStateHolder;

import org.eclipse.core.runtime.IAdaptable;

public interface IGeometryGenerator<T extends IGeometry<?>> extends
		IStateHolder, IAdaptable {
	String getName();

	GeometryType getGeometryType();

	GeneratorNode getGeneratorNode();

	/**
	 * Generate the mesh.
	 * 
	 * @param generatorInputs
	 *            Input parameters of the generator.
	 * @return The generated mesh
	 */
	T generateMesh(final List<IGeneratorInput> generatorInputs);
}
