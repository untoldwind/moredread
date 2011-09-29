package net.untoldwind.moredread.model.mesh;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.untoldwind.moredread.model.math.Vector3;

public abstract class VertexGeometry<T> implements IVertexGeometry<T> {
	protected int vertexCount = 0;
	protected final List<Vertex> vertices;
	protected boolean dirty = false;

	protected VertexGeometry() {
		vertices = new ArrayList<Vertex>();
	}

	public Vertex addVertex(final Vector3 point) {
		return addVertex(point, false);
	}

	public Vertex addVertex(final Vector3 point, final boolean smooth) {
		final Vertex vertex = new Vertex(this, vertices.size(), point);

		vertices.add(vertex);

		return vertex;
	}

	@Override
	public int getVertexCount() {
		return vertices.size();
	}

	@Override
	public List<Vertex> getVertices() {
		return vertices;
	}

	@Override
	public Vertex getVertex(final int vertexIndes) {
		if (vertexIndes < 0 || vertexIndes >= vertices.size()) {
			return null;
		}
		return vertices.get(vertexIndes);
	}

	@Override
	public Vector3 getCenter() {
		final Vector3 center = new Vector3(0, 0, 0);

		for (final IPoint vertex : vertices) {
			center.addLocal(vertex.getPoint());
		}

		center.divideLocal(vertices.size());

		return center;
	}

	public void removeVertices(final Set<Integer> vertexIds) {
		final Iterator<Vertex> it = vertices.iterator();

		while (it.hasNext()) {
			final Vertex vertex = it.next();

			if (vertexIds.contains(vertex.getIndex())) {
				vertex.remove();
				it.remove();
			}
		}
		int index = 0;
		for (final Vertex vertex : vertices) {
			vertex.setIndex(index++);
		}
		markDirty();
	}

	protected void markDirty() {
		dirty = true;
	}

	public boolean clearDirty() {
		if (dirty) {
			dirty = false;
			return true;
		}
		return false;
	}
}
