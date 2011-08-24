package net.untoldwind.moredread.model.op.bool.blebopd;

import java.util.List;

import net.untoldwind.moredread.model.mesh.TriangleFaceId;

public class BoolTriangulator {
	/**
	 * Triangulates the face in two new faces by splitting one edge.
	 * 
	 * <pre>
	 *         *
	 *        /|\
	 *       / | \
	 *      /  |  \
	 *     /   |   \
	 *    /    |    \
	 *   *-----x-----*
	 * </pre>
	 * 
	 * @param mesh
	 *            mesh that contains the faces, edges and vertices
	 * @param faces
	 *            set of faces that contains face and will contains new faces
	 * @param face
	 *            input face to be triangulate
	 * @param v
	 *            vertex index that intersects the edge
	 * @param e
	 *            relative edge index used to triangulate the face
	 */
	static void triangulateA(final BoolMesh mesh, final List<BoolFace> faces,
			final BoolFace face, final BoolVertex v, final int e) {

		BoolFace face1, face2;
		if (e == 1) {
			face1 = new BoolFace(face.getVertex(0), v, face.getVertex(2), face
					.getPlane3d(), face.getOriginalFace());
			face2 = new BoolFace(v, face.getVertex(1), face.getVertex(2), face
					.getPlane3d(), face.getOriginalFace());
		} else if (e == 2) {
			face1 = new BoolFace(face.getVertex(0), face.getVertex(1), v, face
					.getPlane3d(), face.getOriginalFace());
			face2 = new BoolFace(face.getVertex(0), v, face.getVertex(2), face
					.getPlane3d(), face.getOriginalFace());
		} else if (e == 3) {
			face1 = new BoolFace(face.getVertex(0), face.getVertex(1), v, face
					.getPlane3d(), face.getOriginalFace());
			face2 = new BoolFace(face.getVertex(1), face.getVertex(2), v, face
					.getPlane3d(), face.getOriginalFace());
		} else {
			return;
		}

		addFace(mesh, faces, face1, face.getTAG());
		addFace(mesh, faces, face2, face.getTAG());
		face1.setSplit(face.getSplit());
		face2.setSplit(face.getSplit());

		face.setTAG(BoolTag.BROKEN);
	}

	/**
	 * Triangulates the face in three new faces by one inner point.
	 * 
	 * <pre>
	 *         *
	 *        / \
	 *       /   \
	 *      /     \
	 *     /   x   \
	 *    /         \
	 *   *-----------*
	 * </pre>
	 * 
	 * @param mesh
	 *            mesh that contains the faces, edges and vertices
	 * @param faces
	 *            set of faces that contains face and will contains new faces
	 * @param face
	 *            input face to be triangulate
	 * @param v
	 *            vertex index that lays inside face
	 */
	static void triangulateB(final BoolMesh mesh, final List<BoolFace> faces,
			final BoolFace face, final BoolVertex v) {

		final BoolFace face1 = new BoolFace(face.getVertex(0), face
				.getVertex(1), v, face.getPlane3d(), face.getOriginalFace());
		final BoolFace face2 = new BoolFace(face.getVertex(1), face
				.getVertex(2), v, face.getPlane3d(), face.getOriginalFace());
		final BoolFace face3 = new BoolFace(face.getVertex(2), face
				.getVertex(0), v, face.getPlane3d(), face.getOriginalFace());

		addFace(mesh, faces, face1, face.getTAG());
		addFace(mesh, faces, face2, face.getTAG());
		addFace(mesh, faces, face3, face.getTAG());
		face1.setSplit(face.getSplit());
		face2.setSplit(face.getSplit());
		face3.setSplit(face.getSplit());
		face.setTAG(BoolTag.BROKEN);
	}

