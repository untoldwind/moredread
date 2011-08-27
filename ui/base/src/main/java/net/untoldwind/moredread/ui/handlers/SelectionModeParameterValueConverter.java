package net.untoldwind.moredread.ui.handlers;

import net.untoldwind.moredread.model.enums.SelectionMode;

import org.eclipse.core.commands.AbstractParameterValueConverter;
import org.eclipse.core.commands.ParameterValueConversionException;

public class SelectionModeParameterValueConverter extends
		AbstractParameterValueConverter {

	@Override
	public Object convertToObject(final String parameterValue)
			throws ParameterValueConversionException {
		if (parameterValue == null) {
			return null;
		}
		return SelectionMode.valueOf(parameterValue);
	}

	@Override
	public String convertToString(final Object parameterValue)
			throws ParameterValueConversionException {
		if (parameterValue == null) {
			return null;
		}
		return parameterValue.toString();
	}

}
