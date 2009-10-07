package net.untoldwind.moredread.model.scene.event;

import java.util.Arrays;
import java.util.Collection;

import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.Scene;

public class SceneGeometryChangeEvent {
	private final Scene scene;
	private final Collection<? extends INode> changeNodes;

	public SceneGeometryChangeEvent(final Scene scene, final INode[] nodes) {
		this.scene = scene;
		this.changeNodes = Arrays.asList(nodes);
	}

	public SceneGeometryChangeEvent(final Scene scene,
			final Collection<? extends INode> changeNodes) {
		this.scene = scene;
		this.changeNodes = changeNodes;
	}

	public Scene getScene() {
		return scene;
	}

	public Collection<? extends INode> getChangeNodes() {
		return changeNodes;
	}

}
