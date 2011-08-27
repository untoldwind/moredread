package net.untoldwind.moredread.ui.tools.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.untoldwind.moredread.model.scene.ISceneHolder;
import net.untoldwind.moredread.ui.tools.IToolCategoryDescriptor;
import net.untoldwind.moredread.ui.tools.IToolController;
import net.untoldwind.moredread.ui.tools.IToolDescriptor;

public class ToolController implements IToolController {
	private final Map<String, ToolCategoryDescriptor> toolCategoryRegistry;
	private final Map<String, ToolDescriptor> toolRegistry;
	private final Map<ToolEnablement, List<IToolDescriptor>> toolsByEnablement;

	IToolDescriptor activeTool;
	Set<IToolDescriptor> enabledTools;

	ISceneHolder sceneHolder;

	public ToolController(
			final Map<String, ToolCategoryDescriptor> toolCategoryRegistry,
			final Map<String, ToolDescriptor> toolRegistry,
			final Map<ToolEnablement, List<IToolDescriptor>> toolsByEnablement) {
		this.toolCategoryRegistry = toolCategoryRegistry;
		this.toolRegistry = toolRegistry;
		this.toolsByEnablement = toolsByEnablement;
	}

	@Override
	public Collection<? extends IToolCategoryDescriptor> getToolCategories() {
		return toolCategoryRegistry.values();
	}

	@Override
	public IToolCategoryDescriptor getToolCategory(final String categoryId) {
		return toolCategoryRegistry.get(categoryId);
	}

	@Override
	public IToolDescriptor getTool(final String toolId) {
		return toolRegistry.get(toolId);
	}

	@Override
	public IToolDescriptor getActiveTool() {
		return activeTool;
	}

	@Override
	public Set<IToolDescriptor> getEnabledTools() {
		final Set<IToolDescriptor> enabledTools = new HashSet<IToolDescriptor>();

		for (final Map.Entry<ToolEnablement, List<IToolDescriptor>> entry : toolsByEnablement
				.entrySet()) {
			if (entry.getKey().matches(sceneHolder.getSelectionMode(),
					sceneHolder.getScene().getSceneSelection())) {
				enabledTools.addAll(entry.getValue());
			}
		}

		return enabledTools;
	}

	@Override
	public void setSceneHolder(final ISceneHolder sceneHolder) {
		this.sceneHolder = sceneHolder;
	}
}
