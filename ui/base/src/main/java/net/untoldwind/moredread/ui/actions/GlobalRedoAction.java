package net.untoldwind.moredread.ui.actions;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchWindow;

public class GlobalRedoAction extends GlobalOperationHistoryAction {
	public GlobalRedoAction(final IWorkbenchWindow window) {
		super(window);
		setId("redo");
		setActionDefinitionId(IWorkbenchCommandConstants.EDIT_REDO);
		final ISharedImages sharedImages = window.getWorkbench()
				.getSharedImages();
		setImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_REDO));
		setDisabledImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_REDO_DISABLED));
	}

	@Override
	void flush() {
		getHistory().dispose(getUndoContext(), false, true, false);
	}

	@Override
	String getCommandString() {
		return "&Redo {0}";
	}

	@Override
	String getTooltipString() {
		return "Redo {0}";
	}

	@Override
	String getSimpleCommandString() {
		return "&Redo";
	}

	@Override
	String getSimpleTooltipString() {
		return "Redo";
	}

	@Override
	IUndoableOperation getOperation() {
		return getHistory().getRedoOperation(getUndoContext());

	}

	@Override
	IStatus runCommand(final IProgressMonitor pm) throws ExecutionException {
		return getHistory().redo(getUndoContext(), pm, this);
	}

	@Override
	boolean shouldBeEnabled() {
		return getHistory().canRedo(getUndoContext());
	}

}
