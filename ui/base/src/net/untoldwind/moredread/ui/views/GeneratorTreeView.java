package net.untoldwind.moredread.ui.views;

import net.untoldwind.moredread.model.scene.event.ISceneChangeListener;
import net.untoldwind.moredread.model.scene.event.ISceneSelectionChangeListener;
import net.untoldwind.moredread.model.scene.event.SceneChangeEvent;
import net.untoldwind.moredread.model.scene.event.SceneSelectionChangeEvent;
import net.untoldwind.moredread.ui.MoreDreadUI;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class GeneratorTreeView extends ViewPart implements
		ISceneSelectionChangeListener, ISceneChangeListener {

	public static final String ID = "net.untoldwind.moredread.ui.generatorTreeView";

	TreeViewer generatorTreeViewer;

	@Override
	public void createPartControl(final Composite parent) {
		generatorTreeViewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.BORDER);

		getSite().setSelectionProvider(generatorTreeViewer);

		MoreDreadUI.getDefault().getSceneHolder().getScene()
				.getSceneSelection().addSceneSelectionChangeListener(this);
		MoreDreadUI.getDefault().getSceneHolder().getScene()
				.addSceneChangeListener(this);
	}

	@Override
	public void setFocus() {
		generatorTreeViewer.getControl().setFocus();
	}

	@Override
	public void sceneSelectionChanged(final SceneSelectionChangeEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sceneChanged(final SceneChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
