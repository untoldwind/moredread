package net.untoldwind.moredread.model.scene;

import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.scene.change.SceneAddNodeChangeCommand;

public abstract class AbstractSpatialComposite<ChildT extends INode> extends
		AbstractSpatialNode implements IComposite {
	protected final List<ChildT> children = new ArrayList<ChildT>();

	protected AbstractSpatialComposite(
			final AbstractSpatialComposite<? extends INode> parent,
			final String name) {
		super(parent, name);
	}

	@SuppressWarnings("unchecked")
	void addChild(final AbstractSpatialNode node) {
		scene.getSceneChangeHandler().registerCommand(
				new SceneAddNodeChangeCommand(this, node));

		children.add((ChildT) node);
	}

	void removeChild(final AbstractSpatialNode node) {
		children.remove(node);
	}

	@Override
	public List<? extends INode> getChildren() {
		return children;
	}
}
