package net.untoldwind.moredread.model.scene.change;

import java.util.Arrays;

import net.untoldwind.moredread.model.mesh.Mesh;
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
	private final byte[] oldState;
	private byte[] newState;

	public MeshNodeGeometryChangedCommand(final MeshNode meshNode,
			final Mesh<?> mesh) {
		super("Node " + meshNode.getName() + " geometry change");

		nodeId = meshNode.getNodeId();
		oldState = BinaryStateWriter.toByteArray(mesh);
	}

	@Override
	public String getStageId() {
		return "MeshNodeGeometry-" + nodeId;
	}

	@Override
	public void updateCurrentValues(final Scene scene) {
		final MeshNode node = (MeshNode) scene.getNode(nodeId);

		if (node == null) {
			throw new RuntimeException("Node " + nodeId + " not found in scene");
		}
		newState = BinaryStateWriter.toByteArray(node
				.getEditableGeometry(false));
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
			node.setMesh(BinaryStateReader.fromByteArray(newState,
					new Mesh.MeshInstanceCreator()));
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
		final MeshNode node = (MeshNode) scene.getNode(nodeId);

		if (node == null) {
			return Status.CANCEL_STATUS;
		}
		scene.getSceneChangeHandler().begin(false);

		try {
			node.setMesh(BinaryStateReader.fromByteArray(oldState,
					new Mesh.MeshInstanceCreator()));
		} finally {
			scene.getSceneChangeHandler().commit();
		}

		scene.markNodeGeometryChanged(Arrays.asList(node));

		return Status.OK_STATUS;
	}

}
