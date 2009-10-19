package net.untoldwind.moredread.model.op;

public class UnaryOperationFactory {
	public enum Implementation {
		COPLANAR_MERGE
	}

	public IUnaryOperation createOperation(final Implementation implementation) {
		switch (implementation) {
		default:
			throw new RuntimeException("Unknown implementation: "
					+ implementation);
		}
	}
}
