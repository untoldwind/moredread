package net.untoldwind.moredread.model.mesh;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.untoldwind.moredread.model.enums.GeometryType;
import net.untoldwind.moredread.model.enums.MeshType;
import net.untoldwind.moredread.model.state.IStateHolder;

import com.jme.math.Vector3f;

public abstract class Mesh<FaceK extends FaceId, FaceT extends Face<?, ?, ?>>
		implements IMesh, IStateHolder {
	protected int vertexCount = 0;
	protected final List<Vertex<FaceT>> vertices;
	protected final Map<EdgeId, Edge<FaceT>> edges;
	protected final Map<FaceK, FaceT> faces;
	protected boolean dirty = false;

	protected Mesh() {
		vertices = new ArrayList<Vertex<FaceT>>();
		edges = new HashMap<EdgeId, Edge<FaceT>>();
		faces = new LinkedHashMap<FaceK, FaceT>();
	}

	@Override
	public GeometryType getGeometryType() {
		return GeometryType.MESH;
	}

	public Vertex<FaceT> addVertex(final Vector3f point) {
		return addVertex(point, false);
	}

	public Vertex<FaceT> addVertex(final Vector3f point, final boolean smooth) {
		final Vertex<FaceT> vertex = new Vertex<FaceT>(this, vertices.size(),
				point);

		vertices.add(vertex);

		return vertex;
	}

	public List<Vertex<FaceT>> getVertices() {
		return vertices;
	}

	@Override
	public Vertex<FaceT> getVertex(final int vertexIndes) {
		if (vertexIndes < 0 || vertexIndes >= vertices.size()) {
			return null;
		}
		return vertices.get(vertexIndes);
	}

	public Edge<FaceT> getEdge(final EdgeId edgeIndex) {
		return edges.get(edgeIndex);
	}

	protected Edge<FaceT> addEdge(final Vertex<FaceT> vertex1,
			final Vertex<FaceT> vertex2) {
		Edge<FaceT> edge = edges.get(new EdgeId(vertex1.getIndex(), vertex2
				.getIndex()));

		if (edge == null) {
			edge = new Edge<FaceT>(this, vertex1, vertex2);

			edges.put(edge.getIndex(), edge);
		}

		return edge;
	}

	public Collection<Edge<FaceT>> getEdges() {
		return edges.values();
	}

	@Override
	public Collection<FaceT> getFaces() {
		return faces.values();
	}

	public FaceT getFace(final FaceId faceIndex) {
		return faces.get(faceIndex);
	}

	public void removeFaces(final Set<FaceId> faceIds) {
		for (final FaceId faceId : faceIds) {
			final FaceT face = faces.get(faceId);
			if (face != null) {
				face.remove();
				faces.remove(faceId);
			}
		}
		markDirty();
	}

	public void removeEdges(final Set<EdgeId> edgeIds) {
		for (final EdgeId edgeId : edgeIds) {
			final Edge<FaceT> edge = edges.get(edgeId);
			if (edge != null) {
				edge.remove();
				edges.remove(edgeId);
			}
		}
		markDirty();
	}

	public void removeVertices(final Set<Integer> vertexIds) {
		final Iterator<Vertex<FaceT>> it = vertices.iterator();

		while (it.hasNext()) {
			final Vertex<FaceT> vertex = it.next();

			if (vertexIds.contains(vertex.getIndex())) {
				vertex.remove();
				it.remove();
			}
		}
		int index = 0;
		for (final Vertex<FaceT> vertex : vertices) {
			vertex.setIndex(index++);
		}
		markDirty();
	}

	void markDirty() {
		dirty = true;
	}

	public boolean clearDirty() {
		if (dirty) {
			dirty = false;
			return true;
		}
		return false;
	}

	public abstract MeshType getMeshType();
}
