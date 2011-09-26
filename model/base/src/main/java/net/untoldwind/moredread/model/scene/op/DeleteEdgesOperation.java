package net.untoldwind.moredread.model.scene.op;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.untoldwind.moredread.model.mesh.Edge;
import net.untoldwind.moredread.model.mesh.EdgeId;
import net.untoldwind.moredread.model.mesh.Mesh;
import net.untoldwind.moredread.model.mesh.Vertex;
import net.untoldwind.moredread.model.scene.IMeshNode;
import net.untoldwind.moredread.model.scene.ISceneOperation;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.model.scene.SceneSelection.EdgeSelection;

public class DeleteEdgesOperation implements ISceneOperation {
	private final Map<IMeshNode, Set<EdgeId>> edges;

	public DeleteEdgesOperation(final Set<EdgeSelection> edgeSelections) {
		this.edges = new HashMap<IMeshNode, Set<EdgeId>>();

		for (final EdgeSelection edgeSelection : edgeSelections) {
			if (edgeSelection.getNode() instanceof IMeshNode) {
				final IMeshNode meshNode = (IMeshNode) edgeSelection.getNode();
				Set<EdgeId> edgeIds = this.edges.get(meshNode);

				if (edgeIds == null) {
					edgeIds = new HashSet<EdgeId>();
					this.edges.put(meshNode, edgeIds);
				}
				edgeIds.add(edgeSelection.getEdgeIndex());
			}
		}
	}

	@Override
	public String getLabel() {
		return "Delete edges";
	}

	@Override
	public void perform(final Scene scene) {
		for (final IMeshNode meshNode : edges.keySet()) {
			final Mesh<?, ?> mesh = meshNode.getEditableGeometry();

			mesh.removeEdges(edges.get(meshNode));

			final Set<EdgeId> obsoleteEdgeIds = new HashSet<EdgeId>();
			for (final Edge<?> edge : mesh.getEdges()) {
				if (edge.getFaces().isEmpty()) {
					obsoleteEdgeIds.add(edge.getIndex());
				}
			}
			mesh.removeEdges(obsoleteEdgeIds);

			final Set<Integer> obsoleteVertexId = new HashSet<Integer>();
			for (final Vertex<?> vertex : mesh.getVertices()) {
				if (vertex.getEdges().isEmpty()) {
					obsoleteVertexId.add(vertex.getIndex());
				}
			}
			mesh.removeVertices(obsoleteVertexId);

		}
	}

}
