package net.untoldwind.moredread.ui.handlers;

import java.util.HashMap;
import java.util.Map;

import net.untoldwind.moredread.model.enums.SelectionMode;

import org.eclipse.core.commands.IParameterValues;

public class SelectionModeParameterValues implements IParameterValues {

	@Override
	public Map<String, String> getParameterValues() {
		final Map<String, String> values = new HashMap<String, String>();

		for (final SelectionMode selectionMode : SelectionMode.values()) {
			values.put(selectionMode.toString(), selectionMode.toString());
		}
		return values;
	}
}
