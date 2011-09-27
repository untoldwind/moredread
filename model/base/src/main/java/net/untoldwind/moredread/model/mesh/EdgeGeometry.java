package net.untoldwind.moredread.model.mesh;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class EdgeGeometry<T> extends VertexGeometry<T> implements
		IEdgeGeometry<T> {
	protected final Map<EdgeId, Edge> edges;

	protected EdgeGeometry() {
		edges = new HashMap<EdgeId, Edge>();
	}

	public Edge getEdge(final EdgeId edgeIndex) {
		return edges.get(edgeIndex);
	}

	protected Edge addEdge(final Vertex vertex1, final Vertex vertex2) {
		Edge edge = edges
				.get(new EdgeId(vertex1.getIndex(), vertex2.getIndex()));

		if (edge == null) {
			edge = new Edge(this, vertex1, vertex2);

			edges.put(edge.getIndex(), edge);
		}

		return edge;
	}

	public Collection<Edge> getEdges() {
		return edges.values();
	}

	public void removeEdges(final Set<EdgeId> edgeIds) {
		for (final EdgeId edgeId : edgeIds) {
			final Edge edge = edges.get(edgeId);
			if (edge != null) {
				edge.remove();
				edges.remove(edgeId);
			}
		}
		markDirty();
	}
}
