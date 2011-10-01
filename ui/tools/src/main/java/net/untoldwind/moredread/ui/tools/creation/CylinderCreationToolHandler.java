package net.untoldwind.moredread.ui.tools.creation;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import net.untoldwind.moredread.model.generator.CylinderGenerator;
import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.scene.GeneratorNode;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.controls.IViewport;
import net.untoldwind.moredread.ui.controls.Modifier;
import net.untoldwind.moredread.ui.controls.impl.PlaneRestrictedModelControl;
import net.untoldwind.moredread.ui.tools.IToolController;
import net.untoldwind.moredread.ui.tools.spi.IToolAdapter;
import net.untoldwind.moredread.ui.tools.spi.IToolHandler;

import com.jme.math.FastMath;

public class CylinderCreationToolHandler implements IToolHandler {
	protected IToolController toolController;
	protected IModelControl activeControl;

	@Override
	public void activated(final IToolController toolController,
			final Scene scene) {
		this.toolController = toolController;
		this.activeControl = new PlaneRestrictedModelControl(
				new CreateCylinderToolAdapter(scene));
	}

	@Override
	public void aborted(final IToolController toolController, final Scene scene) {
		this.activeControl = null;
		scene.getSceneChangeHandler().rollback();
	}

	@Override
	public void completed(final IToolController toolController,
			final Scene scene) {
		this.activeControl = null;
		scene.getSceneChangeHandler().commit();
	}

	@Override
	public List<? extends IModelControl> getModelControls(final Scene scene,
			final IViewport viewport) {
		final List<IModelControl> controls = new ArrayList<IModelControl>();

		if (activeControl != null) {
			controls.add(activeControl);
		}

		return controls;
	}

	public class CreateCylinderToolAdapter implements IToolAdapter {
		Scene scene;
		Vector3 lastPoint = new Vector3();
		Vector3 position = new Vector3();
		boolean directionMode = false;
		CylinderGenerator generator;
		GeneratorNode node;

		public CreateCylinderToolAdapter(final Scene scene) {
			this.scene = scene;
		}

		@Override
		public Vector3 getCenter() {
			return lastPoint;
		}

		@Override
		public Vector3 getFeedbackPoint() {
			return position;
		}

		@Override
		public boolean handleMove(final IModelControl modelControl,
				final Vector3 point, final EnumSet<Modifier> modifiers) {
			this.position.set(point);

			modelControl.updatePositions();

			if (node != null) {
				scene.getSceneChangeHandler().beginUndoable(
						"Create " + generator.getName());

				try {
					if (directionMode) {
						generator.setEndPoint(point.clone());
					} else {
						generator.setRadius(maxDistance(
								generator.getStartPoint(), point));
					}
					node.regenerate();
				} finally {
					scene.getSceneChangeHandler().savepoint();
				}
			}

			return true;
		}

		@Override
		public boolean handleClick(final IModelControl modelControl,
				final Vector3 point, final EnumSet<Modifier> modifiers) {
			if (node == null) {
				generator = new CylinderGenerator();
				generator.setStartPoint(point.clone());
				lastPoint.set(point);
				generator.setEndPoint(point.add(Vector3.UNIT_Z));

				scene.getSceneChangeHandler().beginUndoable(
						"Create " + generator.getName());

				try {
					node = new GeneratorNode(scene, generator);
				} finally {
					scene.getSceneChangeHandler().savepoint();
				}
			} else if (!directionMode) {
				scene.getSceneChangeHandler().beginUndoable(
						"Create " + generator.getName());

				try {
					generator.setRadius(maxDistance(generator.getStartPoint(),
							point));
					node.regenerate();
				} finally {
					scene.getSceneChangeHandler().savepoint();
				}
				directionMode = true;
			} else {
				scene.getSceneChangeHandler().beginUndoable(
						"Create " + generator.getName());

				try {
					generator.setEndPoint(point.clone());
					node.regenerate();
				} finally {
					scene.getSceneChangeHandler().commit();
				}
				node = null;
				generator = null;

				toolController.setActiveTool(null);
			}
			return true;
		}

		@Override
		public boolean handleDragStart(final IModelControl modelControl,
				final Vector3 dragStart, final EnumSet<Modifier> modifiers) {
			return false;
		}

		@Override
		public boolean handleDragMove(final IModelControl modelControl,
				final Vector3 dragStart, final Vector3 dragEnd,
				final EnumSet<Modifier> modifiers) {
			return false;
		}

		@Override
		public boolean handleDragEnd(final IModelControl modelControl,
				final Vector3 dragStart, final Vector3 dragEnd,
				final EnumSet<Modifier> modifiers) {
			return false;
		}

		private float maxDistance(final Vector3 p1, final Vector3 p2) {
			final float sizeX = FastMath.abs(p1.x - p2.x);
			final float sizeY = FastMath.abs(p1.y - p2.y);
			final float sizeZ = FastMath.abs(p1.z - p2.z);

			if (sizeZ > sizeX && sizeZ > sizeY) {
				return sizeZ;
			} else if (sizeY > sizeZ && sizeY > sizeX) {
				return sizeY;
			} else {
				return sizeX;
			}
		}

	}
}
