package net.untoldwind.moredread.ui.tools.creation;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.mesh.Polygon;
import net.untoldwind.moredread.model.scene.PolygonNode;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.model.state.BinaryStateReader;
import net.untoldwind.moredread.model.state.BinaryStateWriter;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.controls.IViewport;
import net.untoldwind.moredread.ui.controls.Modifier;
import net.untoldwind.moredread.ui.controls.impl.PlaneRestrictedModelControl;
import net.untoldwind.moredread.ui.tools.IToolController;
import net.untoldwind.moredread.ui.tools.spi.IToolAdapter;
import net.untoldwind.moredread.ui.tools.spi.IToolHandler;

public class CreatePolygonToolHandler implements IToolHandler {
	protected IToolController toolController;
	private PolygonNode polygonNode;
	private byte[] completeState;

	@Override
	public void activated(final IToolController toolController,
			final Scene scene) {
		this.toolController = toolController;
	}

	@Override
	public void aborted(final IToolController toolController, final Scene scene) {
		scene.getSceneChangeHandler().rollback();
		polygonNode = null;
		completeState = null;
	}

	@Override
	public void completed(final IToolController toolController,
			final Scene scene) {
		scene.getSceneChangeHandler().beginUndoable("Create polygon");

		try {
			if (polygonNode != null && completeState != null) {
				final Polygon polygon = BinaryStateReader
						.fromByteArray(completeState);

				polygonNode.setGeometry(polygon);
			}
		} finally {
			scene.getSceneChangeHandler().commit();
		}
		polygonNode = null;
		completeState = null;
	}

	@Override
	public List<? extends IModelControl> getModelControls(final Scene scene,
			final IViewport viewport) {
		final List<IModelControl> controls = new ArrayList<IModelControl>();

		controls.add(new PlaneRestrictedModelControl(
				new CreatePolygonToolAdapter(scene)));

		return controls;
	}

	public class CreatePolygonToolAdapter implements IToolAdapter {
		Scene scene;
		Vector3 lastPoint = new Vector3();
		Vector3 position = new Vector3();

		public CreatePolygonToolAdapter(final Scene scene) {
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

			if (polygonNode != null) {
				scene.getSceneChangeHandler().beginUndoable("Create polygon");
				try {
					final Polygon polygon = polygonNode.getEditableGeometry();
					polygon.getVertex(polygon.getVertexCount() - 1).setPoint(
							point);
				} finally {
					scene.getSceneChangeHandler().savepoint();
				}
			}
			return true;
		}

		@Override
		public boolean handleClick(final IModelControl modelControl,
				final Vector3 point, final EnumSet<Modifier> modifiers) {
			if (polygonNode == null) {
				scene.getSceneChangeHandler().beginUndoable("Create polygon");
				try {
					final Polygon polygon = new Polygon();

					polygon.appendVertex(point, false);
					polygon.appendVertex(point, false);
					lastPoint.set(point);
					completeState = BinaryStateWriter.toByteArray(polygon);

					polygonNode = new PolygonNode(scene, polygon);
				} finally {
					scene.getSceneChangeHandler().savepoint();
				}
			} else {
				scene.getSceneChangeHandler().beginUndoable("Create polygon");
				try {
					final Polygon polygon = polygonNode.getEditableGeometry();
					polygon.getVertex(polygon.getVertexCount() - 1).setPoint(
							point);
					completeState = BinaryStateWriter.toByteArray(polygon);

					polygon.appendVertex(point, false);
					lastPoint.set(point);
				} finally {
					scene.getSceneChangeHandler().savepoint();
				}
			}

			return true;
		}

		@Override
		public boolean handleDragStart(final IModelControl modelControl,
				final Vector3 point, final EnumSet<Modifier> modifiers) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean handleDragMove(final IModelControl modelControl,
				final Vector3 dragStart, final Vector3 dragEnd,
				final EnumSet<Modifier> modifiers) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean handleDragEnd(final IModelControl modelControl,
				final Vector3 dragStart, final Vector3 dragEnd,
				final EnumSet<Modifier> modifiers) {
			// TODO Auto-generated method stub
			return false;
		}

	}

}
