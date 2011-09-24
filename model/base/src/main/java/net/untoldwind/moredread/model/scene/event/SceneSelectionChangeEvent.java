package net.untoldwind.moredread.model.scene.event;

import java.util.Set;

import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.model.scene.SceneSelection.EdgeSelection;
import net.untoldwind.moredread.model.scene.SceneSelection.FaceSelection;
import net.untoldwind.moredread.model.scene.SceneSelection.VertexSelection;

public class SceneSelectionChangeEvent {
	private final Scene scene;
	private final Set<INode> selectedNodes;
	private final Set<FaceSelection> selectedFaces;
	private final Set<EdgeSelection> selectedEdges;
	private final Set<VertexSelection> selectedVertices;

	public SceneSelectionChangeEvent(final Scene scene,
			final Set<INode> selectedNodes,
			final Set<FaceSelection> selectedFaces,
			final Set<EdgeSelection> selectedEdges,
			final Set<VertexSelection> selectedVertices) {
		this.scene = scene;
		this.selectedNodes = selectedNodes;
		this.selectedFaces = selectedFaces;
		this.selectedEdges = selectedEdges;
		this.selectedVertices = selectedVertices;
	}

	public Scene getScene() {
		return scene;
	}

	public Set<INode> getSelectedNodes() {
		return selectedNodes;
	}

	public Set<FaceSelection> getSelectedFaces() {
		return selectedFaces;
	}

	public Set<EdgeSelection> getSelectedEdges() {
		return selectedEdges;
	}

	public Set<VertexSelection> getSelectedVertices() {
		return selectedVertices;
	}

}
