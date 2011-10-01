package net.untoldwind.moredread.ui.options.generator;

import net.untoldwind.moredread.model.generator.AbstractCenterSizeGenerator;
import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.scene.AbstractSceneOperation;
import net.untoldwind.moredread.model.scene.GeneratorNode;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.ui.utils.IValueChangedListener;
import net.untoldwind.moredread.ui.utils.LengthValueField;
import net.untoldwind.moredread.ui.utils.ValueChangedEvent;
import net.untoldwind.moredread.ui.utils.XYZValueField;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class CenterSizeGeneratorOptions implements IGeneratorOptionView {
	AbstractCenterSizeGenerator generator;

	Composite container;

	XYZValueField centerField;
	LengthValueField sizeField;

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

		centerField = new XYZValueField(container);
		centerField
				.addValueChangeListener(new IValueChangedListener<Vector3>() {
					@Override
					public void valueChanged(
							final ValueChangedEvent<Vector3> event) {
						if (!generator.getCenter().equals(event.getValue())) {
							node.getScene().undoableChange(
									new AbstractSceneOperation(generator
											.getName() + " operation change") {
										@Override
										public void perform(final Scene scene) {
											generator.setCenter(event
													.getValue());
										}
									});
						}
					}
				});

		final Label sizeLabel = new Label(container, SWT.NONE);
		sizeLabel.setText("Size");

		sizeField = new LengthValueField(container);
		sizeField.addValueChangeListener(new IValueChangedListener<Float>() {
			@Override
			public void valueChanged(final ValueChangedEvent<Float> event) {
				if (generator.getSize() != event.getValue()) {
					node.getScene().undoableChange(
							new AbstractSceneOperation(
									"Cylinder operation change") {
								@Override
								public void perform(final Scene scene) {
									System.out.println(">>> Hereherer");
									generator.setSize(event.getValue());
								}
							});
				}
			}
		});

		centerField.setValue(generator.getCenter());
		sizeField.setValue(generator.getSize());

		return container;
	}

	@Override
	public void dispose() {
		container.dispose();
	}

	@Override
	public void update(final GeneratorNode node) {
		generator = (AbstractCenterSizeGenerator) node.getGenerator();

		centerField.setValue(generator.getCenter());
		sizeField.setValue(generator.getSize());
	}
}
