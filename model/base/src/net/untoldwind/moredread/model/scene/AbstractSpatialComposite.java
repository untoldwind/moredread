package net.untoldwind.moredread.model.scene;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSpatialComposite<ParentT extends AbstractSpatialComposite<?, ? extends INode>, ChildT extends INode>
		extends AbstractSpatialNode<ParentT> implements IComposite {

	protected final List<ChildT> children = new ArrayList<ChildT>();

	protected AbstractSpatialComposite(final ParentT parent, final String name) {
		super(parent, name);
	}

	void addNode(final ChildT node) {
		children.add(node);
	}

	public List<? extends INode> getChildren() {
		return children;
	}
}
