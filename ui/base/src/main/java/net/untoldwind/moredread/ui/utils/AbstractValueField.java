package net.untoldwind.moredread.ui.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class AbstractValueField<T> extends Composite {
	private final List<IValueChangedListener<T>> valueChangeListeners = new ArrayList<IValueChangedListener<T>>();

	protected T value;

	public AbstractValueField(final Composite parent) {
		super(parent, SWT.NONE);
		final GridLayout layout = new GridLayout(1, true);
		layout.marginHeight = 1;
		layout.marginWidth = 1;
		layout.horizontalSpacing = 1;
		layout.verticalSpacing = 1;
		setLayout(layout);
		setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}

	public T getValue() {
		return value;
	}

	public void addValueChangeListener(final IValueChangedListener<T> listener) {
		synchronized (valueChangeListeners) {
			valueChangeListeners.add(listener);
		}
	}

	public void removeValueChangeListener(
			final IValueChangedListener<T> listener) {
		synchronized (valueChangeListeners) {
			valueChangeListeners.remove(listener);
		}
	}

	@SuppressWarnings("unchecked")
	protected void fireValueChangedEvent(final ValueChangedEvent<T> event) {
		final IValueChangedListener<T> listenerArray[];

		synchronized (valueChangeListeners) {
			listenerArray = valueChangeListeners
					.toArray(new IValueChangedListener[valueChangeListeners
							.size()]);
		}

		for (final IValueChangedListener<T> listener : listenerArray) {
			listener.valueChanged(event);
		}

	}
}
