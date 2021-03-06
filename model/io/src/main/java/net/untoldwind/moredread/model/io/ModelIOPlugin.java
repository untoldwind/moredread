package net.untoldwind.moredread.model.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.untoldwind.moredread.model.io.impl.FileIODescriptor;
import net.untoldwind.moredread.model.scene.ISceneHolder;
import net.untoldwind.moredread.model.scene.Scene;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;
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

	private Map<String, IFileIODescriptor> fileIOById;

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

		final Map<String, IFileIODescriptor> newFileIOById = new TreeMap<String, IFileIODescriptor>();

		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		final IExtensionPoint toolExtensionPoint = registry
				.getExtensionPoint(IO_EXTENSION_ID);

		for (final IConfigurationElement element : toolExtensionPoint
				.getConfigurationElements()) {
			if ("file-io".equals(element.getName())) {
				final FileIODescriptor fileReader = new FileIODescriptor(
						element);
				newFileIOById.put(fileReader.getId(), fileReader);
			}
		}
		fileIOById = newFileIOById;
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

	public IFileIODescriptor getFileIO(final String id) {
		return fileIOById.get(id);
	}

	public void sceneLoad(final ISceneHolder sceneHolder) {
		final FileDialog fileDialog = new FileDialog(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(), SWT.OPEN);

		final Map<String, String> fileExtensions = new LinkedHashMap<String, String>();
		final List<IFileIODescriptor> fileIODescriptors = new ArrayList<IFileIODescriptor>();
		for (final IFileIODescriptor descriptor : fileIOById.values()) {
			if (descriptor.getExtension() != null) {
				fileExtensions.put(descriptor.getExtension(),
						descriptor.getLabel());
				fileIODescriptors.add(descriptor);
			}
		}
		fileDialog.setFilterPath(System.getProperty("user.home"));
		fileDialog.setText("Open Scene ...");
		fileDialog.setFilterExtensions(fileExtensions.keySet().toArray(
				new String[fileExtensions.size()]));
		fileDialog.setFilterNames(fileExtensions.values().toArray(
				new String[fileExtensions.size()]));
		String fileName = fileDialog.open();

		if (fileName == null) {
			return;
		}

		try {
			final IFileIODescriptor fileIODescriptor = fileIODescriptors
					.get(fileDialog.getFilterIndex());

			if (!fileName.endsWith(fileIODescriptor.getExtension())) {
				fileName += "." + fileIODescriptor.getExtension();
			}

			final FileInputStream in = new FileInputStream(fileName);

			fileIODescriptor.getModelReader().readScene(sceneHolder, in);
		} catch (final IOException e) {
			log(e);
		}
	}

	public void sceneSave(final Scene scene) {
		String fileName = scene.getSceneMetadata().getFileName();

		if (fileName == null) {
			final FileDialog fileDialog = new FileDialog(PlatformUI
					.getWorkbench().getActiveWorkbenchWindow().getShell(),
					SWT.SAVE);

			final Map<String, String> fileExtensions = new LinkedHashMap<String, String>();
			final List<IFileIODescriptor> fileIODescriptors = new ArrayList<IFileIODescriptor>();
			for (final IFileIODescriptor descriptor : fileIOById.values()) {
				if (descriptor.getExtension() != null) {
					fileExtensions.put(descriptor.getExtension(),
							descriptor.getLabel());
					fileIODescriptors.add(descriptor);
				}
			}
			fileDialog.setFilterPath(System.getProperty("user.home"));
			fileDialog.setText("Save Scene ...");
			fileDialog.setFilterExtensions(fileExtensions.keySet().toArray(
					new String[fileExtensions.size()]));
			fileDialog.setFilterNames(fileExtensions.values().toArray(
					new String[fileExtensions.size()]));
			fileName = fileDialog.open();

			if (fileName == null) {
				return;
			}

			try {
				final IFileIODescriptor fileIODescriptor = fileIODescriptors
						.get(fileDialog.getFilterIndex());

				if (!fileName.endsWith(fileIODescriptor.getExtension())) {
					fileName += "." + fileIODescriptor.getExtension();
				}

				final FileOutputStream out = new FileOutputStream(fileName);

				fileIODescriptor.getModelWriter().writeScene(scene, out);

				scene.getSceneMetadata().setFileIOId(fileIODescriptor.getId());
				scene.getSceneMetadata().setFileName(fileName);
			} catch (final IOException e) {
				log(e);
			}
		} else {
			try {
				final IFileIODescriptor fileIODescriptor = fileIOById.get(scene
						.getSceneMetadata().getFileIOId());

				final FileOutputStream out = new FileOutputStream(fileName);

				fileIODescriptor.getModelWriter().writeScene(scene, out);
			} catch (final IOException e) {
				log(e);
			}
		}
	}

	public void sceneSaveAs(final Scene scene) {
		final FileDialog fileDialog = new FileDialog(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(), SWT.SAVE);

		final Map<String, String> fileExtensions = new LinkedHashMap<String, String>();
		final List<IFileIODescriptor> fileIODescriptors = new ArrayList<IFileIODescriptor>();
		for (final IFileIODescriptor descriptor : fileIOById.values()) {
			if (descriptor.getExtension() != null) {
				fileExtensions.put(descriptor.getExtension(),
						descriptor.getLabel());
				fileIODescriptors.add(descriptor);
			}
		}
		fileDialog.setFilterPath(System.getProperty("user.home"));
		fileDialog.setText("Save Scene As ...");
		fileDialog.setFilterExtensions(fileExtensions.keySet().toArray(
				new String[fileExtensions.size()]));
		fileDialog.setFilterNames(fileExtensions.values().toArray(
				new String[fileExtensions.size()]));
		String fileName = fileDialog.open();

		if (fileName == null) {
			return;
		}

		try {
			final IFileIODescriptor fileIODescriptor = fileIODescriptors
					.get(fileDialog.getFilterIndex());

			if (!fileName.endsWith(fileIODescriptor.getExtension())) {
				fileName += "." + fileIODescriptor.getExtension();
			}

			final FileOutputStream out = new FileOutputStream(fileName);

			fileIODescriptor.getModelWriter().writeScene(scene, out);

		} catch (final IOException e) {
			log(e);
		}
	}

	public void log(final Throwable e) {
		getLog().log(
				new Status(Status.ERROR, PLUGIN_ID, Status.ERROR, e.toString(),
						e));
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
