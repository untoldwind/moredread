package net.untoldwind.moredread.model.op.bool;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.untoldwind.moredread.model.mesh.EdgeId;

import com.jme.math.Vector3f;

public class BoolMesh {
	List<BoolVertex> vertices;
	Map<EdgeId, BoolEdge> edges;
	List<BoolFace> faces;

	public BoolMesh() {
		vertices = new ArrayList<BoolVertex>();
		edges = new HashMap<EdgeId, BoolEdge>();
		faces = new ArrayList<BoolFace>();
	}

	public int getNumVertexs() {
		return vertices.size();
	}

	public BoolVertex getVertex(final int index) {
		return vertices.get(index);
	}

	public List<BoolVertex> getVertices() {
		return vertices;
	}

	public int getNumFaces() {
		return faces.size();
	}

	public BoolFace getFace(final int idx) {
		return faces.get(idx);
	}

	public List<BoolFace> getFaces() {
		return faces;
	}

	public void addFace(final BoolFace face) {
		if (face.size() == 3) {
			addFace3(face);
		} else {
			addFace4(face);
		}
	}

	void addFace3(final BoolFace face) {
		faces.add(face);

		final BoolVertex index1 = face.getVertex(0);
		final BoolVertex index2 = face.getVertex(1);
		final BoolVertex index3 = face.getVertex(2);

		BoolEdge edge = getOrAddEdge(index1, index2);

		edge.addFace(face);

		edge = getOrAddEdge(index2, index3);

		edge.addFace(face);

		edge = getOrAddEdge(index3, index1);

		edge.addFace(face);

		if ((index1 == index2) || (index1 == index3) || (index2 == index3)) {
			face.setTAG(BoolTag.BROKEN);
		}
	}

	void addFace4(final BoolFace face) {
		faces.add(face);

		final BoolVertex index1 = face.getVertex(0);
		final BoolVertex index2 = face.getVertex(1);
		final BoolVertex index3 = face.getVertex(2);
		final BoolVertex index4 = face.getVertex(3);

		BoolEdge edge = getOrAddEdge(index1, index2);

		edge.addFace(face);

		edge = getOrAddEdge(index2, index3);

		edge.addFace(face);

		edge = getOrAddEdge(index3, index4);

		edge.addFace(face);

		edge = getOrAddEdge(index4, index1);

		edge.addFace(face);

		if ((index1 == index2) || (index1 == index3) || (index1 == index4)
				|| (index2 == index3) || (index2 == index4)
				|| (index3 == index4)) {
			face.setTAG(BoolTag.BROKEN);
		}
	}

	public BoolEdge getOrAddEdge(final BoolVertex v1, final BoolVertex v2) {
		final EdgeId edgeId = new EdgeId(v1.getIndex(), v2.getIndex());

		BoolEdge edge = edges.get(edgeId);

		if (edge == null) {
			edge = new BoolEdge(v1, v2);
			edges.put(edgeId, edge);
		}

		return edge;
	}

	public BoolEdge getEdge(final BoolVertex v1, final BoolVertex v2) {
		final EdgeId edgeId = new EdgeId(v1.getIndex(), v2.getIndex());

		return edges.get(edgeId);
	}

	/**
	 * Returns the mesh edge on the specified face and relative edge index.
	 * 
	 * @param face
	 *            mesh face
	 * @param edge
	 *            face relative edge index
	 * @return mesh edge on the specified face and relative index, NULL
	 *         otherwise
	 */
	public BoolEdge getEdge(final BoolFace face, final int edge) {
		if (face.size() == 3) {
			return getEdge3(face, edge);
		} else {
			return getEdge4(face, edge);
		}
	}

	/**
	 * Returns the mesh edge on the specified triangle and relative edge index.
	 * 
	 * @param face
	 *            mesh triangle
	 * @param edge
	 *            face relative edge index
	 * @return mesh edge on the specified triangle and relative index, NULL
	 *         otherwise
	 */
	public BoolEdge getEdge3(final BoolFace face, final int edge) {
		switch (edge) {
		case 1:
			return getEdge(face.getVertex(0), face.getVertex(1));
		case 2:
			return getEdge(face.getVertex(1), face.getVertex(2));
		case 3:
			return getEdge(face.getVertex(2), face.getVertex(0));
		}

		return null;
	}

