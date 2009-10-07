package net.untoldwind.moredread.model.mesh;

import java.io.IOException;

import net.untoldwind.moredread.model.enums.MeshType;
import net.untoldwind.moredread.model.state.IStateReader;

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

		final Edge edgeArr[] = new Edge[3];

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

	public class FaceInstanceCreator implements
			IStateReader.InstanceCreator<TriangleFace> {

		@Override
		public TriangleFace createInstance(final IStateReader reader)
				throws IOException {
			final int vertexIndex1 = reader.readInt();
			final int vertexIndex2 = reader.readInt();
			final int vertexIndex3 = reader.readInt();

			return addFace(vertexIndex1, vertexIndex2, vertexIndex3);
		}
	}

}
