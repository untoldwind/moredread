package net.untoldwind.moredread.model.mesh;

public class EdgeId {
	private final int i1;
	private final int i2;

	public EdgeId(final int i1, final int i2) {
		this.i1 = i1 < i2 ? i1 : i2;
		this.i2 = i1 < i2 ? i2 : i1;
	}

	@Override
	public int hashCode() {
		return 31 * i1 + i2;
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
		final EdgeId other = (EdgeId) obj;
		return i1 == other.i1 && i2 == other.i2;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("EdgeId [i1=");
		builder.append(i1);
		builder.append(", i2=");
		builder.append(i2);
		builder.append("]");
		return builder.toString();
	}

}
