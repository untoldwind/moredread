package net.untoldwind.moredread.ui.utils;

import static net.untoldwind.moredread.ui.utils.FormatUtils.formatLength;
import static net.untoldwind.moredread.ui.utils.FormatUtils.parseLength;
import net.untoldwind.moredread.model.math.Vector3;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

public class XYZValueField extends AbstractValueField<Vector3> {
	Text xText;
	Text yText;
	Text zText;

	public XYZValueField(final Composite parent) {
		super(parent);

		final Display display = parent.getDisplay();

		setBackground(display.getSystemColor(SWT.COLOR_BLACK));

		xText = new Text(this, SWT.FLAT | SWT.SINGLE);
		xText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		xText.setBackground(display.getSystemColor(SWT.COLOR_RED));
		xText.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				checkValueChanged();
			}
		});
		xText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(final FocusEvent e) {
				checkValueChanged();
			}
		});

		yText = new Text(this, SWT.FLAT | SWT.SINGLE);
		yText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		yText.setBackground(display.getSystemColor(SWT.COLOR_GREEN));
		yText.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				checkValueChanged();
			}
		});
		yText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(final FocusEvent e) {
				checkValueChanged();
			}
		});

		zText = new Text(this, SWT.FLAT | SWT.SINGLE);
		zText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		zText.setBackground(display.getSystemColor(SWT.COLOR_BLUE));
		zText.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				checkValueChanged();
			}
		});
		zText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(final FocusEvent e) {
				checkValueChanged();
			}
		});
	}

	public void setValue(final Vector3 value) {
		this.value = value;

		if (value == null) {
			xText.setText("");
			yText.setText("");
			zText.setText("");
		} else {
			xText.setText(formatLength(value.x));
			yText.setText(formatLength(value.y));
			zText.setText(formatLength(value.z));
		}
	}

	private void checkValueChanged() {
		final Vector3 newValue = new Vector3(parseLength(xText.getText()),
				parseLength(yText.getText()), parseLength(zText.getText()));

		if (!Vector3.isValidVector(newValue)) {
			setValue(value);
		} else if (value == null || value.x != newValue.x
				|| value.y != newValue.y || value.z != newValue.z) {
			setValue(newValue);

			fireValueChangedEvent(new ValueChangedEvent<Vector3>(value));
		}
	}

}
