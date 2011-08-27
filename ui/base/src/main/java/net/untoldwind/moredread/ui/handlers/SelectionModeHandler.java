package net.untoldwind.moredread.ui.handlers;

import net.untoldwind.moredread.model.enums.SelectionMode;
import net.untoldwind.moredread.ui.MoreDreadUI;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class SelectionModeHandler extends AbstractHandler {
	private final static String PARAMETER_ID = "net.untoldwind.moredread.ui.selectionMode";

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final SelectionMode selectionMode = (SelectionMode) event
				.getObjectParameterForExecution(PARAMETER_ID);

		if (selectionMode == null) {
			return null;
		}

		MoreDreadUI.getDefault().getSceneHolder()
				.setSelectionMode(selectionMode);

		return null;
	}
}
