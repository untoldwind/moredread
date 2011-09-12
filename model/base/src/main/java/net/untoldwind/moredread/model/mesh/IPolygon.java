package net.untoldwind.moredread.model.mesh;

import java.util.Collection;
import java.util.List;

import com.jme.math.Vector3f;

public interface IPolygon extends IGeometry<IPolygon> {
	Vector3f getMeanNormal();

	int getVertexCount();

	List<? extends IVertex> getVertices();

	Collection<? extends IEdge> getEdges();

	int[] getPolygonStripCounts();

	int[] getPolygonContourCounts();

	boolean isClosed();

	Vector3f getCenter();
}