	/**
	 * Triangulates the face in five new faces by two inner points.
	 * 
	 * <pre>
	 *         *
	 *        / \
	 *       /   \
	 *      /     \
	 *     /  x x  \
	 *    /         \
	 *   *-----------*
	 * </pre>
	 * 
	 * @param mesh
	 *            mesh that contains the faces, edges and vertices
	 * @param faces
	 *            set of faces that contains face and will contains new faces
	 * @param face
	 *            input face to be triangulate
	 * @param v1
	 *            first vertex index that lays inside face
	 * @param v2
	 *            second vertex index that lays inside face
	 */
	static void triangulateC(final BoolMesh mesh, final List<BoolFace> faces,
			final BoolFace face, final BoolVertex v1, final BoolVertex v2) {

		if (!isInsideCircle(face.getVertex(0), v1, v2, face.getVertex(1), face
				.getVertex(2))) {
			triangulateC_split(mesh, faces, face, face.getVertex(0), face
					.getVertex(1), face.getVertex(2), v1, v2);
		} else if (!isInsideCircle(face.getVertex(1), v1, v2,
				face.getVertex(0), face.getVertex(2))) {
			triangulateC_split(mesh, faces, face, face.getVertex(1), face
					.getVertex(2), face.getVertex(0), v1, v2);
		} else if (!isInsideCircle(face.getVertex(2), v1, v2,
				face.getVertex(0), face.getVertex(1))) {
			triangulateC_split(mesh, faces, face, face.getVertex(2), face
					.getVertex(0), face.getVertex(1), v1, v2);
		} else {
			triangulateC_split(mesh, faces, face, face.getVertex(2), face
					.getVertex(0), face.getVertex(1), v1, v2);
		}
	}

	/**
	 * Triangulates the face (v1,v2,v3) in five new faces by two inner points
	 * (v4,v5), where v1 v4 v5 defines the nice triangle and v4 v5 v2 v3 defines
	 * the quad to be tesselated.
	 * 
	 * @param mesh
	 *            mesh that contains the faces, edges and vertices
	 * @param faces
	 *            set of faces that contains face and will contains new faces
	 * @param face
	 *            input face to be triangulate
	 * @param v1
	 *            first vertex index that defines the original triangle
	 * @param v2
	 *            second vertex index that defines the original triangle
	 * @param v3
	 *            third vertex index that defines the original triangle
	 * @param v4
	 *            first vertex index that lays inside face
	 * @param v5
	 *            second vertex index that lays inside face
	 */
	static void triangulateC_split(final BoolMesh mesh,
			final List<BoolFace> faces, final BoolFace face,
			final BoolVertex v1, final BoolVertex v2, final BoolVertex v3,
			final BoolVertex v4, final BoolVertex v5) {

		final BoolVertex v = getTriangleVertex(v1, v2, v4, v5);
		final BoolVertex w = (v == v4 ? v5 : v4);
		final BoolFace face1 = new BoolFace(v1, v, w, face.getPlane3d(), face
				.getOriginalFace());
		final BoolFace face2 = new BoolFace(v1, v2, v, face.getPlane3d(), face
				.getOriginalFace());
		final BoolFace face3 = new BoolFace(v1, w, v3, face.getPlane3d(), face
				.getOriginalFace());

		// v1 v w defines the nice triangle in the correct order
		// v1 v2 v defines one lateral triangle in the correct order
		// v1 w v3 defines the other lateral triangle in the correct order
		// w v v2 v3 defines the quad in the correct order

		addFace(mesh, faces, face1, face.getTAG());
		addFace(mesh, faces, face2, face.getTAG());
		addFace(mesh, faces, face3, face.getTAG());

		face1.setSplit(face.getSplit());
		face2.setSplit(face.getSplit());
		face3.setSplit(face.getSplit());

		final BoolFace faces45[] = new BoolFace[2];

		splitQuad(face.getPlane3d(), v2, v3, w, v, faces45, face
				.getOriginalFace());
		addFace(mesh, faces, faces45[0], face.getTAG());
		addFace(mesh, faces, faces45[1], face.getTAG());
		faces45[0].setSplit(face.getSplit());
		faces45[1].setSplit(face.getSplit());

		face.setTAG(BoolTag.BROKEN);
	}

