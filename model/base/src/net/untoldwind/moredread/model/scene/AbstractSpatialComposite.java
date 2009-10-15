package net.untoldwind.moredread.model.scene;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSpatialComposite<ChildT extends INode> extends
		AbstractSpatialNode implements IComposite {
	protected final List<ChildT> children = new ArrayList<ChildT>();

	protected AbstractSpatialComposite(
			final AbstractSpatialComposite<? extends INode> parent,
			final String name) {
		super(parent, name);
	}

	@SuppressWarnings("unchecked")
	void addNode(final AbstractSpatialNode node) {
		children.add((ChildT) node);
	}

	@Override
	public List<? extends INode> getChildren() {
		return children;
	}
}
