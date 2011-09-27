package net.untoldwind.moredread.model.mesh;

import com.jme.math.Vector3f;

public interface IPolygon extends IEdgeGeometry<IPolygon> {
	Vector3f getMeanNormal();

	int[] getPolygonStripCounts();

	int[] getPolygonContourCounts();

	boolean isClosed();

	Vector3f getCenter();
}