	/**
	 * Returns the mesh edge on the specified quad and relative edge index.
	 * 
	 * @param face
	 *            mesh quad
	 * @param edge
	 *            face relative edge index
	 * @return mesh edge on the specified quad and relative index, NULL
	 *         otherwise
	 */
	public BoolEdge getEdge4(final BoolFace face, final int edge) {
		switch (edge) {
		case 1:
			return getEdge(face.getVertex(0), face.getVertex(1));
		case 2:
			return getEdge(face.getVertex(1), face.getVertex(2));
		case 3:
			return getEdge(face.getVertex(2), face.getVertex(3));
		case 4:
			return getEdge(face.getVertex(3), face.getVertex(0));
		}

		return null;
	}

	public BoolVertex addVertex(final Vector3f point) {
		final BoolVertex vertex = new BoolVertex(point, vertices.size());
		vertices.add(vertex);

		return vertex;
	}

	/**
	 * Marks faces which bad edges as BROKEN (invalid face, no further
	 * processing).
	 * 
	 * @param edge
	 *            edge which is being replaced
	 * @param mesh
	 *            mesh containing faces
	 */

	static void removeBrokenFaces(final BoolEdge edge) {
		final List<BoolFace> edgeFaces = edge.getFaces();
		for (final BoolFace face : edgeFaces) {
			face.setTAG(BoolTag.BROKEN);
		}
	}

	/**
	 * Replaces a vertex index.
	 * 
	 * @param oldIndex
	 *            old vertex index
	 * @param newIndex
	 *            new vertex index
	 */
	BoolVertex replaceVertexIndex(final BoolVertex oldIndex,
			final BoolVertex newIndex) {
		if (oldIndex == newIndex) {
			return newIndex;
		}

		// Update faces, edges and vertices
		final BoolVertex oldVertex = oldIndex;
		final BoolVertex newVertex = newIndex;
		final List<BoolEdge> oldEdges = new ArrayList<BoolEdge>(oldVertex
				.getEdges());

		// Update faces to the newIndex
		for (final BoolEdge edge : oldEdges) {
			if ((edge.getVertex1() == oldIndex && edge.getVertex2() == newIndex)
					|| (edge.getVertex2() == oldIndex && edge.getVertex1() == newIndex)) {
				// Remove old edge ==> set edge faces to BROKEN
				removeBrokenFaces(edge);
				oldVertex.removeEdge(edge);
				newVertex.removeEdge(edge);
			} else {
				final List<BoolFace> faces = edge.getFaces();
				for (final BoolFace face : faces) {
					if (face.getTAG() != BoolTag.BROKEN) {
						face.replaceVertexIndex(oldIndex, newIndex);
					}
				}
			}
		}

		for (final BoolEdge edge : oldEdges) {
			final BoolEdge edge2;
			BoolVertex v1 = edge.getVertex1();

			v1 = (v1 == oldIndex ? edge.getVertex2() : v1);
			if ((edge2 = getEdge(newIndex, v1)) == null) {
				edge.replaceVertexIndex(oldIndex, newIndex);
				if (edge.getVertex1() == edge.getVertex2()) {
					removeBrokenFaces(edge);
					oldVertex.removeEdge(edge);
				}

				newVertex.addEdge(edge);
			} else {
				final List<BoolFace> faces = edge.getFaces();
				for (final BoolFace f : faces) {
					if (f.getTAG() != BoolTag.BROKEN) {
						edge2.addFace(f);
					}
				}
				final BoolVertex oppositeVertex = v1;
				oppositeVertex.removeEdge(edge);
				edge.replaceVertexIndex(oldIndex, newIndex);
				if (edge.getVertex1() == edge.getVertex2()) {
					removeBrokenFaces(edge);
					oldVertex.removeEdge(edge);
					newVertex.removeEdge(edge);
				}

			}
		}
		oldVertex.setTAG(BoolTag.BROKEN);

		return newIndex;
	}

	public void dumpMesh(final PrintStream out) {
		out.println("BSPMesh");
		for (final BoolVertex vertex : vertices) {
			out.println("Point " + vertex.getIndex() + ": "
					+ vertex.getPoint().x + " " + vertex.getPoint().y + " "
					+ vertex.getPoint().z);
		}
		int faceIdx = 0;
		for (final BoolFace face : faces) {
			out.print("Face " + (faceIdx++) + " (" + face.getTAG() + ")");
			for (final BoolVertex vertex : face.getVertices()) {
				out.print(" ");
				out.print(vertex.getIndex());
			}
			out.println();
		}
	}
}
