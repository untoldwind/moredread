package net.untoldwind.moredread.ui.actions;

import net.untoldwind.moredread.model.scene.ISceneHolder;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.model.scene.event.ISceneSelectionChangeListener;
import net.untoldwind.moredread.model.scene.event.SceneSelectionChangeEvent;
import net.untoldwind.moredread.model.scene.op.DeleteEdgesOperation;
import net.untoldwind.moredread.model.scene.op.DeleteFacesOperation;
import net.untoldwind.moredread.model.scene.op.DeleteNodesOperation;
import net.untoldwind.moredread.model.scene.op.DeleteVerticesOperation;
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
								switch (sceneHolder.getSelectionMode()) {
								case OBJECT:
									setEnabled(!event.getSelectedNodes()
											.isEmpty());
									break;
								case FACE:
									setEnabled(!event.getSelectedFaces()
											.isEmpty());
									break;
								case EDGE:
									setEnabled(!event.getSelectedEdges()
											.isEmpty());
									break;
								case VERTEX:
									setEnabled(!event.getSelectedVertices()
											.isEmpty());
									break;
								}
							}
						});
	}

	@Override
	public void run() {
		final Scene scene = sceneHolder.getScene();

		switch (sceneHolder.getSelectionMode()) {
		case OBJECT:
			scene.undoableChange(new DeleteNodesOperation(scene
					.getSceneSelection().getSelectedNodes()));
			break;
		case FACE:
			scene.undoableChange(new DeleteFacesOperation(scene
					.getSceneSelection().getSelectedFaces()));
			break;
		case EDGE:
			scene.undoableChange(new DeleteEdgesOperation(scene
					.getSceneSelection().getSelectedEdges()));
			break;
		case VERTEX:
			scene.undoableChange(new DeleteVerticesOperation(scene
					.getSceneSelection().getSelectedVertices()));

			break;
		}
	}
}
