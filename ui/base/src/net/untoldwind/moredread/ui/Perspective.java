package net.untoldwind.moredread.ui;

import net.untoldwind.moredread.ui.views.Model3DView;
import net.untoldwind.moredread.ui.views.ModelTreeView;
import net.untoldwind.moredread.ui.views.SelectionModeView;
import net.untoldwind.moredread.ui.views.ToolSelectionView;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IPlaceholderFolderLayout;

public class Perspective implements IPerspectiveFactory {

	/**
	 * The ID of the perspective as specified in the extension.
	 */
	public static final String ID = "net.untoldwind.moredread.ui.perspective";

	public void createInitialLayout(final IPageLayout layout) {
		final String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);

		layout.addStandaloneView(ModelTreeView.ID, true, IPageLayout.BOTTOM,
				0.1f, editorArea);
		final IFolderLayout folder = layout.createFolder("messages",
				IPageLayout.RIGHT, 0.2f, ModelTreeView.ID);
		folder.addPlaceholder(Model3DView.ID + ":*");
		folder.addView(Model3DView.ID);

		layout.addStandaloneView(SelectionModeView.ID, false, IPageLayout.TOP,
				0.95f, editorArea);
		layout.getViewLayout(SelectionModeView.ID).setCloseable(false);
		final IPlaceholderFolderLayout toolFolder = layout
				.createPlaceholderFolder("tools", IPageLayout.RIGHT, 0.1f,
						SelectionModeView.ID);
		toolFolder.addPlaceholder(ToolSelectionView.ID + ":*");
		// toolFolder.addView(ToolSelectionView.ID);

		layout.addShowViewShortcut(Model3DView.ID);
		layout.addShowViewShortcut(ModelTreeView.ID);
	}
}
