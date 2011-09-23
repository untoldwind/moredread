package net.untoldwind.moredread.model.op.bool.blebopd;

import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.TriangleFace;
import net.untoldwind.moredread.model.mesh.TriangleMesh;
import net.untoldwind.moredread.model.mesh.Vertex;
import net.untoldwind.moredread.model.op.IBooleanOperation;

public class BlebopdBooleanOperation implements IBooleanOperation {

	@Override
	public IMesh performBoolean(final BoolOperation operation, final IMesh inA,
			final IMesh inB) {
		final TriangleMesh meshA = inA.toTriangleMesh();
		final TriangleMesh meshB = inB.toTriangleMesh();
		final boolean invertMeshA = (operation == BoolOperation.UNION);
		final boolean invertMeshB = (operation != BoolOperation.INTERSECTION);
		final boolean invertMeshC = (operation == BoolOperation.UNION);

		final BoolMesh meshC = new BoolMesh();

		for (final Vertex vertex : meshA.getVertices()) {
			meshC.addVertex(new Vector3d(vertex.getPoint()));
		}
		final int vertexOffsetB = meshC.getNumVertexs();
		for (final Vertex vertex : meshB.getVertices()) {
			meshC.addVertex(new Vector3d(vertex.getPoint()));
		}
		final List<BoolFace> facesA = new ArrayList<BoolFace>();
		for (final TriangleFace face : meshA.getFaces()) {
			final BoolVertex v1 = meshC.getVertex(face.getVertex(0).getIndex());
			final BoolVertex v2 = meshC.getVertex(face.getVertex(1).getIndex());
			final BoolVertex v3 = meshC.getVertex(face.getVertex(2).getIndex());

			if (invertMeshA) {
				final Plane3d plane = MathUtils.createPlane3d(v3.getPoint3d(),
						v2.getPoint3d(), v1.getPoint3d());
				final BoolFace boolFace = new BoolFace(v3, v2, v1, plane,
						face.getIndex());
				facesA.add(boolFace);
				meshC.addFace(boolFace);
			} else {
				final Plane3d plane = MathUtils.createPlane3d(v1.getPoint3d(),
						v2.getPoint3d(), v3.getPoint3d());
				final BoolFace boolFace = new BoolFace(v1, v2, v3, plane,
						face.getIndex());
				facesA.add(boolFace);
				meshC.addFace(boolFace);
			}
		}
		final List<BoolFace> facesB = new ArrayList<BoolFace>();
		for (final TriangleFace face : meshB.getFaces()) {
			final BoolVertex v1 = meshC.getVertex(face.getVertex(0).getIndex()
					+ vertexOffsetB);
			final BoolVertex v2 = meshC.getVertex(face.getVertex(1).getIndex()
					+ vertexOffsetB);
			final BoolVertex v3 = meshC.getVertex(face.getVertex(2).getIndex()
					+ vertexOffsetB);
			if (invertMeshB) {
				final Plane3d plane = MathUtils.createPlane3d(v3.getPoint3d(),
						v2.getPoint3d(), v1.getPoint3d());
				final BoolFace boolFace = new BoolFace(v3, v2, v1, plane,
						face.getIndex() + facesA.size());
				facesB.add(boolFace);
				meshC.addFace(boolFace);
			} else {
				final Plane3d plane = MathUtils.createPlane3d(v1.getPoint3d(),
						v2.getPoint3d(), v3.getPoint3d());
				final BoolFace boolFace = new BoolFace(v1, v2, v3, plane,
						face.getIndex() + facesA.size());
				facesB.add(boolFace);
				meshC.addFace(boolFace);
			}
		}

		BoolImpl.intersectionBoolOp(meshC, facesA, facesB, invertMeshA,
				invertMeshB);

		final TriangleMesh result = new TriangleMesh();

		for (final BoolVertex vertex : meshC.getVertices()) {
			result.addVertex(vertex.getPoint());
		}
		for (final BoolFace face : meshC.getFaces()) {
			if (face.getTAG() != BoolTag.BROKEN
					&& face.getTAG() != BoolTag.PHANTOM) {

				if (invertMeshC) {
					result.addFace(face.getVertex(2).getIndex(), face
							.getVertex(1).getIndex(), face.getVertex(0)
							.getIndex());
				} else {
					result.addFace(face.getVertex(0).getIndex(), face
							.getVertex(1).getIndex(), face.getVertex(2)
							.getIndex());
				}
			}
		}

		return result;
	}
}
