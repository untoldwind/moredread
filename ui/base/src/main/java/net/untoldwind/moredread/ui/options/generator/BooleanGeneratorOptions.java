package net.untoldwind.moredread.ui.options.generator;

import net.untoldwind.moredread.model.generator.BooleanGenerator;
import net.untoldwind.moredread.model.op.IBooleanOperation;
import net.untoldwind.moredread.model.scene.AbstractSceneOperation;
import net.untoldwind.moredread.model.scene.GeneratorNode;
import net.untoldwind.moredread.model.scene.Scene;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class BooleanGeneratorOptions implements IGeneratorOptionView {
	BooleanGenerator generator;

	Composite container;
	Combo typeCombo;

	BooleanGeneratorOptions(final BooleanGenerator generator) {
		this.generator = generator;
	}

	@Override
	public String getTitle() {
		return "Boolean generator";
	}

	@Override
	public Composite createControls(final Composite parent,
			final GeneratorNode node) {
		container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		final Label typeLabel = new Label(container, SWT.NONE);
		typeLabel.setText("Type");
		typeCombo = new Combo(container, SWT.READ_ONLY);
		final String[] items = new String[IBooleanOperation.BoolOperation
				.values().length];
		for (int i = 0; i < items.length; i++) {
			items[i] = IBooleanOperation.BoolOperation.values()[i].name();
		}
		typeCombo.setItems(items);

		typeCombo.select(generator.getBoolOperation().ordinal());

		typeCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final IBooleanOperation.BoolOperation boolOperation = IBooleanOperation.BoolOperation
						.values()[typeCombo.getSelectionIndex()];

				if (generator.getBoolOperation() != boolOperation) {
					node.getScene().undoableChange(
							new AbstractSceneOperation(
									"Boolean operation change") {
								@Override
								public void perform(final Scene scene) {
									node.setMeshGenerator(new BooleanGenerator(
											boolOperation));
								}
							});
				}
			}
		});

		return container;
	}

	@Override
	public void dispose() {
		container.dispose();
	}

	@Override
	public void update(final GeneratorNode node) {
		generator = (BooleanGenerator) node.getMeshGenerator();
		typeCombo.select(generator.getBoolOperation().ordinal());
	}

	public static class Factory implements IAdapterFactory {

		@Override
		public Object getAdapter(final Object adaptableObject,
				@SuppressWarnings("rawtypes") final Class adapterType) {
			if (adapterType == IGeneratorOptionView.class) {
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
			return new Class[] { IGeneratorOptionView.class };
		}
	}
}
