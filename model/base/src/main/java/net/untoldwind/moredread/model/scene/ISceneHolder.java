package net.untoldwind.moredread.model.scene;

import net.untoldwind.moredread.model.enums.SelectionMode;
import net.untoldwind.moredread.model.renderer.INodeRendererAdapter;
import net.untoldwind.moredread.model.scene.event.ISceneChangeListener;
import net.untoldwind.moredread.model.scene.event.ISceneSelectionChangeListener;
import net.untoldwind.moredread.model.scene.event.ISceneSelectionModeListener;

import com.jme.intersection.PickResults;
import com.jme.math.Ray;
import com.jme.renderer.Renderer;

public interface ISceneHolder {
	String NODE_USERDATA_KEY = "net.untoldwind.model.node";

	Scene createScene();

	Scene getScene();

	SelectionMode getSelectionMode();

	void setSelectionMode(SelectionMode selectionMode);

	void addSceneChangeListener(final ISceneChangeListener listener);

	void removeSceneChangeListener(final ISceneChangeListener listener);

	void addSceneSelectionModeListener(ISceneSelectionModeListener listener);

	void removeSceneSelectionModeListener(ISceneSelectionModeListener listener);

	void addSceneSelectionChangeListener(ISceneSelectionChangeListener listener);

	void removeSceneSelectionChangeListener(
			ISceneSelectionChangeListener listener);

	void render(final Renderer renderer,
			final INodeRendererAdapter rendererAdapter);

	void findPick(final Ray toTest, final PickResults results);
}
