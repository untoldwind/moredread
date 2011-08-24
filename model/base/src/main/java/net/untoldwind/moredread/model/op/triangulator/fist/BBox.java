package net.untoldwind.moredread.model.op.triangulator.fist;


/**
 * Bounding Box class for Triangulator.
 */
class BBox {
	int imin; /* lexicographically smallest point, determines min-x */
	int imax; /* lexicographically largest point, determines max-x */
	double ymin; /* minimum y-coordinate */
	double ymax; /* maximum y-coordinate */

	/**
	 * This constructor computes the bounding box of a line segment whose end
	 * points i, j are sorted according to x-coordinates.
	 */
	BBox(Triangulator triRef, int i, int j) {
		// assert(InPointsList(i));
		// assert(InPointsList(j));

		imin = Math.min(i, j);
		imax = Math.max(i, j);
		ymin = Math.min(triRef.points[imin].y, triRef.points[imax].y);
		ymax = Math.max(triRef.points[imin].y, triRef.points[imax].y);
	}

	boolean pntInBBox(Triangulator triRef, int i) {
		return (((imax < i) ? false : ((imin > i) ? false
				: ((ymax < triRef.points[i].y) ? false
						: ((ymin > triRef.points[i].y) ? false : true)))));
	}

	boolean BBoxOverlap(BBox bb) {
		return (((imax < (bb).imin) ? false : ((imin > (bb).imax) ? false
				: ((ymax < (bb).ymin) ? false : ((ymin > (bb).ymax) ? false
						: true)))));
	}

	boolean BBoxContained(BBox bb) {
		return ((imin <= (bb).imin) && (imax >= (bb).imax)
				&& (ymin <= (bb).ymin) && (ymax >= (bb).ymax));
	}

	boolean BBoxIdenticalLeaf(BBox bb) {
		return ((imin == (bb).imin) && (imax == (bb).imax));
	}

	void BBoxUnion(BBox bb1, BBox bb3) {
		(bb3).imin = Math.min(imin, (bb1).imin);
		(bb3).imax = Math.max(imax, (bb1).imax);
		(bb3).ymin = Math.min(ymin, (bb1).ymin);
		(bb3).ymax = Math.max(ymax, (bb1).ymax);
	}

	double BBoxArea(Triangulator triRef) {
		return (triRef.points[imax].x - triRef.points[imin].x) * (ymax - ymin);
	}
}
