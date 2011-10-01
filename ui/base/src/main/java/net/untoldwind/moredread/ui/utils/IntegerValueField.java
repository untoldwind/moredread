package net.untoldwind.moredread.ui.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Spinner;

public class IntegerValueField extends AbstractValueField<Integer> {
	Spinner spinner;

	public IntegerValueField(final Composite parent, final int min,
			final int max) {
		super(parent);

		spinner = new Spinner(this, SWT.FLAT);
		spinner.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		spinner.setMinimum(min);
		spinner.setMaximum(max);
		spinner.addSelectionListener(new SelectionAdapter() {
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

	public void setValue(final Integer value) {
		this.value = value;

		if (value == null) {
			spinner.setSelection(0);
		} else {
			spinner.setSelection(value);
		}
	}

	private void checkValueChanged() {
		final int newValue = spinner.getSelection();

		if (value == null || value != newValue) {
			setValue(newValue);

			fireValueChangedEvent(new ValueChangedEvent<Integer>(newValue));
		}
	}

}
