package net.untoldwind.moredread.ui.tools.spi;

import java.util.List;

import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.tools.IDisplaySystem;

public interface IToolHandler {

	List<? extends IModelControl> getModelControls(Scene scene,
			IDisplaySystem displaySystem);
}
