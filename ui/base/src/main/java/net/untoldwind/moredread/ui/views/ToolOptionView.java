package net.untoldwind.moredread.ui.views;

import net.untoldwind.moredread.ui.tools.ActiveToolChangedEvent;
import net.untoldwind.moredread.ui.tools.IToolActivationListener;
import net.untoldwind.moredread.ui.tools.UIToolsPlugin;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

public class ToolOptionView extends ViewPart implements IToolActivationListener {
	public static final String ID = "net.untoldwind.moredread.ui.toolOptionView";

	@Override
	public void init(final IViewSite site) throws PartInitException {
		super.init(site);
		UIToolsPlugin.getDefault().getToolController()
				.addToolActivationListener(this);
	}

	@Override
	public void createPartControl(final Composite parent) {
	}

	@Override
	public void dispose() {
		UIToolsPlugin.getDefault().getToolController()
				.removeToolActivationListener(this);
		super.dispose();
	}

	@Override
	public void setFocus() {
	}

	@Override
	public void activeToolChanged(final ActiveToolChangedEvent event) {
		System.out.println(">>> Hurra");
		// TODO Auto-generated method stub

	}

}
