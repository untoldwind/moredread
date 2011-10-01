package net.untoldwind.moredread.ui.options.generator;

import net.untoldwind.moredread.model.generator.CylinderMeshGenerator;
import net.untoldwind.moredread.model.scene.GeneratorNode;
import net.untoldwind.moredread.ui.utils.LengthValueField;
import net.untoldwind.moredread.ui.utils.XYZValueField;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

public class CylinderGeneratorOptions implements IGeneratorOptionView {
	CylinderMeshGenerator generator;

	Composite container;

	XYZValueField startPointText;
	XYZValueField endPointText;
	LengthValueField radiusText;
	Spinner numberOfSectionsSpinner;
	Spinner pointsPerSctionSpinner;

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

		startPointText = new XYZValueField(container);

		final Label endPointTextLabel = new Label(container, SWT.NONE);
		endPointTextLabel.setText("End");

		endPointText = new XYZValueField(container);

		final Label radiusLabel = new Label(container, SWT.NONE);
		radiusLabel.setText("Radius");

		radiusText = new LengthValueField(container);

		final Label numberOfSectionsLabel = new Label(container, SWT.NONE);
		numberOfSectionsLabel.setText("Sections");

		numberOfSectionsSpinner = new Spinner(container, SWT.NONE);

		final Label pointsPerSctionLabel = new Label(container, SWT.NONE);
		pointsPerSctionLabel.setText("Pts/sect");

		pointsPerSctionSpinner = new Spinner(container, SWT.NONE);

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
