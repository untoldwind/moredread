package net.untoldwind.moredread.model.scene;

import java.util.Set;

import net.untoldwind.moredread.model.scene.SceneSelection.EdgeSelection;
import net.untoldwind.moredread.model.scene.SceneSelection.FaceSelection;
import net.untoldwind.moredread.model.scene.SceneSelection.VertexSelection;

public interface ISceneSelection {
	Set<INode> getSelectedNodes();

	Set<FaceSelection> getSelectedFaces();

	Set<EdgeSelection> getSelectedEdges();

	Set<VertexSelection> getSelectedVertices();
}
