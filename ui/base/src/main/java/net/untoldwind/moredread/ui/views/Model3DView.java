package net.untoldwind.moredread.ui.views;

import java.util.HashMap;

import net.untoldwind.moredread.jme.MDKeyInput;
import net.untoldwind.moredread.jme.MoreDreadJME;
import net.untoldwind.moredread.model.io.ModelIOPlugin;
import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.ISpatialNode;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.model.scene.SceneSelection;
import net.untoldwind.moredread.model.scene.event.ISceneChangeListener;
import net.untoldwind.moredread.model.scene.event.ISceneSelectionChangeListener;
import net.untoldwind.moredread.model.scene.event.ISceneSelectionModeListener;
import net.untoldwind.moredread.model.scene.event.SceneChangeEvent;
import net.untoldwind.moredread.model.scene.event.SceneSelectionChangeEvent;
import net.untoldwind.moredread.model.scene.event.SceneSelectionModeEvent;
import net.untoldwind.moredread.ui.MoreDreadUI;
import net.untoldwind.moredread.ui.canvas.MDCanvas;
import net.untoldwind.moredread.ui.canvas.MDCanvasConstructor;
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
import net.untoldwind.moredread.ui.properties.NodePropertySheetContributor;
import net.untoldwind.moredread.ui.tools.ActiveToolChangedEvent;
import net.untoldwind.moredread.ui.tools.IToolActivationListener;
import net.untoldwind.moredread.ui.tools.UIToolsPlugin;

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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.jme.input.KeyInput;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.system.DisplaySystem;

public class Model3DView extends ViewPart implements ISaveablePart,
		ISceneSelectionModeListener, ISceneSelectionChangeListener,
		ISceneChangeListener, IToolActivationListener {

	public static final String ID = "net.untoldwind.moredread.ui.model3dview";

	public static final String CONTEXT_ID = "net.untoldwind.moredread.ui.context.3dview";

	private DisplaySystem displaySystem;
	private MDCanvasImplementor implementor;
	private MDCanvas canvas;

	private IContextActivation contextActivation;

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

		displaySystem = MoreDreadJME.getDefault().getDisplaySystem();
		implementor = new MDCanvasImplementor(100, 100, MoreDreadUI
				.getDefault().getSceneHolder());

		final HashMap<String, Object> props = new HashMap<String, Object>();

		props.put(MDCanvasConstructor.PARENT, top);
		props.put(MDCanvasConstructor.STYLE, SWT.NONE);
		props.put(MDCanvasConstructor.DEPTH_BITS, 8);
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

		canvas.addKeyListener((MDKeyInput) KeyInput.get());
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
				.addSceneChangeListener(this);
		UIToolsPlugin.getDefault().getToolController()
				.addToolActivationListener(this);

		getSite().setSelectionProvider(
				new SceneSelectionProvider(MoreDreadUI.getDefault()
						.getSceneHolder().getScene()));

		final IContextService contextService = (IContextService) getSite()
				.getService(IContextService.class);
		contextActivation = contextService.activateContext(CONTEXT_ID);

	}

	@Override
	public void dispose() {
		final IContextService contextService = (IContextService) getSite()
				.getService(IContextService.class);
		contextService.deactivateContext(contextActivation);

		MoreDreadUI.getDefault().getSceneHolder()
				.removeSceneSelectionModeListener(this);
		MoreDreadUI.getDefault().getSceneHolder().getScene()
				.getSceneSelection().removeSceneSelectionChangeListener(this);
		MoreDreadUI.getDefault().getSceneHolder().getScene()
				.removeSceneChangeListener(this);
		UIToolsPlugin.getDefault().getToolController()
				.removeToolActivationListener(this);

		super.dispose();
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
	public void sceneChanged(final SceneChangeEvent event) {
		implementor.updateDisplayNodes();
		canvas.queueRender();
	}

	@Override
	public void activeToolChanged(final ActiveToolChangedEvent event) {
		implementor.updateToolControls();
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
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") final Class adapter) {
		if (adapter.equals(Scene.class)) {
			return MoreDreadUI.getDefault().getSceneHolder().getScene();
		} else if (adapter == IPropertySheetPage.class) {
			return new TabbedPropertySheetPage(
					new NodePropertySheetContributor());
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

	class DragViewListener implements MouseMoveListener, MouseListener {
		boolean drag = false;
		int dragStartX, dragStartY;
		IDragHandler rotateHandler = new RotateXYHandler();

		public void mouseMove(final MouseEvent e) {
			if ((e.stateMask & SWT.BUTTON3) != 0) {
				final int xDiff = (e.x - dragStartX);
				final int yDiff = (e.y - dragStartY);

				if (xDiff * xDiff + yDiff * yDiff > 3) {
					rotateHandler
							.handleDrag(e.x - dragStartX, e.y - dragStartY);

					dragStartX = e.x;
					dragStartY = e.y;
				}
			} else if ((e.stateMask & SWT.BUTTON1) != 0
					&& (e.x - dragStartX) * (e.x - dragStartX)
							+ (e.y - dragStartY) * (e.y - dragStartY) >= 4) {
				if (!drag) {
					implementor.handleDragStart(dragStartX, dragStartY,
							Modifier.fromStateMask(e.stateMask));
				}
				drag = true;
				implementor.handleDragMove(dragStartX, dragStartY, e.x, e.y,
						Modifier.fromStateMask(e.stateMask));
			} else {
				if (implementor.findControl(e.x, e.y)
						|| implementor.handleMove(e.x, e.y,
								Modifier.fromStateMask(e.stateMask))) {
					canvas.queueRender();
				}
			}
		}

		public void mouseUp(final MouseEvent e) {
			if (!drag) {
				implementor.handleClick(e.x, e.y,
						Modifier.fromStateMask(e.stateMask));
			} else if (drag) {
				implementor.handleDragEnd(dragStartX, dragStartY, e.x, e.y,
						Modifier.fromStateMask(e.stateMask));
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
				int counter = 0;
				for (final INode node : sceneSelection.getSelectedNodes()) {
					if (node instanceof ISpatialNode) {
						center.addLocal(((ISpatialNode) node)
								.getWorldBoundingBox().getCenter());
						counter++;
					}
				}
				if (counter > 0) {
					center.divideLocal(counter);
				}
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
