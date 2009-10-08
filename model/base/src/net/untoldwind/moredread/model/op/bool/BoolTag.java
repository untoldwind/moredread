package net.untoldwind.moredread.model.op.bool;

public class BoolTag {
	public static int IN = 0x02; // Below the plane
	public static int ON = 0x00; // On the plane
	public static int OUT = 0x01; // Above the plane
	public static int INOUT = 0x0E; // Above and below the plane
	public static int INON = 0x12; // Below and on the plane
	public static int OUTON = 0x11; // Above and on the plane
	public static int UNCLASSIFIED = 0x0F; // Expecting to be classified

	public static int PHANTOM = 0x0C; // Phantom face: verts form collinear
	// triangle
	public static int OVERLAPPED = 0x0D; // Overlapped face
	public static int BROKEN = 0x0B; // Splitted and unused ...

	public static int ON_ON_IN = IN;
	public static int ON_IN_ON = IN << 2;
	public static int IN_ON_ON = IN << 4;

	public static int ON_ON_OUT = OUT;
	public static int ON_OUT_ON = OUT << 2;
	public static int OUT_ON_ON = OUT << 4;

	public static int ON_ON_ON = ON;
	public static int IN_IN_IN = IN_ON_ON | ON_IN_ON | ON_ON_IN;
	public static int OUT_OUT_OUT = OUT_ON_ON | ON_OUT_ON | ON_ON_OUT;

	public static int IN_IN_ON = IN_ON_ON | ON_IN_ON;
	public static int IN_ON_IN = IN_ON_ON | ON_ON_IN;
	public static int ON_IN_IN = ON_IN_ON | ON_ON_IN;

	public static int OUT_OUT_ON = OUT_ON_ON | ON_OUT_ON;
	public static int OUT_ON_OUT = OUT_ON_ON | ON_ON_OUT;
	public static int ON_OUT_OUT = ON_OUT_ON | ON_ON_OUT;

	public static int IN_OUT_OUT = IN_ON_ON | ON_OUT_OUT;
	public static int OUT_IN_OUT = ON_IN_ON | OUT_ON_OUT;
	public static int OUT_OUT_IN = ON_ON_IN | OUT_OUT_ON;

	public static int OUT_IN_IN = ON_IN_IN | OUT_ON_ON;
	public static int IN_OUT_IN = IN_ON_IN | ON_OUT_ON;
	public static int IN_IN_OUT = IN_IN_ON | ON_ON_OUT;

	public static int IN_ON_OUT = IN_ON_ON | ON_ON_OUT;
	public static int IN_OUT_ON = IN_ON_ON | ON_OUT_ON;
	public static int ON_IN_OUT = ON_IN_ON | ON_ON_OUT;
	public static int ON_OUT_IN = ON_ON_IN | ON_OUT_ON;
	public static int OUT_IN_ON = ON_IN_ON | OUT_ON_ON;
	public static int OUT_ON_IN = ON_ON_IN | OUT_ON_ON;

	public static int createTAG(final int tag1, final int tag2, final int tag3) {
		return (tag1 << 4 | tag2 << 2 | tag3);
	}

	public static int createTAG(final int i) {
		return i < 0 ? IN : i > 0 ? OUT : ON;
	}

	public static int addON(final int tag) {
		return (tag == IN ? INON : (tag == OUT ? OUTON : tag));
	}

	public static boolean compTAG(final int tag1, final int tag2) {
		return (tag1 == tag2) || (addON(tag1) == addON(tag2));
	}
}
