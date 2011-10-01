package net.untoldwind.moredread.ui.utils;

import static net.untoldwind.moredread.ui.utils.FormatUtils.formatLength;
import static net.untoldwind.moredread.ui.utils.FormatUtils.parseLength;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class LengthValueField extends AbstractValueField<Float> {
	Text text;

	public LengthValueField(final Composite parent) {
		super(parent);
		setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_BLACK));

		text = new Text(this, SWT.FLAT);
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		text.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				checkValueChanged();
			}
		});
		text.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(final FocusEvent e) {
				checkValueChanged();
			}
		});
	}

	public void setValue(final Float value) {
		this.value = value;

		if (value == null) {
			text.setText("");
		} else {
			text.setText(formatLength(value));
		}
	}

	private void checkValueChanged() {
		final float newValue = parseLength(text.getText());

		if (newValue == Float.NaN) {
			setValue(value);
		} else if (value == null || value != newValue) {
			setValue(newValue);

			fireValueChangedEvent(new ValueChangedEvent<Float>(newValue));
		}
	}
}
