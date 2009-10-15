package net.untoldwind.moredread.model.op;

import net.untoldwind.moredread.model.op.triangulator.fist.FISTTriangulator;

public class TriangulatorFactory {
	public enum Implementation {
		FIST
	};

	public static ITriangulator createTriangulator(
			final Implementation implementation) {
		switch (implementation) {
		case FIST:
			return new FISTTriangulator();
		}
		throw new RuntimeException("Unknown implementation: " + implementation);
	}

	public static ITriangulator createDefault() {
		// Kind of pointless to make this configurable at this point
		return createTriangulator(Implementation.FIST);
	}
}
