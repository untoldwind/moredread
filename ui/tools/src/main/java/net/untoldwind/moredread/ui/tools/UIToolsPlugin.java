package net.untoldwind.moredread.ui.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.untoldwind.moredread.annotations.Singleton;
import net.untoldwind.moredread.ui.tools.impl.ToolCategoryDescriptor;
import net.untoldwind.moredread.ui.tools.impl.ToolController;
import net.untoldwind.moredread.ui.tools.impl.ToolDescriptor;
import net.untoldwind.moredread.ui.tools.impl.ToolEnablement;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

@Singleton
public class UIToolsPlugin extends AbstractUIPlugin {
	// The plug-in ID
	public static final String PLUGIN_ID = "net.untoldwind.moredread.ui.controls";

	public static final String TOOLS_EXTENSION_ID = "net.untoldwind.moredread.ui.tools";

	// The shared instance
	private static UIToolsPlugin plugin;

	private IToolController toolController;

	/**
	 * The constructor
	 */
	public UIToolsPlugin() {
	}

	/**
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		final Map<String, ToolDescriptor> toolRegistry = new HashMap<String, ToolDescriptor>();
		final Map<String, ToolCategoryDescriptor> toolCategoryRegistry = new HashMap<String, ToolCategoryDescriptor>();
		final Map<ToolEnablement, List<IToolDescriptor>> toolsByEnablement = new HashMap<ToolEnablement, List<IToolDescriptor>>();

		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		final IExtensionPoint toolExtensionPoint = registry
				.getExtensionPoint(TOOLS_EXTENSION_ID);

		for (final IConfigurationElement element : toolExtensionPoint
				.getConfigurationElements()) {
			if ("tool".equals(element.getName())) {
				final ToolDescriptor tool = new ToolDescriptor(element);

				toolRegistry.put(tool.getId(), tool);
				for (final ToolEnablement enablement : tool.getEnablements()) {
					List<IToolDescriptor> tools = toolsByEnablement
							.get(enablement);

					if (tools == null) {
						tools = new ArrayList<IToolDescriptor>();
						toolsByEnablement.put(enablement, tools);
					}
					tools.add(tool);
				}
			} else if ("category".equals(element.getName())) {
				final ToolCategoryDescriptor category = new ToolCategoryDescriptor(
						element);

				toolCategoryRegistry.put(category.getId(), category);
			}
		}
		for (final ToolDescriptor toolDescriptor : toolRegistry.values()) {
			final ToolCategoryDescriptor category = toolCategoryRegistry
					.get(toolDescriptor.getCategoryId());

			if (category != null) {
				category.addTool(toolDescriptor);
			}
		}

		toolController = new ToolController(toolCategoryRegistry, toolRegistry,
				toolsByEnablement);
	}

	/**
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public IToolController getToolController() {
		return toolController;
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static UIToolsPlugin getDefault() {
		return plugin;
	}
}
