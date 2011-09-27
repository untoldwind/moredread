package net.untoldwind.moredread.model.mesh;

import java.util.Collection;

public interface IEdgeGeometry<T> extends IVertexGeometry<T> {
	Collection<? extends IEdge> getEdges();

	IEdge getEdge(final EdgeId edgeIndex);

}
