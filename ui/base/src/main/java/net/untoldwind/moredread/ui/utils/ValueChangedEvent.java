package net.untoldwind.moredread.ui.utils;

public class ValueChangedEvent<T> {
	T value;

	public ValueChangedEvent(final T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}
}
