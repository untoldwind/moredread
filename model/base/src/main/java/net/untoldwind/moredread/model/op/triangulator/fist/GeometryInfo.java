package net.untoldwind.moredread.model.op.triangulator.fist;

import net.untoldwind.moredread.model.math.Vector3;

public class GeometryInfo {
	public static final int POLYGON_ARRAY = 5;

	Vector3[] coordinates;
	int coordinateIndices[] = null;

	int stripCounts[] = null;
	int contourCounts[] = null;

	public int getPrimitive() {
		return POLYGON_ARRAY;
	}

	public Vector3[] getCoordinates() {
		return coordinates;
	}

	public int[] getCoordinateIndices() {
		return coordinateIndices;
	}

	public void setCoordinateIndices(final int[] coordinateIndices) {
		this.coordinateIndices = coordinateIndices;
	}

	public int[] getStripCounts() {
		return stripCounts;
	}

	public void setStripCounts(final int[] stripCounts) {
		this.stripCounts = stripCounts;
	}

	public void setContourCounts(final int[] contourCounts) {
		this.contourCounts = contourCounts;
	}

	public int[] getContourCounts() {
		return contourCounts;
	}

}
