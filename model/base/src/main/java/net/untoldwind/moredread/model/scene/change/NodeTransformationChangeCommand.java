package net.untoldwind.moredread.model.scene.change;

import java.util.List;

import net.untoldwind.moredread.model.math.Quaternion;
import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.scene.AbstractSpatialNode;
import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.Scene;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class NodeTransformationChangeCommand extends AbstractOperation
		implements ISceneChangeCommand {
	private final long nodeId;
	private Quaternion oldLocalRotation;
	private Vector3 oldLocalTranslation;
	private Vector3 oldLocalScale;
	private Quaternion newLocalRotation;
	private Vector3 newLocalTranslation;
	private Vector3 newLocalScale;

	public NodeTransformationChangeCommand(final AbstractSpatialNode node) {
		super("Node " + node.getName() + " transformation change");

		nodeId = node.getNodeId();
	}

	@Override
	public String getStageId() {
		return "NodeTransformation-" + nodeId;
	}

	@Override
	public void updateOriginalValues(final Scene scene) {
		final AbstractSpatialNode node = (AbstractSpatialNode) scene
				.getNode(nodeId);

		if (node == null) {
			throw new RuntimeException("Node " + nodeId + " not found in scene");
		}
		oldLocalRotation = new Quaternion(node.getLocalRotation());
		oldLocalTranslation = new Vector3(node.getLocalTranslation());
		oldLocalScale = new Vector3(node.getLocalScale());
	}

	@Override
	public void updateCurrentValues(final Scene scene) {
		final AbstractSpatialNode node = (AbstractSpatialNode) scene
				.getNode(nodeId);

		if (node == null) {
			throw new RuntimeException("Node " + nodeId + " not found in scene");
		}
		newLocalRotation = new Quaternion(node.getLocalRotation());
		newLocalTranslation = new Vector3(node.getLocalTranslation());
		newLocalScale = new Vector3(node.getLocalScale());
	}

	@Override
	public void collectAffectedNodes(final Scene scene, final List<INode> nodes) {
		final AbstractSpatialNode node = (AbstractSpatialNode) scene
				.getNode(nodeId);

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
		final AbstractSpatialNode node = (AbstractSpatialNode) scene
				.getNode(nodeId);

		if (node == null) {
			return Status.CANCEL_STATUS;
		}

		scene.getSceneChangeHandler().beginNotUndoable();

		try {
			node.setLocalRotation(newLocalRotation);
			node.setLocalTranslation(newLocalTranslation);
			node.setLocalScale(newLocalScale);
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
			node.setLocalRotation(oldLocalRotation);
			node.setLocalTranslation(oldLocalTranslation);
			node.setLocalScale(oldLocalScale);
		} finally {
			scene.getSceneChangeHandler().commit();
		}

		return Status.OK_STATUS;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("NodeTransformationChangeCommand [newLocalRotation=");
		builder.append(newLocalRotation);
		builder.append(", newLocalScale=");
		builder.append(newLocalScale);
		builder.append(", newLocalTranslation=");
		builder.append(newLocalTranslation);
		builder.append(", nodeId=");
		builder.append(nodeId);
		builder.append(", oldLocalRotation=");
		builder.append(oldLocalRotation);
		builder.append(", oldLocalScale=");
		builder.append(oldLocalScale);
		builder.append(", oldLocalTranslation=");
		builder.append(oldLocalTranslation);
		builder.append("]");
		return builder.toString();
	}

}
