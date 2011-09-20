package net.untoldwind.moredread.model.scene.op;

import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.Mesh;
import net.untoldwind.moredread.model.scene.AbstractSpatialComposite;
import net.untoldwind.moredread.model.scene.GeneratorNode;
import net.untoldwind.moredread.model.scene.ISceneOperation;
import net.untoldwind.moredread.model.scene.MeshNode;
import net.untoldwind.moredread.model.scene.Scene;

public class CollapseGeneratorNodeOperation implements ISceneOperation {
	private final GeneratorNode node;

	public CollapseGeneratorNodeOperation(final GeneratorNode node) {
		this.node = node;
	}

	@Override
	public String getLabel() {
		return "Collapse " + node.getName();
	}

	@Override
	public void perform(final Scene scene) {
		final IMesh mesh = node.getRenderGeometry();

		if (mesh instanceof Mesh<?, ?>) {
			MeshNode collapsedNode;

			final AbstractSpatialComposite<?> parent = node.getParent();

			collapsedNode = new MeshNode(parent, (Mesh<?, ?>) mesh);

			collapsedNode.setName(node.getName());
			collapsedNode.setLocalTranslation(node.getLocalTranslation());
			collapsedNode.setLocalScale(node.getLocalScale());
			collapsedNode.setLocalRotation(node.getLocalRotation());

			node.remove();
		}
	}
}
