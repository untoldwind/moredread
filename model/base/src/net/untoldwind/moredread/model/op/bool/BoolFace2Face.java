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
			final Vector3f p1 = faceA.getVertices().get(0).getPoint();
			final Vector3f p2 = faceA.getVertices().get(1).getPoint();
			final Vector3f p3 = faceA.getVertices().get(2).getPoint();

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
							BOP_intersectNonCoplanarFaces(facesA, facesB,
									faceA, faceB);
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

	void intersectCoplanarFaces(final List<BoolFace> facesB,
			final BoolFace faceA, final BoolFace faceB, final boolean invert)

	{
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
	void BOP_intersectNonCoplanarFaces(final List<BoolFace> facesA,
			final List<BoolFace> facesB, final BoolFace faceA,
			final BoolFace faceB) {
	}
}