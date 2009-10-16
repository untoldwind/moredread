package net.untoldwind.moredread.model.mesh;

public class QuadFaceId extends FaceId {
	private final int i1;
	private final int i2;
	private final int i3;
	private final int i4;

	public QuadFaceId(int i1, int i2, int i3, int i4) {
		int swap;

		if (i1 > i2) {
			swap = i2;
			i2 = i1;
			i1 = swap;
		}
		if (i1 > i3) {
			swap = i3;
			i3 = i1;
			i1 = swap;
		}
		if (i1 > i4) {
			swap = i4;
			i4 = i1;
			i1 = swap;
		}
		if (i2 > i3) {
			swap = i3;
			i3 = i2;
			i2 = swap;
		}
		if (i2 > i4) {
			swap = i4;
			i4 = i2;
			i2 = swap;
		}
		if (i3 > i4) {
			swap = i4;
			i4 = i3;
			i3 = swap;
		}

		this.i1 = i1;
		this.i2 = i2;
		this.i3 = i3;
		this.i4 = i4;
	}

	@Override
	public int hashCode() {
		return 31 * 31 * 31 * i1 + 31 * 31 * i2 + 31 * i3 + i4;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final QuadFaceId other = (QuadFaceId) obj;
		return i1 == other.i1 && i2 == other.i2 && i3 == other.i3
				&& i4 == other.i4;
	}

}
