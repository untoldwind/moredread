package net.untoldwind.moredread.model.op.triangulator.fist;

class NoHash {

	static final int NIL = -1;

	static void insertAfterVtx(final Triangulator triRef, final int iVtx) {
		int size;

		if (triRef.vtxList == null) {
			size = Math.max(triRef.numVtxList + 1, 100);
			triRef.vtxList = new PntNode[size];
		} else if (triRef.numVtxList >= triRef.vtxList.length) {
			size = Math.max(triRef.numVtxList + 1, triRef.vtxList.length + 100);
			final PntNode old[] = triRef.vtxList;
			triRef.vtxList = new PntNode[size];
			System.arraycopy(old, 0, triRef.vtxList, 0, old.length);
		}

		triRef.vtxList[triRef.numVtxList] = new PntNode();
		triRef.vtxList[triRef.numVtxList].pnt = iVtx;
		triRef.vtxList[triRef.numVtxList].next = triRef.reflexVertices;
		triRef.reflexVertices = triRef.numVtxList;
		++triRef.numVtxList;
		++triRef.numReflex;
	}

	static void deleteFromList(final Triangulator triRef, final int i) {
		int indPnt, indPnt1;
		int indVtx;

		if (triRef.numReflex == 0) {
			// System.out.println("NoHash:deleteFromList. numReflex is 0.");
			return;

		}
		indPnt = triRef.reflexVertices;
		if (inVtxList(triRef, indPnt) == false) {
			System.out
					.println("NoHash:deleteFromList. Problem :Not is InVtxList ..."
							+ indPnt);
		}

		indVtx = triRef.vtxList[indPnt].pnt;

		if (indVtx == i) {
			triRef.reflexVertices = triRef.vtxList[indPnt].next;
			--triRef.numReflex;
		} else {
			indPnt1 = triRef.vtxList[indPnt].next;
			while (indPnt1 != NIL) {
				if (inVtxList(triRef, indPnt1) == false) {
					System.out
							.println("NoHash:deleteFromList. Problem :Not is InVtxList ..."
									+ indPnt1);
				}

				indVtx = triRef.vtxList[indPnt1].pnt;
				if (indVtx == i) {
					triRef.vtxList[indPnt].next = triRef.vtxList[indPnt1].next;
					indPnt1 = NIL;
					--triRef.numReflex;
				} else {
					indPnt = indPnt1;
					indPnt1 = triRef.vtxList[indPnt].next;
				}
			}
		}
	}

	static boolean inVtxList(final Triangulator triRef, final int vtx) {
		return ((0 <= vtx) && (vtx < triRef.numVtxList));
	}

	static void freeNoHash(final Triangulator triRef) {

		triRef.noHashingEdges = false;
		triRef.noHashingPnts = false;

		triRef.numVtxList = 0;
	}

	static void prepareNoHashEdges(final Triangulator triRef,
			final int currLoopMin, final int currLoopMax) {
		triRef.loopMin = currLoopMin;
		triRef.loopMax = currLoopMax;

		triRef.noHashingEdges = true;

		return;
	}

	static void prepareNoHashPnts(final Triangulator triRef,
			final int currLoopMin) {
		int ind, ind1;

		triRef.numVtxList = 0;
		triRef.reflexVertices = NIL;

		// insert the reflex vertices into a list
		ind = triRef.loops[currLoopMin];
		ind1 = ind;
		triRef.numReflex = 0;

		do {
			if (triRef.getAngle(ind1) < 0) {
				insertAfterVtx(triRef, ind1);
			}

			ind1 = triRef.fetchNextData(ind1);
		} while (ind1 != ind);

		triRef.noHashingPnts = true;

	}

	static boolean noHashIntersectionExists(final Triangulator triRef,
			final int i1, final int ind1, final int i2, final int i3,
			final BBox bb) {
		int indVtx, ind5;
		int indPnt;
		int i4;
		final int type[] = new int[1];
		boolean flag;
		double y;

		if (triRef.noHashingPnts == false) {
			System.out
					.println("NoHash:noHashIntersectionExists noHashingPnts is false");
		}

		// assert(InPointsList(i1));
		// assert(InPointsList(i2));
		// assert(InPointsList(i3));

		if (triRef.numReflex <= 0) {
			return false;
		}

		// first, let's extend the BBox of the line segment i2, i3 to a BBox
		// of the entire triangle.
		if (i1 < bb.imin) {
			bb.imin = i1;
		} else if (i1 > bb.imax) {
			bb.imax = i1;
		}
		y = triRef.points[i1].y;
		if (y < bb.ymin) {
			bb.ymin = y;
		} else if (y > bb.ymax) {
			bb.ymax = y;
		}

		// check whether the triangle i1, i2, i3 contains any reflex vertex; we
		// assume that i2, i3 is the new diagonal, and that the triangle is
		// oriented CCW.
		indPnt = triRef.reflexVertices;
		flag = false;
		do {
			// assert(InVtxList(ind_pnt));
			indVtx = triRef.vtxList[indPnt].pnt;
			// assert(InPolyList(ind_vtx));
			i4 = triRef.fetchData(indVtx);

			if (bb.pntInBBox(triRef, i4)) {
				// only if the reflex vertex lies inside the BBox of the
				// triangle.
				ind5 = triRef.fetchNextData(indVtx);
				if ((indVtx != ind1) && (indVtx != ind5)) {
					// only if this node isn't i1, and if it still belongs to
					// the
					// polygon
					if (i4 == i1) {
						if (Degenerate.handleDegeneracies(triRef, i1, ind1, i2,
								i3, i4, indVtx)) {
							return true;
						}
					} else if ((i4 != i2) && (i4 != i3)) {
						flag = Numerics.vtxInTriangle(triRef, i1, i2, i3, i4,
								type);
						if (flag) {
							return true;
						}
					}
				}
			}
			indPnt = triRef.vtxList[indPnt].next;

		} while (indPnt != NIL);

		return false;
	}

	static void deleteReflexVertex(final Triangulator triRef, final int ind) {
		// assert(InPolyList(ind));
		deleteFromList(triRef, ind);
	}

	static boolean noHashEdgeIntersectionExists(final Triangulator triRef,
			final BBox bb, final int i1, final int i2, final int ind5,
			final int i5) {
		int ind, ind2;
		int i, i3, i4;
		BBox bb1;

		if (triRef.noHashingEdges == false) {
			System.out
					.println("NoHash:noHashEdgeIntersectionExists noHashingEdges is false");
		}

		triRef.identCntr = 0;

		// check the boundary segments.
		for (i = triRef.loopMin; i < triRef.loopMax; ++i) {
			ind = triRef.loops[i];
			ind2 = ind;
			i3 = triRef.fetchData(ind2);

			do {
				ind2 = triRef.fetchNextData(ind2);
				i4 = triRef.fetchData(ind2);
				// check this segment. we first compute its bounding box.
				bb1 = new BBox(triRef, i3, i4);
				if (bb.BBoxOverlap(bb1)) {
					if (Numerics.segIntersect(triRef, bb.imin, bb.imax,
							bb1.imin, bb1.imax, i5)) {
						return true;
					}
				}
				i3 = i4;
			} while (ind2 != ind);
		}

		// oops! this segment shares one endpoint with at least four other
		// boundary segments! oh well, yet another degenerate situation...
		if (triRef.identCntr >= 4) {
			if (BottleNeck.checkBottleNeck(triRef, i5, i1, i2, ind5)) {
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

}
