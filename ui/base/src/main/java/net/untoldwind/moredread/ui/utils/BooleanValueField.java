package net.untoldwind.moredread.ui.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class BooleanValueField extends AbstractValueField<Boolean> {
	Button checkbox;

	public BooleanValueField(final Composite parent) {
		super(parent);

		checkbox = new Button(this, SWT.CHECK);
		checkbox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		checkbox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				checkValueChanged();
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				checkValueChanged();
			}
		});
	}

	public void setValue(final Boolean value) {
		this.value = value;

		if (value == null) {
			checkbox.setSelection(false);
		} else {
			checkbox.setSelection(value);
		}
	}

	private void checkValueChanged() {
		final boolean newValue = checkbox.getSelection();

		if (value == null || value != newValue) {
			setValue(newValue);

			fireValueChangedEvent(new ValueChangedEvent<Boolean>(newValue));
		}
	}
}
