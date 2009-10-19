package net.untoldwind.moredread.ui.actions;

import net.untoldwind.moredread.model.scene.INode;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class GeneratorNodeActionDelegate implements IObjectActionDelegate {

	INode selectedNode;

	@Override
	public void setActivePart(final IAction action,
			final IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run(final IAction action) {
		final String id = action.getId();

		if ("net.untoldwind.moredread.ui.generatorNode.collapse".equals(id)) {
			System.out.println(">> " + selectedNode);
			System.out.println(">>> " + action);

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

}
