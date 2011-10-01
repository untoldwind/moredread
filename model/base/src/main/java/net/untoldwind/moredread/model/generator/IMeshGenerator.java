package net.untoldwind.moredread.model.generator;

import net.untoldwind.moredread.model.mesh.IMesh;

/**
 * Generic interface of all mesh generators.
 * 
 * A mesh generator is some piece of logic creating a mesh.
 */
public interface IMeshGenerator extends IGeometryGenerator<IMesh> {
	/**
	 * Get the name of the generator.
	 * 
	 * @return The name of the generator.
	 */
	String getName();
}
