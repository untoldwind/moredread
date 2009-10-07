package net.untoldwind.moredread.model.scene;

import net.untoldwind.moredread.model.enums.SelectionMode;
import net.untoldwind.moredread.model.scene.event.ISceneSelectionModeListener;

public interface ISceneHolder {
	String NODE_USERDATA_KEY = "net.untoldwind.model.node";

	Scene getScene();

	SelectionMode getSelectionMode();

	void setSelectionMode(SelectionMode selectionMode);

	void addSceneSelectionModeListener(ISceneSelectionModeListener listener);

	void removeSceneSelectionModeListener(ISceneSelectionModeListener listener);
}
