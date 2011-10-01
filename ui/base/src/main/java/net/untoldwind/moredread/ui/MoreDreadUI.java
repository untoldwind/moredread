package net.untoldwind.moredread.ui;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import net.untoldwind.moredread.annotations.Singleton;
import net.untoldwind.moredread.jme.MoreDreadJME;
import net.untoldwind.moredread.model.io.IFileIODescriptor;
import net.untoldwind.moredread.model.io.ModelIOPlugin;
import net.untoldwind.moredread.model.scene.ISceneHolder;
import net.untoldwind.moredread.model.scene.SceneHolder;
import net.untoldwind.moredread.ui.canvas.MDCanvasConstructor;
import net.untoldwind.moredread.ui.options.generator.GeneratorOptionViewAdapterFactory;
import net.untoldwind.moredread.ui.tools.UIToolsPlugin;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.jme.system.DisplaySystem;

/**
 * The activator class controls the plug-in life cycle
 */
@Singleton
public class MoreDreadUI extends AbstractUIPlugin {

	private ResourceBundle resourceBundle;

	private final Map<String, Image> images = new HashMap<String, Image>();

	private ISceneHolder sceneHolder;

	// The plug-in ID
	public static final String PLUGIN_ID = "net.untoldwind.moredread.ui.base";

	public static final String GENERATOR_OPTIONS_EXTENSION_ID = "net.untoldwind.moredread.ui.generatorOptionViews";

	// The shared instance
	private static MoreDreadUI plugin;

	/**
	 * The constructor
	 */
	public MoreDreadUI() {
	}

	/**
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		final IExtensionPoint toolExtensionPoint = registry
				.getExtensionPoint(GENERATOR_OPTIONS_EXTENSION_ID);

		for (final IConfigurationElement element : toolExtensionPoint
				.getConfigurationElements()) {
			if ("generatorOptionView".equals(element.getName())) {
				final Class<?> generatorClass = Class.forName(element
						.getAttribute("generatorClass"));
				final Class<?> optionViewClass = Class.forName(element
						.getAttribute("optionViewClass"));

				Platform.getAdapterManager().registerAdapters(
						new GeneratorOptionViewAdapterFactory(generatorClass,
								optionViewClass), generatorClass);
			}
		}

		initializeScene();

		final DisplaySystem displaySystem = MoreDreadJME.getDefault()
				.getDisplaySystem();
		displaySystem
				.registerCanvasConstructor("MD", MDCanvasConstructor.class);
	}

	/**
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static MoreDreadUI getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(final String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	/**
	 * Get an image from the plugin path.
	 * 
	 * @param path
	 *            The path relative to the plugin
	 * @return Image for <tt>path</tt>
	 */
	public synchronized Image getImage(final String path) {
		Image image = images.get(path);

		if (image != null) {
			return image;
		}

		final ImageDescriptor imageDescriptor = getImageDescriptor(path);

		if (imageDescriptor != null) {
			image = imageDescriptor.createImage();
		} else {
			image = PlatformUI.getWorkbench().getSharedImages()
					.getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}

		images.put(path, image);

		return image;
	}

	public String getString(final String key) {
		if (resourceBundle == null) {
			resourceBundle = Platform.getResourceBundle(getBundle());
		}

		return resourceBundle.getString(key);
	}

	public void log(final Throwable e) {
		getLog().log(
				new Status(Status.ERROR, PLUGIN_ID, Status.ERROR, e.toString(),
						e));
	}

	protected void initializeScene() {
		sceneHolder = new SceneHolder();

		// TODO: Nice for testing
		final File testSceneFile = new File(System.getProperty("user.home"),
				"test1.muf");

		if (testSceneFile.canRead()) {
			final IFileIODescriptor fileIO = ModelIOPlugin.getDefault()
					.getFileIO("net.untoldwind.moredread.model.io.muf");

			try {
				fileIO.getModelReader().readScene(sceneHolder,
						new FileInputStream(testSceneFile));
			} catch (final Exception e) {
				log(e);
			}
		} else {
			sceneHolder.createScene();
		}

		UIToolsPlugin.getDefault().getToolController()
				.setSceneHolder(sceneHolder);
	}

	public ISceneHolder getSceneHolder() {
		return sceneHolder;
	}
}
