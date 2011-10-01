package net.untoldwind.moredread.ui.canvas;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.Callable;

import net.untoldwind.moredread.model.math.Camera;
import net.untoldwind.moredread.model.math.Quaternion;
import net.untoldwind.moredread.model.math.Vector2;
import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.renderer.SolidNodeRenderer;
import net.untoldwind.moredread.model.renderer.SolidNodeRendererParam;
import net.untoldwind.moredread.model.scene.BoundingBox;
import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.ISceneHolder;
import net.untoldwind.moredread.model.scene.SpatialNodeReference;
import net.untoldwind.moredread.ui.MoreDreadUI;
import net.untoldwind.moredread.ui.controls.IControlHandle;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.controls.IViewport;
import net.untoldwind.moredread.ui.controls.Modifier;
import net.untoldwind.moredread.ui.preferences.IPreferencesConstants;
import net.untoldwind.moredread.ui.tools.IToolController;
import net.untoldwind.moredread.ui.tools.UIToolsPlugin;

import com.jme.input.FirstPersonHandler;
import com.jme.input.InputHandler;
import com.jme.intersection.PickData;
import com.jme.intersection.PickResults;
import com.jme.intersection.TrianglePickResults;
import com.jme.math.FastMath;
import com.jme.math.Ray;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.state.BlendState;
import com.jme.scene.state.BlendState.BlendEquation;
import com.jme.scene.state.ZBufferState;
import com.jme.system.DisplaySystem;
import com.jme.system.canvas.JMECanvasImplementor;

