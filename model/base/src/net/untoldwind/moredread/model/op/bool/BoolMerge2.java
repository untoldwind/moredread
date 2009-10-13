package net.untoldwind.moredread.model.op.bool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class BoolMerge2 {
	BoolMesh mesh;
	int firstVertex;

	BoolMerge2() {
	}

	void mergeFaces(final BoolMesh m, final int firstVertex) {
		this.mesh = m;
		this.firstVertex = firstVertex;

		cleanup();

		// Merge faces
		mergeFaces();

		// TODO
	}

	/**
	 * Runs through mesh and makes sure vert/face/edge data is consistent. Most
	 * importantly: (1) mark edges which are no longer used (2) remove broken
	 * faces from edges (3) remove faces from mesh which have a single edge
	 * belonging to no other face (non-manifold edges)
	 */

	void cleanup() {
		final Collection<BoolEdge> edges = mesh.getEdges();
		for (final BoolEdge edge : edges) {
			final List<BoolFace> faces = new ArrayList<BoolFace>(edge
					.getFaces());

			for (final BoolFace f : faces) {
				if (f.getTAG() != BoolTag.UNCLASSIFIED) {
					edge.removeFace(f);
				}
			}
			if (edge.getFaces().size() == 0) {
				edge.setUsed(false);
			}
		}

		final List<BoolVertex> vertices = mesh.getVertices();

		for (final BoolVertex vertex : vertices) {
			if (vertex.getTAG() != BoolTag.BROKEN) {
				final List<BoolEdge> vEdges = new ArrayList<BoolEdge>(vertex
						.getEdges());

				for (final BoolEdge edge : vEdges) {
					if (!edge.isUsed()) {
						vertex.removeEdge(edge);
					}
				}
				if (vertex.getEdges().size() == 0) {
					vertex.setTAG(BoolTag.BROKEN);
				}
			}
		}

		// clean_nonmanifold( m_mesh );
	}

	/**
	 * Simplifies a mesh, merging its faces.
	 */
	boolean mergeFaces() {
		final List<BoolVertex> mergeVertices = new ArrayList<BoolVertex>();
		final List<BoolVertex> vertices = mesh.getVertices();
		final Iterator<BoolVertex> it = vertices.iterator();

		// Advance to first mergeable vertex
		for (int i = 0; it.hasNext() && i < firstVertex; i++) {
			it.next();
		}

		// Add unbroken vertices to the list
		while (it.hasNext()) {
			final BoolVertex v = it.next();
			if (v.getTAG() != BoolTag.BROKEN) {
				mergeVertices.add(v);
			}
		}

		// Merge faces with that vertices
		return mergeFaces(mergeVertices);
	}

	private boolean mergeFaces(final List<BoolVertex> mergeVertices) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Creates a list of lists L1, L2, ... LN where LX = mesh faces with vertex
	 * v that come from the same original face and without any of the vertices
	 * that appear before v in vertices
	 * 
	 * @param facesByOriginalFace
	 *            list of faces lists
	 * @param vertices
	 *            vector with vertices indexs that contains v
	 * @param v
	 *            vertex index
	 */
	void getFaces(final List<List<BoolFace>> facesByOriginalFace,
			final List<BoolVertex> vertices, final BoolVertex v) {
		final List<BoolEdge> edges = v.getEdges();

		for (final BoolEdge edge : edges) {
			// Foreach edge, add its no broken faces to the output list
			final List<BoolFace> faces = edge.getFaces();

			for (final BoolFace face : faces) {
				if (face.getTAG() != BoolTag.BROKEN) {
					boolean found = false;

					for (final BoolVertex vertex : vertices) {
						if (face.containsVertex(vertex)) {
							found = true;
							break;
						}
					}

					// TODO
				}
			}
		}
	}
}
