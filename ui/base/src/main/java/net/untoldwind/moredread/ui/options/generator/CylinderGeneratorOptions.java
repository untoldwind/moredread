package net.untoldwind.moredread.ui.options.generator;

import net.untoldwind.moredread.model.generator.CylinderGenerator;
import net.untoldwind.moredread.model.scene.GeneratorNode;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class CylinderGeneratorOptions implements IGeneratorOptionView {
	CylinderGenerator generator;

	Composite container;
	Combo typeCombo;

	CylinderGeneratorOptions(final CylinderGenerator generator) {
		this.generator = generator;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Composite createControls(final Composite parent,
			final GeneratorNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(final GeneratorNode node) {
		// TODO Auto-generated method stub

	}

}
