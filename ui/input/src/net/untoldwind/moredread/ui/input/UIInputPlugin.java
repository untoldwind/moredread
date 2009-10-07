package net.untoldwind.moredread.ui.input;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.untoldwind.moredread.ui.input.event.IUIInputReceiver;
import net.untoldwind.moredread.ui.input.event.MoveDepthCameraUpdate;
import net.untoldwind.moredread.ui.input.event.MoveHorizontalCameraUpdate;
import net.untoldwind.moredread.ui.input.event.MoveVerticalCameraUpdate;
import net.untoldwind.moredread.ui.input.event.RotateAroundXCameraUpdate;
import net.untoldwind.moredread.ui.input.event.RotateAroundYCameraUpdate;
import net.untoldwind.moredread.ui.input.event.RotateAroundZCameraUpdate;
import net.untoldwind.moredread.ui.input.impl.CameraUpdateComponentAdapter;
import net.untoldwind.moredread.ui.input.impl.ControllerPollTask;
import net.untoldwind.moredread.ui.input.impl.IComponentAdapter;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class UIInputPlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "net.untoldwind.moredread.ui.input";

	// The shared instance
	private static UIInputPlugin plugin;

	private Timer pollTimer;

	/**
	 * The constructor
	 */
	public UIInputPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public void log(final Throwable e) {
		getLog().log(
				new Status(Status.ERROR, PLUGIN_ID, Status.ERROR, e.toString(),
						e));
	}

	public void registerInputReceiver(final IUIInputReceiver inputReceiver) {
		try {
			final ControllerEnvironment ce = ControllerEnvironment
					.getDefaultEnvironment();

			for (final Controller controller : ce.getControllers()) {
				if ("SpaceNavigator".equals(controller.getName())) {
					final Map<Component.Identifier, IComponentAdapter> adapters = new HashMap<Component.Identifier, IComponentAdapter>();

					adapters.put(Component.Identifier.Axis.X,
							new CameraUpdateComponentAdapter(
									MoveHorizontalCameraUpdate.class, 0.02f,
									-2f));
					adapters
							.put(Component.Identifier.Axis.Y,
									new CameraUpdateComponentAdapter(
											MoveVerticalCameraUpdate.class,
											0.02f, -2f));
					adapters.put(Component.Identifier.Axis.Z,
							new CameraUpdateComponentAdapter(
									MoveDepthCameraUpdate.class, 0.02f, 2f));
					adapters.put(Component.Identifier.Axis.RX,
							new CameraUpdateComponentAdapter(
									RotateAroundYCameraUpdate.class, 0.02f,
									-0.4f));
					adapters.put(Component.Identifier.Axis.RY,
							new CameraUpdateComponentAdapter(
									RotateAroundXCameraUpdate.class, 0.02f,
									-0.4f));
					adapters.put(Component.Identifier.Axis.RZ,
							new CameraUpdateComponentAdapter(
									RotateAroundZCameraUpdate.class, 0.02f,
									0.4f));

					if (pollTimer == null) {
						pollTimer = new Timer();
					}

					pollTimer.schedule(new ControllerPollTask(inputReceiver,
							controller, adapters), 1000L, 50L);
				}
			}
		} catch (final Exception e) {
			log(e);
		}
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static UIInputPlugin getDefault() {
		return plugin;
	}

}
