package net.untoldwind.moredread.ui.views;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.event.ISceneChangeListener;
import net.untoldwind.moredread.model.scene.event.ISceneSelectionChangeListener;
import net.untoldwind.moredread.model.scene.event.SceneChangeEvent;
import net.untoldwind.moredread.model.scene.event.SceneSelectionChangeEvent;
import net.untoldwind.moredread.ui.MoreDreadUI;
import net.untoldwind.moredread.ui.properties.NodePropertySheetContributor;
import net.untoldwind.moredread.ui.provider.NodeLabelProvider;
import net.untoldwind.moredread.ui.provider.SceneContentProvider;

import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class ModelTreeView extends ViewPart implements
		ISceneSelectionChangeListener, ISceneChangeListener {

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

		final MenuManager menuMgr = new MenuManager();

		menuMgr.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));

		final Menu menu = menuMgr.createContextMenu(modelViewer.getControl());

		modelViewer.getControl().setMenu(menu);
		getSite()
				.registerContextMenu(menuMgr, getSite().getSelectionProvider());

		MoreDreadUI.getDefault().getSceneHolder().getScene()
				.getSceneSelection().addSceneSelectionChangeListener(this);
		MoreDreadUI.getDefault().getSceneHolder().getScene()
				.addSceneChangeListener(this);
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
	public void sceneChanged(final SceneChangeEvent event) {
		modelViewer.getControl().getDisplay().asyncExec(new Runnable() {
			public void run() {
				final ISelection selection = modelViewer.getSelection();
				final Object[] expanded = modelViewer.getExpandedElements();

				modelViewer.setInput(MoreDreadUI.getDefault().getSceneHolder());

				modelViewer.setExpandedElements(expanded);
				modelViewer.setSelection(selection);
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

	/**
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(final Class adapter) {
		if (adapter == IPropertySheetPage.class) {
			return new TabbedPropertySheetPage(
					new NodePropertySheetContributor());
		}
		return super.getAdapter(adapter);
	}

}
