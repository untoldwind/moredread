package net.untoldwind.moredread.ui.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class StringText extends Composite {
	Text text;

	public StringText(final Composite parent) {
		super(parent, SWT.NONE);
		final GridLayout layout = new GridLayout(1, true);
		layout.marginHeight = 1;
		layout.marginWidth = 1;
		layout.horizontalSpacing = 1;
		layout.verticalSpacing = 1;
		setLayout(layout);
		setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_BLACK));
		text = new Text(this, SWT.FLAT);
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}

	public void setValue(final String value) {
		if (value == null) {
			text.setText("");
		} else {
			text.setText(value);
		}
	}

}
