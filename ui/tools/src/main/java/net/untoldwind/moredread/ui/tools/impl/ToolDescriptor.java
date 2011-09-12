package net.untoldwind.moredread.ui.tools.impl;

import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.controls.IViewport;
import net.untoldwind.moredread.ui.tools.IToolCategoryDescriptor;
import net.untoldwind.moredread.ui.tools.IToolDescriptor;
import net.untoldwind.moredread.ui.tools.ToolType;
import net.untoldwind.moredread.ui.tools.spi.IToolHandler;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class ToolDescriptor implements IToolDescriptor {
	private final String pluginId;
	private final String id;
	private final String label;
	private final ToolType toolType;
	private final String icon;
	private final IToolHandler toolHandler;
	private final String categoryId;
	private IToolCategoryDescriptor category;
	private final List<ToolEnablement> enablements;
	private ToolController toolController;

	public ToolDescriptor(final IConfigurationElement configElement)
			throws CoreException {
		pluginId = configElement.getContributor().getName();
		id = configElement.getAttribute("id");
		label = configElement.getAttribute("label");
		toolType = ToolType.valueOf(configElement.getAttribute("type"));
		icon = configElement.getAttribute("icon");
		toolHandler = (IToolHandler) configElement
				.createExecutableExtension("class");
		categoryId = configElement.getAttribute("categoryId");

		enablements = new ArrayList<ToolEnablement>();
		for (final IConfigurationElement activationElement : configElement
				.getChildren()) {
			enablements.add(new ToolEnablement(activationElement));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public IToolCategoryDescriptor getCategory() {
		return category;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ToolType getToolType() {
		return toolType;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ImageDescriptor getIcon() {
		if (icon != null) {
			return AbstractUIPlugin.imageDescriptorFromPlugin(pluginId, icon);
		}
		return null;
	}

	public List<ToolEnablement> getEnablements() {
		return enablements;
	}

	public String getCategoryId() {
		return categoryId;
	}

	void setCategory(final IToolCategoryDescriptor category) {
		this.category = category;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void activate(final Scene scene) {
		toolHandler.activated(toolController, scene);

		toolController.setActiveTool(this);
	}

	@Override
	public void abort(final Scene scene) {
		toolHandler.aborted(toolController, scene);

		toolController.setActiveTool(null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<? extends IModelControl> getModelControls(final Scene scene,
			final IViewport viewport) {
		return toolHandler.getModelControls(scene, viewport);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((pluginId == null) ? 0 : pluginId.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ToolDescriptor other = (ToolDescriptor) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (pluginId == null) {
			if (other.pluginId != null) {
				return false;
			}
		} else if (!pluginId.equals(other.pluginId)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("ToolDescriptor [pluginId=");
		builder.append(pluginId);
		builder.append(", id=");
		builder.append(id);
		builder.append(", label=");
		builder.append(label);
		builder.append(", toolType=");
		builder.append(toolType);
		builder.append(", icon=");
		builder.append(icon);
		builder.append(", categoryId=");
		builder.append(categoryId);
		builder.append(", enablements=");
		builder.append(enablements);
		builder.append("]");
		return builder.toString();
	}

	void setToolController(final ToolController toolController) {
		this.toolController = toolController;
	}

}
