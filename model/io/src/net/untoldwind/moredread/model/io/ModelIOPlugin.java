package net.untoldwind.moredread.model.io;

import java.util.HashMap;
import java.util.Map;

import net.untoldwind.moredread.model.io.impl.FileReaderDescriptor;
import net.untoldwind.moredread.model.io.impl.FileWriterDescriptor;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class ModelIOPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "net.untoldwind.moredread.model.io";

	public static final String IO_EXTENSION_ID = "net.untoldwind.moredread.model.io";

	// The shared instance
	private static ModelIOPlugin plugin;

	private Map<String, IFileReaderDescriptor> fileReadersById;
	private Map<String, IFileWriterDescriptor> fileWritersById;

	/**
	 * The constructor
	 */
	public ModelIOPlugin() {
	}

	/**
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 *      )
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		final Map<String, IFileReaderDescriptor> newFileReadersById = new HashMap<String, IFileReaderDescriptor>();
		final Map<String, IFileWriterDescriptor> newFileWritersById = new HashMap<String, IFileWriterDescriptor>();

		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		final IExtensionPoint toolExtensionPoint = registry
				.getExtensionPoint(IO_EXTENSION_ID);

		for (final IConfigurationElement element : toolExtensionPoint
				.getConfigurationElements()) {
			if ("file-reader".equals(element.getName())) {
				final FileReaderDescriptor fileReader = new FileReaderDescriptor(
						element);
				newFileReadersById.put(fileReader.getId(), fileReader);
			} else if ("file-writer".equals(element.getName())) {
				final FileWriterDescriptor fileWriter = new FileWriterDescriptor(
						element);
				newFileWritersById.put(fileWriter.getId(), fileWriter);
			}
		}
		fileReadersById = newFileReadersById;
		fileWritersById = newFileWritersById;
	}

	/**
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 *      )
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public IFileReaderDescriptor getFileReader(final String id) {
		return fileReadersById.get(id);
	}

	public IFileWriterDescriptor getFileWriter(final String id) {
		return fileWritersById.get(id);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static ModelIOPlugin getDefault() {
		return plugin;
	}

}
