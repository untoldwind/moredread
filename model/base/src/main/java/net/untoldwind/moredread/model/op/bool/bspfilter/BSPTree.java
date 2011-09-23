package net.untoldwind.moredread.model.op.bool.bspfilter;

import net.untoldwind.moredread.model.mesh.TriangleFace;
import net.untoldwind.moredread.model.mesh.TriangleMesh;
import net.untoldwind.moredread.model.mesh.Vertex;

import com.jme.math.Plane;
import com.jme.math.Vector3f;

public class BSPTree {
	BSPNode root;

	public void addMesh(final TriangleMesh mesh) {
		for (final TriangleFace face : mesh.getFaces()) {
			addFace(face);
		}
	}

	public VertexTag testVertex(final Vector3f v) {
		if (root != null) {
			return root.testVertex(v);
		}
		return VertexTag.OUT;
	}

	public BooleanTag testTriangle(final Vector3f v1, final Vector3f v2,
			final Vector3f v3) {
		if (root != null) {
			final Plane plane = MathUtils.planeForTriangle(v1, v2, v3);

			return root.testFace(v1, v2, v3, plane);
		}
		return BooleanTag.OUT;
	}

	private void addFace(final TriangleFace face) {
		final Vertex[] verticies = face.getVertexArray();

		addTriangle(verticies[0].getPoint(), verticies[1].getPoint(),
				verticies[2].getPoint());
	}

	private void addTriangle(final Vector3f v1, final Vector3f v2,
			final Vector3f v3) {
		final Plane plane = MathUtils.planeForTriangle(v1, v2, v3);

		if (plane == null) {
			// Just ignore degenerated triangles
			return;
		}

		if (root == null) {
			root = new BSPNode(plane);
		} else {
			root.addFace(new Vector3f[] { v1, v2, v3 }, plane);
		}
	}

	public void dump() {
		System.out.println(">>> BSP");
		if (root != null) {
			root.dump(" ");
		}
	}
}
