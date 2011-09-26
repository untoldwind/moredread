package net.untoldwind.moredread.model.mesh;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.untoldwind.moredread.model.enums.MeshType;
import net.untoldwind.moredread.model.op.ITriangulator;
import net.untoldwind.moredread.model.op.TriangulatorFactory;
import net.untoldwind.moredread.model.state.IStateReader;
import net.untoldwind.moredread.model.state.IStateWriter;
import net.untoldwind.moredread.model.transform.ITransformation;

import com.jme.math.Vector3f;

public class PolyMesh extends Mesh<PolyFaceId, PolyFace> {

	private int faceCounter = 0;

	public PolyMesh() {
	}

	@Override
	public MeshType getMeshType() {
		return MeshType.POLY;
	}

	public PolyFace addFace(final int... vertexIndices) {
		final List<Vertex<PolyFace>> vertexList = new ArrayList<Vertex<PolyFace>>(
				vertexIndices.length);

		for (final int vertexIndes : vertexIndices) {
			final Vertex<PolyFace> vertex = vertices.get(vertexIndes);

			vertexList.add(vertex);
		}

		final List<Edge<PolyFace>> edgeList = new ArrayList<Edge<PolyFace>>(
				vertexIndices.length);

		for (int i = 0; i < vertexIndices.length; i++) {
			final Vertex<PolyFace> vertex1 = vertexList.get(i);
			final Vertex<PolyFace> vertex2 = vertexList.get((i + 1)
					% vertexIndices.length);

			edgeList.add(addEdge(vertex1, vertex2));
		}

		final PolyFace face = new PolyFace(this, new PolyFaceId(faceCounter++),
				vertexList, edgeList);

		faces.put(face.getIndex(), face);

		return face;
	}

	@SuppressWarnings("unchecked")
	public PolyFace addFace(final int[][] vertexStripIndices) {
		final Vertex<PolyFace>[][] vertexStrips = new Vertex[vertexStripIndices.length][];
		final List<Edge<PolyFace>> edgeList = new ArrayList<Edge<PolyFace>>();

		for (int k = 0; k < vertexStripIndices.length; k++) {
			final int[] vertexIndices = vertexStripIndices[k];

			vertexStrips[k] = new Vertex[vertexStripIndices[k].length];

			for (int i = 0; i < vertexIndices.length; i++) {
				final Vertex<PolyFace> vertex1 = vertices.get(vertexIndices[i]);

				vertexStrips[k][i] = vertex1;

				final Vertex<PolyFace> vertex2 = vertices
						.get(vertexIndices[(i + 1) % vertexIndices.length]);

				edgeList.add(addEdge(vertex1, vertex2));
			}
		}

		final PolyFace face = new PolyFace(this, new PolyFaceId(faceCounter++),
				vertexStrips, edgeList);

		faces.put(face.getIndex(), face);

		return face;

	}

	public Vertex<PolyFace> addMidpoint(final EdgeId edgeId,
			final Vector3f point) {
		final Edge<PolyFace> edge = edges.get(edgeId);

		if (edge == null) {
			return null;
		}

		final Vertex<PolyFace> vertex = addVertex(point);
		final Edge<PolyFace> newEdge1 = addEdge(edge.getVertex1(), vertex);
		final Edge<PolyFace> newEdge2 = addEdge(vertex, edge.getVertex2());
		final List<PolyFace> faces = new ArrayList<PolyFace>(edge.getFaces());

		for (final PolyFace face : faces) {
			face.addMidpoint(edge, vertex, newEdge1, newEdge2);
		}
		edge.remove();
		edges.remove(edgeId);

		markDirty();

		return vertex;
	}

	@Override
	public TriangleMesh toTriangleMesh() {
		final TriangleMesh triangleMesh = new TriangleMesh();
		final ITriangulator triangulator = TriangulatorFactory.createDefault();

		for (final Vertex<PolyFace> vertex : vertices) {
			triangleMesh.addVertex(vertex.getPoint(), vertex.isSmooth());
		}

		for (final PolyFace face : faces.values()) {
			final List<Vertex<PolyFace>> vertices = face.getVertices();
			final int[] indices = triangulator.triangulate(face);

			for (int i = 0; i < indices.length; i += 3) {
				triangleMesh.addFace(vertices.get(indices[i]).getIndex(),
						vertices.get(indices[i + 1]).getIndex(),
						vertices.get(indices[i + 2]).getIndex());
			}
		}
		return triangleMesh;
	}

	@Override
	public PolyMesh toPolyMesh() {
		return this;
	}

	public PolyMesh invert() {
		final PolyMesh mesh = new PolyMesh();

		for (final Vertex<PolyFace> vertex : vertices) {
			mesh.addVertex(vertex.getPoint(), vertex.isSmooth());
		}

		for (final PolyFace face : faces.values()) {
			final Iterator<Vertex<PolyFace>> it = face.getVertices().iterator();
			final List<Integer> stripCounts = face.getStripCounts();
			final int[][] faceStrips = new int[stripCounts.size()][];

			for (int i = 0; i < faceStrips.length; i++) {
				final int stripCount = stripCounts.get(i);
				faceStrips[i] = new int[stripCount];

				for (int j = stripCount - 1; j >= 0; j--) {
					faceStrips[i][j] = it.next().getIndex();
				}
			}
			mesh.addFace(faceStrips);
		}

		return mesh;
	}

	@Override
	public IMesh transform(final ITransformation transformation) {
		final PolyMesh newMesh = new PolyMesh();

		for (final IVertex vertex : vertices) {
			newMesh.addVertex(vertex.transform(transformation).getPoint());
		}
		for (final PolyFace face : faces.values()) {
			final int[] stripCounts = face.getPolygonStripCounts();
			final int[][] vertexStripIndices = new int[stripCounts.length][];
			final Iterator<Vertex<PolyFace>> it = face.getVertices().iterator();
			for (int i = 0; i < stripCounts.length; i++) {
				vertexStripIndices[i] = new int[stripCounts[i]];
				for (int j = 0; j < stripCounts[i]; j++) {
					vertexStripIndices[i][j] = it.next().getIndex();
				}
			}
			newMesh.addFace(vertexStripIndices);
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
			final int numStrips = reader.readInt();
			final int vertexStripIndices[][] = new int[numStrips][];

			for (int j = 0; j < numStrips; j++) {
				final int stripCount = reader.readInt();
				final int[] stripIndices = new int[stripCount];

				for (int k = 0; k < stripCount; k++) {
					stripIndices[k] = reader.readInt();
				}

				vertexStripIndices[j] = stripIndices;
			}

			addFace(vertexStripIndices);
		}
	}

	@Override
	public void writeState(final IStateWriter writer) throws IOException {
		writer.writeInt("numVertices", vertices.size());
		for (final IVertex vertex : vertices) {
			writer.writeVector3f("vertex", vertex.getPoint());
		}
		writer.writeInt("numFaces", faces.size());
		for (final PolyFace face : faces.values()) {
			writer.writeInt("numStips", face.getStripCounts().size());
			final Iterator<Vertex<PolyFace>> it = face.getVertices().iterator();
			for (final Integer stripCount : face.getStripCounts()) {
				writer.writeInt("stripCount", stripCount);
				for (int i = 0; i < stripCount; i++) {
					writer.writeInt("index", it.next().getIndex());
				}
			}
		}
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
}
