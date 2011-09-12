package net.untoldwind.moredread.ui.tools.impl;

public enum SelectionCount {
	ANY("0-*"),
	ZERO_OR_ONE("0-1"),
	ONE_OR_MORE("1-*"),
	ONE("1"),
	TWO("2");

	private String code;

	private SelectionCount(final String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static SelectionCount forCode(final String code) {
		for (final SelectionCount selectionCount : values()) {
			if (selectionCount.getCode().equals(code)) {
				return selectionCount;
			}
		}
		throw new RuntimeException("InvalidCode: " + code);
	}
}
