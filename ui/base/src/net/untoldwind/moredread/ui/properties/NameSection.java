package net.untoldwind.moredread.ui.properties;

import net.untoldwind.moredread.model.scene.SpatialNode;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class NameSection extends AbstractPropertySection {

	private Text labelText;

	private SpatialNode node;

	private final ModifyListener listener = new ModifyListener() {

		public void modifyText(final ModifyEvent arg0) {
			final IPropertySource properties = (IPropertySource) node
					.getAdapter(IPropertySource.class);
			properties.setPropertyValue("name", labelText.getText());
		}
	};

	@Override
	public void setInput(final IWorkbenchPart part, final ISelection selection) {
		super.setInput(part, selection);
		Assert.isTrue(selection instanceof IStructuredSelection);
		final Object input = ((IStructuredSelection) selection)
				.getFirstElement();
		Assert.isTrue(input instanceof SpatialNode);
		this.node = (SpatialNode) input;
	}

	@Override
	public void createControls(final Composite parent,
			final TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		final Composite composite = getWidgetFactory().createFlatFormComposite(
				parent);
		FormData data;

		labelText = getWidgetFactory().createText(composite, ""); //$NON-NLS-1$
		data = new FormData();
		data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);
		labelText.setLayoutData(data);
		labelText.addModifyListener(listener);

		final CLabel labelLabel = getWidgetFactory().createCLabel(composite,
				"Name:");
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(labelText,
				-ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(labelText, 0, SWT.CENTER);
		labelLabel.setLayoutData(data);
	}

	@Override
	public void refresh() {
		labelText.removeModifyListener(listener);
		final IPropertySource properties = (IPropertySource) node
				.getAdapter(IPropertySource.class);
		labelText.setText(properties.getPropertyValue("name").toString());
		labelText.addModifyListener(listener);
	}

}
