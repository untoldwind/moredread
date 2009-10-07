package net.untoldwind.moredread.model.scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.untoldwind.moredread.model.scene.event.ISceneGeometryChangeListener;
import net.untoldwind.moredread.model.scene.event.SceneGeometryChangeEvent;

public class Scene extends Group {
	private final SceneSelection sceneSelection;
	private final SceneChangeHandler sceneChangeHandler;
	private final SceneMetadata sceneMetadata;
	private final List<ISceneGeometryChangeListener> geometryListeners;

	private final Map<Long, INode> nodesById;

	public Scene() {
		super(null, "Scene");

		sceneSelection = new SceneSelection(this);
		sceneChangeHandler = new SceneChangeHandler(this);
		sceneMetadata = new SceneMetadata();
		geometryListeners = new ArrayList<ISceneGeometryChangeListener>();
		nodesById = new HashMap<Long, INode>();
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

	public void addSceneGeometryChangeListener(
			final ISceneGeometryChangeListener listener) {
		synchronized (geometryListeners) {
			geometryListeners.add(listener);
		}
	}

	public void removeSceneGeometryChangeListener(
			final ISceneGeometryChangeListener listener) {
		synchronized (geometryListeners) {
			geometryListeners.remove(listener);
		}
	}

	public INode getNode(final long nodeId) {
		return nodesById.get(nodeId);
	}

	protected void fireSceneGeometryChangeEvent(
			final SceneGeometryChangeEvent event) {
		final ISceneGeometryChangeListener listenerArray[];

		synchronized (geometryListeners) {
			listenerArray = geometryListeners
					.toArray(new ISceneGeometryChangeListener[geometryListeners
							.size()]);
		}

		for (final ISceneGeometryChangeListener listener : listenerArray) {
			listener.sceneGeometryChanged(event);
		}

	}
}
