package net.untoldwind.moredread.model.op.bool;

import java.util.Iterator;
import java.util.List;

import net.untoldwind.moredread.model.scene.BoundingBox;

import com.jme.math.Plane;
import com.jme.math.Vector3f;

public class BoolFace2Face {
	private final static int sA_sB = 12;
	private final static int sB_sA = 21;
	private final static int sX_sA = 31;
	private final static int sA_sX = 13;
	private final static int sX_sB = 32;
	private final static int sB_sX = 23;
	private final static int sX_sX = 33;

	private final static int sA_sA_sB = 112;
	private final static int sB_sB_sA = 221;
	private final static int sB_sA_sA = 211;
	private final static int sA_sB_sB = 122;
	private final static int sA_sB_sA = 121;
	private final static int sB_sA_sB = 212;
	private final static int sA_sX_sB = 132;
	private final static int sB_sX_sA = 231;
	private final static int sX_sA_sB = 312;
	private final static int sX_sB_sA = 321;
	private final static int sA_sB_sX = 123;
	private final static int sB_sA_sX = 213;

	private final static int sA_sA_sB_sB = 1122;
	private final static int sB_sB_sA_sA = 2211;
	private final static int sA_sB_sA_sB = 1212;
	private final static int sB_sA_sB_sA = 2121;
	private final static int sA_sB_sB_sA = 1221;
	private final static int sB_sA_sA_sB = 2112;

	static class Points {
		Vector3f[] points;
		int[] faces;
		int size;
	}

	/**
	 * Computes intersections between faces of both lists.
	 * 
	 * @param mesh
	 *            mesh that contains the faces, edges and vertices
	 * @param facesA
	 *            set of faces from object A
	 * @param facesB
	 *            set of faces from object B
	 * 
	 *            Two optimizations were added here: 1) keep the bounding box
	 *            for a face once it's created; this is especially important for
	 *            B faces, since they were being created and recreated over and
	 *            over 2) associate a "split" index in the faceB vector with
	 *            each A face; when an A face is split, we will not need to
	 *            recheck any B faces have already been checked against that
	 *            original A face
	 */

	void Face2Face(final List<BoolFace> facesA, final List<BoolFace> facesB) {
		for (final BoolFace faceA : facesA) {
			final Plane planeA = faceA.getPlane();
			final Vector3f p1 = faceA.getVertex(0).getPoint();
			final Vector3f p2 = faceA.getVertex(1).getPoint();
			final Vector3f p3 = faceA.getVertex(2).getPoint();

			/* get (or create) bounding box for face A */
			final BoundingBox boxA = new BoundingBox(faceA.getVertices());

			/* start checking B faces with the previously stored split index */

			for (int idxFaceB = faceA.getSplit(); idxFaceB < facesB.size()
					&& (faceA.getTAG() != BoolTag.BROKEN)
					&& (faceA.getTAG() != BoolTag.PHANTOM);) {
				final BoolFace faceB = facesB.get(idxFaceB);

				faceA.setSplit(idxFaceB);
				if ((faceB.getTAG() != BoolTag.BROKEN)
						&& (faceB.getTAG() != BoolTag.PHANTOM)) {

					/* get (or create) bounding box for face B */
					final BoundingBox boxB = new BoundingBox(faceB
							.getVertices());

					if (boxA.intersects(boxB)) {
						final Plane planeB = faceB.getPlane();
						if (MathUtils.containsPoint(planeB, p1)
								&& MathUtils.containsPoint(planeB, p2)
								&& MathUtils.containsPoint(planeB, p3)) {
							if (planeB.getNormal().dot(planeA.getNormal()) > 0) {
								intersectCoplanarFaces(facesB, faceA, faceB,
										false);
							}
						} else {
							intersectNonCoplanarFaces(facesA, facesB, faceA,
									faceB);
						}
					}
				}
				idxFaceB++;
			}
		}

		// Clean broken faces from facesA
		final Iterator<BoolFace> it = facesA.iterator();
		while (it.hasNext()) {
			final BoolFace face = it.next();
			if (face.getTAG() == BoolTag.BROKEN) {
				it.remove();
			}
		}
	}

	/**
	 * Computes intesections of coplanars faces from object A with faces from
	 * object B.
	 * 
	 * @param mesh
	 *            mesh that contains the faces, edges and vertices
	 * @param facesA
	 *            set of faces from object A
	 * @param facesB
	 *            set of faces from object B
	 */
	void sew(final List<BoolFace> facesA, final List<BoolFace> facesB) {
		for (final BoolFace faceB : facesB) {
			final Plane planeB = faceB.getPlane();
			final Vector3f p1 = faceB.getVertex(0).getPoint();
			final Vector3f p2 = faceB.getVertex(1).getPoint();
			final Vector3f p3 = faceB.getVertex(2).getPoint();

			for (int idxFaceA = 0; idxFaceA < facesA.size()
					&& faceB.getTAG() != BoolTag.BROKEN
					&& faceB.getTAG() != BoolTag.PHANTOM; idxFaceA++) {
				final BoolFace faceA = facesA.get(idxFaceA);
				if ((faceA.getTAG() != BoolTag.BROKEN)
						&& (faceA.getTAG() != BoolTag.PHANTOM)) {
					final Plane planeA = faceA.getPlane();
					if (MathUtils.containsPoint(planeA, p1)
							&& MathUtils.containsPoint(planeA, p2)
							&& MathUtils.containsPoint(planeA, p3)) {
						if (planeA.getNormal().dot(planeB.getNormal()) > 0) {
							intersectCoplanarFaces(facesA, faceB, faceA, true);
						}
					}
				}
			}
		}
	}

