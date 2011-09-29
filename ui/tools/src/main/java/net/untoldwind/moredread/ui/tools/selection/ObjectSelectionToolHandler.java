package net.untoldwind.moredread.ui.tools.selection;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.untoldwind.moredread.annotations.Singleton;
import net.untoldwind.moredread.model.math.Quaternion;
import net.untoldwind.moredread.model.math.Vector2;
import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.scene.AbstractSpatialNode;
import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.ISpatialNode;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.controls.IViewport;
import net.untoldwind.moredread.ui.controls.Modifier;
import net.untoldwind.moredread.ui.controls.impl.FullScreenModelControl;
import net.untoldwind.moredread.ui.controls.impl.MoveRotateCrossModelControl;
import net.untoldwind.moredread.ui.tools.IToolController;
import net.untoldwind.moredread.ui.tools.spi.IToolAdapter;
import net.untoldwind.moredread.ui.tools.spi.IToolHandler;

import com.jme.math.FastMath;

@Singleton
public class ObjectSelectionToolHandler implements IToolHandler {
	@Override
	public void activated(final IToolController toolController,
			final Scene scene) {
	}

	@Override
	public void completed(final IToolController toolController,
			final Scene scene) {
	}

	@Override
	public void aborted(final IToolController toolController, final Scene scene) {
	}

	@Override
	public List<? extends IModelControl> getModelControls(final Scene scene,
			final IViewport viewport) {
		final List<IModelControl> controls = new ArrayList<IModelControl>();
		for (final INode node : scene.getSceneSelection().getSelectedNodes()) {
			if (node instanceof AbstractSpatialNode) {
				controls.add(new MoveRotateCrossModelControl(
						new TransformToolAdapter(scene), new RotateToolAdapter(
								scene)));
				break;
			}
		}
		controls.add(new FullScreenModelControl(new SelectToolAdapter(scene,
				viewport)));

		return controls;
	}

	private static class SelectToolAdapter implements IToolAdapter {
		private final Scene scene;
		private final IViewport viewport;

		private SelectToolAdapter(final Scene scene, final IViewport viewport) {
			this.scene = scene;
			this.viewport = viewport;
		}

		@Override
		public Vector3 getCenter() {
			return new Vector3(0, 0, 0);
		}

		@Override
		public Vector3 getFeedbackPoint() {
			return getCenter();
		}

		@Override
		public boolean handleMove(final IModelControl modelControl,
				final Vector3 point, final EnumSet<Modifier> modifiers) {
			return false;
		}

		@Override
		public boolean handleClick(final IModelControl modelControl,
				final Vector3 point, final EnumSet<Modifier> modifiers) {
			if (modifiers.contains(Modifier.LEFT_MOUSE_BUTTON)) {
				final INode node = viewport.pickNode(new Vector2(point.x,
						point.y));

				if (node != null) {
					if (modifiers.contains(Modifier.SHIFT_KEY)) {
						scene.getSceneSelection().switchSelectedNode(node);
					} else {
						scene.getSceneSelection().setSelectedNode(node);
					}
				}
				return true;
			}
			return false;
		}

		@Override
		public boolean handleDragStart(final IModelControl modelControl,
				final Vector3 point, final EnumSet<Modifier> modifiers) {
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
	}

	private static class TransformToolAdapter implements IToolAdapter {
		private final Scene scene;

		private TransformToolAdapter(final Scene scene) {
			this.scene = scene;
		}

		@Override
		public Vector3 getCenter() {
			final Vector3 center = new Vector3();
			int count = 0;
			for (final INode node : scene.getSceneSelection()
					.getSelectedNodes()) {
				if (node instanceof ISpatialNode) {
					final ISpatialNode spatialNode = (AbstractSpatialNode) node;
					center.addLocal(spatialNode.getWorldBoundingBox()
							.getCenter());
					count++;
				}
			}
			if (count > 0) {
				center.divideLocal(count);
			}

			return center;
		}

		@Override
		public Vector3 getFeedbackPoint() {
			return getCenter();
		}

		@Override
		public boolean handleMove(final IModelControl modelControl,
				final Vector3 point, final EnumSet<Modifier> modifiers) {
			return false;
		}

		@Override
		public boolean handleClick(final IModelControl modelControl,
				final Vector3 point, final EnumSet<Modifier> modifiers) {
			return false;
		}

		@Override
		public boolean handleDragStart(final IModelControl modelControl,
				final Vector3 point, final EnumSet<Modifier> modifiers) {
			return false;
		}

		@Override
		public boolean handleDragMove(final IModelControl modelControl,
				final Vector3 dragStart, final Vector3 dragEnd,
				final EnumSet<Modifier> modifiers) {
			final Vector3 midCenter = getCenter();

			scene.getSceneChangeHandler().beginUndoable("Move nodes");

			try {
				updateScene(dragEnd, midCenter);
			} finally {
				scene.getSceneChangeHandler().savepoint();
			}
			return true;
		}

