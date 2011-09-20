package net.untoldwind.moredread.model.scene.change;

import java.util.List;

import net.untoldwind.moredread.model.mesh.IGeometry;
import net.untoldwind.moredread.model.scene.IGeometryNode;
import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.model.state.BinaryStateReader;
import net.untoldwind.moredread.model.state.BinaryStateWriter;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class NodeGeometryChangedCommand extends AbstractOperation implements
		ISceneChangeCommand {
	private final long nodeId;
	private byte[] oldState;
	private byte[] newState;

	public NodeGeometryChangedCommand(final IGeometryNode<?, ?> meshNode) {
		super("Node " + meshNode.getName() + " geometry change");

		nodeId = meshNode.getNodeId();
	}

	@Override
	public String getStageId() {
		return "MeshNodeGeometry-" + nodeId;
	}

	@Override
	public void updateOriginalValues(final Scene scene) {
		final IGeometryNode<?, ?> node = (IGeometryNode<?, ?>) scene
				.getNode(nodeId);

		if (node == null) {
			throw new RuntimeException("Node " + nodeId + " not found in scene");
		}
		oldState = BinaryStateWriter.toByteArray(node.getGeometry());
	}

	@Override
	public void updateCurrentValues(final Scene scene) {
		final IGeometryNode<?, ?> node = (IGeometryNode<?, ?>) scene
				.getNode(nodeId);

		if (node == null) {
			throw new RuntimeException("Node " + nodeId + " not found in scene");
		}
		newState = BinaryStateWriter.toByteArray(node.getGeometry());
	}

	@Override
	public void collectAffectedNodes(final Scene scene, final List<INode> nodes) {
		final IGeometryNode<?, ?> node = (IGeometryNode<?, ?>) scene
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

	@SuppressWarnings("unchecked")
	@Override
	public IStatus redo(final IProgressMonitor monitor, final IAdaptable info)
			throws ExecutionException {
		final Scene scene = (Scene) info.getAdapter(Scene.class);
		final IGeometryNode<IGeometry<?>, IGeometry<?>> node = (IGeometryNode<IGeometry<?>, IGeometry<?>>) scene
				.getNode(nodeId);

		if (node == null) {
			return Status.CANCEL_STATUS;
		}
		scene.getSceneChangeHandler().beginNotUndoable();

		try {
			node.setGeometry((IGeometry<?>) BinaryStateReader
					.fromByteArray(newState));
		} finally {
			scene.getSceneChangeHandler().commit();
		}

		return Status.OK_STATUS;
	}

	@SuppressWarnings("unchecked")
	@Override
	public IStatus undo(final IProgressMonitor monitor, final IAdaptable info)
			throws ExecutionException {
		final Scene scene = (Scene) info.getAdapter(Scene.class);
		final IGeometryNode<IGeometry<?>, IGeometry<?>> node = (IGeometryNode<IGeometry<?>, IGeometry<?>>) scene
				.getNode(nodeId);

		if (node == null) {
			return Status.CANCEL_STATUS;
		}
		scene.getSceneChangeHandler().beginNotUndoable();

		try {
			node.setGeometry((IGeometry<?>) BinaryStateReader
					.fromByteArray(oldState));
		} finally {
			scene.getSceneChangeHandler().commit();
		}

		return Status.OK_STATUS;
	}
}
