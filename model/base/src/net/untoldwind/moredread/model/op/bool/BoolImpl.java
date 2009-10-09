package net.untoldwind.moredread.model.op.bool;

import java.util.Iterator;
import java.util.List;

import com.jme.math.Vector3f;

public class BoolImpl {
	/**
	 * Computes the intersection boolean operation. Creates a new mesh resulting
	 * from an intersection of two meshes.
	 * 
	 * @param meshC
	 *            Input & Output mesh
	 * @param facesA
	 *            Mesh A faces list
	 * @param facesB
	 *            Mesh B faces list
	 * @param invertMeshA
	 *            determines if object A is inverted
	 * @param invertMeshB
	 *            determines if object B is inverted
	 * @return operation state: BOP_OK, BOP_NO_SOLID, BOP_ERROR
	 */
	void intersectionBoolOp(final BoolMesh meshC, final List<BoolFace> facesA,
			final List<BoolFace> facesB, final boolean invertMeshA,
			final boolean invertMeshB) {
		// Create BSPs trees for mesh A & B
		final BSPTree bspA = new BSPTree();
		bspA.addMesh(facesA);

		final BSPTree bspB = new BSPTree();
		bspB.addMesh(facesB);

		final int numVertices = meshC.getNumVertexs();

		// mesh pre-filter
		simplifiedMeshFilter(meshC, facesA, bspB, invertMeshB);
		if ((0.25 * facesA.size()) > bspB.getDeep()) {
			meshFilter(meshC, facesA, bspB);
		}

		simplifiedMeshFilter(meshC, facesB, bspA, invertMeshA);
		if ((0.25 * facesB.size()) > bspA.getDeep()) {
			meshFilter(meshC, facesB, bspA);
		}

		// Face 2 Face
		BoolFace2Face.Face2Face(meshC, facesA, facesB);

		// BSP classification
		meshClassify(meshC, facesA, bspB);
		meshClassify(meshC, facesB, bspA);

		// Process overlapped faces
		BoolFace2Face.removeOverlappedFaces(meshC, facesA, facesB);

		// Sew two meshes
		BoolFace2Face.sew(meshC, facesA, facesB);

		new BoolMerge2().mergeFaces(meshC, numVertices);
	}

	/**
	 * Preprocess to filter no collisioned faces.
	 * 
	 * @param meshC
	 *            Input & Output mesh data
	 * @param faces
	 *            Faces list to test
	 * @param bsp
	 *            BSP tree used to filter
	 */
	void meshFilter(final BoolMesh meshC, final List<BoolFace> faces,
			final BSPTree bsp) {
		int tag;

		final Iterator<BoolFace> it = faces.iterator();

		while (it.hasNext()) {
			final BoolFace face = it.next();

			final Vector3f p1 = face.getVertex(0).getPoint();
			final Vector3f p2 = face.getVertex(1).getPoint();
			final Vector3f p3 = face.getVertex(2).getPoint();
			if ((tag = bsp.classifyFace(p1, p2, p3, face.getPlane())) == BoolTag.OUT
					|| tag == BoolTag.OUTON) {
				face.setTAG(BoolTag.BROKEN);
				it.remove();
			} else if (tag == BoolTag.IN) {
				it.remove();
			}
		}
	}

	/**
	 * Pre-process to filter no collisioned faces.
	 * 
	 * @param meshC
	 *            Input & Output mesh data
	 * @param faces
	 *            Faces list to test
	 * @param bsp
	 *            BSP tree used to filter
	 * @param inverted
	 *            determines if the object is inverted
	 */
	void simplifiedMeshFilter(final BoolMesh meshC, final List<BoolFace> faces,
			final BSPTree bsp, final boolean inverted) {
		final Iterator<BoolFace> it = faces.iterator();

		while (it.hasNext()) {
			final BoolFace face = it.next();

			final Vector3f p1 = face.getVertex(0).getPoint();
			final Vector3f p2 = face.getVertex(1).getPoint();
			final Vector3f p3 = face.getVertex(2).getPoint();

			if (bsp.filterFace(p1, p2, p3, face) == BoolTag.OUT) {
				if (!inverted) {
					face.setTAG(BoolTag.BROKEN);
				}
				it.remove();
			}
		}
	}

	/**
	 * Process to classify the mesh faces using a bsp tree.
	 * 
	 * @param meshC
	 *            Input & Output mesh data
	 * @param faces
	 *            Faces list to classify
	 * @param bsp
	 *            BSP tree used to face classify
	 */
	void meshClassify(final BoolMesh meshC, final List<BoolFace> faces,
			final BSPTree bsp) {
		for (final BoolFace face : faces) {
			if (face.getTAG() != BoolTag.BROKEN) {
				final Vector3f p1 = face.getVertex(0).getPoint();
				final Vector3f p2 = face.getVertex(1).getPoint();
				final Vector3f p3 = face.getVertex(2).getPoint();
				if (bsp.simplifiedClassifyFace(p1, p2, p3, face.getPlane()) != BoolTag.IN) {
					face.setTAG(BoolTag.BROKEN);
				}
			}
		}
	}
}
