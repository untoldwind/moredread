package net.untoldwind.moredread.model.mesh;

import net.untoldwind.moredread.model.math.Vector3;

public interface IPolygon extends IEdgeGeometry<IPolygon> {
	Vector3 getMeanNormal();

	int[] getPolygonStripCounts();

	int[] getPolygonContourCounts();

	boolean isClosed();
}
