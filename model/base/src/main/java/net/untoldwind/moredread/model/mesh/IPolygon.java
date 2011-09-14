package net.untoldwind.moredread.model.mesh;

import java.util.Collection;
import java.util.List;

import com.jme.math.Vector3f;

public interface IPolygon extends IGeometry<IPolygon> {
	Vector3f getMeanNormal();

	int getVertexCount();

	IVertex getVertex(final int vertexIndes);

	List<? extends IVertex> getVertices();

	Collection<? extends IEdge> getEdges();

	IEdge getEdge(final EdgeId edgeIndex);

	int[] getPolygonStripCounts();

	int[] getPolygonContourCounts();

	boolean isClosed();

	Vector3f getCenter();
}