	/**
	 * Triangulates the face in three new faces by splitting twice an edge.
	 * 
	 * <pre>
	 *         *
	 *        / \
	 *       /   \
	 *      /     \
	 *     /       \
	 *    /         \
	 *   *---x---x---*
	 * </pre>
	 * 
	 * @param mesh
	 *            mesh that contains the faces, edges and vertices
	 * @param faces
	 *            set of faces that contains face and will contains new faces
	 * @param face
	 *            input face to be triangulate
	 * @param v1
	 *            first vertex index that intersects the edge
	 * @param v2
	 *            second vertex index that intersects the edge
	 * @param e
	 *            relative edge index used to triangulate the face
	 */
	static void triangulateD(final BoolMesh mesh, final List<BoolFace> faces,
			final BoolFace face, final BoolVertex v1, final BoolVertex v2,
			final int e) {

		if (e == 1) {
			triangulateD_split(mesh, faces, face, face.getVertex(0), face
					.getVertex(1), face.getVertex(2), v1, v2);
		} else if (e == 2) {
			triangulateD_split(mesh, faces, face, face.getVertex(1), face
					.getVertex(2), face.getVertex(0), v1, v2);
		} else if (e == 3) {
			triangulateD_split(mesh, faces, face, face.getVertex(2), face
					.getVertex(0), face.getVertex(1), v1, v2);
		}
	}

	/**
	 * Triangulates the face (v1,v2,v3) in three new faces by splitting twice an
	 * edge.
	 * 
	 * @param mesh
	 *            mesh that contains the faces, edges and vertices
	 * @param faces
	 *            set of faces that contains face and will contains new faces
	 * @param face
	 *            input face to be triangulate
	 * @param v1
	 *            first vertex index that defines the original triangle
	 * @param v2
	 *            second vertex index that defines the original triangle
	 * @param v3
	 *            third vertex index that defines the original triangle
	 * @param v4
	 *            first vertex index that lays on the edge
	 * @param v5
	 *            second vertex index that lays on the edge
	 */
	static void triangulateD_split(final BoolMesh mesh,
			final List<BoolFace> faces, final BoolFace face,
			final BoolVertex v1, final BoolVertex v2, final BoolVertex v3,
			final BoolVertex v4, final BoolVertex v5) {

		final BoolVertex v = getNearestVertex(v1, v4, v5);
		final BoolVertex w = (v == v4 ? v5 : v4);
		final BoolFace face1 = new BoolFace(v1, v, v3, face.getPlane3d(), face
				.getOriginalFace());
		final BoolFace face2 = new BoolFace(v, w, v3, face.getPlane3d(), face
				.getOriginalFace());
		final BoolFace face3 = new BoolFace(w, v2, v3, face.getPlane3d(), face
				.getOriginalFace());

		addFace(mesh, faces, face1, face.getTAG());
		addFace(mesh, faces, face2, face.getTAG());
		addFace(mesh, faces, face3, face.getTAG());
		face1.setSplit(face.getSplit());
		face2.setSplit(face.getSplit());
		face3.setSplit(face.getSplit());

		face.setTAG(BoolTag.BROKEN);
	}

