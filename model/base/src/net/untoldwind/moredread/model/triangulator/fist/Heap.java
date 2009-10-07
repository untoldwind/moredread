package net.untoldwind.moredread.model.triangulator.fist;


class Heap {

	static void printHeapData(Triangulator triRef) {
		int i;
		System.out.println("\nHeap Data : numZero " + triRef.numZero
				+ " numHeap " + triRef.numHeap);
		for (i = 0; i < triRef.numHeap; i++)
			System.out.println(i + " ratio " + triRef.heap[i].ratio
					+ ", index " + triRef.heap[i].index + ", prev "
					+ triRef.heap[i].prev + ", next " + triRef.heap[i].next);

		System.out.println(" ");

	}

	static void initHeap(Triangulator triRef) {
		// Calculate the maximum bounds : N + (N -2)* 2.
		// triRef.maxNumHeap = triRef.numPoints * 3;
		triRef.maxNumHeap = triRef.numPoints;
		triRef.heap = new HeapNode[triRef.maxNumHeap];

		triRef.numHeap = 0;
		triRef.numZero = 0;

	}

	static void storeHeapData(Triangulator triRef, int index, double ratio,
			int ind, int prev, int next) {
		triRef.heap[index] = new HeapNode();
		triRef.heap[index].ratio = ratio;
		triRef.heap[index].index = ind;
		triRef.heap[index].prev = prev;
		triRef.heap[index].next = next;
	}

	static void dumpOnHeap(Triangulator triRef, double ratio, int ind,
			int prev, int next) {
		int index;

		if (triRef.numHeap >= triRef.maxNumHeap) {
			// System.out.println("Heap:dumpOnHeap.Expanding heap array ...");
			HeapNode old[] = triRef.heap;
			triRef.maxNumHeap = triRef.maxNumHeap + triRef.numPoints;
			triRef.heap = new HeapNode[triRef.maxNumHeap];
			System.arraycopy(old, 0, triRef.heap, 0, old.length);
		}
		if (ratio == 0.0) {
			if (triRef.numZero < triRef.numHeap)
				if (triRef.heap[triRef.numHeap] == null)
					storeHeapData(triRef, triRef.numHeap,
							triRef.heap[triRef.numZero].ratio,
							triRef.heap[triRef.numZero].index,
							triRef.heap[triRef.numZero].prev,
							triRef.heap[triRef.numZero].next);
				else
					triRef.heap[triRef.numHeap]
							.copy(triRef.heap[triRef.numZero]);
			/*
			 * storeHeapData(triRef, triRef.numHeap,
			 * triRef.heap[triRef.numZero].ratio,
			 * triRef.heap[triRef.numZero].index,
			 * triRef.heap[triRef.numZero].prev,
			 * triRef.heap[triRef.numZero].next);
			 */
			index = triRef.numZero;
			++triRef.numZero;
		} else {
			index = triRef.numHeap;
		}

		storeHeapData(triRef, index, ratio, ind, prev, next);
		++triRef.numHeap;

	}

	static void insertIntoHeap(Triangulator triRef, double ratio, int ind,
			int prev, int next) {
		dumpOnHeap(triRef, ratio, ind, prev, next);
	}

	static boolean deleteFromHeap(Triangulator triRef, int[] ind, int[] prev,
			int[] next) {
		double rnd;
		int rndInd;

		// earSorted is not implemented yet.

		if (triRef.numZero > 0) {
			// assert(num_heap >= num_zero);
			--triRef.numZero;
			--triRef.numHeap;

			ind[0] = triRef.heap[triRef.numZero].index;
			prev[0] = triRef.heap[triRef.numZero].prev;
			next[0] = triRef.heap[triRef.numZero].next;
			if (triRef.numZero < triRef.numHeap)
				triRef.heap[triRef.numZero].copy(triRef.heap[triRef.numHeap]);
			/*
			 * storeHeapData( triRef, triRef.numZero,
			 * triRef.heap[triRef.numHeap].ratio,
			 * triRef.heap[triRef.numHeap].index,
			 * triRef.heap[triRef.numHeap].prev,
			 * triRef.heap[triRef.numHeap].next);
			 */
			return true;
		} else if (triRef.earsRandom) {
			if (triRef.numHeap <= 0) {
				triRef.numHeap = 0;
				return false;
			}
			rnd = triRef.randomGen.nextDouble();
			rndInd = (int) (rnd * triRef.numHeap);
			--triRef.numHeap;
			if (rndInd > triRef.numHeap)
				rndInd = triRef.numHeap;

			ind[0] = triRef.heap[rndInd].index;
			prev[0] = triRef.heap[rndInd].prev;
			next[0] = triRef.heap[rndInd].next;
			if (rndInd < triRef.numHeap)
				triRef.heap[rndInd].copy(triRef.heap[triRef.numHeap]);
			/*
			 * storeHeapData( triRef, rndInd, triRef.heap[triRef.numHeap].ratio,
			 * triRef.heap[triRef.numHeap].index,
			 * triRef.heap[triRef.numHeap].prev,
			 * triRef.heap[triRef.numHeap].next);
			 */
			return true;
		} else {
			if (triRef.numHeap <= 0) {
				triRef.numHeap = 0;
				return false;
			}
			--triRef.numHeap;
			ind[0] = triRef.heap[triRef.numHeap].index;
			prev[0] = triRef.heap[triRef.numHeap].prev;
			next[0] = triRef.heap[triRef.numHeap].next;

			return true;
		}

		// return false;
	}

}
