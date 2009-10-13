package net.untoldwind.moredread.model.op.bool;

import java.util.ArrayList;
import java.util.Collection;
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
}
