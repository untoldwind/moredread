package net.untoldwind.moredread.model;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

public class ModelPlugin extends Plugin {
	public static final String PLUGIN_ID = "net.untoldwind.moredread.model";

	// The shared instance
	private static ModelPlugin plugin;

	public ModelPlugin() {
	}

	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	public void log(final Throwable e) {
		getLog().log(
				new Status(Status.ERROR, PLUGIN_ID, Status.ERROR, e.toString(),
						e));
	}

	public void logWarn(final Class<?> clazz, final String message) {
		getLog().log(
				new Status(Status.WARNING, PLUGIN_ID, clazz.getName() + " : "
						+ message));
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static ModelPlugin getDefault() {
		return plugin;
	}
}
