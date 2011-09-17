package net.untoldwind.moredread.ui.views;

import java.util.Set;

import net.untoldwind.moredread.model.scene.event.ISceneSelectionChangeListener;
import net.untoldwind.moredread.model.scene.event.ISceneSelectionModeListener;
import net.untoldwind.moredread.model.scene.event.SceneSelectionChangeEvent;
import net.untoldwind.moredread.model.scene.event.SceneSelectionModeEvent;
import net.untoldwind.moredread.ui.MoreDreadUI;
import net.untoldwind.moredread.ui.tools.ActiveToolChangedEvent;
import net.untoldwind.moredread.ui.tools.IToolActivationListener;
import net.untoldwind.moredread.ui.tools.IToolCategoryDescriptor;
import net.untoldwind.moredread.ui.tools.IToolController;
import net.untoldwind.moredread.ui.tools.IToolDescriptor;
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

public class ToolSelectionView extends ViewPart implements
		ISceneSelectionChangeListener, ISceneSelectionModeListener,
		IToolActivationListener, ISizeProvider {
	public static final String ID = "net.untoldwind.moredread.ui.toolSelectionView";

	private ToolBar toolBar;
	private IToolController toolController;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createPartControl(final Composite parent) {
		final String categoryId = getViewSite().getSecondaryId();

		toolBar = new ToolBar(parent, SWT.NONE);
		toolController = UIToolsPlugin.getDefault().getToolController();

		final IToolCategoryDescriptor toolCategoryDescriptor = toolController
				.getToolCategory(categoryId);

		if (toolCategoryDescriptor != null) {
			setPartName(toolCategoryDescriptor.getLabel());

			for (final IToolDescriptor toolDescriptor : toolCategoryDescriptor
					.getTools()) {
				int style = SWT.NONE;
				switch (toolDescriptor.getToolType()) {
				case PUSH:
					style |= SWT.PUSH;
					break;
				case TOGGLE:
					style |= SWT.CHECK;
					break;
				}
				final ToolItem toolItem = new ToolItem(toolBar, style);

				if (toolDescriptor.getIcon() != null) {
					toolItem.setImage(toolDescriptor.getIcon().createImage());
					toolItem.setToolTipText(toolDescriptor.getLabel());
				} else {
					toolItem.setText(toolDescriptor.getLabel());
				}
				toolItem.setData(toolDescriptor);
				toolItem.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(final SelectionEvent e) {
						selectTool(toolDescriptor);
					}
				});
			}
		}
		MoreDreadUI.getDefault().getSceneHolder()
				.addSceneSelectionModeListener(this);
		MoreDreadUI.getDefault().getSceneHolder().getScene()
				.getSceneSelection().addSceneSelectionChangeListener(this);
		UIToolsPlugin.getDefault().getToolController()
				.addToolActivationListener(this);

		updateTools();
	}

	@Override
	public void dispose() {
		MoreDreadUI.getDefault().getSceneHolder()
				.removeSceneSelectionModeListener(this);
		MoreDreadUI.getDefault().getSceneHolder().getScene()
				.getSceneSelection().removeSceneSelectionChangeListener(this);
		UIToolsPlugin.getDefault().getToolController()
				.removeToolActivationListener(this);

		super.dispose();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFocus() {
	}

	@Override
	public void sceneSelectionChanged(final SceneSelectionChangeEvent event) {
		updateTools();
	}

	@Override
	public void sceneSelectionModeChanged(final SceneSelectionModeEvent event) {
		updateTools();
	}

	@Override
	public void activeToolChanged(final ActiveToolChangedEvent event) {
		updateTools();
	}

	protected void selectTool(final IToolDescriptor toolDescriptor) {
		try {
			toolDescriptor.activate(MoreDreadUI.getDefault().getSceneHolder()
					.getScene());
		} catch (final Throwable e) {
			MoreDreadUI.getDefault().log(e);
		}
	}

	protected void updateTools() {
		final IToolDescriptor activeTool = toolController.getActiveTool();
		final Set<IToolDescriptor> enabledTools = toolController
				.getEnabledTools();

		for (final ToolItem toolItem : toolBar.getItems()) {
			toolItem.setEnabled(enabledTools.contains(toolItem.getData()));
			toolItem.setSelection(activeTool == toolItem.getData());
		}
	}

	@Override
	public int computePreferredSize(final boolean width,
			final int availableParallel, final int availablePerpendicular,
			final int preferredResult) {
		final Point size = toolBar.computeSize(SWT.DEFAULT, SWT.DEFAULT);

		return width ? size.x + 10 : size.y;
	}

	@Override
	public int getSizeFlags(final boolean width) {
		return width ? SWT.MIN : (SWT.MAX | SWT.MIN);
	}

}
