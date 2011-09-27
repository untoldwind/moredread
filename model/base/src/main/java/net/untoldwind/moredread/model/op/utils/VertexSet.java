package net.untoldwind.moredread.model.op.utils;

import java.util.HashMap;
import java.util.Map;

import net.untoldwind.moredread.model.mesh.IVertex;

import com.jme.math.FastMath;
import com.jme.math.Vector3f;

public class VertexSet {
	private final Map<VertexKey, IVertex> vertexMap = new HashMap<VertexKey, IVertex>();

	public void addVertex(final IVertex vertex) {
		vertexMap.put(new VertexKey(vertex.getPoint()), vertex);
	}

	public IVertex findVertex(final Vector3f point) {
		return vertexMap.get(new VertexKey(point));
	}

	private static class VertexKey {
		private final float TOLERANCE = 1e-5f;

		private final Vector3f point;
		private final int hashCode;

		VertexKey(final Vector3f point) {
			this.point = point;
			this.hashCode = 31 * 31 * Math.round(point.x / TOLERANCE / 2) + 31
					* Math.round(point.y / TOLERANCE / 2)
					+ Math.round(point.z / TOLERANCE / 2);
		}

		@Override
		public int hashCode() {
			return hashCode;
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
			final VertexKey other = (VertexKey) obj;

			return FastMath.abs(point.x - other.point.x) < TOLERANCE
					&& FastMath.abs(point.y - other.point.y) < TOLERANCE
					&& FastMath.abs(point.z - other.point.z) < TOLERANCE;
		}
	}
}