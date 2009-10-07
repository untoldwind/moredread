package net.untoldwind.moredread.model.mesh;

import java.util.List;

import com.jme.math.Vector3f;

public interface IPolygon extends IGeometry {
	Vector3f getMeanNormal();

	List<? extends IPoint> getPolygonPoints();

	int[] getPolygonStripCounts();

	int[] getPolygonContourCounts();

	boolean isClosed();
}
