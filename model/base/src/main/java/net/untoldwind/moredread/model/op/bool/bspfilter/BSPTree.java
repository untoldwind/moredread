package net.untoldwind.moredread.model.op.bool.bspfilter;

import net.untoldwind.moredread.model.math.Plane;
import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.mesh.IVertex;
import net.untoldwind.moredread.model.mesh.TriangleFace;
import net.untoldwind.moredread.model.mesh.TriangleMesh;
import net.untoldwind.moredread.model.op.utils.PlaneMap;

public class BSPTree {
	BSPNode root;

	public BSPTree(final TriangleMesh mesh) {
		addMesh(mesh);
	}

	public void addMesh(final TriangleMesh mesh) {
		for (final TriangleFace face : mesh.getFaces()) {
			addFace(face);
		}
	}

	public VertexTag testVertex(final int offset, final IVertex v) {
		if (root != null) {
			return root.testVertex(new BoolVertex(offset, v));
		}
		return VertexTag.OUT;
	}

	public VertexTag testPoint(final Vector3 v) {
		if (root != null) {
			return root.testVertex(new BoolVertex(v));
		}
		return VertexTag.OUT;
	}

	public void testTriangle(final int offset, final IVertex v1,
			final IVertex v2, final IVertex v3, final PlaneMap<BoolFace> result) {
		if (root != null) {
			final Plane plane = MathUtils.planeForTriangle(v1.getPoint(),
					v2.getPoint(), v3.getPoint());

			root.testFace(new BoolVertex[] { new BoolVertex(offset, v1),
					new BoolVertex(offset, v2), new BoolVertex(offset, v3) },
					plane, result);
		}
	}

	private void addFace(final TriangleFace face) {
		final IVertex[] verticies = face.getVertexArray();

		addTriangle(verticies[0].getPoint(), verticies[1].getPoint(),
				verticies[2].getPoint());
	}

	private void addTriangle(final Vector3 v1, final Vector3 v2,
			final Vector3 v3) {
		final Plane plane = MathUtils.planeForTriangle(v1, v2, v3);

		if (plane == null) {
			// Just ignore degenerated triangles
			return;
		}

		if (root == null) {
			root = new BSPNode(plane);
		} else {
			root.addFace(new BoolVertex[] { new BoolVertex(v1),
					new BoolVertex(v2), new BoolVertex(v3) }, plane);
		}
	}

	public void dump() {
		System.out.println(">>> BSP");
		if (root != null) {
			root.dump(" ");
		}
	}
}
