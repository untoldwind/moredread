package net.untoldwind.moredread.model.scene;

public interface INode {
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
