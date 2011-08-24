package net.untoldwind.moredread.ui.tools;

import java.util.List;

public interface IToolCategoryDescriptor {
	String getId();

	String getLabel();

	boolean isVisible();

	List<IToolDescriptor> getTools();
}