		@Override
		public boolean handleDragEnd(final IModelControl modelControl,
				final Vector3 dragStart, final Vector3 dragEnd,
				final EnumSet<Modifier> modifiers) {
			final Vector3 midCenter = getCenter();

			scene.getSceneChangeHandler().beginUndoable("Move nodes");

			try {
				updateScene(dragEnd, midCenter);
			} finally {
				scene.getSceneChangeHandler().commit();
			}
			return true;
		}

		private void updateScene(final Vector3 point, final Vector3 midCenter) {
			final List<INode> changedNodes = new ArrayList<INode>();

			for (final INode node : scene.getSceneSelection()
					.getSelectedNodes()) {
				if (node instanceof AbstractSpatialNode) {
					final AbstractSpatialNode spatialNode = (AbstractSpatialNode) node;
					final Vector3 centerDiff = spatialNode
							.getWorldTranslation().subtract(midCenter);

					Vector3 localCenter;
					if (node.getParent() != null) {
						localCenter = spatialNode.getParent().worldToLocal(
								point.add(centerDiff), new Vector3());
					} else {
						localCenter = point.add(centerDiff);
					}

					spatialNode.setLocalTranslation(localCenter);
					changedNodes.add(spatialNode);
				}
			}
		}
	}

	private static class RotateToolAdapter implements IToolAdapter {
		private final Scene scene;
		Map<Long, Quaternion> originalState;

		private RotateToolAdapter(final Scene scene) {
			this.scene = scene;
		}

		@Override
		public Vector3 getCenter() {
			final Vector3 center = new Vector3();
			int count = 0;
			for (final INode node : scene.getSceneSelection()
					.getSelectedNodes()) {
				if (node instanceof ISpatialNode) {
					final ISpatialNode spatialNode = (AbstractSpatialNode) node;
					center.addLocal(spatialNode.getWorldBoundingBox()
							.getCenter());
					count++;
				}
			}
			if (count > 0) {
				center.divideLocal(count);
			}

			return center;
		}

		@Override
		public Vector3 getFeedbackPoint() {
			return getCenter();
		}

		@Override
		public boolean handleMove(final IModelControl modelControl,
				final Vector3 point, final EnumSet<Modifier> modifiers) {
			return false;
		}

		@Override
		public boolean handleClick(final IModelControl modelControl,
				final Vector3 point, final EnumSet<Modifier> modifiers) {
			return false;
		}

		@Override
		public boolean handleDragStart(final IModelControl modelControl,
				final Vector3 point, final EnumSet<Modifier> modifiers) {
			originalState = new HashMap<Long, Quaternion>();
			for (final INode node : scene.getSceneSelection()
					.getSelectedNodes()) {
				if (node instanceof AbstractSpatialNode) {
					final AbstractSpatialNode spatialNode = (AbstractSpatialNode) node;

					originalState.put(spatialNode.getNodeId(),
							spatialNode.getLocalRotation());
				}
			}
			return true;
		}

		@Override
		public boolean handleDragMove(final IModelControl modelControl,
				final Vector3 dragStart, final Vector3 dragEnd,
				final EnumSet<Modifier> modifiers) {
			final Vector3 center = getCenter();
			final Vector3 start = dragStart.subtract(center).normalizeLocal();
			final Vector3 end = dragEnd.subtract(center).normalizeLocal();
			final Vector3 axis = start.cross(end);
			final float dot = start.dot(end);
			final float axisLen = axis.length();

			if (axisLen < FastMath.ZERO_TOLERANCE) {
				return false;
			}
			final Quaternion rotation = new Quaternion();
			rotation.fromAngleAxis(FastMath.acos(dot), axis);

			scene.getSceneChangeHandler().beginUndoable("Rotate nodes");

			try {
				updateScene(center, rotation);
			} finally {
				scene.getSceneChangeHandler().savepoint();
			}

			return true;
		}

		@Override
		public boolean handleDragEnd(final IModelControl modelControl,
				final Vector3 dragStart, final Vector3 dragEnd,
				final EnumSet<Modifier> modifiers) {
			final Vector3 center = getCenter();
			final Vector3 start = dragStart.subtract(center).normalizeLocal();
			final Vector3 end = dragEnd.subtract(center).normalizeLocal();
			final Vector3 axis = start.cross(end);
			final float dot = start.dot(end);
			final float axisLen = axis.length();

			if (axisLen < FastMath.ZERO_TOLERANCE) {
				return false;
			}
			final Quaternion rotation = new Quaternion();
			rotation.fromAngleAxis(FastMath.acos(dot), axis);

			scene.getSceneChangeHandler().beginUndoable("Rotate nodes");

			try {
				updateScene(center, rotation);
			} finally {
				scene.getSceneChangeHandler().commit();
			}
			originalState = null;

			return true;
		}

		private void updateScene(final Vector3 center, final Quaternion rotation) {
			for (final INode node : scene.getSceneSelection()
					.getSelectedNodes()) {
				if (node instanceof AbstractSpatialNode) {
					final AbstractSpatialNode spatialNode = (AbstractSpatialNode) node;

					final Quaternion localRotation = originalState.get(
							spatialNode.getNodeId()).mult(rotation);
					spatialNode.setLocalRotation(localRotation);
				}
			}
		}

	}
}
