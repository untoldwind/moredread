package net.untoldwind.moredread.ui.actions;

import java.lang.reflect.InvocationTargetException;

import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.ui.MoreDreadUI;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IAdvancedUndoableOperation2;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IOperationHistoryListener;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.OperationHistoryEvent;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public abstract class GlobalOperationHistoryAction extends Action implements
		IAdaptable {
	private static final int MAX_LABEL_LENGTH = 32;

	private class HistoryListener implements IOperationHistoryListener {
		public void historyNotification(final OperationHistoryEvent event) {
			if (workbenchWindow == null) {
				return;
			}

			final Display display = workbenchWindow.getWorkbench().getDisplay();
			if (display == null) {
				return;
			}

			switch (event.getEventType()) {
			case OperationHistoryEvent.OPERATION_ADDED:
			case OperationHistoryEvent.OPERATION_REMOVED:
			case OperationHistoryEvent.UNDONE:
			case OperationHistoryEvent.REDONE:
				if (event.getOperation().hasContext(undoContext)) {
					display.asyncExec(new Runnable() {
						public void run() {
							update();
						}
					});
				}
				break;
			case OperationHistoryEvent.OPERATION_NOT_OK:
				if (event.getOperation().hasContext(undoContext)) {
					display.asyncExec(new Runnable() {
						public void run() {
							update();
						}
					});
				}
				break;
			case OperationHistoryEvent.OPERATION_CHANGED:
				if (event.getOperation() == getOperation()) {
					display.asyncExec(new Runnable() {
						public void run() {
							update();
						}
					});
				}
				break;
			}
		}
	}

	private final IOperationHistoryListener historyListener = new HistoryListener();

	private final IWorkbenchWindow workbenchWindow;
	private final IUndoContext undoContext = IOperationHistory.GLOBAL_UNDO_CONTEXT;

	protected GlobalOperationHistoryAction(final IWorkbenchWindow window) {
		this.workbenchWindow = window;

		getHistory().addOperationHistoryListener(historyListener);
		update();
	}

	/*
	 * Flush the history associated with this action.
	 */
	abstract void flush();

	/**
	 * The undo and redo subclasses should implement this.
	 * 
	 * @return - a boolean indicating enablement state
	 */
	abstract boolean shouldBeEnabled();

	abstract IStatus runCommand(IProgressMonitor pm) throws ExecutionException;

	/*
	 * Return the current operation.
	 */
	abstract IUndoableOperation getOperation();

	/*
	 * Return the string describing the command, including the binding for the
	 * operation label.
	 */
	abstract String getCommandString();

	/*
	 * Return the string describing the command for a tooltip, including the
	 * binding for the operation label.
	 */
	abstract String getTooltipString();

	/*
	 * Return the simple string describing the command, with no binding to any
	 * operation.
	 */
	abstract String getSimpleCommandString();

	/*
	 * Return the simple string describing the tooltip, with no binding to any
	 * operation.
	 */
	abstract String getSimpleTooltipString();

	/*
	 * Get the undo context that should be used.
	 */
	final IUndoContext getUndoContext() {
		return undoContext;
	}

	/*
	 * Return the operation history we are using.
	 */
	IOperationHistory getHistory() {
		if (PlatformUI.getWorkbench() == null) {
			return null;
		}

		return PlatformUI.getWorkbench().getOperationSupport()
				.getOperationHistory();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.actions.ActionFactory.IWorkbenchAction#run()
	 */
	@Override
	public final void run() {
		final IRunnableWithProgress runnable = new IRunnableWithProgress() {
			public void run(final IProgressMonitor pm)
					throws InvocationTargetException {
				try {
					runCommand(pm);
				} catch (final ExecutionException e) {
					throw new InvocationTargetException(e);
				}
			}
		};
		try {
			boolean runInBackground = false;
			if (getOperation() instanceof IAdvancedUndoableOperation2) {
				runInBackground = ((IAdvancedUndoableOperation2) getOperation())
						.runInBackground();
			}
			workbenchWindow.run(runInBackground, false, runnable);
		} catch (final InvocationTargetException e) {
			final Throwable t = e.getTargetException();
			if (t == null) {
				reportException(e);
			} else {
				reportException(t);
			}
		} catch (final InterruptedException e) {
			// Operation was cancelled and acknowledged by runnable with this
			// exception.
			// Do nothing.
		} catch (final OperationCanceledException e) {
			// the operation was cancelled. Do nothing.
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	public Object getAdapter(@SuppressWarnings("rawtypes") final Class adapter) {
		if (adapter.equals(IUndoContext.class)) {
			return undoContext;
		} else if (adapter.equals(Scene.class)) {
			return MoreDreadUI.getDefault().getSceneHolder().getScene();
		}
		return null;
	}

	/*
	 * Report the specified exception to the log and to the user.
	 */
	final void reportException(final Throwable t) {
		MoreDreadUI.getDefault().log(t);
	}

	/**
	 * Update enabling and labels according to the current status of the
	 * operation history.
	 */
	public void update() {
		final boolean enabled = shouldBeEnabled();
		String text, tooltipText;
		if (enabled) {
			tooltipText = NLS.bind(getTooltipString(), getOperation()
					.getLabel());
			text = NLS.bind(getCommandString(), shortenText(getOperation()
					.getLabel()));
		} else {
			tooltipText = NLS.bind("Can''t {0}", getSimpleTooltipString());
			text = getSimpleCommandString();
		}
		setText(text);
		setToolTipText(tooltipText);
		setEnabled(enabled);
	}

	/*
	 * Shorten the specified command label if it is too long
	 */
	private String shortenText(final String message) {
		final int length = message.length();
		if (length > MAX_LABEL_LENGTH) {
			final StringBuffer result = new StringBuffer();
			final int mid = MAX_LABEL_LENGTH / 2;
			result.append(message.substring(0, mid));
			result.append("..."); //$NON-NLS-1$
			result.append(message.substring(length - mid));
			return result.toString();
		}
		return message;
	}
}
