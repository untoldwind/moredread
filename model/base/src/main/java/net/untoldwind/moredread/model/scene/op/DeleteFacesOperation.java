package net.untoldwind.moredread.model.scene.op;

import java.util.Set;

import net.untoldwind.moredread.model.mesh.Mesh;
import net.untoldwind.moredread.model.scene.IMeshNode;
import net.untoldwind.moredread.model.scene.ISceneOperation;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.model.scene.SceneSelection.FaceSelection;

public class DeleteFacesOperation implements ISceneOperation {
	private final Set<FaceSelection> faceSelections;

	public DeleteFacesOperation(final Set<FaceSelection> faceSelections) {
		this.faceSelections = faceSelections;
	}

	@Override
	public String getLabel() {
		return "Delete faces";
	}

	@Override
	public void perform(final Scene scene) {
		for (final FaceSelection faceSelection : faceSelections) {
			if (faceSelection.getNode() instanceof IMeshNode) {
				final IMeshNode meshNode = faceSelection.getNode();
				final Mesh<?, ?> mesh = meshNode.getEditableGeometry();

				mesh.removeFace(faceSelection.getFaceIndex());
			}
		}

	}
}
