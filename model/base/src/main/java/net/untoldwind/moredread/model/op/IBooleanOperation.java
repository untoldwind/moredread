package net.untoldwind.moredread.model.op;

import net.untoldwind.moredread.model.mesh.TriangleMesh;

public interface IBooleanOperation {
	public enum BoolOperation {
		INTERSECTION, UNION, DIFFERENCE
	}

	TriangleMesh performBoolean(BoolOperation operation, TriangleMesh meshA,
			TriangleMesh meshB);
}
