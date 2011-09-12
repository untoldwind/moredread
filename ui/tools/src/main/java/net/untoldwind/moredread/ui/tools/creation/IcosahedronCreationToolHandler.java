package net.untoldwind.moredread.ui.tools.creation;

import net.untoldwind.moredread.model.generator.AbstractCenterSizeGenerator;
import net.untoldwind.moredread.model.generator.IcosahedronMeshGenerator;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.ui.tools.spi.IToolAdapter;

import com.jme.math.Vector3f;

public class IcosahedronCreationToolHandler extends
		AbstractCenterSizeCreationToolHandler {
	@Override
	protected IToolAdapter createToolAdapter(final Scene scene) {
		return new IcosahedronToolAdapter(scene);
	}

	public class IcosahedronToolAdapter extends
			AbstractCenterSizeCreationToolAdapter {
		public IcosahedronToolAdapter(final Scene scene) {
			super(scene);
		}

		@Override
		protected AbstractCenterSizeGenerator createGenerator(
				final Vector3f point) {
			return new IcosahedronMeshGenerator(point, 0.0f);
		}
	}
}
