package net.untoldwind.moredread.model.op.bool;

import com.jme.math.Plane;
import com.jme.math.Vector3f;

public class BoolTriangulator {
	/**
	 * Computes the best quad triangulation.
	 * 
	 * @param mesh
	 *            mesh that contains the faces, edges and vertices
	 * @param plane
	 *            plane used to create the news faces
	 * @param v1
	 *            first vertex index
	 * @param v2
	 *            second vertex index
	 * @param v3
	 *            third vertex index
	 * @param v4
	 *            fourth vertex index
	 * @param triangles
	 *            array of faces where the new two faces will be saved
	 * @param original
	 *            face index to the new faces
	 */
	void splitQuad(final Plane plane, final BoolVertex v1, final BoolVertex v2,
			final BoolVertex v3, final BoolVertex v4,
			final BoolFace triangles[], final BoolFace original) {
		final Vector3f p1 = v1.getPoint();
		final Vector3f p2 = v2.getPoint();
		final Vector3f p3 = v3.getPoint();
		final Vector3f p4 = v4.getPoint();

		final int res = MathUtils.concave(p1, p2, p3, p4);

		if (res == 0) {
			final Plane plane1 = MathUtils.createPlane(p1, p2, p3);
			final Plane plane2 = MathUtils.createPlane(p1, p3, p4);

			if (isInsideCircle(v1, v2, v4, v3)
					&& plane1.getNormal().dot(plane.getNormal()) != 0
					&& plane2.getNormal().dot(plane.getNormal()) != 0) {
				triangles[0] = new BoolFace(v1, v2, v3, plane, original);
				triangles[1] = new BoolFace(v1, v3, v4, plane, original);
			} else {
				triangles[0] = new BoolFace(v1, v2, v4, plane, original);
				triangles[1] = new BoolFace(v2, v3, v4, plane, original);
			}
		} else if (res == -1) {
			triangles[0] = new BoolFace(v1, v2, v4, plane, original);
			triangles[1] = new BoolFace(v2, v3, v4, plane, original);
		} else {
			triangles[0] = new BoolFace(v1, v2, v3, plane, original);
			triangles[1] = new BoolFace(v1, v3, v4, plane, original);
		}
	}

	/**
	 * Returns the vertex (v3 or v4) that splits the quad (v1,v2,v3,v4) in the
	 * best pair of triangles.
	 * 
	 * @param mesh
	 *            mesh that contains the faces, edges and vertices
	 * @param v1
	 *            first vertex index
	 * @param v2
	 *            second vertex index
	 * @param v3
	 *            third vertex index
	 * @param v4
	 *            fourth vertex index
	 * @return v3 if the best split triangles are (v1,v2,v3) and (v1,v3,v4), v4
	 *         otherwise
	 */
	BoolVertex BOP_getTriangleVertex(final BoolVertex v1, final BoolVertex v2,
			final BoolVertex v3, final BoolVertex v4) {
		if (isInsideCircle(v1, v2, v4, v3)) {
			return v3;
		}
		return v4;
	}

	/**
	 * Returns which of vertex v1 or v2 is nearest to u.
	 * 
	 * @param mesh
	 *            mesh that contains the faces, edges and vertices
	 * @param u
	 *            reference vertex index
	 * @param v1
	 *            first vertex index
	 * @param v2
	 *            second vertex index
	 * @return the nearest vertex index
	 */
	BoolVertex getNearestVertex(final BoolVertex u, final BoolVertex v1,
			final BoolVertex v2) {
		final Vector3f q = u.getPoint();
		final Vector3f p1 = v1.getPoint();
		final Vector3f p2 = v2.getPoint();
		if (MathUtils.comp(q.distance(p1), q.distance(p2)) > 0) {
			return v2;
		} else {
			return v1;
		}
	}

	/**
	 * Computes if vertexs v4 and v5 are not inside the circle defined by
	 * v1,v2,v3 (seems to be a nice triangle)
	 * 
	 * @param mesh
	 *            mesh that contains the faces, edges and vertices
	 * @param v1
	 *            first vertex index
	 * @param v2
	 *            second vertex index
	 * @param v3
	 *            third vertex index
	 * @param v4
	 *            fourth vertex index
	 * @param v5
	 *            five vertex index
	 * @return if v1,v2,v3 defines a nice triangle against v4,v5
	 */
	boolean isInsideCircle(final BoolVertex v1, final BoolVertex v2,
			final BoolVertex v3, final BoolVertex v4, final BoolVertex v5) {
		return MathUtils.isInsideCircle(v1.getPoint(), v2.getPoint(), v3
				.getPoint(), v4.getPoint(), v5.getPoint());
	}

	/**
	 * Computes if vertex w is not inside the circle defined by v1,v2,v3 (seems
	 * to be a nice triangle)
	 * 
	 * @param mesh
	 *            mesh that contains the faces, edges and vertices
	 * @param v1
	 *            first vertex index
	 * @param v2
	 *            second vertex index
	 * @param v3
	 *            third vertex index
	 * @param w
	 *            fourth vertex index
	 * @return if v1,v2,v3 defines a nice triangle against w
	 */
	boolean isInsideCircle(final BoolVertex v1, final BoolVertex v2,
			final BoolVertex v3, final BoolVertex w) {
		return MathUtils.isInsideCircle(v1.getPoint(), v2.getPoint(), v3
				.getPoint(), w.getPoint());
	}
}
