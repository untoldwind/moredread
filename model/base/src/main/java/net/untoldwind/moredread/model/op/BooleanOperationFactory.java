package net.untoldwind.moredread.model.op;

import net.untoldwind.moredread.model.op.bool.blebopd.BlebopdBooleanOperation;
import net.untoldwind.moredread.model.op.bool.blebopf.BlebopfBooleanOperation;
import net.untoldwind.moredread.model.op.bool.bspfilter.BSPFilterBooleanOperation;

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
		BLEBOPD,
		BSPFILTER
	};

	public static IBooleanOperation createBooleanOperation(
			final Implementation implementation) {
		switch (implementation) {
		case BLEBOPF:
			return new BlebopfBooleanOperation();
		case BLEBOPD:
			return new BlebopdBooleanOperation();
		case BSPFILTER:
			return new BSPFilterBooleanOperation();
		}

		throw new RuntimeException("Unknown implementation: " + implementation);
	}

	public static IBooleanOperation createDefault() {
		// Kind of pointless to make this configurable at this point
		return createBooleanOperation(Implementation.BLEBOPD);
	}
}
