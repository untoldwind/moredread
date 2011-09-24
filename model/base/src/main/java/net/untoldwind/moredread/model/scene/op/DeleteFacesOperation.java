package net.untoldwind.moredread.model.scene.op;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.untoldwind.moredread.model.mesh.Edge;
import net.untoldwind.moredread.model.mesh.EdgeId;
import net.untoldwind.moredread.model.mesh.FaceId;
import net.untoldwind.moredread.model.mesh.Mesh;
import net.untoldwind.moredread.model.mesh.Vertex;
import net.untoldwind.moredread.model.scene.IMeshNode;
import net.untoldwind.moredread.model.scene.ISceneOperation;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.model.scene.SceneSelection.FaceSelection;

public class DeleteFacesOperation implements ISceneOperation {
	private final Map<IMeshNode, Set<FaceId>> faces;

	public DeleteFacesOperation(final Set<FaceSelection> faceSelections) {
		this.faces = new HashMap<IMeshNode, Set<FaceId>>();

		for (final FaceSelection faceSelection : faceSelections) {
			final IMeshNode meshNode = faceSelection.getNode();
			Set<FaceId> faceIds = this.faces.get(meshNode);

			if (faceIds == null) {
				faceIds = new HashSet<FaceId>();
				this.faces.put(meshNode, faceIds);
			}
			faceIds.add(faceSelection.getFaceIndex());
		}
	}

	@Override
	public String getLabel() {
		return "Delete faces";
	}

	@Override
	public void perform(final Scene scene) {
		for (final IMeshNode meshNode : faces.keySet()) {
			final Mesh<?, ?> mesh = meshNode.getEditableGeometry();
			final Set<EdgeId> obsoleteEdgeIds = new HashSet<EdgeId>();

			mesh.removeFaces(faces.get(meshNode));

			for (final Edge edge : mesh.getEdges()) {
				if (edge.getFaces().isEmpty()) {
					obsoleteEdgeIds.add(edge.getIndex());
				}
			}
			mesh.removeEdges(obsoleteEdgeIds);

			final Set<Integer> obsoleteVertexId = new HashSet<Integer>();
			for (final Vertex vertex : mesh.getVertices()) {
				if (vertex.getEdges().isEmpty()) {
					obsoleteVertexId.add(vertex.getIndex());
				}
			}
			mesh.removeVertices(obsoleteVertexId);
		}
	}
}
