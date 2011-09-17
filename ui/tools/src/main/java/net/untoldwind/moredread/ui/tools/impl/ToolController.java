package net.untoldwind.moredread.ui.tools.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.untoldwind.moredread.model.scene.ISceneHolder;
import net.untoldwind.moredread.model.scene.event.ISceneSelectionModeListener;
import net.untoldwind.moredread.model.scene.event.SceneSelectionModeEvent;
import net.untoldwind.moredread.ui.tools.IToolActivationListener;
import net.untoldwind.moredread.ui.tools.IToolCategoryDescriptor;
import net.untoldwind.moredread.ui.tools.IToolController;
import net.untoldwind.moredread.ui.tools.IToolDescriptor;
import net.untoldwind.moredread.ui.tools.ToolType;

public class ToolController implements IToolController,
		ISceneSelectionModeListener {
	private final Map<String, ToolCategoryDescriptor> toolCategoryRegistry;
	private final Map<String, ToolDescriptor> toolRegistry;
	private final Map<ToolEnablement, List<IToolDescriptor>> toolsByEnablement;
	private final List<IToolActivationListener> activationListeners;

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
		this.activationListeners = new ArrayList<IToolActivationListener>();

		for (final ToolDescriptor toolDescriptor : toolRegistry.values()) {
			toolDescriptor.setToolController(this);
		}
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
		if (activeTool == null) {
			activeTool = getDefaultTool();
		}
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
		this.sceneHolder.addSceneSelectionModeListener(this);
	}

	@Override
	public void addToolActivationListener(final IToolActivationListener listener) {
		activationListeners.add(listener);
	}

	@Override
	public void removeToolActivationListener(
			final IToolActivationListener listener) {
		activationListeners.remove(listener);
	}

	@Override
	public void setActiveTool(IToolDescriptor toolDescriptor) {
		if (toolDescriptor == null) {
			toolDescriptor = getDefaultTool();
		}

		if (activeTool != toolDescriptor
				&& toolDescriptor.getToolType() == ToolType.TOGGLE) {
			this.activeTool = toolDescriptor;

			for (final IToolActivationListener listener : activationListeners) {
				listener.activeToolChanged();
			}
		}
	}

	@Override
	public void abortActiveTool() {
		getActiveTool().abort(sceneHolder.getScene());
	}

	@Override
	public void sceneSelectionModeChanged(final SceneSelectionModeEvent event) {
		getActiveTool().abort(sceneHolder.getScene());
	}

	@Override
	public void completeActiveTool() {
		getActiveTool().complete(sceneHolder.getScene());
	}

	private IToolDescriptor getDefaultTool() {
		for (final IToolDescriptor tool : getEnabledTools()) {
			if (tool.getCategory().isFallback()) {
				return tool;
			}
		}
		throw new RuntimeException("No default tool found in "
				+ getEnabledTools());
	}
}
