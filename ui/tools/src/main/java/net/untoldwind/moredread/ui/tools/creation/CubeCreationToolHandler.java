package net.untoldwind.moredread.ui.tools.creation;

import net.untoldwind.moredread.model.generator.AbstractCenterSizeGenerator;
import net.untoldwind.moredread.model.generator.CubeMeshGenerator;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.ui.tools.spi.IToolAdapter;

import com.jme.math.Vector3f;

public class CubeCreationToolHandler extends
		AbstractCenterSizeCreationToolHandler {

	@Override
	protected IToolAdapter createToolAdapter(final Scene scene) {
		return new CubeCreateAdapter(scene);
	}

	public class CubeCreateAdapter extends
			AbstractCenterSizeCreationToolAdapter {
		public CubeCreateAdapter(final Scene scene) {
			super(scene);
		}

		@Override
		protected AbstractCenterSizeGenerator createGenerator(
				final Vector3f point) {
			return new CubeMeshGenerator(point, 0.0f);
		}
	}
}
