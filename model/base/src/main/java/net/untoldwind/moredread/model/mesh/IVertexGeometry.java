package net.untoldwind.moredread.model.mesh;

import java.util.List;

public interface IVertexGeometry<T> extends IGeometry<T> {
	int getVertexCount();

	List<? extends IVertex> getVertices();

	IVertex getVertex(final int vertexIndes);
}
