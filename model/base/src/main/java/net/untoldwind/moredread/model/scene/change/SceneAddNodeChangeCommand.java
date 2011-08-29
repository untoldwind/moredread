package net.untoldwind.moredread.model.scene.change;

import java.util.List;

import net.untoldwind.moredread.model.scene.AbstractSpatialComposite;
import net.untoldwind.moredread.model.scene.IComposite;
import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.Scene;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class SceneAddNodeChangeCommand extends AbstractOperation implements
		ISceneChangeCommand {

	private final long parentNodeId;
	private final long childNodeId;

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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub

	}

	@Override
	public String getStageId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateCurrentValues(final Scene scene) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateOriginalValues(final Scene scene) {
		// TODO Auto-generated method stub

	}

}
