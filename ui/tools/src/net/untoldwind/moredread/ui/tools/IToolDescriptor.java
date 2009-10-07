package net.untoldwind.moredread.ui.tools;

import java.util.List;

import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.tools.spi.IToolHandler;

import org.eclipse.jface.resource.ImageDescriptor;

public interface IToolDescriptor {
	String getId();

	String getLabel();

	ImageDescriptor getIcon();

	IToolHandler getToolHandler();

	List<? extends IModelControl> getModelControls(Scene scene,
			IDisplaySystem displaySystem);
}
