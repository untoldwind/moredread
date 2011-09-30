package net.untoldwind.moredread.ui.options.generator;

import net.untoldwind.moredread.model.scene.GeneratorNode;

import org.eclipse.swt.widgets.Composite;

public interface IGeneratorOptionView {
	String getTitle();

	Composite createControls(Composite parent, GeneratorNode node);

	void dispose();

	void update(GeneratorNode node);
}
