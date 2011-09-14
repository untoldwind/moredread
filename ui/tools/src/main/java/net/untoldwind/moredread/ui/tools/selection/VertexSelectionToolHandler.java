package net.untoldwind.moredread.ui.tools.selection;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.untoldwind.moredread.annotations.Singleton;
import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.IPolygon;
import net.untoldwind.moredread.model.mesh.IVertex;
import net.untoldwind.moredread.model.mesh.Mesh;
import net.untoldwind.moredread.model.mesh.Polygon;
import net.untoldwind.moredread.model.mesh.Vertex;
import net.untoldwind.moredread.model.scene.IGeometryNode;
import net.untoldwind.moredread.model.scene.IMeshNode;
import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.IPolygonNode;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.model.scene.SceneSelection.VertexSelection;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.controls.IViewport;
import net.untoldwind.moredread.ui.controls.Modifier;
import net.untoldwind.moredread.ui.controls.impl.MoveCrossModelControl;
import net.untoldwind.moredread.ui.controls.impl.VertexSelectionModelControl;
import net.untoldwind.moredread.ui.tools.IToolController;
import net.untoldwind.moredread.ui.tools.spi.IToolAdapter;
import net.untoldwind.moredread.ui.tools.spi.IToolHandler;

import com.jme.math.Vector3f;

@Singleton
public class VertexSelectionToolHandler implements IToolHandler {
	@Override
	public void activated(final IToolController toolController,
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
					for (final IVertex vertex : mesh.getVertices()) {
						controls.add(new VertexSelectionModelControl(meshNode,
								vertex.getIndex(), new SelectToolAdapter(scene,
										meshNode, vertex.getIndex())));
					}
				}
			} else if (node instanceof IPolygonNode) {
				final IPolygonNode polygonNode = (IPolygonNode) node;
				final IPolygon polygon = polygonNode.getGeometry();

				if (polygon != null) {
					for (final IVertex vertex : polygon.getVertices()) {
						controls.add(new VertexSelectionModelControl(
								polygonNode, vertex.getIndex(),
								new SelectToolAdapter(scene, polygonNode,
										vertex.getIndex())));
					}
				}
			}
		}
		if (!scene.getSceneSelection().getSelectedVertices().isEmpty()) {
			controls.add(new MoveCrossModelControl(new TransformToolAdapter(
					scene)));
		}

		return controls;
	}

	private static class SelectToolAdapter implements IToolAdapter {
		private final Scene scene;
		private final IGeometryNode<?, ?> node;
		private final int vertexIndex;

		private SelectToolAdapter(final Scene scene,
				final IGeometryNode<?, ?> node, final int vertexIndex) {
			this.scene = scene;
			this.node = node;
			this.vertexIndex = vertexIndex;
		}

		@Override
		public Vector3f getCenter() {
			// TODO
			return new Vector3f(0, 0, 0);
		}

		@Override
		public boolean handleMove(final IModelControl modelControl,
				final Vector3f point, final EnumSet<Modifier> modifiers) {
			return false;
		}

		@Override
		public boolean handleClick(final IModelControl modelControl,
				final Vector3f point, final EnumSet<Modifier> modifiers) {
			// Click anywhere inside face is ok
			if (modifiers.contains(Modifier.LEFT_MOUSE_BUTTON)) {
				if (modifiers.contains(Modifier.SHIFT_KEY)) {
					scene.getSceneSelection().switchSelectedVertex(node,
							vertexIndex);
				} else {
					scene.getSceneSelection().setSelectedVertex(node,
							vertexIndex);
				}
				return true;
			}
			return false;
		}

		@Override
		public boolean handleDragStart(final IModelControl modelControl,
				final Vector3f point, final EnumSet<Modifier> modifiers) {
			return false;
		}

		@Override
		public boolean handleDragMove(final IModelControl modelControl,
				final Vector3f point, final EnumSet<Modifier> modifiers) {
			return false;
		}

		@Override
		public boolean handleDragEnd(final IModelControl modelControl,
				final Vector3f point, final EnumSet<Modifier> modifiers) {
			return false;
		}
	}

	private static class TransformToolAdapter implements IToolAdapter {
		private final Scene scene;

		private TransformToolAdapter(final Scene scene) {
			this.scene = scene;
		}

		@Override
		public Vector3f getCenter() {
			final Vector3f center = new Vector3f();
			int count = 0;

			for (final VertexSelection vertexSelection : scene
					.getSceneSelection().getSelectedVertices()) {
				final IGeometryNode<?, ?> node = vertexSelection.getNode();
				IVertex vertex;

				if (node instanceof IMeshNode) {
					final IMesh mesh = ((IMeshNode) node).getGeometry();
					vertex = mesh.getVertex(vertexSelection.getVertexIndex());
				} else {
					final IPolygon polygon = ((IPolygonNode) node)
							.getGeometry();
					vertex = polygon
							.getVertex(vertexSelection.getVertexIndex());

				}

				center.addLocal(node.localToWorld(vertex.getPoint(),
						new Vector3f()));
				count++;
			}
			if (count > 0) {
				center.divideLocal(count);
			}
			return center;
		}

		@Override
		public boolean handleMove(final IModelControl modelControl,
				final Vector3f point, final EnumSet<Modifier> modifiers) {
			return false;
		}

		@Override
		public boolean handleClick(final IModelControl modelControl,
				final Vector3f point, final EnumSet<Modifier> modifiers) {
			return false;
		}

		@Override
		public boolean handleDragStart(final IModelControl modelControl,
				final Vector3f point, final EnumSet<Modifier> modifiers) {
			return false;
		}

		@Override
		public boolean handleDragMove(final IModelControl modelControl,
				final Vector3f point, final EnumSet<Modifier> modifiers) {
			scene.getSceneChangeHandler().begin(true);

			try {
				updateScene(point);
			} finally {
				scene.getSceneChangeHandler().savepoint();
			}
			return true;
		}

		@Override
		public boolean handleDragEnd(final IModelControl modelControl,
				final Vector3f point, final EnumSet<Modifier> modifiers) {
			scene.getSceneChangeHandler().begin(true);

			try {
				updateScene(point);
			} finally {
				scene.getSceneChangeHandler().commit();
			}
			return true;
		}

		private void updateScene(final Vector3f point) {
			final Vector3f centerDiff = getCenter();
			centerDiff.subtractLocal(point);

			final Set<INode> changedNodes = new HashSet<INode>();

			for (final VertexSelection vertexSelection : scene
					.getSceneSelection().getSelectedVertices()) {
				final IGeometryNode<?, ?> node = vertexSelection.getNode();
				Vertex vertex;

				if (node instanceof IMeshNode) {
					final Mesh<?, ?> mesh = ((IMeshNode) node)
							.getEditableGeometry();
					vertex = mesh.getVertex(vertexSelection.getVertexIndex());
				} else {
					final Polygon polygon = ((IPolygonNode) node)
							.getEditableGeometry();
					vertex = polygon
							.getVertex(vertexSelection.getVertexIndex());
				}

				final Vector3f worldPoint = node.localToWorld(
						vertex.getPoint(), new Vector3f());
				worldPoint.subtractLocal(centerDiff);
				vertex.setPoint(node.worldToLocal(worldPoint, new Vector3f()));

				changedNodes.add(node);
			}
		}
	}
}