	/**
	 * Triangulates the face in three new faces by splitting two edges.
	 * 
	 * <pre>
	 *         *
	 *        / \
	 *       /   \
	 *      x     x
	 *     /       \
	 *    /         \
	 *   *-----------*
	 * </pre>
	 * 
	 * @param mesh
	 *            mesh that contains the faces, edges and vertices
	 * @param faces
	 *            set of faces that contains face and will contains new faces
	 * @param face
	 *            input face to be triangulate
	 * @param v1
	 *            vertex index that intersects the first edge
	 * @param v1
	 *            vertex index that intersects the second edge
	 * @param e1
	 *            first relative edge index used to triangulate the face
	 * @param e2
	 *            second relative edge index used to triangulate the face
	 */
	static void triangulateE(final BoolMesh mesh, final List<BoolFace> faces,
			final BoolFace face, BoolVertex v1, BoolVertex v2, int e1, int e2) {

		// Sort the edges to reduce the cases
		if (e1 > e2) {
			final int aux = e1;
			e1 = e2;
			e2 = aux;
			final BoolVertex auxV = v1;
			v1 = v2;
			v2 = auxV;
		}
		// e1 < e2!
		BoolFace face1;
		final BoolFace faces23[] = new BoolFace[2];
		if (e1 == 1 && e2 == 2) {
			// the vertex is 2
			face1 = new BoolFace(face.getVertex(1), v2, v1, face.getPlane3d(),
					face.getOriginalFace());
			splitQuad(face.getPlane3d(), face.getVertex(2), face.getVertex(0),
					v1, v2, faces23, face.getOriginalFace());
		} else if (e1 == 1 && e2 == 3) {
			// the vertex is 1
			face1 = new BoolFace(face.getVertex(0), v1, v2, face.getPlane3d(),
					face.getOriginalFace());
			splitQuad(face.getPlane3d(), face.getVertex(1), face.getVertex(2),
					v2, v1, faces23, face.getOriginalFace());
		} else if (e1 == 2 && e2 == 3) {
			// the vertex is 3
			face1 = new BoolFace(face.getVertex(2), v2, v1, face.getPlane3d(),
					face.getOriginalFace());
			splitQuad(face.getPlane3d(), face.getVertex(0), face.getVertex(1),
					v1, v2, faces23, face.getOriginalFace());
		} else {
			return;
		}

		addFace(mesh, faces, face1, face.getTAG());
		addFace(mesh, faces, faces23[0], face.getTAG());
		addFace(mesh, faces, faces23[1], face.getTAG());
		face1.setSplit(face.getSplit());
		faces23[0].setSplit(face.getSplit());
		faces23[1].setSplit(face.getSplit());
		face.setTAG(BoolTag.BROKEN);
	}

	/**
	 * Triangulates the face in four new faces by one edge and one inner point.
	 * 
	 * <pre>
	 *         *
	 *        / \
	 *       /   \
	 *      x  x  \
	 *     /       \
	 *    /         \
	 *   *-----------*
	 * </pre>
	 * 
	 * @param mesh
	 *            mesh that contains the faces, edges and vertices
	 * @param faces
	 *            set of faces that contains face and will contains new faces
	 * @param face
	 *            input face to be triangulate
	 * @param v1
	 *            vertex index that lays inside face
	 * @param v2
	 *            vertex index that intersects the edge
	 * @param e
	 *            relative edge index used to triangulate the face
	 */
	static void triangulateF(final BoolMesh mesh, final List<BoolFace> faces,
			final BoolFace face, final BoolVertex v1, final BoolVertex v2,
			final int e) {

		final BoolFace faces12[] = new BoolFace[2];
		final BoolFace faces34[] = new BoolFace[2];
		if (e == 1) {
			splitQuad(face.getPlane3d(), face.getVertex(2), face.getVertex(0),
					v2, v1, faces12, face.getOriginalFace());
			splitQuad(face.getPlane3d(), face.getVertex(1), face.getVertex(2),
					v1, v2, faces34, face.getOriginalFace());
		} else if (e == 2) {
			splitQuad(face.getPlane3d(), face.getVertex(0), face.getVertex(1),
					v2, v1, faces12, face.getOriginalFace());
			splitQuad(face.getPlane3d(), face.getVertex(2), face.getVertex(0),
					v1, v2, faces34, face.getOriginalFace());
		} else if (e == 3) {
			splitQuad(face.getPlane3d(), face.getVertex(1), face.getVertex(2),
					v2, v1, faces12, face.getOriginalFace());
			splitQuad(face.getPlane3d(), face.getVertex(0), face.getVertex(1),
					v1, v2, faces34, face.getOriginalFace());
		} else {
			return;
		}

		addFace(mesh, faces, faces12[0], face.getTAG());
		addFace(mesh, faces, faces12[1], face.getTAG());
		addFace(mesh, faces, faces34[0], face.getTAG());
		addFace(mesh, faces, faces34[1], face.getTAG());
		faces12[0].setSplit(face.getSplit());
		faces12[1].setSplit(face.getSplit());
		faces34[0].setSplit(face.getSplit());
		faces34[1].setSplit(face.getSplit());

		face.setTAG(BoolTag.BROKEN);
	}

