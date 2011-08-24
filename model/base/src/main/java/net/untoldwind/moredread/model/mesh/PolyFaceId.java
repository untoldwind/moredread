package net.untoldwind.moredread.model.mesh;

public class PolyFaceId extends FaceId {
	private final int index;

	public PolyFaceId(final int index) {
		this.index = index;
	}

	@Override
	public int hashCode() {
		return index;
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
		final PolyFaceId other = (PolyFaceId) obj;
		return index == other.index;
	}

}
