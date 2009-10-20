package net.untoldwind.moredread.model.scene;

import net.untoldwind.moredread.model.state.IStateHolder;

public interface INode extends IStateHolder {
	long getNodeId();

	Scene getScene();

	String getName();

	IComposite getParent();

	boolean isSelected();

	BoundingBox getWorldBoundingBox();

	BoundingBox getLocalBoundingBox();

	void markDirty();

	<T> T accept(ISceneVisitor<T> visitor);

	void remove();
}
