package net.untoldwind.moredread.model.op.triangulator.fist;

import com.jme.math.Vector3f;

class Simple {

	/**
	 * Handle a triangle or a quadrangle in a simple and efficient way. if the
	 * face is more complex than false is returned.
	 * 
	 * warning: the correctness of this function depends upon the fact that
	 * `CleanPolyhedralFace' has not yet been executed; i.e., the vertex indices
	 * have not been changed since the execution of `CleanPolyhedron'!
	 * (otherwise, we would have to get the original indices via calls to
	 * `GetOriginal'...)
	 */
	static boolean simpleFace(final Triangulator triRef, final int ind1) {
		int ind0, ind2, ind3, ind4;
		int i1, i2, i3, i4;

		Vector3f pq, pr, nr;

		double x, y, z;
		int ori2, ori4;

		ind0 = triRef.fetchPrevData(ind1);

		if (ind0 == ind1) {
			// this polygon has only one vertex! nothing to triangulate...
			System.out.println("***** polygon with only one vertex?! *****\n");
			return true;
		}

		ind2 = triRef.fetchNextData(ind1);
		i2 = triRef.fetchData(ind2);
		if (ind0 == ind2) {
			// this polygon has only two vertices! nothing to triangulate...
			System.out
					.println("***** polygon with only two vertices?! *****\n");
			return true;
		}

		ind3 = triRef.fetchNextData(ind2);
		i3 = triRef.fetchData(ind3);
		if (ind0 == ind3) {
			// this polygon is a triangle! let's triangulate it!
			i1 = triRef.fetchData(ind1);
			// triRef.storeTriangle(i1, i2, i3);
			triRef.storeTriangle(ind1, ind2, ind3);
			return true;
		}

		ind4 = triRef.fetchNextData(ind3);
		i4 = triRef.fetchData(ind4);
		if (ind0 == ind4) {
			// this polygon is a quadrangle! not too hard to triangulate it...
			// we project the corners of the quadrangle onto one of the
			// coordinate
			// planes
			triRef.initPnts(5);
			i1 = triRef.fetchData(ind1);

			pq = new Vector3f();
			pr = new Vector3f();
			nr = new Vector3f();
			/*
			 * System.out.println("ind0 " + ind0 + ", ind1 " + ind1 + ", ind2 "
			 * + ind2 + ", ind3 " + ind3 + ", ind4 " + ind4);
			 * System.out.println("i0 " + i0 +", i1 " + i1 + ", i2 " + i2 +
			 * ", i3 " + i3 + ", i4 " + i4);
			 * 
			 * System.out.println("vert[i1] " + triRef.vertices[i1] +
			 * "vert[i2] " + triRef.vertices[i2] + "vert[i3] " +
			 * triRef.vertices[i3]);
			 */

			Basic.vectorSub(triRef.vertices[i1], triRef.vertices[i2], pq);
			Basic.vectorSub(triRef.vertices[i3], triRef.vertices[i2], pr);
			Basic.vectorProduct(pq, pr, nr);

			// System.out.println("pq " + pq + " pr " + pr + " nr " + nr);
			x = Math.abs(nr.x);
			y = Math.abs(nr.y);
			z = Math.abs(nr.z);
			if ((z >= x) && (z >= y)) {
				// System.out.println("((z >= x)  &&  (z >= y))");
				triRef.points[1].x = triRef.vertices[i1].x;
				triRef.points[1].y = triRef.vertices[i1].y;
				triRef.points[2].x = triRef.vertices[i2].x;
				triRef.points[2].y = triRef.vertices[i2].y;
				triRef.points[3].x = triRef.vertices[i3].x;
				triRef.points[3].y = triRef.vertices[i3].y;
				triRef.points[4].x = triRef.vertices[i4].x;
				triRef.points[4].y = triRef.vertices[i4].y;
			} else if ((x >= y) && (x >= z)) {
				// System.out.println("((x >= y)  &&  (x >= z))");
				triRef.points[1].x = triRef.vertices[i1].z;
				triRef.points[1].y = triRef.vertices[i1].y;
				triRef.points[2].x = triRef.vertices[i2].z;
				triRef.points[2].y = triRef.vertices[i2].y;
				triRef.points[3].x = triRef.vertices[i3].z;
				triRef.points[3].y = triRef.vertices[i3].y;
				triRef.points[4].x = triRef.vertices[i4].z;
				triRef.points[4].y = triRef.vertices[i4].y;
			} else {
				triRef.points[1].x = triRef.vertices[i1].x;
				triRef.points[1].y = triRef.vertices[i1].z;
				triRef.points[2].x = triRef.vertices[i2].x;
				triRef.points[2].y = triRef.vertices[i2].z;
				triRef.points[3].x = triRef.vertices[i3].x;
				triRef.points[3].y = triRef.vertices[i3].z;
				triRef.points[4].x = triRef.vertices[i4].x;
				triRef.points[4].y = triRef.vertices[i4].z;
			}
			triRef.numPoints = 5;

			// find a valid diagonal
			ori2 = Numerics.orientation(triRef, 1, 2, 3);
			ori4 = Numerics.orientation(triRef, 1, 3, 4);

			/*
			 * for(int i=0; i<5; i++) System.out.println("point " + i + ", " +
			 * triRef.points[i]); System.out.println("ori2 : " + ori2 +
			 * " ori4 : " + ori4);
			 */

			if (((ori2 > 0) && (ori4 > 0)) || ((ori2 < 0) && (ori4 < 0))) {

				// i1, i3 is a valid diagonal;
				//
				// encode as a 2-triangle strip: the triangles are (2, 3, 1)
				// and (1, 3, 4).

				// triRef.storeTriangle(i1, i2, i3);
				// triRef.storeTriangle(i1, i3, i4);
				triRef.storeTriangle(ind1, ind2, ind3);
				triRef.storeTriangle(ind1, ind3, ind4);
			} else {
				// i2, i4 has to be a valid diagonal. (if this is no valid
				// diagonal then the corners of the quad form a figure of eight;
				// shall we apply any heuristics in order to guess which
				// diagonal
				// is more likely to be the better choice? alternatively, we
				// could
				// return false and subject it to the standard triangulation
				// algorithm. well, let's see how this brute-force solution
				// works.)

				// encode as a 2-triangle strip: the triangles are (1, 2, 4)
				// and (4, 2, 3).

				// triRef.storeTriangle(i2, i3, i4);
				// triRef.storeTriangle(i2, i4, i1);
				triRef.storeTriangle(ind2, ind3, ind4);
				triRef.storeTriangle(ind2, ind4, ind1);
			}
			return true;
		}

		return false;
	}

}
