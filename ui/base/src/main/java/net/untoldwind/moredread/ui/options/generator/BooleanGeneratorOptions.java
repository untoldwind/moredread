package net.untoldwind.moredread.ui.options.generator;

import net.untoldwind.moredread.model.generator.BooleanGenerator;
import net.untoldwind.moredread.model.op.IBooleanOperation;
import net.untoldwind.moredread.ui.options.IOptionView;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class BooleanGeneratorOptions implements IOptionView {
	BooleanGenerator generator;

	Composite container;

	BooleanGeneratorOptions(final BooleanGenerator generator) {
		this.generator = generator;
	}

	@Override
	public void createControls(final Composite parent) {
		container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		final Label typeLabel = new Label(container, SWT.NONE);
		typeLabel.setText("Type");
		final Combo typeCombo = new Combo(container, SWT.READ_ONLY);
		final String[] items = new String[IBooleanOperation.BoolOperation
				.values().length];
		for (int i = 0; i < items.length; i++) {
			items[i] = IBooleanOperation.BoolOperation.values()[i].name();
		}
		typeCombo.setItems(items);

		typeCombo.select(generator.getBoolOperation().ordinal());
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
				if (adaptableObject instanceof BooleanGenerator) {
					return new BooleanGeneratorOptions(
							(BooleanGenerator) adaptableObject);
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
