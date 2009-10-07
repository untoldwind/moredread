package net.untoldwind.moredread.model.mesh;

import net.untoldwind.moredread.model.enums.GeometryType;

/**
 * Most abstract interface for all geometry objects.
 * 
 * A geometry is some kind of object in 3D space.
 * 
 * @author junglas
 */
public interface IGeometry {
	GeometryType getGeometryType();
}
