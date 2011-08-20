package net.untoldwind.moredread.model.mesh;

import net.untoldwind.moredread.model.enums.GeometryType;
import net.untoldwind.moredread.model.state.IStateHolder;

/**
 * Most abstract interface for all geometry objects.
 * 
 * A geometry is some kind of object in 3D space.
 */
public interface IGeometry extends IStateHolder {
	GeometryType getGeometryType();
}
