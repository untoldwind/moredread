package net.untoldwind.moredread.jme;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import com.jme.input.KeyInput;
import com.jme.input.MouseInput;
import com.jme.system.DisplaySystem;

public class MoreDreadJME extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "net.untoldwind.moredread.jme";

	// The shared instance
	private static MoreDreadJME plugin;

	private DisplaySystem displaySystem;

	/**
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		Logger.getLogger("com.jme").setLevel(Level.OFF);

		displaySystem = DisplaySystem.getDisplaySystem("LWJGL");
		KeyInput.setProvider(MDKeyInput.class.getCanonicalName());
		MouseInput.setProvider(MDMouseInput.class.getCanonicalName());

	}

	/**
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public DisplaySystem getDisplaySystem() {
		return displaySystem;
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static MoreDreadJME getDefault() {
		return plugin;
	}

}
