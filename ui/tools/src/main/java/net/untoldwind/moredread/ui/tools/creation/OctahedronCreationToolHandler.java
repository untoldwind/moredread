package net.untoldwind.moredread.ui.tools.creation;

import net.untoldwind.moredread.model.generator.AbstractCenterSizeGenerator;
import net.untoldwind.moredread.model.generator.OctahedronMeshGenerator;
import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.ui.tools.spi.IToolAdapter;

public class OctahedronCreationToolHandler extends
		AbstractCenterSizeCreationToolHandler {

	@Override
	protected IToolAdapter createToolAdapter(final Scene scene) {
		return new OctahedronToolAdapter(scene);
	}

	public class OctahedronToolAdapter extends
			AbstractCenterSizeCreationToolAdapter {
		public OctahedronToolAdapter(final Scene scene) {
			super(scene);
		}

		@Override
		protected AbstractCenterSizeGenerator createGenerator(
				final Vector3 point) {
			return new OctahedronMeshGenerator(point, 0.0f);
		}

	}
}
