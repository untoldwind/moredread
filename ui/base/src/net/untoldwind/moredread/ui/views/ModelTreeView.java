package net.untoldwind.moredread.ui.views;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.event.ISceneSelectionChangeListener;
import net.untoldwind.moredread.model.scene.event.SceneSelectionChangeEvent;
import net.untoldwind.moredread.ui.MoreDreadUI;
import net.untoldwind.moredread.ui.provider.NodeLabelProvider;
import net.untoldwind.moredread.ui.provider.SceneContentProvider;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class ModelTreeView extends ViewPart implements
		ISceneSelectionChangeListener {

	public static final String ID = "net.untoldwind.moredread.ui.modelTreeView";

	TreeViewer modelViewer;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createPartControl(final Composite parent) {
		modelViewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.BORDER);
		modelViewer.setContentProvider(new SceneContentProvider());
		modelViewer.setLabelProvider(new NodeLabelProvider());
		modelViewer.setInput(MoreDreadUI.getDefault().getSceneHolder());

		modelViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					@SuppressWarnings("unchecked")
					public void selectionChanged(
							final SelectionChangedEvent event) {
						final ISelection selection = event.getSelection();

						if (selection != null
								&& selection instanceof IStructuredSelection) {
							final Set<INode> selectedNodes = new HashSet<INode>();

							selectedNodes
									.addAll(((IStructuredSelection) selection)
											.toList());

							MoreDreadUI.getDefault().getSceneHolder()
									.getScene().getSceneSelection()
									.setSelectedNodes(selectedNodes);
						}
					}
				});

		getSite().setSelectionProvider(modelViewer);

		MoreDreadUI.getDefault().getSceneHolder().getScene()
				.getSceneSelection().addSceneSelectionChangeListener(this);
	}

	public void sceneSelectionChanged(final SceneSelectionChangeEvent event) {
		modelViewer.getControl().getDisplay().asyncExec(new Runnable() {
			public void run() {
				final List<INode> selectedNodes = new ArrayList<INode>();

				selectedNodes.addAll(event.getSelectedNodes());

				modelViewer
						.setSelection(new StructuredSelection(selectedNodes));

			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFocus() {
		modelViewer.getControl().setFocus();
	}

}
