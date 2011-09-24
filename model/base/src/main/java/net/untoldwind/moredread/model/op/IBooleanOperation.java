package net.untoldwind.moredread.model.op;

import net.untoldwind.moredread.model.mesh.IMesh;

public interface IBooleanOperation {
	public enum BoolOperation {
		INTERSECTION,
		UNION,
		DIFFERENCE
	}

	IMesh performBoolean(BoolOperation operation, IMesh meshA, IMesh meshB);
}
