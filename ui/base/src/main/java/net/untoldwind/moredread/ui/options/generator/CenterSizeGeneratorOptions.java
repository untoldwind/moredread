package net.untoldwind.moredread.ui.options.generator;

import net.untoldwind.moredread.model.generator.AbstractCenterSizeGenerator;
import net.untoldwind.moredread.model.scene.GeneratorNode;
import net.untoldwind.moredread.ui.utils.LengthText;
import net.untoldwind.moredread.ui.utils.XYZText;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class CenterSizeGeneratorOptions implements IGeneratorOptionView {
	private final AbstractCenterSizeGenerator generator;

	Composite container;

	XYZText centerText;
	LengthText sizeText;

	CenterSizeGeneratorOptions(final AbstractCenterSizeGenerator generator) {
		this.generator = generator;
	}

	@Override
	public String getTitle() {
		return generator.getName() + " generator";
	}

	@Override
	public Composite createControls(final Composite parent,
			final GeneratorNode node) {
		container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		final Label centerLabel = new Label(container, SWT.NONE);
		centerLabel.setText("Center");

		centerText = new XYZText(container);

		final Label sizeLabel = new Label(container, SWT.NONE);
		sizeLabel.setText("Size");

		sizeText = new LengthText(container);

		centerText.setValue(generator.getCenter());
		sizeText.setValue(generator.getSize());

		return container;
	}

	@Override
	public void dispose() {
		container.dispose();
	}

	@Override
	public void update(final GeneratorNode node) {
		// TODO Auto-generated method stub

	}
}
