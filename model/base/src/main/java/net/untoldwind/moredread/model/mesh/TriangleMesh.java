package net.untoldwind.moredread.model.mesh;

import java.io.IOException;

import net.untoldwind.moredread.model.enums.MeshType;
import net.untoldwind.moredread.model.state.IStateReader;
import net.untoldwind.moredread.model.state.IStateWriter;
import net.untoldwind.moredread.model.transform.ITransformation;

public class TriangleMesh extends Mesh<TriangleFace> {
	@Override
	public MeshType getMeshType() {
		return MeshType.TRIANGLE;
	}

	public TriangleFace addFace(final int vertexIndex1, final int vertexIndex2,
			final int vertexIndex3) {
		final Vertex vertexArr[] = new Vertex[3];

		vertexArr[0] = vertices.get(vertexIndex1);
		vertexArr[1] = vertices.get(vertexIndex2);
		vertexArr[2] = vertices.get(vertexIndex3);

		final AbstractEdge edgeArr[] = new AbstractEdge[3];

		edgeArr[0] = addEdge(vertexArr[0], vertexArr[1]);
		edgeArr[1] = addEdge(vertexArr[1], vertexArr[2]);
		edgeArr[2] = addEdge(vertexArr[2], vertexArr[0]);

		final TriangleFace face = new TriangleFace(this, faces.size(),
				vertexArr, edgeArr);

		faces.add(face);

		return face;
	}

	@Override
	public TriangleMesh toTriangleMesh() {
		return this;
	}

	@Override
	public IMesh transform(final ITransformation transformation) {
		final TriangleMesh newMesh = new TriangleMesh();

		for (final IVertex vertex : vertices) {
			newMesh.addVertex(vertex.transform(transformation).getPoint());
		}
		for (final TriangleFace face : faces) {
			newMesh.addFace(face.getVertex(0).getIndex(), face.getVertex(1)
					.getIndex(), face.getVertex(2).getIndex());
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
			addFace(vertexIndex1, vertexIndex2, vertexIndex3);
		}
	}

	@Override
	public void writeState(final IStateWriter writer) throws IOException {
		writer.writeInt("numVertices", vertices.size());
		for (final IVertex vertex : vertices) {
			writer.writeVector3f("vertex", vertex.getPoint());
		}
		writer.writeInt("numFaces", faces.size());
		for (final TriangleFace face : faces) {
			final Vertex[] vertices = face.getVertexArray();
			for (int i = 0; i < 3; i++) {
				writer.writeInt("index", vertices[i].getIndex());
			}
		}
	}
}
