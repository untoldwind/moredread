package net.untoldwind.moredread.ui.tools;

import java.util.Collection;
import java.util.Set;

import net.untoldwind.moredread.model.scene.ISceneHolder;

public interface IToolController {
	public Collection<? extends IToolCategoryDescriptor> getToolCategories();

	public IToolCategoryDescriptor getToolCategory(final String categoryId);

	public IToolDescriptor getTool(final String toolId);

	IToolDescriptor getActiveTool();

	Set<IToolDescriptor> getEnabledTools();

	void setSceneHolder(ISceneHolder sceneHolder);

	void addToolActivationListener(IToolActivationListener listener);

	void removeToolActivationListener(IToolActivationListener listener);

	void setActiveTool(IToolDescriptor tool);

	void abortActiveTool();

	void completeActiveTool();
}
