package net.untoldwind.moredread.model.op.bool;

import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.mesh.IFace;
import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.scene.BoundingBox;

import com.jme.math.Plane;
import com.jme.math.Vector3f;

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
	void addMesh(final IMesh mesh) {
		for (final IFace face : mesh.getFaces()) {
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

	void addFace(final IFace face) {
		addFace(face.getVertex(0).getPoint(), face.getVertex(1)
				.getPoint(), face.getVertex(2).getPoint(), face
				.getPlane());
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
	void addFace(final Vector3f p1, final Vector3f p2, final Vector3f p3,
			final Plane plane) {
		if (root == null) {
			root = new BSPNode(plane);
		} else {
			final List<Vector3f> points = new ArrayList<Vector3f>();

			points.add(p1);
			points.add(p2);
			points.add(p3);

			root.addFace(points, plane);
		}

		// update bounding box
		boundingBox.add(p1);
		boundingBox.add(p2);
		boundingBox.add(p3);
	}

	public BSPNode getRoot() {
		return root;
	}

	public void setRoot(final BSPNode root) {
		this.root = root;
	}
}
