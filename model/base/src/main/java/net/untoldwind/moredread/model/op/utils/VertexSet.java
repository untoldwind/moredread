package net.untoldwind.moredread.model.op.utils;

import java.util.HashMap;
import java.util.Map;

import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.mesh.IVertex;

public class VertexSet {
	private final Map<VectorKey, IVertex> vertexMap = new HashMap<VectorKey, IVertex>();

	public void addVertex(final IVertex vertex) {
		vertexMap.put(new VectorKey(vertex.getPoint()), vertex);
	}

	public IVertex findVertex(final Vector3 point) {
		return vertexMap.get(new VectorKey(point));
	}
}
