package net.untoldwind.moredread.model.mesh;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.enums.MeshType;
import net.untoldwind.moredread.model.op.ITriangulator;
import net.untoldwind.moredread.model.op.TriangulatorFactory;
import net.untoldwind.moredread.model.state.IStateReader;

public class PolyMesh extends Mesh<PolyFace> {

	public PolyMesh() {
	}

	@Override
	public MeshType getMeshType() {
		return MeshType.POLY;
	}

	public PolyFace addFace(final int... vertexIndices) {
		final List<Vertex> vertexList = new ArrayList<Vertex>(
				vertexIndices.length);

		for (final int vertexIndes : vertexIndices) {
			final Vertex vertex = vertices.get(vertexIndes);

			vertexList.add(vertex);
		}

		final List<Edge> edgeList = new ArrayList<Edge>(vertexIndices.length);

		for (int i = 0; i < vertexIndices.length; i++) {
			final Vertex vertex1 = vertexList.get(i);
			final Vertex vertex2 = vertexList.get((i + 1)
					% vertexIndices.length);

			edgeList.add(addEdge(vertex1, vertex2));
		}

		final PolyFace face = new PolyFace(this, faces.size(), vertexList,
				edgeList);

		faces.add(face);

		return face;
	}

	public PolyFace addFace(final int[][] vertexStripIndices) {
		final Vertex[][] vertexStrips = new Vertex[vertexStripIndices.length][];
		final List<Edge> edgeList = new ArrayList<Edge>();

		for (int k = 0; k < vertexStripIndices.length; k++) {
			final int[] vertexIndices = vertexStripIndices[k];

			vertexStrips[k] = new Vertex[vertexStripIndices[k].length];

			for (int i = 0; i < vertexIndices.length; i++) {
				final Vertex vertex1 = vertices.get(vertexIndices[i]);

				vertexStrips[k][i] = vertex1;

				final Vertex vertex2 = vertices.get(vertexIndices[(i + 1)
						% vertexIndices.length]);

				edgeList.add(addEdge(vertex1, vertex2));
			}
		}

		final PolyFace face = new PolyFace(this, faces.size(), vertexStrips,
				edgeList);

		faces.add(face);

		return face;

	}

	@Override
	public TriangleMesh toTriangleMesh() {
		final TriangleMesh triangleMesh = new TriangleMesh();
		final ITriangulator triangulator = TriangulatorFactory.createDefault();

		for (final Vertex vertex : vertices) {
			triangleMesh.addVertex(vertex.getPoint(), vertex.isSmooth());
		}

		for (final PolyFace face : faces) {
			final List<Vertex> vertices = face.getVertices();
			final int[] indices = triangulator.triangulate(face);

			for (int i = 0; i < indices.length; i += 3) {
				triangleMesh.addFace(vertices.get(indices[i]).getIndex(),
						vertices.get(indices[i + 1]).getIndex(), vertices.get(
								indices[i + 2]).getIndex());
			}
		}
		return triangleMesh;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("PolyMesh [vertices=");
		builder.append(vertices);
		builder.append(", faces=");
		builder.append(faces);
		builder.append("]");
		return builder.toString();
	}

	public class FaceInstanceCreator implements
			IStateReader.InstanceCreator<PolyFace> {

		@Override
		public PolyFace createInstance(final IStateReader reader)
				throws IOException {
			final int vertexNumber = reader.readInt();
			final int[] vertexIndices = new int[vertexNumber];

			for (int i = 0; i < vertexNumber; i++) {
				vertexIndices[i] = reader.readInt();
			}

			return addFace(vertexIndices);
		}
	}
}
