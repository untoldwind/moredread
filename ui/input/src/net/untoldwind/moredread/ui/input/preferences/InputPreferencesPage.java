package net.untoldwind.moredread.ui.input.preferences;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.untoldwind.moredread.ui.input.UIInputPlugin;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class InputPreferencesPage extends PreferencePage implements
		IWorkbenchPreferencePage {

	private ListViewer controllerListViewer;
	private ListViewer componentListViewer;

	@Override
	public void init(final IWorkbench workbench) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Control createContents(final Composite parent) {
		final Composite top = new Composite(parent, SWT.NONE);

		top.setLayout(new GridLayout(1, false));

		controllerListViewer = new ListViewer(top, SWT.V_SCROLL);
		controllerListViewer.setContentProvider(new ArrayContentProvider());
		controllerListViewer.setLabelProvider(new ControllerLabelProvider());
		controllerListViewer.getControl().setLayoutData(
				new GridData(GridData.FILL_BOTH));

		updateControllerList();

		componentListViewer = new ListViewer(top, SWT.V_SCROLL);
		componentListViewer.setContentProvider(new ArrayContentProvider());
		componentListViewer.setLabelProvider(new ComponentLabelProvider());
		componentListViewer.getControl().setLayoutData(
				new GridData(GridData.FILL_BOTH));

		controllerListViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					@Override
					public void selectionChanged(
							final SelectionChangedEvent event) {
						updateComponentList();
					}
				});

		return top;
	}

	private void updateControllerList() {
		try {
			final ControllerEnvironment ce = ControllerEnvironment
					.getDefaultEnvironment();

			controllerListViewer.setInput(ce.getControllers());
		} catch (final Exception e) {
			UIInputPlugin.getDefault().log(e);
		}
	}

	private void updateComponentList() {
		final ISelection selection = controllerListViewer.getSelection();

		if (!selection.isEmpty() && selection instanceof IStructuredSelection) {
			final Object selected = ((IStructuredSelection) selection)
					.getFirstElement();

			if (selected != null && selected instanceof Controller) {
				componentListViewer.setInput(((Controller) selected)
						.getComponents());
			}
		}
	}

	static class ControllerLabelProvider extends LabelProvider {
		@Override
		public String getText(final Object element) {
			if (element instanceof Controller) {
				return ((Controller) element).getName();
			}

			return super.getText(element);
		}
	}

	static class ComponentLabelProvider extends LabelProvider {
		@Override
		public String getText(final Object element) {
			if (element instanceof Component) {
				return ((Component) element).getName();
			}
			return super.getText(element);
		}

	}
}
