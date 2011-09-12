package net.untoldwind.moredread.model.scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.untoldwind.moredread.model.scene.event.ISceneChangeListener;
import net.untoldwind.moredread.model.scene.event.SceneChangeEvent;

public class Scene extends Group {
	private final SceneSelection sceneSelection;
	private final SceneChangeHandler sceneChangeHandler;
	private final SceneMetadata sceneMetadata;
	private final List<ISceneChangeListener> geometryListeners;

	private final Map<Long, INode> nodesById;

	public Scene() {
		super(null, "Scene");

		sceneSelection = new SceneSelection(this);
		sceneChangeHandler = new SceneChangeHandler(this);
		sceneMetadata = new SceneMetadata();
		geometryListeners = new ArrayList<ISceneChangeListener>();
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

	public void addSceneChangeListener(final ISceneChangeListener listener) {
		synchronized (geometryListeners) {
			geometryListeners.add(listener);
		}
	}

	public void removeSceneChangeListener(final ISceneChangeListener listener) {
		synchronized (geometryListeners) {
			geometryListeners.remove(listener);
		}
	}

	public INode getNode(final long nodeId) {
		return nodesById.get(nodeId);
	}

	public void undoableChange(final ISceneOperation operation) {
		sceneChangeHandler.begin(true);

		try {
			operation.perform(this);
		} finally {
			sceneChangeHandler.commit();
		}
	}

	public void notUndoableChange(final ISceneOperation operation) {
		sceneChangeHandler.begin(false);

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
		final ISceneChangeListener listenerArray[];

		synchronized (geometryListeners) {
			listenerArray = geometryListeners
					.toArray(new ISceneChangeListener[geometryListeners.size()]);
		}

		for (final ISceneChangeListener listener : listenerArray) {
			listener.sceneChanged(event);
		}

	}
}
