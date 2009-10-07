package net.untoldwind.moredread.model.triangulator.fist;


class Orientation {

	/**
	 * determine the outer polygon and the orientation of the polygons; the
	 * default orientation is CCW for the outer-most polygon, and CW for the
	 * inner polygons. the polygonal loops are referenced by loops[i1,..,i2-1].
	 */
	static void adjustOrientation(Triangulator triRef, int i1, int i2) {

		double area;
		int i, outer;
		int ind;

		if (i1 >= i2)
			System.out
					.println("Orientation:adjustOrientation Problem i1>=i2 !!!");

		if (triRef.numLoops >= triRef.maxNumPolyArea) {
			// System.out.println("Orientation:adjustOrientation Expanding polyArea array .");
			triRef.maxNumPolyArea = triRef.numLoops;
			double old[] = triRef.polyArea;
			triRef.polyArea = new double[triRef.maxNumPolyArea];
			if (old != null)
				System.arraycopy(old, 0, triRef.polyArea, 0, old.length);
		}

		// for each contour, compute its signed area, i.e., its orientation. the
		// contour with largest area is assumed to be the outer-most contour.
		for (i = i1; i < i2; ++i) {
			ind = triRef.loops[i];
			triRef.polyArea[i] = polygonArea(triRef, ind);
		}

		// determine the outer-most contour
		area = Math.abs(triRef.polyArea[i1]);
		outer = i1;
		for (i = i1 + 1; i < i2; ++i) {
			if (area < Math.abs(triRef.polyArea[i])) {
				area = Math.abs(triRef.polyArea[i]);
				outer = i;
			}
		}

		// default: the outer contour is referenced by loops[i1]
		if (outer != i1) {
			ind = triRef.loops[i1];
			triRef.loops[i1] = triRef.loops[outer];
			triRef.loops[outer] = ind;

			area = triRef.polyArea[i1];
			triRef.polyArea[i1] = triRef.polyArea[outer];
			triRef.polyArea[outer] = area;
		}

		// adjust the orientation
		if (triRef.polyArea[i1] < 0.0)
			triRef.swapLinks(triRef.loops[i1]);
		for (i = i1 + 1; i < i2; ++i) {
			if (triRef.polyArea[i] > 0.0)
				triRef.swapLinks(triRef.loops[i]);
		}
	}

	/**
	 * This function computes twice the signed area of a simple closed polygon.
	 */
	static double polygonArea(Triangulator triRef, int ind) {
		int hook = 0;
		int ind1, ind2;
		int i1, i2;
		double area = 0.0, area1 = 0;

		ind1 = ind;
		i1 = triRef.fetchData(ind1);
		ind2 = triRef.fetchNextData(ind1);
		i2 = triRef.fetchData(ind2);
		area = Numerics.stableDet2D(triRef, hook, i1, i2);

		ind1 = ind2;
		i1 = i2;
		while (ind1 != ind) {
			ind2 = triRef.fetchNextData(ind1);
			i2 = triRef.fetchData(ind2);
			area1 = Numerics.stableDet2D(triRef, hook, i1, i2);
			area += area1;
			ind1 = ind2;
			i1 = i2;
		}

		return area;
	}

	/**
	 * Determine the orientation of the polygon. The default orientation is CCW.
	 */
	static void determineOrientation(Triangulator triRef, int ind) {
		double area;

		// compute the polygon's signed area, i.e., its orientation.
		area = polygonArea(triRef, ind);

		// adjust the orientation (i.e., make it CCW)
		if (area < 0.0) {
			triRef.swapLinks(ind);
			triRef.ccwLoop = false;
		}

	}

}
