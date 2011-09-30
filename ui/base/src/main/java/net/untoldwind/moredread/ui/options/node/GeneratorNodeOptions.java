package net.untoldwind.moredread.ui.options.node;

import net.untoldwind.moredread.model.scene.GeneratorNode;
import net.untoldwind.moredread.model.scene.op.CollapseGeneratorNodeOperation;
import net.untoldwind.moredread.ui.options.IOptionView;
import net.untoldwind.moredread.ui.options.generator.IGeneratorOptionView;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class GeneratorNodeOptions implements IOptionView {
	private final GeneratorNode node;

	Composite container;
	IGeneratorOptionView generatorOptions;

	GeneratorNodeOptions(final GeneratorNode node) {
		this.node = node;
	}

	@Override
	public void createControls(final Composite parent) {
		container = new Composite(parent, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		final GridLayout layout = new GridLayout(1, true);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		container.setLayout(layout);

		generatorOptions = (IGeneratorOptionView) node.getMeshGenerator()
				.getAdapter(IGeneratorOptionView.class);

		if (generatorOptions != null) {
			final Label titleLabel = new Label(container, SWT.CENTER);
			titleLabel.setText(generatorOptions.getTitle());
			titleLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			generatorOptions.createControls(container, node);
		} else {
			final Composite empty = new Composite(container, SWT.NONE);
			empty.setLayoutData(new GridData(GridData.FILL_BOTH));
		}

		final Button collapseButton = new Button(container, SWT.NONE);
		collapseButton.setText("Collapse to mesh");
		collapseButton.setLayoutData(new GridData(
				GridData.HORIZONTAL_ALIGN_CENTER));
		collapseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				node.getScene().undoableChange(
						new CollapseGeneratorNodeOperation(node));
			}
		});
	}

	@Override
	public void dispose() {
		container.dispose();
	}

	@Override
	public void update() {
		if (generatorOptions != null) {
			generatorOptions.update(node);
		}
	}

	public static class Factory implements IAdapterFactory {

		@Override
		public Object getAdapter(final Object adaptableObject,
				@SuppressWarnings("rawtypes") final Class adapterType) {
			if (adapterType == IOptionView.class) {
				if (adaptableObject instanceof GeneratorNode) {
					return new GeneratorNodeOptions(
							(GeneratorNode) adaptableObject);
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
