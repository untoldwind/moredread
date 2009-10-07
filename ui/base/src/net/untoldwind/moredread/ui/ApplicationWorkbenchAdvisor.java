package net.untoldwind.moredread.ui;

import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

/**
 * This workbench advisor creates the window advisor, and specifies the
 * perspective id for the initial window.
 */
public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
			final IWorkbenchWindowConfigurer configurer) {
		return new ApplicationWorkbenchWindowAdvisor(configurer);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getInitialWindowPerspectiveId() {
		return Perspective.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(final IWorkbenchConfigurer configurer) {
		configurer.setSaveAndRestore(true);

		super.initialize(configurer);
	}

}
