package net.untoldwind.moredread.ui.actions;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchWindow;

public class GlobalUndoAction extends GlobalOperationHistoryAction {
	public GlobalUndoAction(final IWorkbenchWindow window) {
		super(window);
		setId("undo");
		setActionDefinitionId(IWorkbenchCommandConstants.EDIT_UNDO);
		final ISharedImages sharedImages = window.getWorkbench()
				.getSharedImages();
		setImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_UNDO));
		setDisabledImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_UNDO_DISABLED));
	}

	@Override
	void flush() {
		getHistory().dispose(getUndoContext(), true, false, false);
	}

	@Override
	String getCommandString() {
		return "&Undo {0}";
	}

	@Override
	String getTooltipString() {
		return "Undo {0}";
	}

	@Override
	String getSimpleCommandString() {
		return "&Undo";
	}

	@Override
	String getSimpleTooltipString() {
		return "Undo";
	}

	@Override
	IUndoableOperation getOperation() {
		return getHistory().getUndoOperation(getUndoContext());

	}

	@Override
	IStatus runCommand(final IProgressMonitor pm) throws ExecutionException {
		return getHistory().undo(getUndoContext(), pm, this);
	}

	@Override
	boolean shouldBeEnabled() {
		return getHistory().canUndo(getUndoContext());
	}

}
