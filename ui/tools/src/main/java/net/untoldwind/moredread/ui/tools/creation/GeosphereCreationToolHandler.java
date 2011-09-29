package net.untoldwind.moredread.ui.tools.creation;

import net.untoldwind.moredread.model.generator.AbstractCenterSizeGenerator;
import net.untoldwind.moredread.model.generator.GeosphereMeshGenerator;
import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.ui.tools.spi.IToolAdapter;

public class GeosphereCreationToolHandler extends
		AbstractCenterSizeCreationToolHandler {

	@Override
	protected IToolAdapter createToolAdapter(final Scene scene) {
		return new GeosphereToolAdapter(scene);
	}

	public class GeosphereToolAdapter extends
			AbstractCenterSizeCreationToolAdapter {
		public GeosphereToolAdapter(final Scene scene) {
			super(scene);
		}

		@Override
		protected AbstractCenterSizeGenerator createGenerator(
				final Vector3 point) {
			return new GeosphereMeshGenerator(5, point, 0.0f);
		}

	}

}
