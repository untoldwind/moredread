package net.untoldwind.moredread.ui.options.generator;

import net.untoldwind.moredread.model.generator.GeosphereMeshGenerator;
import net.untoldwind.moredread.model.scene.AbstractSceneOperation;
import net.untoldwind.moredread.model.scene.GeneratorNode;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.ui.utils.IValueChangedListener;
import net.untoldwind.moredread.ui.utils.IntegerValueField;
import net.untoldwind.moredread.ui.utils.ValueChangedEvent;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class GeosphereOptionView extends CenterSizeGeneratorOptions {
	GeosphereMeshGenerator geosphereMeshGenerator;

	IntegerValueField levelField;

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

		levelField = new IntegerValueField(container, 0, 12);
		levelField.setValue(geosphereMeshGenerator.getNumLevels());
		levelField.addValueChangeListener(new IValueChangedListener<Integer>() {
			@Override
			public void valueChanged(final ValueChangedEvent<Integer> event) {
				node.getScene().undoableChange(
						new AbstractSceneOperation("Geosphere level change") {
							@Override
							public void perform(final Scene scene) {
								geosphereMeshGenerator.setNumLevels(event
										.getValue());
							}
						});
			}
		});

		return container;
	}

	@Override
	public void update(final GeneratorNode node) {
		super.update(node);

		geosphereMeshGenerator = (GeosphereMeshGenerator) node.getGenerator();
		levelField.setValue(geosphereMeshGenerator.getNumLevels());
	}
}
