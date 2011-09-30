package net.untoldwind.moredread.ui.views;

import java.util.EnumSet;

import net.untoldwind.moredread.model.enums.SelectionMode;
import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.event.ISceneSelectionChangeListener;
import net.untoldwind.moredread.model.scene.event.ISceneSelectionModeListener;
import net.untoldwind.moredread.model.scene.event.SceneSelectionChangeEvent;
import net.untoldwind.moredread.model.scene.event.SceneSelectionModeEvent;
import net.untoldwind.moredread.ui.MoreDreadUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISizeProvider;
import org.eclipse.ui.part.ViewPart;

public class SelectionModeView extends ViewPart implements
		ISceneSelectionChangeListener, ISceneSelectionModeListener,
		ISizeProvider {
	public static final String ID = "net.untoldwind.moredread.ui.selectionModeView";

	private ToolBar toolBar;

	private ToolItem objectSelection;
	private ToolItem faceSelection;
	private ToolItem edgeSelection;
	private ToolItem vertexSelection;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createPartControl(final Composite parent) {
		toolBar = new ToolBar(parent, SWT.NONE);

		objectSelection = new ToolItem(toolBar, SWT.RADIO | SWT.NONE);
		objectSelection.setImage(MoreDreadUI.getDefault().getImage(
				"/icons/SelectionModeObject.png"));
		objectSelection.setSelection(true);
		objectSelection.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				MoreDreadUI.getDefault().getSceneHolder()
						.setSelectionMode(SelectionMode.OBJECT);
			}
		});
		faceSelection = new ToolItem(toolBar, SWT.RADIO | SWT.NONE);
		faceSelection.setImage(MoreDreadUI.getDefault().getImage(
				"/icons/SelectionModeFace.png"));
		faceSelection.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				MoreDreadUI.getDefault().getSceneHolder()
						.setSelectionMode(SelectionMode.FACE);
			}
		});
		faceSelection.setEnabled(false);
		edgeSelection = new ToolItem(toolBar, SWT.RADIO | SWT.NONE);
		edgeSelection.setImage(MoreDreadUI.getDefault().getImage(
				"/icons/SelectionModeEdge.png"));
		edgeSelection.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				MoreDreadUI.getDefault().getSceneHolder()
						.setSelectionMode(SelectionMode.EDGE);
			}
		});
		edgeSelection.setEnabled(false);
		vertexSelection = new ToolItem(toolBar, SWT.RADIO | SWT.NONE);
		vertexSelection.setImage(MoreDreadUI.getDefault().getImage(
				"/icons/SelectionModeVertex.png"));
		vertexSelection.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				MoreDreadUI.getDefault().getSceneHolder()
						.setSelectionMode(SelectionMode.VERTEX);
			}
		});
		vertexSelection.setEnabled(false);
		MoreDreadUI.getDefault().getSceneHolder()
				.addSceneSelectionModeListener(this);
		MoreDreadUI.getDefault().getSceneHolder()
				.addSceneSelectionChangeListener(this);
	}

	@Override
	public void dispose() {
		MoreDreadUI.getDefault().getSceneHolder()
				.removeSceneSelectionModeListener(this);
		MoreDreadUI.getDefault().getSceneHolder()
				.removeSceneSelectionChangeListener(this);
		super.dispose();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFocus() {
	}

	@Override
	public void sceneSelectionChanged(final SceneSelectionChangeEvent event) {
		EnumSet<SelectionMode> supportedModes = null;
		if (event.getSelectedNodes().isEmpty()) {
			supportedModes = EnumSet.of(SelectionMode.OBJECT);
		} else {
			supportedModes = EnumSet.allOf(SelectionMode.class);

			for (final INode node : event.getSelectedNodes()) {
				supportedModes.retainAll(node.getSupportedSelectionModes());
			}
		}

		objectSelection.setEnabled(supportedModes
				.contains(SelectionMode.OBJECT));
		faceSelection.setEnabled(supportedModes.contains(SelectionMode.FACE));
		edgeSelection.setEnabled(supportedModes.contains(SelectionMode.EDGE));
		vertexSelection.setEnabled(supportedModes
				.contains(SelectionMode.VERTEX));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sceneSelectionModeChanged(final SceneSelectionModeEvent event) {
		final SelectionMode selectionMode = event.getSelectionMode();

		objectSelection.setSelection(selectionMode == SelectionMode.OBJECT);
		faceSelection.setSelection(selectionMode == SelectionMode.FACE);
		edgeSelection.setSelection(selectionMode == SelectionMode.EDGE);
		vertexSelection.setSelection(selectionMode == SelectionMode.VERTEX);
	}

	@Override
	public int computePreferredSize(final boolean width,
			final int availableParallel, final int availablePerpendicular,
			final int preferredResult) {
		final Point size = toolBar.computeSize(SWT.DEFAULT, SWT.DEFAULT);

		return width ? size.x + 10 : size.y + 30;
	}

	@Override
	public int getSizeFlags(final boolean width) {
		return width ? SWT.MIN : (SWT.MAX | SWT.MIN);
	}

}
