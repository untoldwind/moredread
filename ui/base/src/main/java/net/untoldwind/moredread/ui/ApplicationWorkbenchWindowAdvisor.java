package net.untoldwind.moredread.ui;

import net.untoldwind.moredread.ui.tools.IToolCategoryDescriptor;
import net.untoldwind.moredread.ui.tools.IToolController;
import net.untoldwind.moredread.ui.tools.UIToolsPlugin;
import net.untoldwind.moredread.ui.views.ToolSelectionView;

import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	public ApplicationWorkbenchWindowAdvisor(
			final IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	@Override
	public ActionBarAdvisor createActionBarAdvisor(
			final IActionBarConfigurer configurer) {
		return new ApplicationActionBarAdvisor(configurer);
	}

	@Override
	public void preWindowOpen() {
		final IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setInitialSize(new Point(1000, 700));
		configurer.setShowCoolBar(false);
		configurer.setShowStatusLine(false);

	}

	@Override
	public void postWindowOpen() {
		try {
			final IToolController toolController = UIToolsPlugin.getDefault()
					.getToolController();

			for (final IToolCategoryDescriptor toolCategory : toolController
					.getToolCategories()) {
				if (toolCategory.isVisible()) {
					getWindowConfigurer()
							.getWindow()
							.getActivePage()
							.showView(ToolSelectionView.ID,
									toolCategory.getId(),
									IWorkbenchPage.VIEW_VISIBLE);
				}
			}
		} catch (final Exception e) {
			MoreDreadUI.getDefault().log(e);
		}
	}

}
