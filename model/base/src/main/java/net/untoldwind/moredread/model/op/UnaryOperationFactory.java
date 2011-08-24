package net.untoldwind.moredread.model.op;

import net.untoldwind.moredread.model.op.merge.coplanar.CoplanarMergeOperation;

public class UnaryOperationFactory {
	public enum Implementation {
		COPLANAR_MERGE
	}

	public static IUnaryOperation createOperation(
			final Implementation implementation) {
		switch (implementation) {
		case COPLANAR_MERGE:
			return new CoplanarMergeOperation();
		default:
			throw new RuntimeException("Unknown implementation: "
					+ implementation);
		}
	}
}
