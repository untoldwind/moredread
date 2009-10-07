package net.untoldwind.moredread.model.scene;

import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.enums.SelectionMode;
import net.untoldwind.moredread.model.scene.event.ISceneSelectionModeListener;
import net.untoldwind.moredread.model.scene.event.SceneSelectionModeEvent;

public class SceneHolder implements ISceneHolder {
	private final Scene scene;
	private SelectionMode selectionMode;
	private final List<ISceneSelectionModeListener> selectionModeListeners;

	public SceneHolder(final Scene scene) {
		this.scene = scene;
		this.selectionModeListeners = new ArrayList<ISceneSelectionModeListener>();
		this.selectionMode = SelectionMode.OBJECT;
	}

	@Override
	public Scene getScene() {
		return scene;
	}

	public SelectionMode getSelectionMode() {
		return selectionMode;
	}

	public void setSelectionMode(final SelectionMode selectionMode) {
		this.selectionMode = selectionMode;

		fireSceneGeometryChangeEvent(new SceneSelectionModeEvent(selectionMode));
	}

	@Override
	public void addSceneSelectionModeListener(
			final ISceneSelectionModeListener listener) {
		synchronized (selectionModeListeners) {
			selectionModeListeners.add(listener);
		}
	}

	@Override
	public void removeSceneSelectionModeListener(
			final ISceneSelectionModeListener listener) {
		synchronized (selectionModeListeners) {
			selectionModeListeners.remove(listener);
		}
	}

	protected void fireSceneGeometryChangeEvent(
			final SceneSelectionModeEvent event) {
		final ISceneSelectionModeListener listenerArray[];

		synchronized (selectionModeListeners) {
			listenerArray = selectionModeListeners
					.toArray(new ISceneSelectionModeListener[selectionModeListeners
							.size()]);
		}

		for (final ISceneSelectionModeListener listener : listenerArray) {
			listener.sceneSelectionModeChanged(event);
		}
	}

}
