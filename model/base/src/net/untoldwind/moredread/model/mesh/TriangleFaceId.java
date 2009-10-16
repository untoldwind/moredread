package net.untoldwind.moredread.model.mesh;

public class TriangleFaceId extends FaceId {
	private final int i1;
	private final int i2;
	private final int i3;

	public TriangleFaceId(int i1, int i2, int i3) {
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
		if (i2 > i3) {
			swap = i3;
			i3 = i2;
			i2 = swap;
		}

		this.i1 = i1;
		this.i2 = i2;
		this.i3 = i3;
	}

	public int getIndex1() {
		return i1;
	}

	public int getIndex2() {
		return i2;
	}

	public int getIndex3() {
		return i3;
	}

	@Override
	public int hashCode() {
		return 31 * 31 * i1 + 31 * i2 + i3;
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
		final TriangleFaceId other = (TriangleFaceId) obj;
		return i1 == other.i1 && i2 == other.i2 && i3 == other.i3;
	}
}
