package net.untoldwind.moredread.model.scene.event;

import java.util.Set;

import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.Scene;

public class SceneSelectionChangeEvent {
	private final Scene scene;
	private final Set<INode> selectedNodes;

	public SceneSelectionChangeEvent(final Scene scene,
			final Set<INode> selectedNodes) {
		this.scene = scene;
		this.selectedNodes = selectedNodes;
	}

	public Scene getScene() {
		return scene;
	}

	public Set<INode> getSelectedNodes() {
		return selectedNodes;
	}

}
