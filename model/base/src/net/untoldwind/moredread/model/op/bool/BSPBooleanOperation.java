package net.untoldwind.moredread.model.op.bool;

import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.mesh.TriangleFace;
import net.untoldwind.moredread.model.mesh.TriangleMesh;
import net.untoldwind.moredread.model.mesh.Vertex;
import net.untoldwind.moredread.model.op.IBooleanOperation;

import com.jme.math.Plane;

public class BSPBooleanOperation implements IBooleanOperation {

	@Override
	public TriangleMesh intersect(final TriangleMesh meshA,
			final TriangleMesh meshB) {
		final BoolMesh meshC = new BoolMesh();

		for (final Vertex vertex : meshA.getVertices()) {
			meshC.addVertex(vertex.getPoint());
		}
		final int offsetB = meshC.getNumVertexs();
		for (final Vertex vertex : meshB.getVertices()) {
			meshC.addVertex(vertex.getPoint());
		}
		final List<BoolFace> facesA = new ArrayList<BoolFace>();
		for (final TriangleFace face : meshA.getFaces()) {
			final BoolVertex v1 = meshC.getVertex(face.getVertex(0).getIndex());
			final BoolVertex v2 = meshC.getVertex(face.getVertex(1).getIndex());
			final BoolVertex v3 = meshC.getVertex(face.getVertex(2).getIndex());
			final Plane plane = MathUtils.createPlane(v1.getPoint(), v2
					.getPoint(), v3.getPoint());
			facesA.add(new BoolFace(v1, v2, v3, plane, null));
		}
		final List<BoolFace> facesB = new ArrayList<BoolFace>();
		for (final TriangleFace face : meshB.getFaces()) {
			final BoolVertex v1 = meshC.getVertex(face.getVertex(0).getIndex()
					+ offsetB);
			final BoolVertex v2 = meshC.getVertex(face.getVertex(1).getIndex()
					+ offsetB);
			final BoolVertex v3 = meshC.getVertex(face.getVertex(2).getIndex()
					+ offsetB);
			final Plane plane = MathUtils.createPlane(v1.getPoint(), v2
					.getPoint(), v3.getPoint());
			facesB.add(new BoolFace(v1, v2, v3, plane, null));
		}

		BoolImpl.intersectionBoolOp(meshC, facesA, facesB, false, false);

		for (final BoolVertex vertex : meshC.getVertices()) {
			System.out.println(vertex);
		}
		for (final BoolFace face : meshC.getFaces()) {
			System.out.println(face);
		}
		return null;
	}

}
