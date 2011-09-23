package net.untoldwind.moredread.model.op.bool.bspfilter;

public enum VertexTag {
	ON,
	IN,
	OUT;

	public static boolean allOn(final VertexTag... tags) {
		for (final VertexTag tag : tags) {
			if (tag != ON) {
				return false;
			}
		}
		return true;
	}

	public static boolean allIn(final VertexTag... tags) {
		for (final VertexTag tag : tags) {
			if (tag == OUT) {
				return false;
			}
		}
		return true;
	}

	public static boolean allOut(final VertexTag... tags) {
		for (final VertexTag tag : tags) {
			if (tag == IN) {
				return false;
			}
		}
		return true;
	}
}
