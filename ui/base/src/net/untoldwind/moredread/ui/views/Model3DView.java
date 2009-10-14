package net.untoldwind.moredread.ui.views;

import java.util.HashMap;

import net.untoldwind.moredread.jme.MoreDreadJME;
import net.untoldwind.moredread.model.io.ModelIOPlugin;
import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.model.scene.SceneSelection;
import net.untoldwind.moredread.model.scene.event.ISceneGeometryChangeListener;
import net.untoldwind.moredread.model.scene.event.ISceneSelectionChangeListener;
import net.untoldwind.moredread.model.scene.event.ISceneSelectionModeListener;
import net.untoldwind.moredread.model.scene.event.SceneGeometryChangeEvent;
import net.untoldwind.moredread.model.scene.event.SceneSelectionChangeEvent;
import net.untoldwind.moredread.model.scene.event.SceneSelectionModeEvent;
import net.untoldwind.moredread.ui.MoreDreadUI;
import net.untoldwind.moredread.ui.canvas.MDCanvas;
import net.untoldwind.moredread.ui.canvas.MDCanvasImplementor;
import net.untoldwind.moredread.ui.canvas.SceneSelectionProvider;
import net.untoldwind.moredread.ui.controls.Modifier;
import net.untoldwind.moredread.ui.input.UIInputPlugin;
import net.untoldwind.moredread.ui.input.event.MoveDepthCameraUpdate;
import net.untoldwind.moredread.ui.input.event.MoveHorizontalCameraUpdate;
import net.untoldwind.moredread.ui.input.event.MoveVerticalCameraUpdate;
import net.untoldwind.moredread.ui.input.event.RotateAroundXCameraUpdate;
import net.untoldwind.moredread.ui.input.event.RotateAroundYCameraUpdate;
import net.untoldwind.moredread.ui.preferences.IPreferencesConstants;

import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.opengl.GLData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.operations.RedoActionHandler;
import org.eclipse.ui.operations.UndoActionHandler;
import org.eclipse.ui.part.ViewPart;

import com.jme.input.KeyInput;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.system.DisplaySystem;
import com.jmex.swt.input.SWTKeyInput;
import com.jmex.swt.lwjgl.LWJGLSWTConstants;

