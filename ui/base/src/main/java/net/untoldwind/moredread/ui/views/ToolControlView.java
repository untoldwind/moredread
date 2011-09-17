package net.untoldwind.moredread.ui.views;

import net.untoldwind.moredread.ui.MoreDreadUI;
import net.untoldwind.moredread.ui.tools.ActiveToolChangedEvent;
import net.untoldwind.moredread.ui.tools.IToolActivationListener;
import net.untoldwind.moredread.ui.tools.ToolType;
import net.untoldwind.moredread.ui.tools.UIToolsPlugin;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISizeProvider;
import org.eclipse.ui.part.ViewPart;

public class ToolControlView extends ViewPart implements
		IToolActivationListener, ISizeProvider {
	public static final String ID = "net.untoldwind.moredread.ui.toolControlView";

	private ToolBar toolBar;

	private ToolItem completeTool;
	private ToolItem abortTool;

	@Override
	public void createPartControl(final Composite parent) {
		toolBar = new ToolBar(parent, SWT.VERTICAL);

		completeTool = new ToolItem(toolBar, SWT.NONE);
		completeTool.setImage(MoreDreadUI.getDefault().getImage(
				"/icons/Complete.png"));
		completeTool.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				UIToolsPlugin.getDefault().getToolController()
						.completeActiveTool();
			}
		});
		completeTool.setEnabled(false);

		abortTool = new ToolItem(toolBar, SWT.NONE);
		abortTool.setImage(MoreDreadUI.getDefault()
				.getImage("/icons/Abort.png"));
		abortTool.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				UIToolsPlugin.getDefault().getToolController()
						.abortActiveTool();
			}
		});
		abortTool.setEnabled(false);

		UIToolsPlugin.getDefault().getToolController()
				.addToolActivationListener(this);
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
	public int computePreferredSize(final boolean width,
			final int availableParallel, final int availablePerpendicular,
			final int preferredResult) {
		final Point size = toolBar.computeSize(SWT.DEFAULT, SWT.DEFAULT);

		return width ? size.x + 2 : size.y;
	}

	@Override
	public int getSizeFlags(final boolean width) {
		return width ? SWT.MIN : (SWT.MAX | SWT.MIN);
	}

	@Override
	public void activeToolChanged(final ActiveToolChangedEvent event) {
		if (event.getActiveTool().getToolType() == ToolType.TOGGLE) {
			completeTool.setEnabled(true);
			abortTool.setEnabled(true);
		} else {
			completeTool.setEnabled(false);
			abortTool.setEnabled(false);
		}
	}

}
