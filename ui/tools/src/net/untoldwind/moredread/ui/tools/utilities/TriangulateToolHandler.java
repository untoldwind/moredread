package net.untoldwind.moredread.ui.tools.utilities;

import java.util.Collections;
import java.util.List;

import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.tools.IDisplaySystem;
import net.untoldwind.moredread.ui.tools.spi.IToolHandler;

public class TriangulateToolHandler implements IToolHandler {

	@Override
	public List<? extends IModelControl> getModelControls(final Scene scene,
			final IDisplaySystem displaySystem) {
		return Collections.emptyList();
	}

}
