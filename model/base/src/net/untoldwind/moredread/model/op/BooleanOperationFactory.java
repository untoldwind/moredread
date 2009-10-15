package net.untoldwind.moredread.model.op;

import net.untoldwind.moredread.model.op.bool.blebopd.BlebopdBooleanOperation;
import net.untoldwind.moredread.model.op.bool.blebopf.BlebopfBooleanOperation;

public class BooleanOperationFactory {
	public enum Implementation {
		/**
		 * Blenders boolop in float (the name roughly reflects the stability of
		 * this algorithm)
		 */
		BLEBOPF,
		/**
		 * Blenders boolop in double (the name roughly reflects the stability of
		 * this algorithm)
		 */
		BLEBOPD
	};

	public static IBooleanOperation createBooleanOperation(
			final Implementation implementation) {
		switch (implementation) {
		case BLEBOPF:
			return new BlebopfBooleanOperation();
		case BLEBOPD:
			return new BlebopdBooleanOperation();
		}

		throw new RuntimeException("Unknown implementation: " + implementation);
	}

	public static IBooleanOperation createDefault() {
		// Kind of pointless to make this configurable at this point
		return createBooleanOperation(Implementation.BLEBOPD);
	}
}
