package net.untoldwind.moredread.model.op;

import net.untoldwind.moredread.model.op.bool.BSPBooleanOperation;

public class BooleanOperationFactory {
	public enum Implementation {
		BSP
	};

	public static IBooleanOperation createBooleanOperation(
			final Implementation implementation) {
		switch (implementation) {
		case BSP:
			return new BSPBooleanOperation();
		}

		throw new RuntimeException("Unknown implementation: " + implementation);
	}
}
