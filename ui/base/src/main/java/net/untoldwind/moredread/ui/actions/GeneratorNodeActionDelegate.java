package net.untoldwind.moredread.ui.actions;

import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.Mesh;
import net.untoldwind.moredread.model.scene.AbstractSpatialComposite;
import net.untoldwind.moredread.model.scene.GeneratorNode;
import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.MeshNode;
import net.untoldwind.moredread.model.scene.SceneChangeHandler;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class GeneratorNodeActionDelegate implements IObjectActionDelegate {

	INode selectedNode;

	@Override
	public void run(final IAction action) {
		final String id = action.getId();

		if ("net.untoldwind.moredread.ui.generatorNode.collapse".equals(id)) {
			if (selectedNode != null && selectedNode instanceof GeneratorNode) {
				final GeneratorNode selectedGeneratorNode = (GeneratorNode) selectedNode;
				final IMesh mesh = selectedGeneratorNode.getRenderGeometry();

				if (mesh instanceof Mesh<?>) {
					MeshNode collapsedNode;
					final SceneChangeHandler sceneChangeHandler = selectedNode
							.getScene().getSceneChangeHandler();

					sceneChangeHandler.begin(true);
					try {
						final AbstractSpatialComposite<?> parent = ((GeneratorNode) selectedNode)
								.getParent();

						collapsedNode = new MeshNode(parent, (Mesh<?>) mesh);

						collapsedNode.setName(selectedNode.getName());
						collapsedNode.setLocalTranslation(selectedGeneratorNode
								.getLocalTranslation());
						collapsedNode.setLocalScale(selectedGeneratorNode
								.getLocalScale());
						collapsedNode.setLocalRotation(selectedGeneratorNode
								.getLocalRotation());

						selectedNode.remove();
					} finally {
						sceneChangeHandler.commit();
					}
				}
			}
		}
	}

	@Override
	public void selectionChanged(final IAction action,
			final ISelection selection) {

		selectedNode = null;

		if (selection instanceof IStructuredSelection) {
			final Object sel = ((IStructuredSelection) selection)
					.getFirstElement();

			if (sel != null && sel instanceof INode) {
				selectedNode = (INode) sel;
			}
		}
	}

	@Override
	public void setActivePart(final IAction action,
			final IWorkbenchPart targetPart) {
	}
}
