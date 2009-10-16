package net.untoldwind.moredread.ui.tools.selection;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.untoldwind.moredread.annotations.Singleton;
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
import net.untoldwind.moredread.ui.controls.Modifier;
import net.untoldwind.moredread.ui.controls.impl.FaceSelectionModelControl;
import net.untoldwind.moredread.ui.controls.impl.MoveCrossModelControl;
import net.untoldwind.moredread.ui.tools.IDisplaySystem;
import net.untoldwind.moredread.ui.tools.spi.IToolAdapter;
import net.untoldwind.moredread.ui.tools.spi.IToolHandler;

import com.jme.math.Vector3f;

@Singleton
public class FaceSelectionToolHandler implements IToolHandler {
	@Override
	public boolean activate(final Scene scene) {
		return true;
	}

	@Override
	public List<? extends IModelControl> getModelControls(final Scene scene,
			final IDisplaySystem displaySystem) {
		final List<IModelControl> controls = new ArrayList<IModelControl>();

		for (final INode node : scene.getSceneSelection().getSelectedNodes()) {
			if (node instanceof IMeshNode) {
				final IMeshNode meshNode = (IMeshNode) node;
				final IMesh mesh = meshNode.getGeometry();

				if (mesh != null) {
					for (final IFace face : mesh.getFaces()) {
						controls.add(new FaceSelectionModelControl(meshNode,
								face.getIndex(), new SelectToolAdapter(scene,
										displaySystem, meshNode, face
												.getIndex())));
					}
				}
			}
		}
		if (!scene.getSceneSelection().getSelectedFaces().isEmpty()) {
			controls.add(new MoveCrossModelControl(new TransformToolAdapter(
					scene, displaySystem)));
		}
		return controls;
	}

	private static class SelectToolAdapter implements IToolAdapter {
		private final Scene scene;
		private final IDisplaySystem displaySystem;
		private final IMeshNode node;
		private final FaceId faceIndex;

		private SelectToolAdapter(final Scene scene,
				final IDisplaySystem displaySystem, final IMeshNode node,
				final FaceId faceIndex) {
			this.scene = scene;
			this.displaySystem = displaySystem;
			this.node = node;
			this.faceIndex = faceIndex;
		}

		@Override
		public Vector3f getCenter() {
			// TODO
			return new Vector3f(0, 0, 0);
		}

		@Override
		public boolean handleClick(final Vector3f point,
				final EnumSet<Modifier> modifiers) {
			// Click anywhere inside face is ok
			if (modifiers.contains(Modifier.LEFT_MOUSE_BUTTON)) {
				if (modifiers.contains(Modifier.SHIFT_KEY)) {
					scene.getSceneSelection().switchSelectedFace(node,
							faceIndex);
				} else {
					scene.getSceneSelection().setSelectedFace(node, faceIndex);
				}
			}
			return true;
		}

		@Override
		public boolean handleDrag(final Vector3f point,
				final EnumSet<Modifier> modifiers, final boolean finished) {
			// Do nothing
			return false;
		}
	}

	private static class TransformToolAdapter implements IToolAdapter {
		private final Scene scene;
		private final IDisplaySystem displaySystem;

		private TransformToolAdapter(final Scene scene,
				final IDisplaySystem displaySystem) {
			this.scene = scene;
			this.displaySystem = displaySystem;
		}

		@Override
		public Vector3f getCenter() {
			final Vector3f center = new Vector3f();
			int count = 0;

			for (final FaceSelection faceSelection : scene.getSceneSelection()
					.getSelectedFaces()) {
				final IMeshNode node = faceSelection.getNode();
				final IMesh mesh = node.getGeometry();
				final IFace face = mesh.getFace(faceSelection.getFaceIndex());

				center.addLocal(node.localToWorld(face.getCenter(),
						new Vector3f()));
				count++;
			}
			if (count > 0) {
				center.divideLocal(count);
			}
			return center;
		}

		@Override
		public boolean handleClick(final Vector3f point,
				final EnumSet<Modifier> modifiers) {
			// Do nothing
			return false;
		}

		@Override
		public boolean handleDrag(final Vector3f point,
				final EnumSet<Modifier> modifiers, final boolean finished) {
			scene.getSceneChangeHandler().begin(true);

			try {
				final Vector3f centerDiff = getCenter();
				centerDiff.subtractLocal(point);

				final Set<INode> changedNodes = new HashSet<INode>();
				final Set<VertexSelection> updatedVertices = new HashSet<VertexSelection>();

				for (final FaceSelection faceSelection : scene
						.getSceneSelection().getSelectedFaces()) {
					final IMeshNode node = faceSelection.getNode();
					final Mesh<?, ?> mesh = node.getEditableGeometry();
					final Face<?, ?> face = mesh.getFace(faceSelection
							.getFaceIndex());

					for (final Vertex vertex : face.getVertices()) {
						final VertexSelection vertexId = new VertexSelection(
								node, vertex.getIndex());
						if (!updatedVertices.contains(vertexId)) {
							updatedVertices.add(vertexId);
							final Vector3f worldPoint = node.localToWorld(
									vertex.getPoint(), new Vector3f());
							worldPoint.subtractLocal(centerDiff);
							vertex.setPoint(node.worldToLocal(worldPoint,
									new Vector3f()));
						}
					}
					changedNodes.add(node);
				}
			} finally {
				if (finished) {
					scene.getSceneChangeHandler().commit();
				} else {
					scene.getSceneChangeHandler().savepoint();
				}
			}

			return true;
		}
	}

}
