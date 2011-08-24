package net.untoldwind.moredread.model.op.bool.blebopd;

import java.util.Iterator;
import java.util.List;

import net.untoldwind.moredread.model.mesh.TriangleFaceId;

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
		Vector3d[] points;
		int[] face;
		int size;
		boolean invertA;
		boolean invertB;
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
	static void Face2Face(final BoolMesh mesh, final List<BoolFace> facesA,
			final List<BoolFace> facesB) {
		for (int idxFaceA = 0; idxFaceA < facesA.size(); idxFaceA++) {
			final BoolFace faceA = facesA.get(idxFaceA);

			final Plane3d planeA = faceA.getPlane3d();
			final Vector3d p1 = faceA.getVertex(0).getPoint3d();
			final Vector3d p2 = faceA.getVertex(1).getPoint3d();
			final Vector3d p3 = faceA.getVertex(2).getPoint3d();

			/* get (or create) bounding box for face A */
			final BoolBoundingBox boxA = faceA.getBoundingBox();

			/* start checking B faces with the previously stored split index */

			for (int idxFaceB = faceA.getSplit(); idxFaceB < facesB.size()
					&& (faceA.getTAG() != BoolTag.BROKEN)
					&& (faceA.getTAG() != BoolTag.PHANTOM);) {
				final BoolFace faceB = facesB.get(idxFaceB);

				faceA.setSplit(idxFaceB);
				if ((faceB.getTAG() != BoolTag.BROKEN)
						&& (faceB.getTAG() != BoolTag.PHANTOM)) {

					/* get (or create) bounding box for face B */
					final BoolBoundingBox boxB = faceB.getBoundingBox();

					if (boxA.intersect(boxB)) {

						final Plane3d planeB = faceB.getPlane3d();
						if (MathUtils.containsPoint(planeB, p1)
								&& MathUtils.containsPoint(planeB, p2)
								&& MathUtils.containsPoint(planeB, p3)) {
							if (planeB.getNormal().dot(planeA.getNormal()) > 0) {
								intersectCoplanarFaces(mesh, facesB, faceA,
										faceB, false);
							}
						} else {
							intersectNonCoplanarFaces(mesh, facesA, facesB,
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
	static void sew(final BoolMesh mesh, final List<BoolFace> facesA,
			final List<BoolFace> facesB) {
		for (final BoolFace faceB : facesB) {
			final Plane3d planeB = faceB.getPlane3d();
			final Vector3d p1 = faceB.getVertex(0).getPoint3d();
			final Vector3d p2 = faceB.getVertex(1).getPoint3d();
			final Vector3d p3 = faceB.getVertex(2).getPoint3d();

			for (int idxFaceA = 0; idxFaceA < facesA.size()
					&& faceB.getTAG() != BoolTag.BROKEN
					&& faceB.getTAG() != BoolTag.PHANTOM; idxFaceA++) {
				final BoolFace faceA = facesA.get(idxFaceA);
				if ((faceA.getTAG() != BoolTag.BROKEN)
						&& (faceA.getTAG() != BoolTag.PHANTOM)) {
					final Plane3d planeA = faceA.getPlane3d();
					if (MathUtils.containsPoint(planeA, p1)
							&& MathUtils.containsPoint(planeA, p2)
							&& MathUtils.containsPoint(planeA, p3)) {
						if (planeA.getNormal().dot(planeB.getNormal()) > 0) {
							intersectCoplanarFaces(mesh, facesA, faceB, faceA,
									true);
						}
					}
				}
			}
		}
	}

	static void intersectCoplanarFaces(final BoolMesh mesh,
			final List<BoolFace> facesB, final BoolFace faceA,
			final BoolFace faceB, final boolean invert)

	{
		final int oldSize = facesB.size();
		final TriangleFaceId originalFaceBIndex = faceB.getOriginalFace();

		final Vector3d p1 = faceA.getVertex(0).getPoint3d();
		final Vector3d p2 = faceA.getVertex(1).getPoint3d();
		final Vector3d p3 = faceA.getVertex(2).getPoint3d();

		final Vector3d normal = new Vector3d(faceA.getPlane3d().getNormal());
		final Vector3d p1p2 = p2.subtract(p1);
		final Plane3d plane1 = MathUtils.createPlane3d(
				(p1p2.cross(normal).normalize()), p1);

		final BoolSegment sA = new BoolSegment();
		sA.cfg1 = BoolSegment.createVertexCfg(1);
		sA.v1 = faceA.getVertex(0);
		sA.cfg2 = BoolSegment.createVertexCfg(2);
		sA.v2 = faceA.getVertex(1);

		intersectCoplanarFaces(mesh, facesB, faceB, sA, plane1, invert);

		final Vector3d p2p3 = p3.subtract(p2);
		final Plane3d plane2 = MathUtils.createPlane3d(
				(p2p3.cross(normal).normalize()), p2);

		sA.cfg1 = BoolSegment.createVertexCfg(2);
		sA.v1 = faceA.getVertex(1);
		sA.cfg2 = BoolSegment.createVertexCfg(3);
		sA.v2 = faceA.getVertex(2);

		if (faceB.getTAG() == BoolTag.BROKEN) {
			for (int idxFace = oldSize; idxFace < facesB.size(); idxFace++) {
				final BoolFace face = facesB.get(idxFace);
				if (face.getTAG() != BoolTag.BROKEN
						&& originalFaceBIndex.equals(face.getOriginalFace())) {
					intersectCoplanarFaces(mesh, facesB, face, sA, plane2,
							invert);
				}
			}
		} else {
			intersectCoplanarFaces(mesh, facesB, faceB, sA, plane2, invert);
		}

		final Vector3d p3p1 = p1.subtract(p3);
		final Plane3d plane3 = MathUtils.createPlane3d(
				(p3p1.cross(normal).normalize()), p3);

		sA.cfg1 = BoolSegment.createVertexCfg(3);
		sA.v1 = faceA.getVertex(2);
		sA.cfg2 = BoolSegment.createVertexCfg(1);
		sA.v2 = faceA.getVertex(0);

		if (faceB.getTAG() == BoolTag.BROKEN) {
			for (int idxFace = oldSize; idxFace < facesB.size(); idxFace++) {
				final BoolFace face = facesB.get(idxFace);
				if (face.getTAG() != BoolTag.BROKEN
						&& originalFaceBIndex == face.getOriginalFace()) {
					intersectCoplanarFaces(mesh, facesB, face, sA, plane3,
							invert);
				}
			}
		} else {
			intersectCoplanarFaces(mesh, facesB, faceB, sA, plane3, invert);
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
	static void intersectCoplanarFaces(final BoolMesh mesh,
			final List<BoolFace> facesB, final BoolFace faceB,
			final BoolSegment sA, final Plane3d planeA, final boolean invert) {
		final BoolSegment sB = BoolSplitter.splitFace(planeA, faceB);

		if (BoolSegment.isDefined(sB.cfg1)) {
			final BoolSegment xSegment[] = new BoolSegment[2];
			createXS(mesh, null, faceB, planeA, new Plane3d(), sA, sB, invert,
					xSegment);
			if (BoolSegment.isDefined(xSegment[1].cfg1)) {
				final int sizefaces = mesh.getNumFaces();
				triangulate(mesh, facesB, faceB, xSegment[1]);
				mergeVertexs(mesh, sizefaces);
			}
		}
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
	static void intersectNonCoplanarFaces(final BoolMesh mesh,
			final List<BoolFace> facesA, final List<BoolFace> facesB,
			final BoolFace faceA, final BoolFace faceB) {

		// Obtain segments of faces A and B from the intersection with their
		// planes
		final BoolSegment sA = BoolSplitter
				.splitFace(faceB.getPlane3d(), faceA);
		final BoolSegment sB = BoolSplitter
				.splitFace(faceA.getPlane3d(), faceB);

		if (BoolSegment.isDefined(sA.cfg1) && BoolSegment.isDefined(sB.cfg1)) {
			// There is an intesection, build the X-segment
			final BoolSegment xSegment[] = new BoolSegment[2];
			xSegment[0] = new BoolSegment();
			xSegment[1] = new BoolSegment();

			createXS(mesh, faceA, faceB, sA, sB, false, xSegment);

			int sizefaces = mesh.getNumFaces();
			triangulate(mesh, facesA, faceA, xSegment[0]);
			mergeVertexs(mesh, sizefaces);

			sizefaces = mesh.getNumFaces();
			triangulate(mesh, facesB, faceB, xSegment[1]);
			mergeVertexs(mesh, sizefaces);
		}
	}

	/**
	 * Tests if faces since firstFace have all vertexs non-coincident of
	 * colinear, otherwise repairs the mesh.
	 * 
	 * @param mesh
	 *            mesh that contains the faces, edges and vertices
	 * @param firstFace
	 *            first face index to be tested
	 */
	static void mergeVertexs(final BoolMesh mesh, final int firstFace) {
		final int numFaces = mesh.getNumFaces();
		for (int idxFace = firstFace; idxFace < numFaces; idxFace++) {
			final BoolFace face = mesh.getFace(idxFace);

			if ((face.getTAG() != BoolTag.BROKEN)
					&& (face.getTAG() != BoolTag.PHANTOM)) {
				final Vector3d vertex1 = face.getVertex(0).getPoint3d();
				final Vector3d vertex2 = face.getVertex(1).getPoint3d();
				final Vector3d vertex3 = face.getVertex(2).getPoint3d();
				if (MathUtils.collinear(vertex1, vertex2, vertex3)) {
					face.setTAG(BoolTag.PHANTOM);
				}
			}
		}
	}

	/**
	 * Obtains the points of the segment created from the intersection between
	 * faceA and planeB.
	 * 
	 * @param mesh
	 *            mesh that contains the faces, edges and vertices
	 * @param faceA
	 *            intersected face
	 * @param sA
	 *            segment of the intersection between faceA and planeB
	 * @param planeB
	 *            intersected plane
	 * @param points
	 *            array of points where the new points are saved
	 * @param faces
	 *            array of relative face index to the points
	 * @param size
	 *            size of arrays points and faces
	 * @param faceValue
	 *            relative face index of new points
	 */
	static void getPoints(final Points points, final BoolMesh mesh,
			final BoolFace faceA, final BoolSegment sA, final Plane3d planeB,
			final int faceValue) {
		Vector3d p1 = new Vector3d(), p2 = new Vector3d();

		if (BoolSegment.isDefined(sA.cfg1)) {
			if (BoolSegment.isEdge(sA.cfg1)) {
				// the new point becomes of split faceA edge
				p1 = BoolSplitter.splitEdge(planeB, faceA,
						BoolSegment.getEdge(sA.cfg1));
			} else if (BoolSegment.isVertex(sA.cfg1)) {
				// the new point becomes of vertex faceA
				p1 = sA.v1.getPoint3d();
			}

			if (BoolSegment.isDefined(sA.cfg2)) {
				if (BoolSegment.isEdge(sA.cfg2)) {
					p2 = BoolSplitter.splitEdge(planeB, faceA,
							BoolSegment.getEdge(sA.cfg2));
				} else if (BoolSegment.isVertex(sA.cfg2)) {
					p2 = sA.v2.getPoint3d();
				}
				points.points[points.size] = p1;
				points.points[points.size + 1] = p2;
				points.face[points.size] = faceValue;
				points.face[points.size + 1] = faceValue;
				points.size += 2;
			}

			else {
				points.points[points.size] = p1;
				points.face[points.size] = faceValue;
				points.size++;
			}
		}
	}

	/**
	 * Sorts the colinear points and relative face indices.
	 * 
	 * @param points
	 *            array of points where the new points are saved
	 * @param faces
	 *            array of relative face index to the points
	 * @param size
	 *            size of arrays points and faces
	 * @param invertA
	 *            indicates if points of same relative face had been exchanged
	 */
	static void mergeSort(final Points points) {
		final Vector3d sortedPoints[] = new Vector3d[4];
		final int sortedFaces[] = new int[4], position[] = new int[4];
		int i;
		if (points.size == 2) {

			// Trivial case, only test the merge ...
			if (MathUtils
					.fuzzyZero(points.points[0].distance(points.points[1]))) {
				points.face[0] = 3;
				points.size--;
			}
		} else {
			// size is 3 or 4
			// Get segment extreme points
			double maxDistance = -1;
			for (i = 0; i < points.size - 1; i++) {
				for (int j = i + 1; j < points.size; j++) {
					final double distance = points.points[i]
							.distance(points.points[j]);
					if (distance > maxDistance) {
						maxDistance = distance;
						position[0] = i;
						position[points.size - 1] = j;
					}
				}
			}

			// Get segment inner points
			position[1] = position[2] = points.size;
			for (i = 0; i < points.size; i++) {
				if ((i != position[0]) && (i != position[points.size - 1])) {
					if (position[1] == points.size) {
						position[1] = i;
					} else {
						position[2] = i;
					}
				}
			}

			// Get inner points
			if (position[2] < points.size) {
				final double d1 = points.points[position[1]]
						.distance(points.points[position[0]]);
				final double d2 = points.points[position[2]]
						.distance(points.points[position[0]]);
				if (d1 > d2) {
					final int aux = position[1];
					position[1] = position[2];
					position[2] = aux;
				}
			}

			// Sort data
			for (i = 0; i < points.size; i++) {
				sortedPoints[i] = points.points[position[i]];
				sortedFaces[i] = points.face[position[i]];
			}

			points.invertA = false;
			points.invertB = false;
			if (points.face[1] == 1) {

				// invert
				for (i = 0; i < points.size; i++) {
					if (position[i] == 1) {
						points.invertA = true;
						break;
					} else if (position[i] == 0) {
						break;
					}
				}

				// invert
				if (points.size == 4) {
					for (i = 0; i < points.size; i++) {
						if (position[i] == 3) {
							points.invertB = true;
							break;
						} else if (position[i] == 2) {
							break;
						}
					}
				}
			} else if (points.face[1] == 2) {
				// invert
				for (i = 0; i < points.size; i++) {
					if (position[i] == 2) {
						points.invertB = true;
						break;
					} else if (position[i] == 1) {
						break;
					}
				}
			}

			// Merge data
			double d1 = sortedPoints[1].distance(sortedPoints[0]);
			final double d2 = sortedPoints[1].distance(sortedPoints[2]);

			if (MathUtils.fuzzyZero(d1) && sortedFaces[1] != sortedFaces[0]) {
				if (MathUtils.fuzzyZero(d2) && sortedFaces[1] != sortedFaces[2]) {
					if (d1 < d2) {
						// merge 0 and 1
						sortedFaces[0] = 3;
						for (i = 1; i < points.size - 1; i++) {
							sortedPoints[i] = sortedPoints[i + 1];
							sortedFaces[i] = sortedFaces[i + 1];
						}
						points.size--;
						if (points.size == 3) {
							// merge 1 and 2 ???
							d1 = sortedPoints[1].distance(sortedPoints[2]);
							if (MathUtils.fuzzyZero(d1)
									&& sortedFaces[1] != sortedFaces[2]) {
								// merge!
								sortedFaces[1] = 3;
								points.size--;
							}
						}
					} else {
						// merge 1 and 2
						sortedFaces[1] = 3;
						for (i = 2; i < points.size - 1; i++) {
							sortedPoints[i] = sortedPoints[i + 1];
							sortedFaces[i] = sortedFaces[i + 1];
						}
						points.size--;
					}
				} else {
					// merge 0 and 1
					sortedFaces[0] = 3;
					for (i = 1; i < points.size - 1; i++) {
						sortedPoints[i] = sortedPoints[i + 1];
						sortedFaces[i] = sortedFaces[i + 1];
					}
					points.size--;
					if (points.size == 3) {
						// merge 1 i 2 ???
						d1 = sortedPoints[1].distance(sortedPoints[2]);
						if (MathUtils.fuzzyZero(d1)
								&& sortedFaces[1] != sortedFaces[2]) {
							// merge!
							sortedFaces[1] = 3;
							points.size--;
						}
					}
				}
			} else {
				if (MathUtils.fuzzyZero(d2) && sortedFaces[1] != sortedFaces[2]) {
					// merge 1 and 2
					sortedFaces[1] = 3;
					for (i = 2; i < points.size - 1; i++) {
						sortedPoints[i] = sortedPoints[i + 1];
						sortedFaces[i] = sortedFaces[i + 1];
					}
					points.size--;
				} else if (points.size == 4) {
					d1 = sortedPoints[2].distance(sortedPoints[3]);
					if (MathUtils.fuzzyZero(d1)
							&& sortedFaces[2] != sortedFaces[3]) {
						// merge 2 and 3
						sortedFaces[2] = 3;
						points.size--;
					}
				}
			}

			// Merge initial points ...
			for (i = 0; i < points.size; i++) {
				points.points[i] = sortedPoints[i];
				points.face[i] = sortedFaces[i];
			}

		}
	}

	/**
	 * Computes the x-segment of two segments (the shared interval). The
	 * segments needs to have sA.m_cfg1 > 0 && sB.m_cfg1 > 0 .
	 * 
	 * @param mesh
	 *            mesh that contains the faces, edges and vertices
	 * @param faceA
	 *            face of object A
	 * @param faceB
	 *            face of object B
	 * @param sA
	 *            segment of intersection between faceA and planeB
	 * @param sB
	 *            segment of intersection between faceB and planeA
	 * @param invert
	 *            indicates if faceA has priority over faceB
	 * @param segmemts
	 *            array of the output x-segments
	 */
	static void createXS(final BoolMesh mesh, final BoolFace faceA,
			final BoolFace faceB, final BoolSegment sA, final BoolSegment sB,
			final boolean invert, final BoolSegment[] segments) {
		createXS(mesh, faceA, faceB, faceA.getPlane3d(), faceB.getPlane3d(),
				sA, sB, invert, segments);
	}

	/**
	 * Computes the x-segment of two segments (the shared interval). The
	 * segments needs to have sA.m_cfg1 > 0 && sB.m_cfg1 > 0 .
	 * 
	 * @param mesh
	 *            mesh that contains the faces, edges and vertices
	 * @param faceA
	 *            face of object A
	 * @param faceB
	 *            face of object B
	 * @param planeA
	 *            plane of faceA
	 * @param planeB
	 *            plane of faceB
	 * @param sA
	 *            segment of intersection between faceA and planeB
	 * @param sB
	 *            segment of intersection between faceB and planeA
	 * @param invert
	 *            indicates if faceA has priority over faceB
	 * @param segmemts
	 *            array of the output x-segments
	 */
	static void createXS(final BoolMesh mesh, final BoolFace faceA,
			final BoolFace faceB, final Plane3d planeA, final Plane3d planeB,
			final BoolSegment sA, final BoolSegment sB, final boolean invert,
			final BoolSegment[] segments) {
		final Points points = new Points();

		points.points = new Vector3d[4]; // points of the segments
		points.face = new int[4]; // relative face indexs (1 => faceA, 2 =>
		// faceB)
		points.size = 0; // size of points and relative face indexs

		getPoints(points, mesh, faceA, sA, planeB, 1);
		getPoints(points, mesh, faceB, sB, planeA, 2);

		points.invertA = false;
		points.invertB = false;

		mergeSort(points);

		if (points.invertA) {
			sA.invert();
		}
		if (points.invertB) {
			sB.invert();
		}

		// Compute the configuration label
		int label = 0;
		for (int i = 0; i < points.size; i++) {
			label = points.face[i] + label * 10;
		}

		if (points.size == 1) {
			// Two coincident points
			segments[0].cfg1 = sA.cfg1;
			segments[1].cfg1 = sB.cfg1;

			segments[0].v1 = getVertexIndex(mesh, points.points[0], sA.cfg1,
					sB.cfg1, sA.v1, sB.v1, invert);
			segments[1].v1 = segments[0].v1;
			segments[0].cfg2 = segments[1].cfg2 = BoolSegment
					.createUndefinedCfg();
		} else if (points.size == 2) {
			switch (label) {
			// Two non-coincident points
			case sA_sB:
			case sB_sA:
				segments[0].cfg1 = segments[1].cfg1 = segments[0].cfg2 = segments[1].cfg2 = BoolSegment
						.createUndefinedCfg();
				break;

			// Two coincident points and one non-coincident of sA
			case sA_sX:
				segments[0].cfg1 = sA.cfg2;
				segments[1].cfg1 = sB.cfg1;
				segments[0].v1 = getVertexIndex(mesh, points.points[1],
						sA.cfg2, sB.cfg1, sA.v2, sB.v1, invert);
				segments[1].v1 = segments[0].v1;

				segments[0].cfg2 = BoolSegment.createUndefinedCfg();
				segments[1].cfg2 = BoolSegment.createUndefinedCfg();
				break;
			case sX_sA:
				segments[0].cfg1 = sA.cfg1;
				segments[1].cfg1 = sB.cfg1;
				segments[0].v1 = getVertexIndex(mesh, points.points[0],
						sA.cfg1, sB.cfg1, sA.v1, sB.v1, invert);
				segments[1].v1 = segments[0].v1;

				segments[0].cfg2 = BoolSegment.createUndefinedCfg();
				segments[1].cfg2 = BoolSegment.createUndefinedCfg();
				break;

			// Two coincident points and one non-coincident of sB
			case sB_sX:
				segments[0].cfg1 = sA.cfg1;
				segments[1].cfg1 = sB.cfg2;
				segments[0].v1 = getVertexIndex(mesh, points.points[1],
						sA.cfg1, sB.cfg2, sA.v1, sB.v2, invert);
				segments[1].v1 = segments[0].v1;

				segments[0].cfg2 = BoolSegment.createUndefinedCfg();
				segments[1].cfg2 = BoolSegment.createUndefinedCfg();
				break;
			case sX_sB:
				segments[0].cfg1 = sA.cfg1;
				segments[1].cfg1 = sB.cfg1;
				segments[0].v1 = getVertexIndex(mesh, points.points[0],
						sA.cfg1, sB.cfg1, sA.v1, sB.v1, invert);
				segments[1].v1 = segments[0].v1;

				segments[0].cfg2 = BoolSegment.createUndefinedCfg();
				segments[1].cfg2 = BoolSegment.createUndefinedCfg();
				break;

			// coincident points 2-2
			case sX_sX:
				segments[0].cfg1 = sA.cfg1;
				segments[1].cfg1 = sB.cfg1;
				segments[0].v1 = getVertexIndex(mesh, points.points[0],
						sA.cfg1, sB.cfg1, sA.v1, sB.v1, invert);
				segments[1].v1 = segments[0].v1;

				segments[0].cfg2 = sA.cfg2;
				segments[1].cfg2 = sB.cfg2;
				segments[0].v2 = getVertexIndex(mesh, points.points[1],
						sA.cfg2, sB.cfg2, sA.v2, sB.v2, invert);
				segments[1].v2 = segments[0].v2;
				break;

			default:
				break;
			}
		} else if (points.size == 3) {
			switch (label) {
			case sA_sA_sB:
			case sB_sA_sA:
			case sA_sB_sB:
			case sB_sB_sA:
				segments[0].cfg1 = segments[1].cfg1 = segments[0].cfg2 = segments[1].cfg2 = BoolSegment
						.createUndefinedCfg();
				break;

			case sA_sB_sA:
				segments[1].v1 = getVertexIndex(mesh, points.points[1],
						sB.cfg1, sB.v1);
				segments[1].cfg1 = sB.cfg1;
				segments[1].cfg2 = BoolSegment.createUndefinedCfg();
				segments[0].cfg1 = sA.getConfig();
				segments[0].cfg2 = BoolSegment.createUndefinedCfg();
				segments[0].v1 = segments[1].v1;
				break;

			case sB_sA_sB:
				segments[0].v1 = getVertexIndex(mesh, points.points[1],
						sA.cfg1, sA.v1);
				segments[0].cfg1 = sA.cfg1;
				segments[0].cfg2 = BoolSegment.createUndefinedCfg();
				segments[1].cfg1 = sB.getConfig();
				segments[1].cfg2 = BoolSegment.createUndefinedCfg();
				segments[1].v1 = segments[0].v1;
				break;

			case sA_sX_sB:
				segments[0].cfg1 = sA.cfg2;
				segments[1].cfg1 = sB.cfg1;
				segments[0].v1 = getVertexIndex(mesh, points.points[1],
						sA.cfg2, sB.cfg1, sA.v2, sB.v1, invert);
				segments[1].v1 = segments[0].v1;
				segments[0].cfg2 = BoolSegment.createUndefinedCfg();
				segments[1].cfg2 = BoolSegment.createUndefinedCfg();
				break;

			case sB_sX_sA:
				segments[0].cfg1 = sA.cfg1;
				segments[1].cfg1 = sB.cfg2;
				segments[0].v1 = getVertexIndex(mesh, points.points[1],
						sA.cfg1, sB.cfg2, sA.v1, sB.v2, invert);
				segments[1].v1 = segments[0].v1;
				segments[0].cfg2 = BoolSegment.createUndefinedCfg();
				segments[1].cfg2 = BoolSegment.createUndefinedCfg();
				break;

			case sX_sA_sB:
				segments[0].cfg1 = sA.cfg1;
				segments[1].cfg1 = sB.cfg1;
				segments[0].v1 = getVertexIndex(mesh, points.points[0],
						sA.cfg1, sB.cfg1, sA.v1, sB.v1, invert);
				segments[1].v1 = segments[0].v1;

				segments[0].cfg2 = sA.cfg2;
				segments[1].cfg2 = sB.getConfig();
				segments[0].v2 = getVertexIndex(mesh, points.points[1],
						sA.cfg2, sA.v2);
				segments[1].v2 = segments[0].v2;
				break;

			case sX_sB_sA:
				segments[0].cfg1 = sA.cfg1;
				segments[1].cfg1 = sB.cfg1;
				segments[0].v1 = getVertexIndex(mesh, points.points[0],
						sA.cfg1, sB.cfg1, sA.v1, sB.v1, invert);
				segments[1].v1 = segments[0].v1;

				segments[0].cfg2 = sA.getConfig();
				segments[1].cfg2 = sB.cfg2;
				segments[0].v2 = getVertexIndex(mesh, points.points[1],
						sB.cfg2, sB.v2);
				segments[1].v2 = segments[0].v2;
				break;

			case sA_sB_sX:
				segments[0].cfg1 = sA.getConfig();
				segments[1].cfg1 = sB.cfg1;
				segments[0].v1 = getVertexIndex(mesh, points.points[1],
						sB.cfg1, sB.v1);
				segments[1].v1 = segments[0].v1;

				segments[0].cfg2 = sA.cfg2;
				segments[1].cfg2 = sB.cfg2;
				segments[0].v2 = getVertexIndex(mesh, points.points[2],
						sA.cfg2, sB.cfg2, sA.v2, sB.v2, invert);
				segments[1].v2 = segments[0].v2;
				break;

			case sB_sA_sX:
				segments[0].cfg1 = sA.cfg1;
				segments[1].cfg1 = sB.getConfig();
				segments[0].v1 = getVertexIndex(mesh, points.points[1],
						sA.cfg1, sA.v1);
				segments[1].v1 = segments[0].v1;

				segments[0].cfg2 = sA.cfg2;
				segments[1].cfg2 = sB.cfg2;
				segments[0].v2 = getVertexIndex(mesh, points.points[2],
						sA.cfg2, sB.cfg2, sA.v2, sB.v2, invert);
				segments[1].v2 = segments[0].v2;
				break;

			default:
				break;
			}
		} else {
			// 4!
			switch (label) {
			case sA_sA_sB_sB:
			case sB_sB_sA_sA:
				segments[0].cfg1 = segments[1].cfg1 = segments[0].cfg2 = segments[1].cfg2 = BoolSegment
						.createUndefinedCfg();
				break;

			case sA_sB_sA_sB:
				segments[0].cfg1 = sA.getConfig();
				segments[1].cfg1 = sB.cfg1;
				segments[0].v1 = getVertexIndex(mesh, points.points[1],
						sB.cfg1, sB.v1);
				segments[1].v1 = segments[0].v1;

				segments[0].cfg2 = sA.cfg2;
				segments[1].cfg2 = sB.getConfig();
				segments[0].v2 = getVertexIndex(mesh, points.points[2],
						sA.cfg2, sA.v2);
				segments[1].v2 = segments[0].v2;
				break;

			case sB_sA_sB_sA:
				segments[0].cfg1 = sA.cfg1;
				segments[1].cfg1 = sB.getConfig();
				segments[0].v1 = getVertexIndex(mesh, points.points[1],
						sA.cfg1, sA.v1);
				segments[1].v1 = segments[0].v1;

				segments[0].cfg2 = sA.getConfig();
				segments[1].cfg2 = sB.cfg2;
				segments[0].v2 = getVertexIndex(mesh, points.points[2],
						sB.cfg2, sB.v2);
				segments[1].v2 = segments[0].v2;
				break;

			case sA_sB_sB_sA:
				segments[0].cfg1 = sA.getConfig();
				segments[1].cfg1 = sB.cfg1;
				segments[0].v1 = getVertexIndex(mesh, points.points[1],
						sB.cfg1, sB.v1);
				segments[1].v1 = segments[0].v1;

				segments[0].cfg2 = segments[0].cfg1;
				segments[1].cfg2 = sB.cfg2;
				segments[0].v2 = getVertexIndex(mesh, points.points[2],
						sB.cfg2, sB.v2);
				segments[1].v2 = segments[0].v2;
				break;

			case sB_sA_sA_sB:
				segments[0].cfg1 = sA.cfg1;
				segments[1].cfg1 = sB.getConfig();
				segments[0].v1 = getVertexIndex(mesh, points.points[1],
						sA.cfg1, sA.v1);
				segments[1].v1 = segments[0].v1;

				segments[0].cfg2 = sA.cfg2;
				segments[1].cfg2 = segments[1].cfg1;
				segments[0].v2 = getVertexIndex(mesh, points.points[2],
						sA.cfg2, sA.v2);
				segments[1].v2 = segments[0].v2;
				break;

			default:
				break;
			}
		}

		segments[0].sort();
		segments[1].sort();
	}

	/**
	 * Computes the vertex index of a point.
	 * 
	 * @param mesh
	 *            mesh that contains the faces, edges and vertices
	 * @param point
	 *            input point
	 * @param cfgA
	 *            configuration of point on faceA
	 * @param cfgB
	 *            configuration of point on faceB
	 * @param vA
	 *            vertex index of point on faceA
	 * @param vB
	 *            vertex index of point on faceB
	 * @param invert
	 *            indicates if vA has priority over vB
	 * @return final vertex index in the mesh
	 */
	static BoolVertex getVertexIndex(final BoolMesh mesh, final Vector3d point,
			final int cfgA, final int cfgB, final BoolVertex vA,
			final BoolVertex vB, final boolean invert) {
		if (BoolSegment.isVertex(cfgA)) { // exists vertex index on A
			if (BoolSegment.isVertex(cfgB)) { // exists vertex index on B
				// unify vertex indexs
				if (invert) {
					return mesh.replaceVertexIndex(vA, vB);
				} else {
					return mesh.replaceVertexIndex(vB, vA);
				}
			} else {
				return vA;
			}
		} else {// does not exist vertex index on A
			if (BoolSegment.isVertex(cfgB)) {
				return vB;
			} else {// does not exist vertex index on B
				return mesh.addVertex(point);
			}
		}
	}

	/**
	 * Computes the vertex index of a point.
	 * 
	 * @param mesh
	 *            mesh that contains the faces, edges and vertices
	 * @param cfg
	 *            configuration of point
	 * @param v
	 *            vertex index of point
	 * @return final vertex index in the mesh
	 */
	static BoolVertex getVertexIndex(final BoolMesh mesh, final Vector3d point,
			final int cfg, final BoolVertex v) {
		if (BoolSegment.isVertex(cfg)) {
			return v;
		} else {
			return mesh.addVertex(point);
		}
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
	static void triangulate(final BoolMesh mesh, final List<BoolFace> faces,
			final BoolFace face, final BoolSegment s) {

		if (BoolSegment.isUndefined(s.cfg1)) {
			// Nothing to do
		} else if (BoolSegment.isVertex(s.cfg1)) {
			// VERTEX(v1) + VERTEX(v2) => nothing to do
		} else if (BoolSegment.isEdge(s.cfg1)) {
			if (BoolSegment.isVertex(s.cfg2) || BoolSegment.isUndefined(s.cfg2)) {
				// EDGE(v1) + VERTEX(v2)
				final BoolEdge edge = mesh.getEdge(face,
						BoolSegment.getEdge(s.cfg1));
				BoolTriangulator.triangulateA(mesh, faces, face, s.v1,
						BoolSegment.getEdge(s.cfg1));
				final BoolFace opposite = getOppositeFace(faces, face, edge);
				if (opposite != null) {
					final int e = opposite.getEdgeIndex(edge.getVertex1(),
							edge.getVertex2());
					BoolTriangulator.triangulateA(mesh, faces, opposite, s.v1,
							e);
				}
			} else {
				// EDGE(v1) + EDGE(v2)
				if (BoolSegment.getEdge(s.cfg1) == BoolSegment.getEdge(s.cfg2)) {
					// EDGE(v1) == EDGE(v2)
					final BoolEdge edge = mesh.getEdge(face,
							BoolSegment.getEdge(s.cfg1));
					BoolTriangulator.triangulateD(mesh, faces, face, s.v1,
							s.v2, BoolSegment.getEdge(s.cfg1));
					final BoolFace opposite = getOppositeFace(faces, face, edge);
					if (opposite != null) {
						final int e = opposite.getEdgeIndex(edge.getVertex1(),
								edge.getVertex2());
						BoolTriangulator.triangulateD(mesh, faces, opposite,
								s.v1, s.v2, e);
					}
				} else { // EDGE(v1) != EDGE(v2)
					final BoolEdge edge1 = mesh.getEdge(face,
							BoolSegment.getEdge(s.cfg1));
					final BoolEdge edge2 = mesh.getEdge(face,
							BoolSegment.getEdge(s.cfg2));
					BoolTriangulator.triangulateE(mesh, faces, face, s.v1,
							s.v2, BoolSegment.getEdge(s.cfg1),
							BoolSegment.getEdge(s.cfg2));
					BoolFace opposite = getOppositeFace(faces, face, edge1);
					if (opposite != null) {
						final int e = opposite.getEdgeIndex(edge1.getVertex1(),
								edge1.getVertex2());
						BoolTriangulator.triangulateA(mesh, faces, opposite,
								s.v1, e);
					}
					opposite = getOppositeFace(faces, face, edge2);
					if (opposite != null) {
						final int e = opposite.getEdgeIndex(edge2.getVertex1(),
								edge2.getVertex2());
						BoolTriangulator.triangulateA(mesh, faces, opposite,
								s.v2, e);
					}
				}
			}
		} else if (BoolSegment.isIn(s.cfg1)) {
			if (BoolSegment.isVertex(s.cfg2) || BoolSegment.isUndefined(s.cfg2)) {
				// IN(v1) + VERTEX(v2)
				BoolTriangulator.triangulateB(mesh, faces, face, s.v1);
			} else if (BoolSegment.isEdge(s.cfg2)) {
				// IN(v1) + EDGE(v2)
				final BoolEdge edge = mesh.getEdge(face,
						BoolSegment.getEdge(s.cfg2));
				BoolTriangulator.triangulateF(mesh, faces, face, s.v1, s.v2,
						BoolSegment.getEdge(s.cfg2));
				final BoolFace opposite = getOppositeFace(faces, face, edge);
				if (opposite != null) {
					final int e = opposite.getEdgeIndex(edge.getVertex1(),
							edge.getVertex2());
					BoolTriangulator.triangulateA(mesh, faces, opposite, s.v2,
							e);
				}
			} else {
				BoolTriangulator.triangulateC(mesh, faces, face, s.v1, s.v2);
			}
		}

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
	static BoolFace getOppositeFace(final List<BoolFace> faces,
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

	/**
	 * Removes faces from facesB that are overlapped with anyone from facesA.
	 * 
	 * @param mesh
	 *            mesh that contains the faces, edges and vertices
	 * @param facesA
	 *            set of faces from object A
	 * @param facesB
	 *            set of faces from object B
	 */
	static void removeOverlappedFaces(final BoolMesh mesh,
			final List<BoolFace> facesA, final List<BoolFace> facesB) {
		for (final BoolFace faceI : facesA) {
			if (faceI.getTAG() == BoolTag.BROKEN) {
				continue;
			}
			boolean overlapped = false;
			final Vector3d p1 = faceI.getVertex(0).getPoint3d();
			final Vector3d p2 = faceI.getVertex(1).getPoint3d();
			final Vector3d p3 = faceI.getVertex(2).getPoint3d();
			for (int j = 0; j < facesB.size();) {
				final BoolFace faceJ = facesB.get(j);

				if (faceJ.getTAG() != BoolTag.BROKEN) {
					final Plane3d planeJ = faceJ.getPlane3d();
					if (MathUtils.containsPoint(planeJ, p1)
							&& MathUtils.containsPoint(planeJ, p2)
							&& MathUtils.containsPoint(planeJ, p3)) {
						final Vector3d q1 = faceJ.getVertex(0).getPoint3d();
						final Vector3d q2 = faceJ.getVertex(1).getPoint3d();
						final Vector3d q3 = faceJ.getVertex(2).getPoint3d();

						if (overlap(planeJ.getNormal(), p1, p2, p3, q1, q2, q3)) {
							facesB.remove(j);
							faceJ.setTAG(BoolTag.BROKEN);
							overlapped = true;
						} else {
							j++;
						}
					} else {
						j++;
					}
				} else {
					j++;
				}
			}
			if (overlapped) {
				faceI.setTAG(BoolTag.OVERLAPPED);
			}
		}
	}

	/**
	 * Computes if triangle p1,p2,p3 is overlapped with triangle q1,q2,q3.
	 * 
	 * @param normal
	 *            normal of the triangle p1,p2,p3
	 * @param p1
	 *            point of first triangle
	 * @param p2
	 *            point of first triangle
	 * @param p3
	 *            point of first triangle
	 * @param q1
	 *            point of second triangle
	 * @param q2
	 *            point of second triangle
	 * @param q3
	 *            point of second triangle
	 * @return if there is overlapping between both triangles
	 */
	static boolean overlap(final Vector3d normal, final Vector3d p1,
			final Vector3d p2, final Vector3d p3, final Vector3d q1,
			final Vector3d q2, final Vector3d q3) {
		final Vector3d p1p2 = p2.subtract(p1);
		final Plane3d plane1 = MathUtils.createPlane3d(p1p2.cross(normal), p1);

		final Vector3d p2p3 = p3.subtract(p2);
		final Plane3d plane2 = MathUtils.createPlane3d(p2p3.cross(normal), p2);

		final Vector3d p3p1 = p1.subtract(p3);
		final Plane3d plane3 = MathUtils.createPlane3d(p3p1.cross(normal), p3);

		int tag1 = BoolTag.createTAG(MathUtils.classify(q1, plane1));
		int tag2 = BoolTag.createTAG(MathUtils.classify(q1, plane2));
		int tag3 = BoolTag.createTAG(MathUtils.classify(q1, plane3));
		final int tagQ1 = BoolTag.createTAG(tag1, tag2, tag3);
		if (tagQ1 == BoolTag.IN_IN_IN) {
			return true;
		}

		tag1 = BoolTag.createTAG(MathUtils.classify(q2, plane1));
		tag2 = BoolTag.createTAG(MathUtils.classify(q2, plane2));
		tag3 = BoolTag.createTAG(MathUtils.classify(q2, plane3));
		final int tagQ2 = BoolTag.createTAG(tag1, tag2, tag3);
		if (tagQ2 == BoolTag.IN_IN_IN) {
			return true;
		}

		tag1 = BoolTag.createTAG(MathUtils.classify(q3, plane1));
		tag2 = BoolTag.createTAG(MathUtils.classify(q3, plane2));
		tag3 = BoolTag.createTAG(MathUtils.classify(q3, plane3));
		final int tagQ3 = BoolTag.createTAG(tag1, tag2, tag3);
		if (tagQ3 == BoolTag.IN_IN_IN) {
			return true;
		}

		if ((tagQ1 & BoolTag.OUT_OUT_OUT) == 0
				&& (tagQ2 & BoolTag.OUT_OUT_OUT) == 0
				&& (tagQ3 & BoolTag.OUT_OUT_OUT) == 0) {
			return true;
		} else {
			return false;
		}
	}
}