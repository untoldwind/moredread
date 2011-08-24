package net.untoldwind.moredread.ui.tools.impl;

import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.ui.tools.IToolCategoryDescriptor;
import net.untoldwind.moredread.ui.tools.IToolDescriptor;

import org.eclipse.core.runtime.IConfigurationElement;

public class ToolCategoryDescriptor implements IToolCategoryDescriptor {
	private final String id;
	private final String label;
	private final boolean visible;
	private final List<IToolDescriptor> tools;

	public ToolCategoryDescriptor(final IConfigurationElement element) {
		this.id = element.getAttribute("id");
		this.label = element.getAttribute("label");
		this.visible = Boolean.parseBoolean(element.getAttribute("visible"));
		this.tools = new ArrayList<IToolDescriptor>();
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public List<IToolDescriptor> getTools() {
		return tools;
	}

	public void addTool(final IToolDescriptor toolDescriptor) {
		tools.add(toolDescriptor);
	}
}
