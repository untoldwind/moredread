package net.untoldwind.moredread.model.op.triangulator.fist;

import java.util.List;

import net.untoldwind.moredread.model.mesh.IPoint;
import net.untoldwind.moredread.model.mesh.IPolygon;
import net.untoldwind.moredread.model.op.triangulator.ITriangulator;

import com.jme.math.Vector3f;

public class FISTTriangulator implements ITriangulator {

	public int[] triangulate(final IPolygon polygon) {
		final Triangulator legacyTriangulator = new Triangulator(
				Triangulator.EARS_SEQUENCE);
		final GeometryInfo gi = new GeometryInfo();

		gi.contourCounts = polygon.getPolygonContourCounts();
		gi.stripCounts = polygon.getPolygonStripCounts();
		final List<? extends IPoint> coordinatePoints = polygon.getVertices();

		gi.coordinates = new Vector3f[coordinatePoints.size()];
		for (int i = 0; i < gi.coordinates.length; i++) {
			gi.coordinates[i] = coordinatePoints.get(i).getPoint();
		}
		gi.coordinateIndices = new int[gi.coordinates.length];

		for (int i = 0; i < gi.coordinates.length; i++) {
			gi.coordinateIndices[i] = i;
		}

		legacyTriangulator.triangulate(gi);

		return gi.coordinateIndices;
	}

}
