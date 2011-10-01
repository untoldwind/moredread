package net.untoldwind.moredread.ui.options.generator;

import net.untoldwind.moredread.model.generator.CylinderMeshGenerator;
import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.scene.AbstractSceneOperation;
import net.untoldwind.moredread.model.scene.GeneratorNode;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.ui.utils.BooleanValueField;
import net.untoldwind.moredread.ui.utils.IValueChangedListener;
import net.untoldwind.moredread.ui.utils.IntegerValueField;
import net.untoldwind.moredread.ui.utils.LengthValueField;
import net.untoldwind.moredread.ui.utils.ValueChangedEvent;
import net.untoldwind.moredread.ui.utils.XYZValueField;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class CylinderGeneratorOptions implements IGeneratorOptionView {
	CylinderMeshGenerator generator;

	Composite container;

	XYZValueField startPointField;
	XYZValueField endPointField;
	LengthValueField radiusField;
	IntegerValueField numberOfSectionsField;
	IntegerValueField pointsPerSctionField;
	BooleanValueField closedField;

	CylinderGeneratorOptions(final CylinderMeshGenerator generator) {
		this.generator = generator;
	}

	@Override
	public String getTitle() {
		return "Cylinder generator";
	}

	@Override
	public Composite createControls(final Composite parent,
			final GeneratorNode node) {
		container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		final Label startPointTextLabel = new Label(container, SWT.NONE);
		startPointTextLabel.setText("Start");

		startPointField = new XYZValueField(container);
		startPointField
				.addValueChangeListener(new IValueChangedListener<Vector3>() {
					@Override
					public void valueChanged(
							final ValueChangedEvent<Vector3> event) {
						if (!generator.getStartPoint().equals(event.getValue())) {
							node.getScene().undoableChange(
									new AbstractSceneOperation(
											"Cylinder operation change") {
										@Override
										public void perform(final Scene scene) {
											generator.setStartPoint(event
													.getValue());
										}
									});
						}
					}
				});

		final Label endPointTextLabel = new Label(container, SWT.NONE);
		endPointTextLabel.setText("End");

		endPointField = new XYZValueField(container);
		endPointField
				.addValueChangeListener(new IValueChangedListener<Vector3>() {
					@Override
					public void valueChanged(
							final ValueChangedEvent<Vector3> event) {
						if (!generator.getEndPoint().equals(event.getValue())) {
							node.getScene().undoableChange(
									new AbstractSceneOperation(
											"Cylinder operation change") {
										@Override
										public void perform(final Scene scene) {
											generator.setEndPoint(event
													.getValue());
										}
									});
						}
					}
				});

		final Label radiusLabel = new Label(container, SWT.NONE);
		radiusLabel.setText("Radius");

		radiusField = new LengthValueField(container);
		radiusField.addValueChangeListener(new IValueChangedListener<Float>() {
			@Override
			public void valueChanged(final ValueChangedEvent<Float> event) {
				if (generator.getRadius() != event.getValue()) {
					node.getScene().undoableChange(
							new AbstractSceneOperation(
									"Cylinder operation change") {
								@Override
								public void perform(final Scene scene) {
									generator.setRadius(event.getValue());
								}
							});
				}
			}
		});

		final Label numberOfSectionsLabel = new Label(container, SWT.NONE);
		numberOfSectionsLabel.setText("Sections");

		numberOfSectionsField = new IntegerValueField(container, 1, 100);
		numberOfSectionsField
				.addValueChangeListener(new IValueChangedListener<Integer>() {
					@Override
					public void valueChanged(
							final ValueChangedEvent<Integer> event) {
						if (generator.getNumSections() != event.getValue()) {
							node.getScene().undoableChange(
									new AbstractSceneOperation(
											"Cylinder operation change") {
										@Override
										public void perform(final Scene scene) {
											generator.setNumSections(event
													.getValue());
										}
									});
						}
					}
				});

		final Label pointsPerSctionLabel = new Label(container, SWT.NONE);
		pointsPerSctionLabel.setText("Pts/sect");

		pointsPerSctionField = new IntegerValueField(container, 3, 64);
		pointsPerSctionField
				.addValueChangeListener(new IValueChangedListener<Integer>() {
					@Override
					public void valueChanged(
							final ValueChangedEvent<Integer> event) {
						if (generator.getPointsPerSection() != event.getValue()) {
							node.getScene().undoableChange(
									new AbstractSceneOperation(
											"Cylinder operation change") {
										@Override
										public void perform(final Scene scene) {
											generator.setPointsPerSection(event
													.getValue());
										}
									});
						}
					}
				});

		final Label closedLabel = new Label(container, SWT.NONE);
		closedLabel.setText("Closed");

		closedField = new BooleanValueField(container);
		closedField
				.addValueChangeListener(new IValueChangedListener<Boolean>() {
					@Override
					public void valueChanged(
							final ValueChangedEvent<Boolean> event) {
						if (generator.isClosed() != event.getValue()) {
							node.getScene().undoableChange(
									new AbstractSceneOperation(
											"Cylinder operation change") {
										@Override
										public void perform(final Scene scene) {
											generator.setClosed(event
													.getValue());
										}
									});
						}
					}
				});

		update(node);

		return container;
	}

	@Override
	public void dispose() {
		container.dispose();
	}

	@Override
	public void update(final GeneratorNode node) {
		generator = (CylinderMeshGenerator) node.getGenerator();
		startPointField.setValue(generator.getStartPoint());
		endPointField.setValue(generator.getEndPoint());
		radiusField.setValue(generator.getRadius());
		numberOfSectionsField.setValue(generator.getNumSections());
		pointsPerSctionField.setValue(generator.getPointsPerSection());
		closedField.setValue(generator.isClosed());
	}

}
