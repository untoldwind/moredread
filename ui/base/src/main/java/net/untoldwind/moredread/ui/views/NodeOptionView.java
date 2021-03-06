package net.untoldwind.moredread.ui.views;

import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.SceneSelection;
import net.untoldwind.moredread.model.scene.event.ISceneChangeListener;
import net.untoldwind.moredread.model.scene.event.ISceneSelectionChangeListener;
import net.untoldwind.moredread.model.scene.event.SceneChangeEvent;
import net.untoldwind.moredread.model.scene.event.SceneSelectionChangeEvent;
import net.untoldwind.moredread.ui.MoreDreadUI;
import net.untoldwind.moredread.ui.options.IOptionView;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class NodeOptionView extends ViewPart implements ISceneChangeListener,
		ISceneSelectionChangeListener {
	public static final String ID = "net.untoldwind.moredread.ui.nodeOptionView";

	Composite optionsContainer;
	IOptionView activeOptionView;

	@Override
	public void createPartControl(final Composite parent) {
		optionsContainer = new Composite(parent, SWT.NONE);
		final GridLayout layout = new GridLayout(1, true);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		optionsContainer.setLayout(layout);

		MoreDreadUI.getDefault().getSceneHolder().addSceneChangeListener(this);
		MoreDreadUI.getDefault().getSceneHolder()
				.addSceneSelectionChangeListener(this);

		updateOptionView();
	}

	@Override
	public void dispose() {
		MoreDreadUI.getDefault().getSceneHolder()
				.removeSceneChangeListener(this);
		MoreDreadUI.getDefault().getSceneHolder()
				.removeSceneSelectionChangeListener(this);
		super.dispose();
	}

	@Override
	public void setFocus() {
	}

	@Override
	public void sceneChanged(final SceneChangeEvent event) {
		if (activeOptionView != null) {
			activeOptionView.update();
		}
	}

	@Override
	public void sceneSelectionChanged(final SceneSelectionChangeEvent event) {
		updateOptionView();
	}

	private void updateOptionView() {
		if (activeOptionView != null) {
			activeOptionView.dispose();
			activeOptionView = null;
		}

		final SceneSelection sceneSelection = MoreDreadUI.getDefault()
				.getSceneHolder().getScene().getSceneSelection();
		if (sceneSelection.getSelectedNodes().size() == 1) {
			final INode node = sceneSelection.getSelectedNodes().iterator()
					.next();

			activeOptionView = (IOptionView) node.getAdapter(IOptionView.class);
			if (activeOptionView != null) {
				activeOptionView.createControls(optionsContainer);
				optionsContainer.layout(true);
			}
		}
	}
}
