package net.untoldwind.moredread.ui.tools.selection;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.untoldwind.moredread.annotations.Singleton;
import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.mesh.Face;
import net.untoldwind.moredread.model.mesh.FaceId;
import net.untoldwind.moredread.model.mesh.IFace;
import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.Mesh;
import net.untoldwind.moredread.model.mesh.Vertex;
import net.untoldwind.moredread.model.scene.IMeshNode;
import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.model.scene.SceneSelection.FaceSelection;
import net.untoldwind.moredread.model.scene.SceneSelection.VertexSelection;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.controls.IViewport;
import net.untoldwind.moredread.ui.controls.Modifier;
import net.untoldwind.moredread.ui.controls.impl.FaceSelectionModelControl;
import net.untoldwind.moredread.ui.controls.impl.MoveCrossModelControl;
import net.untoldwind.moredread.ui.tools.IToolController;
import net.untoldwind.moredread.ui.tools.spi.IToolAdapter;
import net.untoldwind.moredread.ui.tools.spi.IToolHandler;

@Singleton
public class FaceSelectionToolHandler implements IToolHandler {
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
			if (node instanceof IMeshNode) {
				final IMeshNode meshNode = (IMeshNode) node;
				final IMesh mesh = meshNode.getGeometry();

				if (mesh != null) {
					for (final IFace face : mesh.getFaces()) {
						controls.add(new FaceSelectionModelControl(meshNode,
								face.getIndex(), new SelectToolAdapter(scene,
										meshNode, face.getIndex())));
					}
				}
			}
		}
		if (!scene.getSceneSelection().getSelectedFaces().isEmpty()) {
			controls.add(new MoveCrossModelControl(new TransformToolAdapter(
					scene)));
		}
		return controls;
	}

	private static class SelectToolAdapter implements IToolAdapter {
		private final Scene scene;
		private final IMeshNode node;
		private final FaceId faceIndex;

		private SelectToolAdapter(final Scene scene, final IMeshNode node,
				final FaceId faceIndex) {
			this.scene = scene;
			this.node = node;
			this.faceIndex = faceIndex;
		}

		@Override
		public Vector3 getCenter() {
			// TODO
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
			// Click anywhere inside face is ok
			if (modifiers.contains(Modifier.LEFT_MOUSE_BUTTON)) {
				if (modifiers.contains(Modifier.SHIFT_KEY)) {
					scene.getSceneSelection().switchSelectedFace(node,
							faceIndex);
				} else {
					scene.getSceneSelection().setSelectedFace(node, faceIndex);
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

			for (final FaceSelection faceSelection : scene.getSceneSelection()
					.getSelectedFaces()) {
				if (faceSelection.getNode() instanceof IMeshNode) {
					final IMeshNode node = (IMeshNode) faceSelection.getNode();
					final IMesh mesh = node.getGeometry();
					final IFace face = mesh.getFace(faceSelection
							.getFaceIndex());

					center.addLocal(node.localToWorld(face.getCenter(),
							new Vector3()));
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
			scene.getSceneChangeHandler().beginUndoable("Move faces");

			try {
				updateScene(dragEnd);
			} finally {
				scene.getSceneChangeHandler().savepoint();
			}
			return true;
		}

		@Override
		public boolean handleDragEnd(final IModelControl modelControl,
				final Vector3 dragStart, final Vector3 dragEnd,
				final EnumSet<Modifier> modifiers) {
			scene.getSceneChangeHandler().beginUndoable("Move faces");

			try {
				updateScene(dragEnd);
			} finally {
				scene.getSceneChangeHandler().commit();
			}
			return true;
		}

		private void updateScene(final Vector3 point) {
			final Vector3 centerDiff = getCenter();
			centerDiff.subtractLocal(point);

			final Set<INode> changedNodes = new HashSet<INode>();
			final Set<VertexSelection> updatedVertices = new HashSet<VertexSelection>();

			for (final FaceSelection faceSelection : scene.getSceneSelection()
					.getSelectedFaces()) {
				if (faceSelection.getNode() instanceof IMeshNode) {
					final IMeshNode node = (IMeshNode) faceSelection.getNode();
					final Mesh<?, ?> mesh = node.getEditableGeometry();
					final Face<?, ?> face = mesh.getFace(faceSelection
							.getFaceIndex());

					for (final Vertex vertex : face.getVertices()) {
						final VertexSelection vertexId = new VertexSelection(
								node, vertex.getIndex());
						if (!updatedVertices.contains(vertexId)) {
							updatedVertices.add(vertexId);
							final Vector3 worldPoint = node.localToWorld(
									vertex.getPoint(), new Vector3());
							worldPoint.subtractLocal(centerDiff);
							vertex.setPoint(node.worldToLocal(worldPoint,
									new Vector3()));
						}
					}
					changedNodes.add(node);
				}
			}
		}
	}

}
