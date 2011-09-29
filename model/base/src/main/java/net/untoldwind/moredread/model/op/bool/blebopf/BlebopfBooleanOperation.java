package net.untoldwind.moredread.model.op.bool.blebopf;

import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.math.Plane;
import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.IVertex;
import net.untoldwind.moredread.model.mesh.TriangleFace;
import net.untoldwind.moredread.model.mesh.TriangleFaceId;
import net.untoldwind.moredread.model.mesh.TriangleMesh;
import net.untoldwind.moredread.model.op.IBooleanOperation;

public class BlebopfBooleanOperation implements IBooleanOperation {

	@Override
	public IMesh performBoolean(final BoolOperation operation, final IMesh inA,
			final IMesh inB) {
		final TriangleMesh meshA = inA.toTriangleMesh();
		final TriangleMesh meshB = inB.toTriangleMesh();
		final boolean invertMeshA = (operation == BoolOperation.UNION);
		final boolean invertMeshB = (operation != BoolOperation.INTERSECTION);
		final boolean invertMeshC = (operation == BoolOperation.UNION);

		final BoolMesh meshC = new BoolMesh();

		for (final IVertex vertex : meshA.getVertices()) {
			meshC.addVertex(vertex.getPoint());
		}
		final int vertexOffsetB = meshC.getNumVertexs();
		for (final IVertex vertex : meshB.getVertices()) {
			meshC.addVertex(vertex.getPoint());
		}
		final List<BoolFace> facesA = new ArrayList<BoolFace>();
		for (final TriangleFace face : meshA.getFaces()) {
			final BoolVertex v1 = meshC.getVertex(face.getVertex(0).getIndex());
			final BoolVertex v2 = meshC.getVertex(face.getVertex(1).getIndex());
			final BoolVertex v3 = meshC.getVertex(face.getVertex(2).getIndex());

			if (invertMeshA) {
				final Plane plane = MathUtils.createPlane(v3.getPoint(),
						v2.getPoint(), v1.getPoint());
				final BoolFace boolFace = new BoolFace(v3, v2, v1, plane,
						face.getIndex());
				facesA.add(boolFace);
				meshC.addFace(boolFace);
			} else {
				final Plane plane = MathUtils.createPlane(v1.getPoint(),
						v2.getPoint(), v3.getPoint());
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
				final Plane plane = MathUtils.createPlane(v3.getPoint(),
						v2.getPoint(), v1.getPoint());
				final TriangleFaceId faceId = face.getIndex();
				final BoolFace boolFace = new BoolFace(v3, v2, v1, plane,
						new TriangleFaceId(faceId.getIndex1() + vertexOffsetB,
								faceId.getIndex2() + vertexOffsetB,
								faceId.getIndex3() + vertexOffsetB));
				facesB.add(boolFace);
				meshC.addFace(boolFace);
			} else {
				final Plane plane = MathUtils.createPlane(v1.getPoint(),
						v2.getPoint(), v3.getPoint());
				final TriangleFaceId faceId = face.getIndex();
				final BoolFace boolFace = new BoolFace(v1, v2, v3, plane,
						new TriangleFaceId(faceId.getIndex1() + vertexOffsetB,
								faceId.getIndex2() + vertexOffsetB,
								faceId.getIndex3() + vertexOffsetB));
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
