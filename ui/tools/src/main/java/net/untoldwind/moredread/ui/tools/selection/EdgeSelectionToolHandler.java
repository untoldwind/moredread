package net.untoldwind.moredread.ui.tools.selection;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.untoldwind.moredread.annotations.Singleton;
import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.mesh.Edge;
import net.untoldwind.moredread.model.mesh.EdgeGeometry;
import net.untoldwind.moredread.model.mesh.EdgeId;
import net.untoldwind.moredread.model.mesh.IEdge;
import net.untoldwind.moredread.model.mesh.IEdgeGeometry;
import net.untoldwind.moredread.model.mesh.Vertex;
import net.untoldwind.moredread.model.scene.IEdgeGeometryNode;
import net.untoldwind.moredread.model.scene.IGeometryNode;
import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.model.scene.SceneSelection.EdgeSelection;
import net.untoldwind.moredread.model.scene.SceneSelection.VertexSelection;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.controls.IViewport;
import net.untoldwind.moredread.ui.controls.Modifier;
import net.untoldwind.moredread.ui.controls.impl.EdgeSelectionModelControl;
import net.untoldwind.moredread.ui.controls.impl.MoveCrossModelControl;
import net.untoldwind.moredread.ui.tools.IToolController;
import net.untoldwind.moredread.ui.tools.spi.IToolAdapter;
import net.untoldwind.moredread.ui.tools.spi.IToolHandler;

@Singleton
public class EdgeSelectionToolHandler implements IToolHandler {

	@Override
	public void activated(final IToolController toolController,
			final Scene scene) {
	}

	@Override
	public void aborted(final IToolController toolController, final Scene scene) {
	}

	@Override
	public void completed(final IToolController toolController,
			final Scene scene) {
	}

	@Override
	public List<? extends IModelControl> getModelControls(final Scene scene,
			final IViewport viewport) {
		final List<IModelControl> controls = new ArrayList<IModelControl>();

		for (final INode node : scene.getSceneSelection().getSelectedNodes()) {
			if (node instanceof IEdgeGeometryNode<?, ?>) {
				final IEdgeGeometryNode<?, ?> edgeGeometryNode = (IEdgeGeometryNode<?, ?>) node;
				final IEdgeGeometry<?> geometry = edgeGeometryNode
						.getGeometry();

				if (geometry != null) {
					for (final IEdge edge : geometry.getEdges()) {
						controls.add(new EdgeSelectionModelControl(
								edgeGeometryNode, edge.getIndex(),
								new SelectToolAdapter(scene, edgeGeometryNode,
										edge.getIndex())));
					}
				}
			}
		}
		if (!scene.getSceneSelection().getSelectedEdges().isEmpty()) {
			controls.add(new MoveCrossModelControl(new TransformToolAdapter(
					scene)));
		}
		return controls;
	}

	private static class SelectToolAdapter implements IToolAdapter {
		private final Scene scene;
		private final IEdgeGeometryNode<?, ?> node;
		private final EdgeId edgeIndex;

		private SelectToolAdapter(final Scene scene,
				final IEdgeGeometryNode<?, ?> node, final EdgeId edgeIndex) {
			this.scene = scene;
			this.node = node;
			this.edgeIndex = edgeIndex;
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
					scene.getSceneSelection().switchSelectedEdge(node,
							edgeIndex);
				} else {
					scene.getSceneSelection().setSelectedEdge(node, edgeIndex);
				}
				return true;
			}
			return false;
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

			for (final EdgeSelection edgeSelection : scene.getSceneSelection()
					.getSelectedEdges()) {
				final IGeometryNode<?, ?> node = edgeSelection.getNode();

				if (node instanceof IEdgeGeometryNode<?, ?>) {
					final IEdgeGeometry<?> geometry = ((IEdgeGeometryNode<?, ?>) node)
							.getGeometry();
					final IEdge edge = geometry.getEdge(edgeSelection
							.getEdgeIndex());
					center.addLocal(node.localToWorld(edge.getVertex1()
							.getPoint(), new Vector3()));
					center.addLocal(node.localToWorld(edge.getVertex2()
							.getPoint(), new Vector3()));
					count += 2;
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
				final Vector3 dragStart, final EnumSet<Modifier> modifiers) {
			return false;
		}

		@Override
		public boolean handleDragMove(final IModelControl modelControl,
				final Vector3 dragStart, final Vector3 dragEnd,
				final EnumSet<Modifier> modifiers) {
			scene.getSceneChangeHandler().beginUndoable("Move edges");

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
			scene.getSceneChangeHandler().beginUndoable("Move edges");

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

			for (final EdgeSelection edgeSelection : scene.getSceneSelection()
					.getSelectedEdges()) {
				final IGeometryNode<?, ?> node = edgeSelection.getNode();
				Edge edge = null;

				if (node instanceof IEdgeGeometryNode<?, ?>) {
					final EdgeGeometry<?> geometry = ((IEdgeGeometryNode<?, ?>) node)
							.getEditableGeometry();
					edge = geometry.getEdge(edgeSelection.getEdgeIndex());
				}

				if (edge == null) {
					continue;
				}

				final Vertex vertex1 = edge.getVertex1();
				final VertexSelection vertex1Id = new VertexSelection(node,
						vertex1.getIndex());
				if (!updatedVertices.contains(vertex1Id)) {
					updatedVertices.add(vertex1Id);
					final Vector3 worldPoint = node.localToWorld(
							vertex1.getPoint(), new Vector3());
					worldPoint.subtractLocal(centerDiff);
					vertex1.setPoint(node.worldToLocal(worldPoint,
							new Vector3()));
				}

				final Vertex vertex2 = edge.getVertex2();
				final VertexSelection vertex2Id = new VertexSelection(node,
						vertex2.getIndex());
				if (!updatedVertices.contains(vertex2Id)) {
					updatedVertices.add(vertex2Id);
					final Vector3 worldPoint = node.localToWorld(
							vertex2.getPoint(), new Vector3());
					worldPoint.subtractLocal(centerDiff);
					vertex2.setPoint(node.worldToLocal(worldPoint,
							new Vector3()));
				}

				changedNodes.add(node);
			}
		}
	}

}
