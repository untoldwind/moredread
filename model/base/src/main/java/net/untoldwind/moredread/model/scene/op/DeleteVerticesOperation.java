package net.untoldwind.moredread.model.scene.op;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.untoldwind.moredread.model.mesh.Mesh;
import net.untoldwind.moredread.model.scene.IMeshNode;
import net.untoldwind.moredread.model.scene.ISceneOperation;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.model.scene.SceneSelection.VertexSelection;

public class DeleteVerticesOperation implements ISceneOperation {
	private final Map<IMeshNode, Set<Integer>> vertices;

	public DeleteVerticesOperation(final Set<VertexSelection> vertexSelections) {
		this.vertices = new HashMap<IMeshNode, Set<Integer>>();

		for (final VertexSelection vertexSelection : vertexSelections) {
			if (vertexSelection.getNode() instanceof IMeshNode) {
				final IMeshNode meshNode = (IMeshNode) vertexSelection
						.getNode();
				Set<Integer> vertexIds = this.vertices.get(meshNode);

				if (vertexIds == null) {
					vertexIds = new HashSet<Integer>();
					this.vertices.put(meshNode, vertexIds);
				}
				vertexIds.add(vertexSelection.getVertexIndex());
			}
		}
	}

	@Override
	public String getLabel() {
		return "Delete vertices";
	}

	@Override
	public void perform(final Scene scene) {
		for (final IMeshNode meshNode : vertices.keySet()) {
			final Mesh<?, ?> mesh = meshNode.getEditableGeometry();

			mesh.removeVertices(vertices.get(meshNode));
		}
	}

}
