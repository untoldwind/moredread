package net.untoldwind.moredread.model.scene.change;

import java.lang.reflect.Constructor;
import java.util.List;

import net.untoldwind.moredread.model.scene.AbstractSpatialComposite;
import net.untoldwind.moredread.model.scene.IComposite;
import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.model.state.BinaryStateReader;
import net.untoldwind.moredread.model.state.BinaryStateWriter;
import net.untoldwind.moredread.model.state.IStateReader.IInstanceCreator;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class SceneAddNodeChangeCommand extends AbstractOperation implements
		ISceneChangeCommand {

	private final long parentNodeId;
	private long childNodeId;
	private byte[] nodeState;

	public SceneAddNodeChangeCommand(final AbstractSpatialComposite<?> parent,
			final INode child) {
		super("Add node " + child.getName());

		parentNodeId = parent.getNodeId();
		childNodeId = child.getNodeId();
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
		final IComposite parentNode = (IComposite) scene.getNode(parentNodeId);

		scene.getSceneChangeHandler().begin(false);

		BinaryStateReader.fromByteArray(nodeState,
				new IInstanceCreator<INode>() {
					@Override
					public INode createInstance(final Class<INode> clazz) {
						try {
							final Constructor<INode> constructor = clazz
									.getDeclaredConstructor(AbstractSpatialComposite.class);

							constructor.setAccessible(true);
							final INode node = constructor
									.newInstance(parentNode);

							childNodeId = node.getNodeId();

							return node;
						} catch (final Exception e) {
							throw new RuntimeException(e);
						}
					}
				});
		scene.getSceneChangeHandler().commit();

		return Status.OK_STATUS;
	}

	@Override
	public IStatus undo(final IProgressMonitor monitor, final IAdaptable info)
			throws ExecutionException {
		final Scene scene = (Scene) info.getAdapter(Scene.class);
		final IComposite parentNode = (IComposite) scene.getNode(parentNodeId);
		final INode childNode = scene.getNode(childNodeId);

		scene.getSceneChangeHandler().begin(false);

		try {
			parentNode.removeChild(childNode);
		} finally {
			scene.getSceneChangeHandler().commit();
		}

		return Status.OK_STATUS;
	}

	@Override
	public void collectAffectedNodes(final Scene scene, final List<INode> nodes) {
		final INode node = scene.getNode(parentNodeId);

		if (node == null) {
			throw new RuntimeException("Node " + parentNodeId
					+ " not found in scene");
		}

		nodes.add(node);
	}

	@Override
	public String getStageId() {
		return "Add_" + parentNodeId + "_" + childNodeId;
	}

	@Override
	public void updateCurrentValues(final Scene scene) {
		final INode node = scene.getNode(childNodeId);

		if (node == null) {
			throw new RuntimeException("Node " + parentNodeId
					+ " not found in scene");
		}

		nodeState = BinaryStateWriter.toByteArray(node);
	}

	@Override
	public void updateOriginalValues(final Scene scene) {
	}

}
