package net.untoldwind.moredread.ui.options.generator;

import static net.untoldwind.moredread.ui.utils.FormatUtils.formatLength;
import net.untoldwind.moredread.model.generator.AbstractCenterSizeGenerator;
import net.untoldwind.moredread.ui.options.IOptionView;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class CenterSizeGeneratorOptions implements IOptionView {
	private final AbstractCenterSizeGenerator generator;

	Composite container;

	Text centerXText;
	Text centerYText;
	Text centerZText;
	Text sizeText;

	CenterSizeGeneratorOptions(final AbstractCenterSizeGenerator generator) {
		this.generator = generator;
	}

	@Override
	public void createControls(final Composite parent) {
		container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		final Label typeLabel = new Label(container, SWT.NONE);
		typeLabel.setText("Type");
		final Text typeText = new Text(container, SWT.FLAT);
		typeText.setText(generator.getName());
		typeText.setEditable(false);
		typeText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		final Display display = Display.getDefault();
		final Label centerLabel = new Label(container, SWT.NONE);
		centerLabel.setText("Center");

		final Composite center = new Composite(container, SWT.NONE);
		GridLayout layout = new GridLayout(3, true);
		layout.marginHeight = 1;
		layout.marginWidth = 1;
		layout.horizontalSpacing = 1;
		layout.verticalSpacing = 1;
		center.setLayout(layout);
		center.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		center.setBackground(display.getSystemColor(SWT.COLOR_BLACK));

		centerXText = new Text(center, SWT.FLAT);
		centerXText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		centerXText.setBackground(display.getSystemColor(SWT.COLOR_RED));

		centerYText = new Text(center, SWT.FLAT);
		centerYText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		centerYText.setBackground(display.getSystemColor(SWT.COLOR_GREEN));

		centerZText = new Text(center, SWT.FLAT);
		centerZText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		centerZText.setBackground(display.getSystemColor(SWT.COLOR_BLUE));

		final Label sizeLabel = new Label(container, SWT.NONE);
		sizeLabel.setText("Size");

		final Composite size = new Composite(container, SWT.NONE);
		layout = new GridLayout(1, true);
		layout.marginHeight = 1;
		layout.marginWidth = 1;
		layout.horizontalSpacing = 1;
		layout.verticalSpacing = 1;
		size.setLayout(layout);
		size.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		size.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
		sizeText = new Text(size, SWT.FLAT);
		sizeText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		centerXText.setText(formatLength(generator.getCenter().x));
		centerYText.setText(formatLength(generator.getCenter().y));
		centerZText.setText(formatLength(generator.getCenter().z));
		sizeText.setText(formatLength(generator.getSize()));
	}

	@Override
	public void dispose() {
		container.dispose();
	}

	public static class Factory implements IAdapterFactory {

		@Override
		public Object getAdapter(final Object adaptableObject,
				@SuppressWarnings("rawtypes") final Class adapterType) {
			if (adapterType == IOptionView.class) {
				if (adaptableObject instanceof AbstractCenterSizeGenerator) {
					return new CenterSizeGeneratorOptions(
							(AbstractCenterSizeGenerator) adaptableObject);
				}

			}
			return null;
		}

		@SuppressWarnings("rawtypes")
		@Override
		public Class[] getAdapterList() {
			return new Class[] { IOptionView.class };
		}

	}
}
