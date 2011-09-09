package net.untoldwind.moredread.model.mesh;

import java.io.IOException;

import net.untoldwind.moredread.model.enums.MeshType;
import net.untoldwind.moredread.model.state.IStateReader;
import net.untoldwind.moredread.model.state.IStateWriter;
import net.untoldwind.moredread.model.transform.ITransformation;

public class QuadMesh extends Mesh<QuadFaceId, QuadFace> {
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

		final Edge edgeArr[] = new Edge[4];

		edgeArr[0] = addEdge(vertexArr[0], vertexArr[1]);
		edgeArr[1] = addEdge(vertexArr[1], vertexArr[2]);
		edgeArr[2] = addEdge(vertexArr[2], vertexArr[3]);
		edgeArr[3] = addEdge(vertexArr[3], vertexArr[0]);

		final QuadFace face = new QuadFace(this, new QuadFaceId(vertexIndex1,
				vertexIndex2, vertexIndex3, vertexIndex4), vertexArr, edgeArr);

		faces.put(face.getIndex(), face);

		return face;
	}

	@Override
	public TriangleMesh toTriangleMesh() {
		final TriangleMesh mesh = new TriangleMesh();

		for (final Vertex vertex : vertices) {
			mesh.addVertex(vertex.getPoint(), vertex.isSmooth());
		}

		for (final QuadFace face : faces.values()) {
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
		for (final QuadFace face : faces.values()) {
			newMesh.addFace(face.getVertex(0).getIndex(), face.getVertex(1)
					.getIndex(), face.getVertex(2).getIndex(), face
					.getVertex(3).getIndex());
		}
		return newMesh;
	}

	@Override
	public void readState(final IStateReader reader) throws IOException {
		final int numVertices = reader.readInt();

		for (int i = 0; i < numVertices; i++) {
			addVertex(reader.readVector3f());
		}
		final int numFaces = reader.readInt();

		for (int i = 0; i < numFaces; i++) {
			final int vertexIndex1 = reader.readInt();
			final int vertexIndex2 = reader.readInt();
			final int vertexIndex3 = reader.readInt();
			final int vertexIndex4 = reader.readInt();
			addFace(vertexIndex1, vertexIndex2, vertexIndex3, vertexIndex4);
		}
	}

	@Override
	public void writeState(final IStateWriter writer) throws IOException {
		writer.writeInt("numVertices", vertices.size());
		for (final IVertex vertex : vertices) {
			writer.writeVector3f("vertex", vertex.getPoint());
		}
		writer.writeInt("numFaces", faces.size());
		for (final QuadFace face : faces.values()) {
			final Vertex[] vertices = face.getVertexArray();
			for (int i = 0; i < 4; i++) {
				writer.writeInt("index", vertices[i].getIndex());
			}
		}
	}
}
