package net.untoldwind.moredread.ui.tools.creation;

import net.untoldwind.moredread.model.generator.AbstractCenterSizeGenerator;
import net.untoldwind.moredread.model.generator.DodecahedronMeshGenerator;
import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.ui.tools.spi.IToolAdapter;

public class DodecahedronCreationToolHandler extends
		AbstractCenterSizeCreationToolHandler {
	@Override
	protected IToolAdapter createToolAdapter(final Scene scene) {
		return new DodecahedronToolAdapter(scene);
	}

	public class DodecahedronToolAdapter extends
			AbstractCenterSizeCreationToolAdapter {
		public DodecahedronToolAdapter(final Scene scene) {
			super(scene);
		}

		@Override
		protected AbstractCenterSizeGenerator createGenerator(
				final Vector3 point) {
			return new DodecahedronMeshGenerator(point, 0.0f);
		}

	}

}
