package net.untoldwind.moredread.model.scene;

import java.util.HashMap;
import java.util.Map;

import net.untoldwind.moredread.model.scene.event.SceneChangeEvent;

public class Scene extends Group {
	private final SceneHolder sceneHolder;
	private final SceneSelection sceneSelection;
	private final SceneChangeHandler sceneChangeHandler;
	private final SceneMetadata sceneMetadata;

	private final Map<Long, INode> nodesById;

	public Scene(final SceneHolder sceneHolder) {
		super(null, "Scene");

		this.sceneHolder = sceneHolder;
		sceneSelection = new SceneSelection(this);
		sceneChangeHandler = new SceneChangeHandler(this);
		sceneMetadata = new SceneMetadata();
		nodesById = new HashMap<Long, INode>();
		nodesById.put(nodeId, this);
	}

	public SceneSelection getSceneSelection() {
		return sceneSelection;
	}

	public SceneChangeHandler getSceneChangeHandler() {
		return sceneChangeHandler;
	}

	public SceneMetadata getSceneMetadata() {
		return sceneMetadata;
	}

	void registerNode(final INode node) {
		nodesById.put(node.getNodeId(), node);
	}

	void unregisterNode(final INode node) {
		nodesById.remove(node.getNodeId());
	}

	public INode getNode(final long nodeId) {
		return nodesById.get(nodeId);
	}

	public void undoableChange(final ISceneOperation operation) {
		sceneChangeHandler.begin(operation.getLabel(), true);

		try {
			operation.perform(this);
		} finally {
			sceneChangeHandler.commit();
		}
	}

	public void notUndoableChange(final ISceneOperation operation) {
		sceneChangeHandler.begin(operation.getLabel(), false);

		try {
			operation.perform(this);
		} finally {
			sceneChangeHandler.commit();
		}
	}

	@Override
	public <T> T accept(final ISceneVisitor<T> visitor) {
		return visitor.visitScene(this);
	}

	protected void fireSceneGeometryChangeEvent(final SceneChangeEvent event) {
		sceneHolder.fireSceneGeometryChangeEvent(event);
	}

	public SceneHolder getSceneHolder() {
		return sceneHolder;
	}
}
