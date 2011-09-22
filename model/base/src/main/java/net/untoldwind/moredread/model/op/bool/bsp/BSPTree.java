package net.untoldwind.moredread.model.op.bool.bsp;

import net.untoldwind.moredread.model.mesh.TriangleFace;
import net.untoldwind.moredread.model.mesh.TriangleMesh;
import net.untoldwind.moredread.model.mesh.Vertex;

import com.jme.math.Plane;
import com.jme.math.Vector3f;

public class BSPTree {
	BSPNode root;

	public void addMesh(final TriangleMesh mesh, final boolean inverted) {
		for (final TriangleFace face : mesh.getFaces()) {
			addFace(face, inverted);
		}
	}

	public VertexTag testVertex(final Vector3f v) {
		if (root != null) {
			return root.testVertex(v);
		}
		return VertexTag.OUT;
	}

	private void addFace(final TriangleFace face, final boolean inverted) {
		final Vertex[] verticies = face.getVertexArray();

		if (inverted) {
			addTriangle(verticies[2].getPoint(), verticies[1].getPoint(),
					verticies[0].getPoint());
		} else {
			addTriangle(verticies[0].getPoint(), verticies[1].getPoint(),
					verticies[2].getPoint());
		}
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
			root.addTriangle(v1, v2, v3, plane);
		}
	}
}
