package net.untoldwind.moredread.ui.handlers;

import net.untoldwind.moredread.ui.tools.UIToolsPlugin;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class CompleteToolHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		UIToolsPlugin.getDefault().getToolController().completeActiveTool();

		return null;
	}

}
