package net.untoldwind.moredread.model.mesh;

import java.util.List;

import com.jme.math.Vector3f;

public interface IVertexGeometry<T> extends IGeometry<T> {
	int getVertexCount();

	List<? extends IVertex> getVertices();

	IVertex getVertex(final int vertexIndes);

	Vector3f getCenter();
}
