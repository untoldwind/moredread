package net.untoldwind.moredread.model.op.bool.bspfilter;

public enum BooleanTag {
	IN,
	OUT,
	IN_OUT;

	public static BooleanTag combine(final BooleanTag tag1,
			final BooleanTag tag2) {
		if (tag1 == tag2) {
			return tag1;
		}
		return IN_OUT;
	}

	public static BooleanTag combine(final BooleanTag tag1,
			final BooleanTag tag2, final BooleanTag tag3) {
		if (tag1 == tag2 && tag2 == tag3) {
			return tag1;
		}
		return IN_OUT;
	}
}
