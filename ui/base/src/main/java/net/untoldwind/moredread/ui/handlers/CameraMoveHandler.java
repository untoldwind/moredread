package net.untoldwind.moredread.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PlatformUI;

public class CameraMoveHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		System.out.println("Doit");
		System.out.println(event);
		System.out.println(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().getActivePart());
		// TODO Auto-generated method stub
		return null;
	}

}
