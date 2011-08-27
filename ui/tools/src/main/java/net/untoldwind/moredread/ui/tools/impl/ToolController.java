package net.untoldwind.moredread.ui.tools.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.untoldwind.moredread.model.enums.SelectionMode;
import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.ISceneSelection;
import net.untoldwind.moredread.model.scene.SceneSelection.EdgeSelection;
import net.untoldwind.moredread.model.scene.SceneSelection.FaceSelection;
import net.untoldwind.moredread.model.scene.SceneSelection.VertexSelection;
import net.untoldwind.moredread.ui.tools.IToolCategoryDescriptor;
import net.untoldwind.moredread.ui.tools.IToolController;
import net.untoldwind.moredread.ui.tools.IToolDescriptor;

public class ToolController implements IToolController {
	private final Map<String, ToolCategoryDescriptor> toolCategoryRegistry;
	private final Map<String, ToolDescriptor> toolRegistry;
	private final Map<ToolEnablement, List<IToolDescriptor>> toolsByEnablement;

	IToolDescriptor activeTool;
	Set<IToolDescriptor> enabledTools;

	SelectionMode currentSelectionMode;
	ISceneSelection currentSceneSelection;

	public ToolController(
			final Map<String, ToolCategoryDescriptor> toolCategoryRegistry,
			final Map<String, ToolDescriptor> toolRegistry,
			final Map<ToolEnablement, List<IToolDescriptor>> toolsByEnablement) {
		this.toolCategoryRegistry = toolCategoryRegistry;
		this.toolRegistry = toolRegistry;
		this.toolsByEnablement = toolsByEnablement;
		this.currentSelectionMode = SelectionMode.OBJECT;
		this.currentSceneSelection = new ISceneSelection() {
			@Override
			public Set<INode> getSelectedNodes() {
				return Collections.emptySet();
			}

			@Override
			public Set<FaceSelection> getSelectedFaces() {
				return Collections.emptySet();
			}

			@Override
			public Set<EdgeSelection> getSelectedEdges() {
				return Collections.emptySet();
			}

			@Override
			public Set<VertexSelection> getSelectedVertices() {
				return Collections.emptySet();
			}
		};

		updateEnabledTools();
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
		return enabledTools;
	}

	@Override
	public void setSelectionMode(final SelectionMode selectionMode) {
		if (selectionMode != currentSelectionMode) {
			currentSelectionMode = selectionMode;

			updateEnabledTools();
		}
	}

	@Override
	public void setSceneSelection(final ISceneSelection sceneSelection) {
		currentSceneSelection = sceneSelection;

		updateEnabledTools();
	}

	private void updateEnabledTools() {
		final Set<IToolDescriptor> newEnabledTools = new HashSet<IToolDescriptor>();

		for (final Map.Entry<ToolEnablement, List<IToolDescriptor>> entry : toolsByEnablement
				.entrySet()) {
			if (entry.getKey().matches(currentSelectionMode,
					currentSceneSelection)) {
				newEnabledTools.addAll(entry.getValue());
			}
		}
		this.enabledTools = newEnabledTools;
	}

}
