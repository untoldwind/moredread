package net.untoldwind.moredread.model.mesh;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.untoldwind.moredread.model.enums.GeometryType;
import net.untoldwind.moredread.model.enums.MeshType;
import net.untoldwind.moredread.model.state.IStateHolder;

import com.jme.math.Vector3f;

public abstract class Mesh<T extends AbstractFace<?>> implements IMesh,
		IStateHolder {
	protected int vertexCount = 0;
	protected final List<Vertex> vertices;
	protected final Map<EdgeId, AbstractEdge> edges;
	protected final List<T> faces;
	protected boolean dirty = false;

	protected Mesh() {
		vertices = new ArrayList<Vertex>();
		edges = new HashMap<EdgeId, AbstractEdge>();
		faces = new ArrayList<T>();
	}

	@Override
	public GeometryType getGeometryType() {
		return GeometryType.MESH;
	}

	public Vertex addVertex(final Vector3f point) {
		return addVertex(point, false);
	}

	public Vertex addVertex(final Vector3f point, final boolean smooth) {
		final Vertex vertex = new Vertex(this, vertices.size(), point);

		vertices.add(vertex);

		return vertex;
	}

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

	public AbstractEdge getEdge(final EdgeId edgeIndex) {
		return edges.get(edgeIndex);
	}

	protected AbstractEdge addEdge(final Vertex vertex1, final Vertex vertex2) {
		AbstractEdge edge = edges.get(new EdgeId(vertex1.getIndex(), vertex2
				.getIndex()));

		if (edge == null) {
			edge = new AbstractEdge(this, vertex1, vertex2);

			edges.put(edge.getIndex(), edge);
		}

		return edge;
	}

	public Collection<AbstractEdge> getEdges() {
		return edges.values();
	}

	public List<T> getFaces() {
		return faces;
	}

	public T getFace(final int faceIndex) {
		if (faceIndex < 0 || faceIndex >= faces.size()) {
			return null;
		}
		return faces.get(faceIndex);
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

	public abstract TriangleMesh toTriangleMesh();
}
