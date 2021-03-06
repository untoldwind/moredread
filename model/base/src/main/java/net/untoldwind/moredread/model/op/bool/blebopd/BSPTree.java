package net.untoldwind.moredread.model.op.bool.blebopd;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.scene.BoundingBox;

public class BSPTree {
	BSPNode root;
	BSPNode bspBB;
	BoundingBox boundingBox;

	BSPTree() {
		boundingBox = new BoundingBox();
	}

	/**
	 * Adds all mesh faces to BSP tree.
	 * 
	 * @param mesh
	 *            mesh to add.
	 * @param facesList
	 *            face list to add.
	 */
	void addMesh(final BoolMesh mesh) {
		for (final BoolFace face : mesh.getFaces()) {
			addFace(face);
		}
	}

	void addMesh(final List<BoolFace> faces) {
		for (final BoolFace face : faces) {
			addFace(face);
		}
	}

	/**
	 * Adds a new face into bsp tree.
	 * 
	 * @param mesh
	 *            Input data for BSP tree.
	 * @param face
	 *            index to mesh face.
	 */

	void addFace(final BoolFace face) {
		addFace(face.getVertex(0).getPoint3d(), face.getVertex(1).getPoint3d(),
				face.getVertex(2).getPoint3d(), face.getPlane3d());
	}

	/**
	 * Adds new facee to the bsp-tree.
	 * 
	 * @param p1
	 *            first face point.
	 * @param p2
	 *            second face point.
	 * @param p3
	 *            third face point.
	 * @param plane
	 *            face plane.
	 */
	void addFace(final Vector3d p1, final Vector3d p2, final Vector3d p3,
			final Plane3d plane) {
		if (root == null) {
			root = new BSPNode(plane);
		} else {
			final List<Vector3d> points = new ArrayList<Vector3d>();

			points.add(p1);
			points.add(p2);
			points.add(p3);

			root.addFace(points, plane);
		}

		// update bounding box
		boundingBox.add(p1.toVector3());
		boundingBox.add(p2.toVector3());
		boundingBox.add(p3.toVector3());
	}

	public BSPNode getRoot() {
		return root;
	}

	public void setRoot(final BSPNode root) {
		this.root = root;
	}

	/**
	 * Tests face vs bsp-tree (returns where is the face respect bsp planes).
	 * 
	 * @param p1
	 *            first face triangle point.
	 * @param p2
	 *            secons face triangle point.
	 * @param p3
	 *            third face triangle point.
	 * @param plane
	 *            face plane.
	 * @return BSP_IN, BSP_OUT or BSP_IN_OUT
	 */
	int classifyFace(final Vector3d p1, final Vector3d p2, final Vector3d p3,
			final Plane3d plane) {
		if (root != null) {
			return root.classifyFace(p1, p2, p3, plane);
		} else {
			return BoolTag.OUT;
		}
	}

	/**
	 * Filters a face using the BSP bounding infomation.
	 * 
	 * @param p1
	 *            first face triangle point.
	 * @param p2
	 *            secons face triangle point.
	 * @param p3
	 *            third face triangle point.
	 * @param face
	 *            face to test.
	 * @return UNCLASSIFIED, BSP_IN, BSP_OUT or BSP_IN_OUT
	 */
	int filterFace(final Vector3d p1, final Vector3d p2, final Vector3d p3,
			final BoolFace face) {
		if (bspBB != null) {
			return bspBB.classifyFace(p1, p2, p3, face.getPlane3d());
		} else {
			return BoolTag.UNCLASSIFIED;
		}
	}

	/**
	 * Tests face vs bsp-tree (returns where is the face respect bsp planes).
	 * 
	 * @param p1
	 *            first face triangle point.
	 * @param p2
	 *            secons face triangle point.
	 * @param p3
	 *            third face triangle point.
	 * @param plane
	 *            face plane.
	 * @return BSP_IN, BSP_OUT or BSP_IN_OUT
	 */
	int simplifiedClassifyFace(final Vector3d p1, final Vector3d p2,
			final Vector3d p3, final Plane3d plane) {
		if (root != null) {
			return root.simplifiedClassifyFace(p1, p2, p3, plane);
		} else {
			return BoolTag.OUT;
		}
	}

	/**
	 * Returns the deep of this BSP tree.
	 * 
	 * @return tree deep
	 */
	int getDeep() {
		if (root != null) {
			return root.getDeep();
		} else {
			return 0;
		}
	}

	public void dumpTree(final PrintStream out) {
		out.println("BSPTree");
		if (root != null) {
			root.dumpNode("", out);
		}
	}
}
