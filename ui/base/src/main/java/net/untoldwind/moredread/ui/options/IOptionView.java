package net.untoldwind.moredread.ui.options;

import org.eclipse.swt.widgets.Composite;

public interface IOptionView {
	void createControls(Composite parent);

	void dispose();
}
