package net.untoldwind.moredread.ui.actions;

import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.ui.dialogs.DumpNodeDialog;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;

public class DumpNodeActionDelegate implements IObjectActionDelegate {
	IWorkbenchWindow workbenchWindow;
	INode selectedNode;

	@Override
	public void run(final IAction action) {
		final String id = action.getId();

		if ("net.untoldwind.moredread.ui.node.dump".equals(id)) {
			if (selectedNode != null && workbenchWindow != null) {
				final DumpNodeDialog dialog = new DumpNodeDialog(
						workbenchWindow.getShell(), selectedNode);

				dialog.open();
			}
		}
	}

	@Override
	public void selectionChanged(final IAction action,
			final ISelection selection) {
		selectedNode = null;

		if (selection instanceof IStructuredSelection) {
			final Object sel = ((IStructuredSelection) selection)
					.getFirstElement();

			if (sel != null && sel instanceof INode) {
				selectedNode = (INode) sel;
			}
		}
	}

	@Override
	public void setActivePart(final IAction action,
			final IWorkbenchPart targetPart) {
		workbenchWindow = targetPart.getSite().getWorkbenchWindow();
	}

}
