package net.untoldwind.moredread.ui.tools.creation;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.controls.Modifier;
import net.untoldwind.moredread.ui.controls.impl.PlaneRestrictedModelControl;
import net.untoldwind.moredread.ui.tools.IDisplaySystem;
import net.untoldwind.moredread.ui.tools.spi.IToolAdapter;
import net.untoldwind.moredread.ui.tools.spi.IToolHandler;

import com.jme.math.Vector3f;

public class CubeCreationToolHandler implements IToolHandler {

	@Override
	public void activate(final Scene scene) {
	}

	@Override
	public List<? extends IModelControl> getModelControls(final Scene scene,
			final IDisplaySystem displaySystem) {
		final List<IModelControl> controls = new ArrayList<IModelControl>();

		controls.add(new PlaneRestrictedModelControl(new CubeCreateAdapter()));

		return controls;
	}

	public static class CubeCreateAdapter implements IToolAdapter {
		Vector3f position = new Vector3f();

		@Override
		public Vector3f getCenter() {
			return position;
		}

		@Override
		public boolean handleMove(final IModelControl modelControl,
				final Vector3f point, final EnumSet<Modifier> modifiers) {
			System.out.println(point);

			this.position.set(point);

			modelControl.updatePositions();

			return true;
		}

		@Override
		public void handleClick(final Vector3f point,
				final EnumSet<Modifier> modifiers) {
			// TODO Auto-generated method stub

		}

		@Override
		public void handleDragStart(final Vector3f point,
				final EnumSet<Modifier> modifiers) {
			// Do nothing
		}

		@Override
		public void handleDragMove(final Vector3f point,
				final EnumSet<Modifier> modifiers) {
			// TODO Auto-generated method stub

		}

		@Override
		public void handleDragEnd(final Vector3f point,
				final EnumSet<Modifier> modifiers) {
			// TODO Auto-generated method stub

		}

	}
}
