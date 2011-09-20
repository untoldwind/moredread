package net.untoldwind.moredread.ui.actions;

import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.ISceneHolder;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.model.scene.event.ISceneSelectionChangeListener;
import net.untoldwind.moredread.model.scene.event.SceneSelectionChangeEvent;
import net.untoldwind.moredread.ui.MoreDreadUI;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchWindow;

public class GlobalDeleteAction extends Action {

	ISceneHolder sceneHolder;

	public GlobalDeleteAction(final IWorkbenchWindow window) {
		setId("delete");
		setActionDefinitionId(IWorkbenchCommandConstants.EDIT_DELETE);
		final ISharedImages sharedImages = window.getWorkbench()
				.getSharedImages();
		setImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
		setDisabledImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE_DISABLED));
		setText("&Delete");
		setToolTipText("Delete");

		sceneHolder = MoreDreadUI.getDefault().getSceneHolder();
		setEnabled(!sceneHolder.getScene().getSceneSelection()
				.getSelectedNodes().isEmpty());

		sceneHolder
				.getScene()
				.getSceneSelection()
				.addSceneSelectionChangeListener(
						new ISceneSelectionChangeListener() {
							@Override
							public void sceneSelectionChanged(
									final SceneSelectionChangeEvent event) {
								setEnabled(!event.getSelectedNodes().isEmpty());
							}
						});
	}

	@Override
	public void run() {
		final Scene scene = sceneHolder.getScene();

		scene.getSceneChangeHandler().beginUndoable("Delete nodes");

		final List<INode> nodes = new ArrayList<INode>(sceneHolder.getScene()
				.getSceneSelection().getSelectedNodes());

		for (final INode node : nodes) {
			node.remove();
		}

		scene.getSceneChangeHandler().commit();
	}
}
