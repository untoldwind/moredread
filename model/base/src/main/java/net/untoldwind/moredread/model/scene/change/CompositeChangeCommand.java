package net.untoldwind.moredread.model.scene.change;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.Scene;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;

public class CompositeChangeCommand extends AbstractOperation implements
		ISceneChangeCommand {
	private final LinkedList<ISceneChangeCommand> commands = new LinkedList<ISceneChangeCommand>();

	public CompositeChangeCommand(final String label,
			final Collection<ISceneChangeCommand> commands) {
		super(label != null ? label : "Multiple changes");
		this.commands.addAll(commands);
	}

	@Override
	public String getStageId() {
		return "Composite";
	}

	@Override
	public void updateOriginalValues(final Scene scene) {
		for (final ISceneChangeCommand cmd : commands) {
			cmd.updateOriginalValues(scene);
		}
	}

	@Override
	public void updateCurrentValues(final Scene scene) {
		for (final ISceneChangeCommand cmd : commands) {
			cmd.updateCurrentValues(scene);
		}
	}

	@Override
	public void collectAffectedNodes(final Scene scene, final List<INode> nodes) {
		for (final ISceneChangeCommand cmd : commands) {
			cmd.collectAffectedNodes(scene, nodes);
		}
	}

	@Override
	public IStatus execute(final IProgressMonitor monitor, final IAdaptable info)
			throws ExecutionException {
		try {
			monitor.beginTask("Composite", commands.size());

			for (final ISceneChangeCommand cmd : commands) {
				cmd.execute(new SubProgressMonitor(monitor, 1), info);
			}
		} finally {
			monitor.done();
		}
		return Status.OK_STATUS;
	}

	@Override
	public IStatus redo(final IProgressMonitor monitor, final IAdaptable info)
			throws ExecutionException {
		try {
			monitor.beginTask("Composite", commands.size());

			for (final ISceneChangeCommand cmd : commands) {
				cmd.redo(new SubProgressMonitor(monitor, 1), info);
			}
		} finally {
			monitor.done();
		}
		return Status.OK_STATUS;
	}

	@Override
	public IStatus undo(final IProgressMonitor monitor, final IAdaptable info)
			throws ExecutionException {
		try {
			monitor.beginTask("Composite", commands.size());

			for (final Iterator<ISceneChangeCommand> it = commands
					.descendingIterator(); it.hasNext();) {
				it.next().undo(new SubProgressMonitor(monitor, 1), info);
			}
		} finally {
			monitor.done();
		}
		return Status.OK_STATUS;
	}

}
