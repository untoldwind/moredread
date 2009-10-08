package net.untoldwind.moredread.model.op.triangulator.fist;

import com.jme.math.Matrix4f;
import com.jme.math.Vector3f;

class Project {

	/**
	 * This function projects the vertices of the polygons referenced by
	 * loops[i1,..,i2-1] to an approximating plane.
	 */
	static void projectFace(Triangulator triRef, int loopMin, int loopMax) {
		Vector3f normal, nr;
		int i, j;
		double d;

		normal = new Vector3f();
		nr = new Vector3f();

		// determine the normal of the plane onto which the points get projected
		determineNormal(triRef, triRef.loops[loopMin], normal);
		j = loopMin + 1;
		if (j < loopMax) {
			for (i = j; i < loopMax; ++i) {
				determineNormal(triRef, triRef.loops[i], nr);
				if (Basic.dotProduct(normal, nr) < 0.0) {
					Basic.invertVector(nr);
				}
				Basic.vectorAdd(normal, nr, normal);
			}
			d = Basic.lengthL2(normal);
			if (Numerics.gt(d, Triangulator.ZERO)) {
				Basic.divScalar(d, normal);
			} else {
				// System.out.println("*** ProjectFace: zero-length normal vector!? ***\n");
				normal.x = normal.y = 0.0f;
				normal.z = 1.0f;
			}
		}

		// project the points onto this plane. the projected points are stored
		// in
		// the array `points[0,..,numPoints]'

		// System.out.println("loopMin " + loopMin + " loopMax " + loopMax);
		projectPoints(triRef, loopMin, loopMax, normal);

	}

	/**
	 * This function computes the average of all normals defined by triples of
	 * successive vertices of the polygon. we'll see whether this is a good
	 * heuristic for finding a suitable plane normal...
	 */
	static void determineNormal(Triangulator triRef, int ind, Vector3f normal) {
		Vector3f nr, pq, pr;
		int ind0, ind1, ind2;
		int i0, i1, i2;
		double d;

		ind1 = ind;
		i1 = triRef.fetchData(ind1);
		ind0 = triRef.fetchPrevData(ind1);
		i0 = triRef.fetchData(ind0);
		ind2 = triRef.fetchNextData(ind1);
		i2 = triRef.fetchData(ind2);
		pq = new Vector3f();
		Basic.vectorSub(triRef.vertices[i0], triRef.vertices[i1], pq);
		pr = new Vector3f();
		Basic.vectorSub(triRef.vertices[i2], triRef.vertices[i1], pr);
		nr = new Vector3f();
		Basic.vectorProduct(pq, pr, nr);
		d = Basic.lengthL2(nr);
		if (Numerics.gt(d, Triangulator.ZERO)) {
			Basic.divScalar(d, nr);
			normal.set(nr);
		} else {
			normal.x = normal.y = normal.z = 0.0f;
		}

		pq.set(pr);
		ind1 = ind2;
		ind2 = triRef.fetchNextData(ind1);
		i2 = triRef.fetchData(ind2);
		while (ind1 != ind) {
			Basic.vectorSub(triRef.vertices[i2], triRef.vertices[i1], pr);
			Basic.vectorProduct(pq, pr, nr);
			d = Basic.lengthL2(nr);
			if (Numerics.gt(d, Triangulator.ZERO)) {
				Basic.divScalar(d, nr);
				if (Basic.dotProduct(normal, nr) < 0.0) {
					Basic.invertVector(nr);
				}
				Basic.vectorAdd(normal, nr, normal);
			}
			pq.set(pr);
			ind1 = ind2;
			ind2 = triRef.fetchNextData(ind1);
			i2 = triRef.fetchData(ind2);
		}

		d = Basic.lengthL2(normal);
		if (Numerics.gt(d, Triangulator.ZERO)) {
			Basic.divScalar(d, normal);
		} else {
			// System.out.println("*** DetermineNormal: zero-length normal vector!? ***\n");
			normal.x = normal.y = 0.0f;
			normal.z = 1.0f;

		}
	}

	/**
	 * This function maps the vertices of the polygon referenced by `ind' to the
	 * plane n3.x * x + n3.y * y + n3.z * z = 0. every mapped vertex (x,y,z) is
	 * then expressed in terms of (x',y',z'), where z'=0. this is achieved by
	 * transforming the original vertices into a coordinate system whose z-axis
	 * coincides with n3, and whose two other coordinate axes n1 and n2 are
	 * orthonormal on n3. note that n3 is supposed to be of unit length!
	 */
	static void projectPoints(Triangulator triRef, int i1, int i2, Vector3f n3) {
		Matrix4f matrix = new Matrix4f();
		Vector3f vtx = new Vector3f();
		Vector3f n1, n2;
		double d;
		int ind, ind1;
		int i, j1;

		n1 = new Vector3f();
		n2 = new Vector3f();

		// choose n1 and n2 appropriately
		if ((Math.abs(n3.x) > 0.1) || (Math.abs(n3.y) > 0.1)) {
			n1.x = -n3.y;
			n1.y = n3.x;
			n1.z = 0.0f;
		} else {
			n1.x = n3.z;
			n1.z = -n3.x;
			n1.y = 0.0f;
		}
		d = Basic.lengthL2(n1);
		Basic.divScalar(d, n1);
		Basic.vectorProduct(n1, n3, n2);
		d = Basic.lengthL2(n2);
		Basic.divScalar(d, n2);

		// initialize the transformation matrix
		matrix.m00 = n1.x;
		matrix.m01 = n1.y;
		matrix.m02 = n1.z;
		matrix.m03 = 0.0f; // translation of the coordinate system
		matrix.m10 = n2.x;
		matrix.m11 = n2.y;
		matrix.m12 = n2.z;
		matrix.m13 = 0.0f; // translation of the coordinate system
		matrix.m20 = n3.x;
		matrix.m21 = n3.y;
		matrix.m22 = n3.z;
		matrix.m23 = 0.0f; // translation of the coordinate system
		matrix.m30 = 0.0f;
		matrix.m31 = 0.0f;
		matrix.m32 = 0.0f;
		matrix.m33 = 1.0f;

		// transform the vertices and store the transformed vertices in the
		// array
		// `points'
		triRef.initPnts(20);
		for (i = i1; i < i2; ++i) {
			ind = triRef.loops[i];
			ind1 = ind;
			j1 = triRef.fetchData(ind1);
			matrix.mult(triRef.vertices[j1], vtx);
			j1 = triRef.storePoint(vtx.x, vtx.y);
			triRef.updateIndex(ind1, j1);
			ind1 = triRef.fetchNextData(ind1);
			j1 = triRef.fetchData(ind1);
			while (ind1 != ind) {
				matrix.mult(triRef.vertices[j1], vtx);
				j1 = triRef.storePoint(vtx.x, vtx.y);
				triRef.updateIndex(ind1, j1);
				ind1 = triRef.fetchNextData(ind1);
				j1 = triRef.fetchData(ind1);
			}
		}
	}

}
