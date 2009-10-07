package net.untoldwind.moredread.model.triangulator;

import net.untoldwind.moredread.model.mesh.IPolygon;

public interface ITriangulator {
	int[] triangulate(IPolygon polygon);
}
