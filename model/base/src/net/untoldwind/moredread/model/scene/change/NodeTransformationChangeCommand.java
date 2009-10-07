package net.untoldwind.moredread.model.scene.change;

import java.util.Arrays;

import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.model.scene.SpatialNode;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

public class NodeTransformationChangeCommand extends AbstractOperation
		implements ISceneChangeCommand {
	private final long nodeId;
	private final Quaternion oldLocalRotation;
	private final Vector3f oldLocalTranslation;
	private final Vector3f oldLocalScale;
	private Quaternion newLocalRotation;
	private Vector3f newLocalTranslation;
	private Vector3f newLocalScale;

	public NodeTransformationChangeCommand(final SpatialNode node) {
		super("Node " + node.getName() + " transformation change");
		nodeId = node.getNodeId();
		oldLocalRotation = new Quaternion(node.getLocalRotation());
		oldLocalTranslation = new Vector3f(node.getLocalTranslation());
		oldLocalScale = new Vector3f(node.getLocalScale());
	}

	@Override
	public String getStageId() {
		return "NodeTransformation-" + nodeId;
	}

	@Override
	public void updateCurrentValues(final Scene scene) {
		final SpatialNode node = (SpatialNode) scene.getNode(nodeId);

		if (node == null) {
			throw new RuntimeException("Node " + nodeId + " not found in scene");
		}
		newLocalRotation = new Quaternion(node.getLocalRotation());
		newLocalTranslation = new Vector3f(node.getLocalTranslation());
		newLocalScale = new Vector3f(node.getLocalScale());
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
		final SpatialNode node = (SpatialNode) scene.getNode(nodeId);

		if (node == null) {
			return Status.CANCEL_STATUS;
		}

		scene.getSceneChangeHandler().begin(false);

		try {
			node.setLocalRotation(newLocalRotation);
			node.setLocalTranslation(newLocalTranslation);
			node.setLocalScale(newLocalScale);
		} finally {
			scene.getSceneChangeHandler().commit();
		}

		scene.markNodeGeometryChanged(Arrays.asList(node));

		return Status.OK_STATUS;
	}

	@Override
	public IStatus undo(final IProgressMonitor monitor, final IAdaptable info)
			throws ExecutionException {
		final Scene scene = (Scene) info.getAdapter(Scene.class);
		final SpatialNode node = (SpatialNode) scene.getNode(nodeId);

		if (node == null) {
			return Status.CANCEL_STATUS;
		}

		scene.getSceneChangeHandler().begin(false);

		try {
			node.setLocalRotation(oldLocalRotation);
			node.setLocalTranslation(oldLocalTranslation);
			node.setLocalScale(oldLocalScale);
		} finally {
			scene.getSceneChangeHandler().commit();
		}

		scene.markNodeGeometryChanged(Arrays.asList(node));

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