public class MDCanvasImplementor extends JMECanvasImplementor implements
		IViewport {

	private Quaternion rotQuat;
	private Vector3 axis;
	long startTime = 0;
	long fps = 0;
	private InputHandler input;

	protected Node rootNode;

	protected Camera cam;

	Node controlsNode;
	Node backdropNode;

	GridBackdrop gridBackdrop;

	private List<IControlHandle> controlHandles;
	private final List<IModelControl> modelControls = new ArrayList<IModelControl>();
	private IControlHandle activeControlHandle;

	private final ISceneHolder sceneHolder;

	boolean updateNecessary = true;

	TaskQueue taskQueue = new TaskQueue();

	public MDCanvasImplementor(final int width, final int height,
			final ISceneHolder sceneHolder) {
		this.width = width;
		this.height = height;

		this.sceneHolder = sceneHolder;
	}

	@Override
	public Camera getCamera() {
		return cam;
	}

	@Override
	public void doSetup() {

		final DisplaySystem display = DisplaySystem.getDisplaySystem();
		renderer = display.getRenderer();

		/**
		 * Create a camera specific to the DisplaySystem that works with the
		 * width and height
		 */
		cam = new Camera(renderer.createCamera(width, height));

		/** Set up how our camera sees. */
		cam.setFrustumPerspective(45.0f, (float) width / (float) height, 1,
				1000);
		final Vector3 loc = new Vector3(0.0f, 0.0f, 25.0f);
		final Vector3 left = new Vector3(-1.0f, 0.0f, 0.0f);
		final Vector3 up = new Vector3(0.0f, 1.0f, 0.0f);
		final Vector3 dir = new Vector3(0.0f, 0f, -1.0f);
		/** Move our camera to a correct place and orientation. */
		cam.setFrame(loc, left, up, dir);
		/** Signal that we've changed our camera's location/frustum. */
		cam.update();
		/** Assign the camera to this renderer. */
		renderer.setCamera(cam.toJME());

		/** Set a black background. */
		renderer.setBackgroundColor(ColorRGBA.black.clone());

		/** Create rootNode */
		rootNode = new Node("rootNode");

		/**
		 * Create a ZBuffer to display pixels closest to the camera above
		 * farther ones.
		 */
		final ZBufferState buf = renderer.createZBufferState();
		buf.setEnabled(true);
		buf.setFunction(ZBufferState.TestFunction.LessThanOrEqualTo);

		rootNode.setRenderState(buf);

		simpleSetup();

		/**
		 * Update geometric and rendering information for both the rootNode and
		 * fpsNode.
		 */
		rootNode.updateGeometricState(0.0f, true);
		rootNode.updateRenderState();

		setup = true;
	}

	@Override
	public void doUpdate() {
		simpleUpdate();

		getRootNode().updateGeometricState(0, true);
		getRootNode().updateWorldBound();
	}

	@Override
	public void doRender() {
		renderer.clearBuffers();
		renderer.draw(rootNode);
		simpleRender();
		renderer.displayBackBuffer();
	}

	public Node getRootNode() {
		return rootNode;
	}

	public void simpleSetup() {
		controlsNode = new Node("controlsNode");
		backdropNode = new Node("backdropNode");

		rootNode.attachChild(backdropNode);

		final ZBufferState zBufferState = renderer.createZBufferState();
		zBufferState.setEnabled(false);
		final BlendState blendState = renderer.createBlendState();
		blendState.setEnabled(true);
		blendState.setBlendEnabled(true);
		blendState.setSourceFunction(BlendState.SourceFunction.SourceAlpha);
		blendState
				.setDestinationFunction(BlendState.DestinationFunction.DestinationAlpha);
		blendState.setBlendEquation(BlendEquation.Subtract);

		controlsNode.setRenderState(blendState);
		controlsNode.setRenderState(zBufferState);

		// Normal Scene setup stuff...
		rotQuat = new Quaternion();
		axis = new Vector3(1, 1, 0.5f);
		axis.normalizeLocal();

		startTime = System.currentTimeMillis() + 5000;

		input = new FirstPersonHandler(cam.toJME(), 50, 1);

		controlsNode.updateGeometricState(0, true);
		controlsNode.updateRenderState();

		updateToolControls();

		gridBackdrop = new GridBackdrop(this);
		backdropNode.attachChild(gridBackdrop);

		backdropNode.updateGeometricState(0, true);
		backdropNode.updateRenderState();
	}

	public void updateDisplayNodes() {
		updateNecessary = true;
		if (controlsNode.getChildren() != null) {
			for (final IModelControl modelControl : modelControls) {
				modelControl.updatePositions();
			}
			controlsNode.updateGeometricState(0, true);
			controlsNode.updateWorldBound();
		}

	}

	public void simpleUpdate() {
		input.update(0.05f);

		for (final IModelControl modelControl : modelControls) {
			modelControl.viewportChanged(this);
		}

		controlsNode.updateGeometricState(0, true);
		controlsNode.updateWorldBound();

		rotQuat.fromAngleNormalAxis(1 * FastMath.DEG_TO_RAD, axis);

		gridBackdrop.updateGrid(this);
		backdropNode.updateGeometricState(0, true);
		backdropNode.updateRenderState();
	}

	public void simpleRender() {
		if (updateNecessary) {
			final SolidNodeRendererParam rendererParam = new SolidNodeRendererParam(
					MoreDreadUI
							.getDefault()
							.getPreferenceStore()
							.getBoolean(
									IPreferencesConstants.MODEL3DVIEW_SELECTED_SHOW_NORMALS),
					MoreDreadUI
							.getDefault()
							.getPreferenceStore()
							.getBoolean(
									IPreferencesConstants.MODEL3DVIEW_SELECTED_SHOW_BOUNDINGBOX));

			sceneHolder.render(renderer, new SolidNodeRenderer(renderer,
					sceneHolder.getSelectionMode(), rendererParam));

			updateNecessary = false;
		} else {
			sceneHolder.render(renderer, null);
		}

		renderer.renderQueue();
		renderer.clearZBuffer();
		renderer.draw(controlsNode);
	}

	@Override
	public void resizeCanvas(final int newWidth, final int newHeight) {
		final int fWidth = newWidth <= 0 ? 1 : newWidth;
		final int fHeight = newHeight <= 0 ? 1 : newHeight;
		final Callable<?> exe = new Callable<Object>() {
			public Object call() {
				if (renderer != null) {
					height = fHeight;
					width = fWidth;

					renderer.getCamera().setFrustumPerspective(45.0f,
							(float) fWidth / (float) fHeight, 1, 1000);
					renderer.getCamera().apply();
					renderer.reinit(fWidth, fHeight);
				}
				return null;
			}
		};
		taskQueue.enqueue(exe);
	}

	@Override
	public INode pickNode(final Vector2 screenCoord) {
		final PickResults results = new TrianglePickResults();
		final Ray ray = new Ray();

		renderer.getCamera().getWorldCoordinates(screenCoord, 0, ray.origin);
		renderer.getCamera()
				.getWorldCoordinates(screenCoord, 0.3f, ray.direction)
				.subtractLocal(ray.origin).normalizeLocal();

		results.setCheckDistance(true);
		sceneHolder.findPick(ray, results);

		for (int i = 0; i < results.getNumber(); i++) {
			final PickData pick = results.getPickData(i);
			final SpatialNodeReference userData = (SpatialNodeReference) pick
					.getTargetMesh()
					.getUserData(ISceneHolder.NODE_USERDATA_KEY);

			if (userData != null) {
				return userData.getNode();
			}
		}
		return null;
	}

	@Override
	public BoundingBox getBoundingBox() {
		final BoundingBox boundingBox = new BoundingBox(sceneHolder.getScene()
				.getWorldBoundingBox());

		if (boundingBox.getXExtent() < 10.0f) {
			boundingBox.setXExtent(10.0f);
		}
		if (boundingBox.getYExtent() < 10.0f) {
			boundingBox.setYExtent(10.0f);
		}
		if (boundingBox.getZExtent() < 10.0f) {
			boundingBox.setZExtent(10.0f);
		}
		return boundingBox;
	}

	public void updateToolControls() {
		controlHandles = null;
		controlsNode.detachAllChildren();
		modelControls.clear();

		final IToolController toolController = UIToolsPlugin.getDefault()
				.getToolController();
		final List<? extends IModelControl> toolControls = toolController
				.getActiveTool().getModelControls(sceneHolder.getScene(), this);

		if (toolControls != null) {
			modelControls.addAll(toolControls);
		}

		for (final IModelControl modelControl : modelControls) {
			if (modelControl.getSpatial() != null) {
				controlsNode.attachChild(modelControl.getSpatial());
			}
		}

		controlsNode.updateGeometricState(0, true);
		controlsNode.updateRenderState();
	}

	public boolean handleMove(final int x, final int y,
			final EnumSet<Modifier> modifiers) {
		if (activeControlHandle != null) {
			return activeControlHandle.handleMove(new Vector2(x, height - y),
					modifiers);
		}
		return false;
	}

	public void handleClick(final int x, final int y,
			final EnumSet<Modifier> modifiers) {
		if (activeControlHandle != null) {
			activeControlHandle.handleClick(new Vector2(x, height - y),
					modifiers);
		}

	}

	public void handleDragStart(final int startX, final int startY,
			final EnumSet<Modifier> modifiers) {
		if (activeControlHandle != null) {
			activeControlHandle.handleDragStart(new Vector2(startX, height
					- startY), modifiers);
		}
	}

	public void handleDragMove(final int startX, final int startY,
			final int endX, final int endY, final EnumSet<Modifier> modifiers) {
		if (activeControlHandle != null) {
			activeControlHandle.handleDragMove(new Vector2(startX, height
					- startY), new Vector2(endX, height - endY), modifiers);
		}
	}

	public void handleDragEnd(final int startX, final int startY,
			final int endX, final int endY, final EnumSet<Modifier> modifiers) {
		if (activeControlHandle != null) {
			activeControlHandle.handleDragEnd(new Vector2(startX, height
					- startY), new Vector2(endX, height - endY), modifiers);
		}
	}

	public boolean findControl(final int x, final int y) {
		IControlHandle foundHandle = null;
		float minSalience = Float.MAX_VALUE;
		final Vector2 screenCoord = new Vector2(x, height - y);
		for (final IControlHandle controlHandle : getControlHandles()) {
			final float salience = controlHandle.matches(screenCoord);
			if (salience >= 0 && salience < minSalience) {
				minSalience = salience;
				foundHandle = controlHandle;
			}
		}

		if (foundHandle != activeControlHandle) {
			if (activeControlHandle != null) {
				activeControlHandle.setActive(false);
			}
			activeControlHandle = foundHandle;
			if (activeControlHandle != null) {
				activeControlHandle.setActive(true);
			}
			return true;
		}
		return false;
	}

	public TaskQueue getTaskQueue() {
		return taskQueue;
	}

	private List<IControlHandle> getControlHandles() {
		if (controlHandles == null) {
			final List<IControlHandle> newControlHandles = new ArrayList<IControlHandle>();
			for (final IModelControl modelControl : modelControls) {
				modelControl.collectControlHandles(newControlHandles, this);
			}

			controlHandles = newControlHandles;
		}
		return controlHandles;
	}
}
