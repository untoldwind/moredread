package net.untoldwind.moredread.model.mesh;

import java.util.List;

import net.untoldwind.moredread.model.math.Vector3;

public interface IVertexGeometry<T> extends IGeometry<T> {
	int getVertexCount();

	List<? extends IVertex> getVertices();

	IVertex getVertex(final int vertexIndes);

	Vector3 getCenter();
}
