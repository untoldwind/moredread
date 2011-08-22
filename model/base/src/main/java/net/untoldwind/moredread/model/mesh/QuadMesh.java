package net.untoldwind.moredread.model.mesh;

import java.io.IOException;

import net.untoldwind.moredread.model.enums.MeshType;
import net.untoldwind.moredread.model.state.IStateReader;
import net.untoldwind.moredread.model.transform.ITransformation;

public class QuadMesh extends Mesh<QuadFace> {
	@Override
	public MeshType getMeshType() {
		return MeshType.QUAD;
	}

	public QuadFace addFace(final int vertexIndex1, final int vertexIndex2,
			final int vertexIndex3, final int vertexIndex4) {
		final Vertex vertexArr[] = new Vertex[4];

		vertexArr[0] = vertices.get(vertexIndex1);
		vertexArr[1] = vertices.get(vertexIndex2);
		vertexArr[2] = vertices.get(vertexIndex3);
		vertexArr[3] = vertices.get(vertexIndex4);

		final AbstractEdge edgeArr[] = new AbstractEdge[4];

		edgeArr[0] = addEdge(vertexArr[0], vertexArr[1]);
		edgeArr[1] = addEdge(vertexArr[1], vertexArr[2]);
		edgeArr[2] = addEdge(vertexArr[2], vertexArr[3]);
		edgeArr[3] = addEdge(vertexArr[3], vertexArr[0]);

		final QuadFace face = new QuadFace(this, faces.size(), vertexArr,
				edgeArr);

		faces.add(face);

		return face;
	}

	@Override
	public TriangleMesh toTriangleMesh() {
		final TriangleMesh mesh = new TriangleMesh();

		for (final Vertex vertex : vertices) {
			mesh.addVertex(vertex.getPoint(), vertex.isSmooth());
		}

		for (final QuadFace face : faces) {
			final Vertex vertices[] = face.getVertexArray();

			mesh.addFace(vertices[0].getIndex(), vertices[1].getIndex(),
					vertices[2].getIndex());
			mesh.addFace(vertices[2].getIndex(), vertices[3].getIndex(),
					vertices[0].getIndex());
		}

		return mesh;
	}

	@Override
	public IMesh transform(final ITransformation transformation) {
		final QuadMesh newMesh = new QuadMesh();

		for (final IVertex vertex : vertices) {
			newMesh.addVertex(vertex.transform(transformation).getPoint());
		}
		for (final QuadFace face : faces) {
			newMesh.addFace(face.getVertex(0).getIndex(), face.getVertex(1)
					.getIndex(), face.getVertex(2).getIndex(), face
					.getVertex(3).getIndex());
		}
		return newMesh;
	}

	public class FaceInstanceCreator implements
			IStateReader.InstanceCreator<QuadFace> {

		@Override
		public QuadFace createInstance(final IStateReader reader)
				throws IOException {
			final int vertexIndex1 = reader.readInt();
			final int vertexIndex2 = reader.readInt();
			final int vertexIndex3 = reader.readInt();
			final int vertexIndex4 = reader.readInt();

			return addFace(vertexIndex1, vertexIndex2, vertexIndex3,
					vertexIndex4);
		}
	}

}
