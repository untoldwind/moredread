package net.untoldwind.moredread.model.scene.change;

import java.util.List;

import net.untoldwind.moredread.model.mesh.Mesh;
import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.MeshNode;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.model.state.BinaryStateReader;
import net.untoldwind.moredread.model.state.BinaryStateWriter;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class MeshNodeGeometryChangedCommand extends AbstractOperation implements
		ISceneChangeCommand {
	private final long nodeId;
	private byte[] oldState;
	private byte[] newState;

	public MeshNodeGeometryChangedCommand(final MeshNode meshNode) {
		super("Node " + meshNode.getName() + " geometry change");

		nodeId = meshNode.getNodeId();
	}

	@Override
	public String getStageId() {
		return "MeshNodeGeometry-" + nodeId;
	}

	@Override
	public void updateOriginalValues(final Scene scene) {
		final MeshNode node = (MeshNode) scene.getNode(nodeId);

		if (node == null) {
			throw new RuntimeException("Node " + nodeId + " not found in scene");
		}
		oldState = BinaryStateWriter.toByteArray(node.getGeometry());
	}

	@Override
	public void updateCurrentValues(final Scene scene) {
		final MeshNode node = (MeshNode) scene.getNode(nodeId);

		if (node == null) {
			throw new RuntimeException("Node " + nodeId + " not found in scene");
		}
		newState = BinaryStateWriter.toByteArray(node.getGeometry());
	}

	@Override
	public void collectAffectedNodes(final Scene scene, final List<INode> nodes) {
		final MeshNode node = (MeshNode) scene.getNode(nodeId);

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
		final MeshNode node = (MeshNode) scene.getNode(nodeId);

		if (node == null) {
			return Status.CANCEL_STATUS;
		}
		scene.getSceneChangeHandler().begin(false);

		try {
			node.setMesh((Mesh<?>) BinaryStateReader.fromByteArray(newState));
		} finally {
			scene.getSceneChangeHandler().commit();
		}

		return Status.OK_STATUS;
	}

	@Override
	public IStatus undo(final IProgressMonitor monitor, final IAdaptable info)
			throws ExecutionException {
		final Scene scene = (Scene) info.getAdapter(Scene.class);
		final MeshNode node = (MeshNode) scene.getNode(nodeId);

		if (node == null) {
			return Status.CANCEL_STATUS;
		}
		scene.getSceneChangeHandler().begin(false);

		try {
			node.setMesh((Mesh<?>) BinaryStateReader.fromByteArray(oldState));
		} finally {
			scene.getSceneChangeHandler().commit();
		}

		return Status.OK_STATUS;
	}

}
