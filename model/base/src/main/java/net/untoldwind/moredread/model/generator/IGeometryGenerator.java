package net.untoldwind.moredread.model.generator;

import java.util.List;

import net.untoldwind.moredread.model.enums.GeometryType;
import net.untoldwind.moredread.model.mesh.IGeometry;
import net.untoldwind.moredread.model.scene.GeneratorNode;
import net.untoldwind.moredread.model.state.IStateHolder;

import org.eclipse.core.runtime.IAdaptable;

/**
 * Interface of all geometry generator.
 * 
 * A geometry generator is just some sort of algorithm producing some sort of
 * geometry.
 * 
 * @param <T>
 *            The concrete type of geometry being generated.
 */
public interface IGeometryGenerator<T extends IGeometry<?>> extends
		IStateHolder, IAdaptable {
	/**
	 * Get the name of the geometry generator.
	 * 
	 * @return The name of the geometry generator
	 */
	String getName();

	/**
	 * The type of geometry the generator will produce.
	 * 
	 * @return The geometry type of the generated geometry.
	 */
	GeometryType getGeometryType();

	/**
	 * Get the GeneratorNode using this geometry generator.
	 * 
	 * In most cases geometry generators are attached to a GeneratorNode in the
	 * scene. In some cases a geometry generator might be used standalone, in
	 * these cases this method will return <tt>null</tt>.
	 * 
	 * @return The GeneratorNode using the generator (might by <tt>null</tt>)
	 */
	GeneratorNode getGeneratorNode();

	/**
	 * Generate the geometry.
	 * 
	 * @param generatorInputs
	 *            Input parameters of the generator.
	 * @return The generated geometry
	 */
	T generateGeometry(final List<IGeneratorInput> generatorInputs);
}
