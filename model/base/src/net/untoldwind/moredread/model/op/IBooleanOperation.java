package net.untoldwind.moredread.model.op;

import net.untoldwind.moredread.model.mesh.TriangleMesh;

public interface IBooleanOperation {
	TriangleMesh intersect(TriangleMesh meshA, TriangleMesh meshB);
}