	/**
	 * Adds the new face into the faces set and the mesh and sets it a new tag.
	 * 
	 * @param mesh
	 *            mesh that contains the faces, edges and vertices
	 * @param faces
	 *            set of faces that contains oldFace
	 * @param face
	 *            input face to be added
	 * @param tag
	 *            tag of the new face
	 */
	static void addFace(final BoolMesh mesh, final List<BoolFace> faces,
			final BoolFace face, final int tag) {

		final BoolVertex av1 = face.getVertex(0);
		final BoolVertex av2 = face.getVertex(1);
		final BoolVertex av3 = face.getVertex(2);

		/*
		 * Before adding a new face to the face list, be sure it's not already
		 * there. Duplicate faces have been found to cause at least two
		 * instances of infinite loops. Also, some faces are created which have
		 * the same vertex twice. Don't add these either.
		 * 
		 * When someone has more time to look into this issue, it's possible
		 * this code may be removed again.
		 */
		if (av1 == av2 || av2 == av3 || av3 == av1) {
			return;
		}

		for (final BoolFace faceA : faces) {
			final BoolVertex bv1 = faceA.getVertex(0);
			final BoolVertex bv2 = faceA.getVertex(1);
			final BoolVertex bv3 = faceA.getVertex(2);

			if ((av1 == bv1 && av2 == bv2 && av3 == bv3)
					|| (av1 == bv1 && av2 == bv3 && av3 == bv2)
					|| (av1 == bv2 && av2 == bv1 && av3 == bv3)
					|| (av1 == bv2 && av2 == bv3 && av3 == bv1)
					|| (av1 == bv3 && av2 == bv2 && av3 == bv1)
					|| (av1 == bv3 && av2 == bv1 && av3 == bv3)) {
				return;
			}
		}

		face.setTAG(tag);
		faces.add(face);
		mesh.addFace(face);
	}

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
	static void splitQuad(final Plane3d plane, final BoolVertex v1,
			final BoolVertex v2, final BoolVertex v3, final BoolVertex v4,
			final BoolFace triangles[], final TriangleFaceId original) {

		final Vector3d p1 = v1.getPoint3d();
		final Vector3d p2 = v2.getPoint3d();
		final Vector3d p3 = v3.getPoint3d();
		final Vector3d p4 = v4.getPoint3d();

		final int res = MathUtils.concave(p1, p2, p3, p4);

		if (res == 0) {
			final Plane3d plane1 = MathUtils.createPlane3d(p1, p2, p3);
			final Plane3d plane2 = MathUtils.createPlane3d(p1, p3, p4);

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
	static BoolVertex getTriangleVertex(final BoolVertex v1,
			final BoolVertex v2, final BoolVertex v3, final BoolVertex v4) {
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
	static BoolVertex getNearestVertex(final BoolVertex u, final BoolVertex v1,
			final BoolVertex v2) {
		final Vector3d q = u.getPoint3d();
		final Vector3d p1 = v1.getPoint3d();
		final Vector3d p2 = v2.getPoint3d();
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
	static boolean isInsideCircle(final BoolVertex v1, final BoolVertex v2,
			final BoolVertex v3, final BoolVertex v4, final BoolVertex v5) {
		return MathUtils.isInsideCircle(v1.getPoint3d(), v2.getPoint3d(), v3
				.getPoint3d(), v4.getPoint3d(), v5.getPoint3d());
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
	static boolean isInsideCircle(final BoolVertex v1, final BoolVertex v2,
			final BoolVertex v3, final BoolVertex w) {
		return MathUtils.isInsideCircle(v1.getPoint3d(), v2.getPoint3d(), v3
				.getPoint3d(), w.getPoint3d());
	}
}
