package net.untoldwind.moredread.ui.tools.impl;

import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.tools.IDisplaySystem;
import net.untoldwind.moredread.ui.tools.IToolDescriptor;
import net.untoldwind.moredread.ui.tools.spi.IToolHandler;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class ToolDescriptor implements IToolDescriptor {
	private final String pluginId;
	private final String id;
	private final String label;
	private final String icon;
	private final IToolHandler toolHandler;
	private final String categoryId;
	private final List<ToolActivation> activations;

	public ToolDescriptor(final IConfigurationElement configElement)
			throws CoreException {

		pluginId = configElement.getContributor().getName();
		id = configElement.getAttribute("id");
		label = configElement.getAttribute("label");
		icon = configElement.getAttribute("icon");
		toolHandler = (IToolHandler) configElement
				.createExecutableExtension("class");
		categoryId = configElement.getAttribute("categoryId");

		activations = new ArrayList<ToolActivation>();
		for (final IConfigurationElement activationElement : configElement
				.getChildren()) {
			activations.add(new ToolActivation(activationElement));
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IToolHandler getToolHandler() {
		return toolHandler;
	}

	public List<ToolActivation> getActivations() {
		return activations;
	}

	public String getCategoryId() {
		return categoryId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<? extends IModelControl> getModelControls(final Scene scene,
			final IDisplaySystem displaySystem) {
		return toolHandler.getModelControls(scene, displaySystem);
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

}
