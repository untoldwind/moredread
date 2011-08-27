package net.untoldwind.moredread.ui.tools;

import java.util.Collection;
import java.util.Set;

import net.untoldwind.moredread.model.enums.SelectionMode;
import net.untoldwind.moredread.model.scene.ISceneSelection;

public interface IToolController {
	public Collection<? extends IToolCategoryDescriptor> getToolCategories();

	public IToolCategoryDescriptor getToolCategory(final String categoryId);

	public IToolDescriptor getTool(final String toolId);

	IToolDescriptor getActiveTool();

	Set<IToolDescriptor> getEnabledTools();

	void setSelectionMode(SelectionMode selectionMode);

	void setSceneSelection(ISceneSelection sceneSelection);
}
