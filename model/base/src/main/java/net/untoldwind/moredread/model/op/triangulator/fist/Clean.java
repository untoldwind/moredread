package net.untoldwind.moredread.model.op.triangulator.fist;

import com.jme.math.Vector2f;

class Clean {

	static void initPUnsorted(Triangulator triRef, int number) {
		if (number > triRef.maxNumPUnsorted) {
			triRef.maxNumPUnsorted = number;
			triRef.pUnsorted = new Vector2f[triRef.maxNumPUnsorted];
			for (int i = 0; i < triRef.maxNumPUnsorted; i++)
				triRef.pUnsorted[i] = new Vector2f();
		}
	}

	static int cleanPolyhedralFace(Triangulator triRef, int i1, int i2) {
		int removed;
		int i, j, numSorted, index;
		int ind1, ind2;

		initPUnsorted(triRef, triRef.numPoints);

		for (i = 0; i < triRef.numPoints; ++i)
			triRef.pUnsorted[i].set(triRef.points[i]);

		// sort points according to lexicographical order
		/*
		 * System.out.println("Points : (Unsorted)"); for(i=0;
		 * i<triRef.numPoints; i++) System.out.println( i + "pt ( " +
		 * triRef.points[i].x + ", " + triRef.points[i].y + ")");
		 */

		// qsort(points, num_pnts, sizeof(point), &p_comp);

		sort(triRef.points, triRef.numPoints);

		/*
		 * System.out.println("Points : (Sorted)"); for(i=0; i<triRef.numPoints;
		 * i++) System.out.println( i +"pt ( " + triRef.points[i].x + ", " +
		 * triRef.points[i].y + ")");
		 */

		// eliminate duplicate vertices
		i = 0;
		for (j = 1; j < triRef.numPoints; ++j) {
			if (pComp(triRef.points[i], triRef.points[j]) != 0) {
				++i;
				triRef.points[i] = triRef.points[j];
			}
		}
		numSorted = i + 1;
		removed = triRef.numPoints - numSorted;

		/*
		 * System.out.println("Points : (Sorted and eliminated)"); for(i=0;
		 * i<triRef.numPoints; i++) System.out.println( i + "pt ( " +
		 * triRef.points[i].x + ", " + triRef.points[i].y + ")");
		 */

		// renumber the vertices of the polygonal face
		for (i = i1; i < i2; ++i) {
			ind1 = triRef.loops[i];
			ind2 = triRef.fetchNextData(ind1);
			index = triRef.fetchData(ind2);
			while (ind2 != ind1) {
				j = findPInd(triRef.points, numSorted, triRef.pUnsorted[index]);
				triRef.updateIndex(ind2, j);
				ind2 = triRef.fetchNextData(ind2);
				index = triRef.fetchData(ind2);
			}
			j = findPInd(triRef.points, numSorted, triRef.pUnsorted[index]);
			triRef.updateIndex(ind2, j);
		}

		triRef.numPoints = numSorted;

		return removed;
	}

	static void sort(Vector2f points[], int numPts) {
		int i, j;
		Vector2f swap = new Vector2f();

		for (i = 0; i < numPts; i++) {
			for (j = i + 1; j < numPts; j++) {
				if (pComp(points[i], points[j]) > 0) {
					swap.set(points[i]);
					points[i].set(points[j]);
					points[j].set(swap);
				}
			}
		}
		/*
		 * for (i = 0; i < numPts; i++) { System.out.println("pt " + points[i]);
		 * }
		 */
	}

	static int findPInd(Vector2f sorted[], int numPts, Vector2f pnt) {
		int i;

		for (i = 0; i < numPts; i++) {
			if ((pnt.x == sorted[i].x) && (pnt.y == sorted[i].y)) {
				return i;
			}
		}
		return -1;
	}

	static int pComp(Vector2f a, Vector2f b) {
		if (a.x < b.x)
			return -1;
		else if (a.x > b.x)
			return 1;
		else {
			if (a.y < b.y)
				return -1;
			else if (a.y > b.y)
				return 1;
			else
				return 0;
		}
	}

}
