package net.untoldwind.moredread.ui.tools.utilities;

import java.util.Collections;
import java.util.List;

import net.untoldwind.moredread.model.mesh.IEdge;
import net.untoldwind.moredread.model.mesh.Mesh;
import net.untoldwind.moredread.model.scene.AbstractSceneOperation;
import net.untoldwind.moredread.model.scene.IMeshNode;
import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.MeshNode;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.model.scene.SceneSelection;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.controls.IViewport;
import net.untoldwind.moredread.ui.tools.IToolController;
import net.untoldwind.moredread.ui.tools.spi.IToolHandler;

import com.jme.math.Vector3f;

public class AddMidpointToolHandler implements IToolHandler {

	@Override
	public void activated(final IToolController toolController,
			final Scene scene) {
		scene.undoableChange(new AbstractSceneOperation("Add midpoint") {
			@Override
			public void perform(final Scene scene) {
				for (final SceneSelection.EdgeSelection edgeSelection : scene
						.getSceneSelection().getSelectedEdges()) {
					final INode node = edgeSelection.getNode();
					if (node instanceof IMeshNode) {
						final IMeshNode meshNode = (MeshNode) node;
						final Mesh<?, ?> mesh = meshNode.getEditableGeometry();
						final IEdge edge = mesh.getEdge(edgeSelection
								.getEdgeIndex());
						final Vector3f point = new Vector3f();
						point.set(edge.getVertex1().getPoint());
						point.addLocal(edge.getVertex2().getPoint());
						point.divideLocal(2);

						mesh.addMidpoint(edgeSelection.getEdgeIndex(), point);
					}
				}
			}
		});
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
		return Collections.emptyList();
	}

}
