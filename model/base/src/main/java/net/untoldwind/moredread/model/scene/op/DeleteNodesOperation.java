package net.untoldwind.moredread.model.scene.op;

import java.util.Set;

import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.ISceneOperation;
import net.untoldwind.moredread.model.scene.Scene;

public class DeleteNodesOperation implements ISceneOperation {
	private final Set<INode> nodes;

	public DeleteNodesOperation(final Set<INode> nodes) {
		this.nodes = nodes;
	}

	@Override
	public String getLabel() {
		if (nodes.size() == 1) {
			return "Delete " + nodes.iterator().next().getName();
		}
		return "Delete nodes";
	}

	@Override
	public void perform(final Scene scene) {
		for (final INode node : nodes) {
			node.remove();
		}
	}
}
