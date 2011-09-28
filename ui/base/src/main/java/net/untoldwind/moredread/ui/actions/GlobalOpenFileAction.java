package net.untoldwind.moredread.ui.actions;

import net.untoldwind.moredread.model.io.ModelIOPlugin;
import net.untoldwind.moredread.model.scene.ISceneHolder;
import net.untoldwind.moredread.ui.MoreDreadUI;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchWindow;

public class GlobalOpenFileAction extends Action {
	ISceneHolder sceneHolder;

	public GlobalOpenFileAction(final IWorkbenchWindow window) {
		setId("openfile");
		setText("&Open file...");
		setToolTipText("Open file...");

		sceneHolder = MoreDreadUI.getDefault().getSceneHolder();
	}

	@Override
	public void run() {
		ModelIOPlugin.getDefault().sceneLoad(sceneHolder);
	}
}