	void intersectCoplanarFaces(final List<BoolFace> facesB,
			final BoolFace faceA, final BoolFace faceB, final boolean invert)

	{
		final int oldSize = facesB.size();
		final BoolFace originalFaceB = faceB.getOriginalFace();

		final Vector3f p1 = faceA.getVertex(0).getPoint();
		final Vector3f p2 = faceA.getVertex(1).getPoint();
		final Vector3f p3 = faceA.getVertex(2).getPoint();

		final Vector3f normal = new Vector3f(faceA.getPlane().getNormal());
		final Vector3f p1p2 = p2.subtract(p1);
		final Plane plane1 = MathUtils.createPlane((p1p2.cross(normal)
				.normalize()), p1);

		final BoolSegment sA = new BoolSegment();
		sA.cfg1 = BoolSegment.createVertexCfg(1);
		sA.v1 = faceA.getVertex(0);
		sA.cfg2 = BoolSegment.createVertexCfg(2);
		sA.v2 = faceA.getVertex(1);

		intersectCoplanarFaces(facesB, faceB, sA, plane1, invert);

		final Vector3f p2p3 = p3.subtract(p2);
		final Plane plane2 = MathUtils.createPlane((p2p3.cross(normal)
				.normalize()), p2);

		sA.cfg1 = BoolSegment.createVertexCfg(2);
		sA.v1 = faceA.getVertex(1);
		sA.cfg2 = BoolSegment.createVertexCfg(3);
		sA.v2 = faceA.getVertex(2);

		if (faceB.getTAG() == BoolTag.BROKEN) {
			for (int idxFace = oldSize; idxFace < facesB.size(); idxFace++) {
				final BoolFace face = facesB.get(idxFace);
				if (face.getTAG() != BoolTag.BROKEN
						&& originalFaceB == face.getOriginalFace()) {
					intersectCoplanarFaces(facesB, face, sA, plane2, invert);
				}
			}
		} else {
			intersectCoplanarFaces(facesB, faceB, sA, plane2, invert);
		}

		final Vector3f p3p1 = p1.subtract(p3);
		final Plane plane3 = MathUtils.createPlane((p3p1.cross(normal)
				.normalize()), p3);

		sA.cfg1 = BoolSegment.createVertexCfg(3);
		sA.v1 = faceA.getVertex(2);
		sA.cfg2 = BoolSegment.createVertexCfg(1);
		sA.v2 = faceA.getVertex(0);

		if (faceB.getTAG() == BoolTag.BROKEN) {
			for (int idxFace = oldSize; idxFace < facesB.size(); idxFace++) {
				final BoolFace face = facesB.get(idxFace);
				if (face.getTAG() != BoolTag.BROKEN
						&& originalFaceB == face.getOriginalFace()) {
					intersectCoplanarFaces(facesB, face, sA, plane3, invert);
				}
			}
		} else {
			intersectCoplanarFaces(facesB, faceB, sA, plane3, invert);
		}
	}

	/**
	 * Triangulates faceB using segment sA and planeA.
	 * 
	 * @param mesh
	 *            mesh that contains the faces, edges and vertices
	 * @param facesB
	 *            set of faces from object B
	 * @param faceB
	 *            face from object B
	 * @param sA
	 *            segment to intersect with faceB
	 * @param planeA
	 *            plane to intersect with faceB
	 * @param invert
	 *            indicates if sA has priority over faceB
	 */
	void intersectCoplanarFaces(final List<BoolFace> facesB,
			final BoolFace faceB, final BoolSegment sA, final Plane planeA,
			final boolean invert) {
		// TODO
	}

	/**
	 * Triangulates faceB using edges of faceA that both are not complanars.
	 * 
	 * @param mesh
	 *            mesh that contains the faces, edges and vertices
	 * @param facesB
	 *            set of faces from object B
	 * @param faceA
	 *            face from object A
	 * @param faceB
	 *            face from object B
	 */
	void intersectNonCoplanarFaces(final List<BoolFace> facesA,
			final List<BoolFace> facesB, final BoolFace faceA,
			final BoolFace faceB) {

		// TODO
	}

	/**
	 * Triangulates the input face according to the specified segment.
	 * 
	 * @param mesh
	 *            mesh that contains the faces, edges and vertices
	 * @param faces
	 *            set of faces that contains the original face and the new
	 *            triangulated faces
	 * @param face
	 *            face to be triangulated
	 * @param s
	 *            segment used to triangulate face
	 */
	void triangulate(final List<BoolFace> faces, final BoolFace face,
			final BoolSegment s) {
		// TODO
	}

	/**
	 * Returns the first face of faces that shares the input edge of face.
	 * 
	 * @param mesh
	 *            mesh that contains the faces, edges and vertices
	 * @param faces
	 *            set of faces
	 * @param face
	 *            input face
	 * @param edge
	 *            face's edge
	 * @return first face that shares the edge of input face
	 */
	BoolFace BOP_getOppositeFace(final List<BoolFace> faces,
			final BoolFace face, final BoolEdge edge) {
		if (edge == null) {
			return null;
		}

		final List<BoolFace> auxfaces = edge.getFaces();

		for (final BoolFace auxface : auxfaces) {
			if (auxface != face && auxface.getTAG() != BoolTag.BROKEN
					&& faces.contains(auxface)) {
				return auxface;
			}
		}

		return null;
	}
}