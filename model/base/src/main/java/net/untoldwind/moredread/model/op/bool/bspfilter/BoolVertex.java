package net.untoldwind.moredread.model.op.bool.bspfilter;

import net.untoldwind.moredread.model.math.Plane;
import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.mesh.IVertex;

public class BoolVertex {
	public interface IBoolIndex {
	}

	public static class IntegerIndex implements IBoolIndex {
		private final int index;

		public IntegerIndex(final int index) {
			super();
			this.index = index;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + index;
			return result;
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
			final IntegerIndex other = (IntegerIndex) obj;
			if (index != other.index) {
				return false;
			}
			return true;
		}

	}

	public static class ConstructedIndex implements IBoolIndex {
		private final IBoolIndex index1;
		private final IBoolIndex index2;
		private final Plane plane;

		public ConstructedIndex(final IBoolIndex index1,
				final IBoolIndex index2, final Plane plane) {
			this.index1 = index1;
			this.index2 = index2;
			this.plane = plane;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + index1.hashCode() + index2.hashCode();
			result = prime * result + plane.hashCode();
			return result;
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
			final ConstructedIndex other = (ConstructedIndex) obj;
			return plane.equals(other.plane)
					&& ((index1.equals(other.index1) && index2
							.equals(other.index2)) || (index1
							.equals(other.index2) && index2
							.equals(other.index1)));
		}
	}

	private final Vector3 point;
	private final IBoolIndex index;

	public BoolVertex(final Vector3 point) {
		this.point = point;
		this.index = null;
	}

	public BoolVertex(final Vector3 point, final IBoolIndex index1,
			final IBoolIndex index2, final Plane plane) {
		this.point = point;
		this.index = new ConstructedIndex(index1, index2, plane);
	}

	public BoolVertex(final int offset, final IVertex vertex) {
		this.point = vertex.getPoint();
		this.index = new IntegerIndex(vertex.getIndex() + offset);
	}

	public Vector3 getPoint() {
		return point;
	}

	public IBoolIndex getIndex() {
		return index;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("BoolVertex [point=");
		builder.append(point);
		builder.append(", orginalIndex=");
		builder.append(index);
		builder.append("]");
		return builder.toString();
	}

}
