package net.untoldwind.moredread.model.op;

import net.untoldwind.moredread.model.mesh.IPolygon;

public interface ITriangulator {
	int[] triangulate(IPolygon polygon);
}
