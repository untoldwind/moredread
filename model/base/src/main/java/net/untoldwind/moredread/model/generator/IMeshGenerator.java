package net.untoldwind.moredread.model.generator;

import java.util.List;

import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.state.IStateHolder;

/**
 * Generic interface of all mesh generators.
 * 
 * A mesh generator is some piece of logic creating a mesh.
 */
public interface IMeshGenerator extends IGeometryGenerator, IStateHolder {
	/**
	 * Get the name of the generator.
	 * 
	 * @return The name of the generator.
	 */
	String getName();

	/**
	 * Generate the mesh.
	 * 
	 * @param generatorInputs
	 *            Input parameters of the generator.
	 * @return The generated mesh
	 */
	IMesh generateMesh(final List<IGeneratorInput> generatorInputs);
}
