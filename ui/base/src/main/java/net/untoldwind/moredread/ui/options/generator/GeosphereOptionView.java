package net.untoldwind.moredread.ui.options.generator;

import net.untoldwind.moredread.model.generator.GeosphereMeshGenerator;
import net.untoldwind.moredread.model.scene.GeneratorNode;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

public class GeosphereOptionView extends CenterSizeGeneratorOptions {
	GeosphereMeshGenerator geosphereMeshGenerator;

	Spinner levelSpinner;

	public GeosphereOptionView(final GeosphereMeshGenerator generator) {
		super(generator);

		this.geosphereMeshGenerator = generator;
	}

	@Override
	public Composite createControls(final Composite parent,
			final GeneratorNode node) {
		final Composite container = super.createControls(parent, node);

		final Label levelsLabel = new Label(container, SWT.NONE);
		levelsLabel.setText("Levels");

		levelSpinner = new Spinner(container, SWT.NONE);
		levelSpinner.setSelection(geosphereMeshGenerator.getNumLevels());

		return container;
	}

}
