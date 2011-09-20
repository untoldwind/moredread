package net.untoldwind.moredread.model.scene.change;

import java.util.List;

import net.untoldwind.moredread.model.scene.AbstractNode;
import net.untoldwind.moredread.model.scene.IComposite;
import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.Scene;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class NodeParentChangeCommand extends AbstractOperation implements
		ISceneChangeCommand {
	private final long nodeId;
	private long oldParentId;
	private long newParentId;

	public NodeParentChangeCommand(final AbstractNode node) {
		super("Node " + node.getName() + " parent change");

		nodeId = node.getNodeId();
	}

	@Override
	public String getStageId() {
		return "NodeParent_" + nodeId;
	}

	@Override
	public void updateOriginalValues(final Scene scene) {
		final INode node = scene.getNode(nodeId);

		if (node == null) {
			throw new RuntimeException("Node " + nodeId + " not found in scene");
		}

		oldParentId = node.getParent().getNodeId();
	}

	@Override
	public void updateCurrentValues(final Scene scene) {
		final INode node = scene.getNode(nodeId);

		if (node == null) {
			throw new RuntimeException("Node " + nodeId + " not found in scene");
		}

		newParentId = node.getParent().getNodeId();
	}

	@Override
	public void collectAffectedNodes(final Scene scene, final List<INode> nodes) {
		final INode node = scene.getNode(nodeId);

		if (node == null) {
			throw new RuntimeException("Node " + nodeId + " not found in scene");
		}

		nodes.add(node);
		nodes.add(node.getParent());
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
		final INode node = scene.getNode(nodeId);
		final IComposite newParent = (IComposite) scene.getNode(newParentId);

		scene.getSceneChangeHandler().beginNotUndoable();

		node.setParent(newParent);

		scene.getSceneChangeHandler().commit();

		return Status.OK_STATUS;
	}

	@Override
	public IStatus undo(final IProgressMonitor monitor, final IAdaptable info)
			throws ExecutionException {
		final Scene scene = (Scene) info.getAdapter(Scene.class);
		final INode node = scene.getNode(nodeId);
		final IComposite oldParent = (IComposite) scene.getNode(oldParentId);

		scene.getSceneChangeHandler().beginNotUndoable();

		node.setParent(oldParent);

		scene.getSceneChangeHandler().commit();

		return Status.OK_STATUS;
	}
}
