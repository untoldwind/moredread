package net.untoldwind.moredread.ui.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.untoldwind.moredread.annotations.Singleton;
import net.untoldwind.moredread.model.enums.SelectionMode;
import net.untoldwind.moredread.model.scene.SceneSelection;
import net.untoldwind.moredread.ui.tools.impl.ToolCategoryDescriptor;
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

	private Map<String, ToolCategoryDescriptor> toolCategoryRegistry;
	private Map<String, ToolDescriptor> toolRegistry;
	private Map<ToolEnablement, List<IToolDescriptor>> toolsByEnablement;

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

		final Map<String, ToolDescriptor> newToolRegistry = new HashMap<String, ToolDescriptor>();
		final Map<String, ToolCategoryDescriptor> newToolCategoryRegistry = new HashMap<String, ToolCategoryDescriptor>();
		final Map<ToolEnablement, List<IToolDescriptor>> newToolsByEnablement = new HashMap<ToolEnablement, List<IToolDescriptor>>();

		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		final IExtensionPoint toolExtensionPoint = registry
				.getExtensionPoint(TOOLS_EXTENSION_ID);

		for (final IConfigurationElement element : toolExtensionPoint
				.getConfigurationElements()) {
			if ("tool".equals(element.getName())) {
				final ToolDescriptor tool = new ToolDescriptor(element);

				newToolRegistry.put(tool.getId(), tool);
				for (final ToolEnablement enablement : tool.getEnablements()) {
					List<IToolDescriptor> tools = newToolsByEnablement
							.get(enablement);

					if (tools == null) {
						tools = new ArrayList<IToolDescriptor>();
						newToolsByEnablement.put(enablement, tools);
					}
					tools.add(tool);
				}
			} else if ("category".equals(element.getName())) {
				final ToolCategoryDescriptor category = new ToolCategoryDescriptor(
						element);

				newToolCategoryRegistry.put(category.getId(), category);
			}
		}
		for (final ToolDescriptor toolDescriptor : newToolRegistry.values()) {
			final ToolCategoryDescriptor category = newToolCategoryRegistry
					.get(toolDescriptor.getCategoryId());

			if (category != null) {
				category.addTool(toolDescriptor);
			}
		}

		toolRegistry = newToolRegistry;
		toolCategoryRegistry = newToolCategoryRegistry;
		toolsByEnablement = newToolsByEnablement;
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
	public static UIToolsPlugin getDefault() {
		return plugin;
	}

	public Collection<? extends IToolCategoryDescriptor> getToolCategories() {
		return toolCategoryRegistry.values();
	}

	public IToolCategoryDescriptor getToolCategory(final String categoryId) {
		return toolCategoryRegistry.get(categoryId);
	}

	public IToolDescriptor getTool(final String toolId) {
		return toolRegistry.get(toolId);
	}

	public Set<IToolDescriptor> getEnabledTools(
			final SelectionMode selectionMode,
			final SceneSelection sceneSelection) {
		final Set<IToolDescriptor> enabledTools = new HashSet<IToolDescriptor>();

		for (final Map.Entry<ToolEnablement, List<IToolDescriptor>> entry : toolsByEnablement
				.entrySet()) {
			if (entry.getKey().matches(selectionMode, sceneSelection)) {
				enabledTools.addAll(entry.getValue());
			}
		}
		return enabledTools;
	}
}
