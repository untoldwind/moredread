package net.untoldwind.moredread.model.scene.event;

import net.untoldwind.moredread.model.enums.SelectionMode;

public class SceneSelectionModeEvent {
	private final SelectionMode selectionMode;

	public SceneSelectionModeEvent(final SelectionMode selectionMode) {
		this.selectionMode = selectionMode;
	}

	public SelectionMode getSelectionMode() {
		return selectionMode;
	}

}
