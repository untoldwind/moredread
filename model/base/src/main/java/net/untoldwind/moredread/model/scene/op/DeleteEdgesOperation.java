package net.untoldwind.moredread.model.scene.op;

import java.util.Set;

import net.untoldwind.moredread.model.mesh.Mesh;
import net.untoldwind.moredread.model.scene.IMeshNode;
import net.untoldwind.moredread.model.scene.ISceneOperation;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.model.scene.SceneSelection.EdgeSelection;

public class DeleteEdgesOperation implements ISceneOperation {
	private final Set<EdgeSelection> edgeSelections;

	public DeleteEdgesOperation(final Set<EdgeSelection> edgeSelections) {
		this.edgeSelections = edgeSelections;
	}

	@Override
	public String getLabel() {
		return "Delete edges";
	}

	@Override
	public void perform(final Scene scene) {
		for (final EdgeSelection edgeSelection : edgeSelections) {
			if (edgeSelection.getNode() instanceof IMeshNode) {
				final IMeshNode meshNode = (IMeshNode) edgeSelection.getNode();
				final Mesh<?, ?> mesh = meshNode.getEditableGeometry();

				mesh.removeEdge(edgeSelection.getEdgeIndex());
			}
		}
	}

}
