package net.untoldwind.moredread.model.scene.change;

import java.util.List;

import net.untoldwind.moredread.model.scene.AbstractNode;
import net.untoldwind.moredread.model.scene.AbstractSpatialNode;
import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.Scene;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class NodeNameChangeCommand extends AbstractOperation implements
		ISceneChangeCommand {
	private final long nodeId;
	private String oldName;
	private String newName;

	public NodeNameChangeCommand(final AbstractNode node) {
		super("Node " + node.getName() + " name change");

		nodeId = node.getNodeId();
	}

	@Override
	public String getStageId() {
		return "NodeName-" + nodeId;
	}

	@Override
	public void updateOriginalValues(final Scene scene) {
		final AbstractSpatialNode node = (AbstractSpatialNode) scene
				.getNode(nodeId);

		if (node == null) {
			throw new RuntimeException("Node " + nodeId + " not found in scene");
		}
		oldName = node.getName();
	}

	@Override
	public void updateCurrentValues(final Scene scene) {
		final INode node = scene.getNode(nodeId);

		if (node == null) {
			throw new RuntimeException("Node " + nodeId + " not found in scene");
		}
		newName = node.getName();
	}

	@Override
	public void collectAffectedNodes(final Scene scene, final List<INode> nodes) {
		final INode node = scene.getNode(nodeId);

		if (node == null) {
			throw new RuntimeException("Node " + nodeId + " not found in scene");
		}

		nodes.add(node);
	}

	@Override
	public IStatus execute(final IProgressMonitor monitor, final IAdaptable info)
			throws ExecutionException {
		return Status.OK_STATUS;
	}

	@Override
	public IStatus redo(final IProgressMonitor monitor, final IAdaptable info)
			throws ExecutionException {
		final Scene scene = (Scene) info.getAdapter(Scene.class);
		final AbstractNode node = (AbstractNode) scene.getNode(nodeId);

		if (node == null) {
			return Status.CANCEL_STATUS;
		}

		scene.getSceneChangeHandler().beginNotUndoable();

		try {
			node.setName(oldName);
		} finally {
			scene.getSceneChangeHandler().commit();
		}

		return Status.OK_STATUS;
	}

	@Override
	public IStatus undo(final IProgressMonitor monitor, final IAdaptable info)
			throws ExecutionException {
		final Scene scene = (Scene) info.getAdapter(Scene.class);
		final AbstractSpatialNode node = (AbstractSpatialNode) scene
				.getNode(nodeId);

		if (node == null) {
			return Status.CANCEL_STATUS;
		}

		scene.getSceneChangeHandler().beginNotUndoable();

		try {
			node.setName(oldName);
		} finally {
			scene.getSceneChangeHandler().commit();
		}

		return Status.OK_STATUS;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("NodeNameChangeCommand [newName=");
		builder.append(newName);
		builder.append(", nodeId=");
		builder.append(nodeId);
		builder.append(", oldName=");
		builder.append(oldName);
		builder.append("]");
		return builder.toString();
	}

}
