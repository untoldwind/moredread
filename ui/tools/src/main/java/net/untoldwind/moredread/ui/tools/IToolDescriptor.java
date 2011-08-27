package net.untoldwind.moredread.ui.tools;

import java.util.List;

import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.ui.controls.IModelControl;

import org.eclipse.jface.resource.ImageDescriptor;

public interface IToolDescriptor {
	String getId();

	String getLabel();

	ToolType getToolType();

	ImageDescriptor getIcon();

	IToolCategoryDescriptor getCategory();

	void activate(Scene scene);

	List<? extends IModelControl> getModelControls(Scene scene,
			IDisplaySystem displaySystem);
}
