package net.untoldwind.moredread.jme;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import com.jme.input.KeyInput;
import com.jme.input.MouseInput;
import com.jme.system.DisplaySystem;
import com.jmex.swt.input.SWTKeyInput;
import com.jmex.swt.input.SWTMouseInput;
import com.jmex.swt.lwjgl.LWJGLSWTCanvasConstructor;

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
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		displaySystem = DisplaySystem.getDisplaySystem("LWJGL");
		displaySystem.registerCanvasConstructor("SWT",
				LWJGLSWTCanvasConstructor.class);
		KeyInput.setProvider(SWTKeyInput.class.getCanonicalName());
		MouseInput.setProvider(SWTMouseInput.class.getCanonicalName());
	}

	/**
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
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
