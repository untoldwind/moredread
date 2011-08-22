package net.untoldwind.moredread.model.mesh;

import java.util.List;

import com.jme.math.Vector3f;

public interface IPolygon extends IGeometry<IPolygon> {
	Vector3f getMeanNormal();

	int getVertexCount();

	List<? extends IPoint> getVertices();

	int[] getPolygonStripCounts();

	int[] getPolygonContourCounts();

	boolean isClosed();

	Vector3f getCenter();
}