public class Model3DView extends ViewPart implements ISaveablePart,
		ISceneSelectionModeListener, ISceneSelectionChangeListener,
		ISceneGeometryChangeListener {

	public static final String ID = "net.untoldwind.moredread.ui.model3dview";

	DisplaySystem displaySystem;
	MDCanvasImplementor implementor;
	MDCanvas canvas;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createPartControl(final Composite parent) {
		final Composite top = new Composite(parent, SWT.NONE);
		final GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		top.setLayout(layout);

		final CoolBar coolBar = new CoolBar(top, SWT.NONE | SWT.VERTICAL);
		coolBar.setLayoutData(new GridData(GridData.FILL_VERTICAL));

		final ToolBar cameraToolBar = new ToolBar(coolBar, SWT.FLAT
				| SWT.VERTICAL);

		final ToolItem rotateItem = new ToolItem(cameraToolBar, SWT.NONE);
		rotateItem.setImage(MoreDreadUI.getDefault().getImage(
				"/icons/RotateXY.png"));
		rotateItem.setData(new RotateXYHandler());
		final ToolItem moveXYItem = new ToolItem(cameraToolBar, SWT.NONE);
		moveXYItem.setImage(MoreDreadUI.getDefault().getImage(
				"/icons/MoveXY.png"));
		moveXYItem.setData(new MoveXYHandler());
		final ToolItem moveXZItem = new ToolItem(cameraToolBar, SWT.NONE);
		moveXZItem.setImage(MoreDreadUI.getDefault().getImage(
				"/icons/MoveXZ.png"));
		moveXZItem.setData(new MoveXZHandler());

		cameraToolBar.pack();

		final Point size = cameraToolBar.getSize();
		final CoolItem item = new CoolItem(coolBar, SWT.NONE);
		item.setControl(cameraToolBar);
		final Point preferred = item.computeSize(size.x, size.y);
		item.setPreferredSize(preferred);

		final DragToolbarListener dragToolbarListener = new DragToolbarListener(
				cameraToolBar);

		cameraToolBar.addMouseListener(dragToolbarListener);
		cameraToolBar.addMouseMoveListener(dragToolbarListener);

		final GLData data = new GLData();
		data.doubleBuffer = true;

		displaySystem = MoreDreadJME.getDefault().getDisplaySystem();
		implementor = new MDCanvasImplementor(100, 100, MoreDreadUI
				.getDefault().getSceneHolder());

		final HashMap<String, Object> props = new HashMap<String, Object>();

		props.put(LWJGLSWTConstants.PARENT, top);
		props.put(LWJGLSWTConstants.STYLE, SWT.NONE);
		props.put(LWJGLSWTConstants.DEPTH_BITS, 8);
		canvas = (MDCanvas) displaySystem.createCanvas(100, 100, "MD", props);
		canvas.setImplementor(implementor);
		canvas.setDrawWhenDirty(false);
		canvas.setUpdateInput(true);

		canvas.setLayoutData(new GridData(GridData.FILL_BOTH));

		canvas.addListener(SWT.Resize, new Listener() {
			public void handleEvent(final Event event) {
				final Rectangle bounds = canvas.getBounds();
				implementor.resizeCanvas(bounds.width, bounds.height);
				canvas.queueRender();
			}
		});

		canvas.addKeyListener((SWTKeyInput) KeyInput.get());
		canvas.addKeyListener(new KeyListener() {
			public void keyReleased(final KeyEvent e) {
			}

			public void keyPressed(final KeyEvent e) {
				canvas.queueRender();
			}
		});

		final DragViewListener dragViewListener = new DragViewListener();
		canvas.addMouseListener(dragViewListener);
		canvas.addMouseMoveListener(dragViewListener);

		canvas.setTargetRate(60);
		canvas.init();

		applyPreferences();

		UIInputPlugin.getDefault().registerInputReceiver(canvas);

		canvas.queueRender();

		MoreDreadUI.getDefault().getSceneHolder()
				.addSceneSelectionModeListener(this);
		MoreDreadUI.getDefault().getSceneHolder().getScene()
				.getSceneSelection().addSceneSelectionChangeListener(this);
		MoreDreadUI.getDefault().getSceneHolder().getScene()
				.addSceneGeometryChangeListener(this);

		createGlobalActionHandlers();

		getSite().setSelectionProvider(
				new SceneSelectionProvider(MoreDreadUI.getDefault()
						.getSceneHolder().getScene()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFocus() {
		canvas.setFocus();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sceneSelectionModeChanged(final SceneSelectionModeEvent event) {
		implementor.updateDisplayNodes();
		implementor.updateToolControls();
		canvas.queueRender();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sceneSelectionChanged(final SceneSelectionChangeEvent event) {
		implementor.updateDisplayNodes();
		implementor.updateToolControls();
		canvas.queueRender();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sceneGeometryChanged(final SceneGeometryChangeEvent event) {
		implementor.updateDisplayNodes();
		canvas.queueRender();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doSave(final IProgressMonitor monitor) {
		ModelIOPlugin.getDefault().sceneSave(
				MoreDreadUI.getDefault().getSceneHolder().getScene());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doSaveAs() {
		ModelIOPlugin.getDefault().sceneSaveAs(
				MoreDreadUI.getDefault().getSceneHolder().getScene());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isDirty() {
		// TODO: Use change handling to eval this
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSaveOnCloseNeeded() {
		// TODO: Use change handling to eval this
		return false;
	}

	/**
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(final Class adapter) {
		if (adapter.equals(Scene.class)) {
			return MoreDreadUI.getDefault().getSceneHolder().getScene();
			/*
			 * } else if (adapter == IPropertySheetPage.class) { return new
			 * TabbedPropertySheetPage( new NodePropertySheetContributor());
			 */
		}
		return super.getAdapter(adapter);
	}

	private void applyPreferences() {
		final RGB backgroundRGB = PreferenceConverter.getColor(MoreDreadUI
				.getDefault().getPreferenceStore(),
				IPreferencesConstants.MODEL3DVIEW_BACKGROUND_COLOR);

		implementor
				.setBackground(new ColorRGBA(backgroundRGB.red / 255.0f,
						backgroundRGB.green / 255.0f,
						backgroundRGB.blue / 255.0f, 1.0f));
	}

	private void createGlobalActionHandlers() {
		// set up action handlers that operate on the current context
		final UndoActionHandler undoAction = new UndoActionHandler(this
				.getSite(), IOperationHistory.GLOBAL_UNDO_CONTEXT);
		final RedoActionHandler redoAction = new RedoActionHandler(this
				.getSite(), IOperationHistory.GLOBAL_UNDO_CONTEXT);
		final IActionBars actionBars = getViewSite().getActionBars();
		actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(),
				undoAction);
		actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(),
				redoAction);
	}

	class DragViewListener implements MouseMoveListener, MouseListener {
		boolean drag = false;
		int dragStartX, dragStartY;

		public void mouseMove(final MouseEvent e) {
			if ((e.stateMask & SWT.BUTTON1) != 0
					&& (e.x - dragStartX) * (e.x - dragStartX)
							+ (e.y - dragStartY) * (e.y - dragStartY) >= 4) {
				drag = true;
				implementor.handleDrag(dragStartX, dragStartY, e.x, e.y,
						Modifier.fromStateMask(e.stateMask), false);
			} else {
				if (implementor.findControl(e.x, e.y)) {
					canvas.queueRender();
				}
			}
		}

		public void mouseUp(final MouseEvent e) {
			if (!drag) {
				implementor.handleClick(e.x, e.y, Modifier
						.fromStateMask(e.stateMask));
			} else if (drag) {
				implementor.handleDrag(dragStartX, dragStartY, e.x, e.y,
						Modifier.fromStateMask(e.stateMask), true);
			}
		}

		public void mouseDoubleClick(final MouseEvent e) {
		}

		public void mouseDown(final MouseEvent e) {
			drag = false;
			dragStartX = e.x;
			dragStartY = e.y;
		}
	}

	static interface IDragHandler {
		void handleDrag(int xDiff, int yDiff);
	}

	class DragToolbarListener implements MouseMoveListener, MouseListener {
		ToolBar toolBar;

		IDragHandler currentHandler;
		Point dragStart;
		Point dragStartAbs;

		public DragToolbarListener(final ToolBar toolBar) {
			this.toolBar = toolBar;
		}

		public void mouseMove(final MouseEvent e) {
			if (currentHandler != null) {
				final int xDiff = (e.x - dragStart.x);
				final int yDiff = (e.y - dragStart.y);

				if (xDiff * xDiff + yDiff * yDiff > 3) {
					currentHandler.handleDrag(xDiff, yDiff);

					dragStart.x = e.x;
					dragStart.y = e.y;
				}
				// e.display.setCursorLocation(dragStartAbs);
			}
		}

		public void mouseDoubleClick(final MouseEvent e) {
			// TODO Auto-generated method stub

		}

		public void mouseDown(final MouseEvent e) {
			dragStart = new Point(e.x, e.y);
			dragStartAbs = e.display.getCursorLocation();
			final ToolItem item = toolBar.getItem(dragStart);

			if (item != null && item.getData() != null
					&& item.getData() instanceof IDragHandler) {
				currentHandler = (IDragHandler) item.getData();
			}
		}

		public void mouseUp(final MouseEvent e) {
			currentHandler = null;
		}
	}

	class MoveXYHandler implements IDragHandler {

		final float factor = 0.5f;

		public void handleDrag(final int xDiff, final int yDiff) {
			if (xDiff != 0) {
				canvas.queueCameraUpdate(new MoveHorizontalCameraUpdate(factor
						* xDiff));
			}
			if (yDiff != 0) {
				canvas.queueCameraUpdate(new MoveVerticalCameraUpdate(factor
						* yDiff));
			}
		}
	}

	class MoveXZHandler implements IDragHandler {
		final float factor = 0.5f;

		public void handleDrag(final int xDiff, final int yDiff) {
			if (xDiff != 0) {
				canvas.queueCameraUpdate(new MoveHorizontalCameraUpdate(factor
						* xDiff));
			}
			if (yDiff != 0) {
				canvas.queueCameraUpdate(new MoveDepthCameraUpdate(factor
						* yDiff));
			}
		}
	}

	class RotateXYHandler implements IDragHandler {
		final float factor = 0.01f;

		public void handleDrag(final int xDiff, final int yDiff) {
			final Vector3f center = new Vector3f(0, 0, 0);
			final SceneSelection sceneSelection = MoreDreadUI.getDefault()
					.getSceneHolder().getScene().getSceneSelection();

			if (!sceneSelection.getSelectedNodes().isEmpty()) {
				for (final INode node : sceneSelection.getSelectedNodes()) {
					center.addLocal(node.getWorldBoundingBox().getCenter());
				}
				center.divideLocal(sceneSelection.getSelectedNodes().size());
			}
			if (xDiff != 0) {
				canvas.queueCameraUpdate(new RotateAroundXCameraUpdate(factor
						* xDiff, center, new Vector3f(0, 1, 0)));
			}
			if (yDiff != 0) {
				canvas.queueCameraUpdate(new RotateAroundYCameraUpdate(factor
						* yDiff, center));
			}
		}
	}
}
