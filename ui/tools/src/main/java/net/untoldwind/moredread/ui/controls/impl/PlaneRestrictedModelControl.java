package net.untoldwind.moredread.ui.controls.impl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.EnumSet;
import java.util.List;

import net.untoldwind.moredread.model.scene.BoundingBox;
import net.untoldwind.moredread.ui.controls.IControlHandle;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.controls.IViewport;
import net.untoldwind.moredread.ui.controls.Modifier;
import net.untoldwind.moredread.ui.tools.spi.IToolAdapter;

import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Line;
import com.jme.scene.Node;
import com.jme.scene.QuadMesh;
import com.jme.scene.Spatial;
import com.jme.scene.state.BlendState;
import com.jme.system.DisplaySystem;
import com.jme.util.geom.BufferUtils;

public class PlaneRestrictedModelControl extends Node implements IModelControl {
	private static final long serialVersionUID = 1L;

	IToolAdapter toolAdapter;
	PlaneControlHandle planeControlHandle;

	IViewport viewport;

	public PlaneRestrictedModelControl(final IToolAdapter toolAdapter) {
		super("PlaneRestrictedModelControl");

		this.toolAdapter = toolAdapter;
		this.planeControlHandle = new PlaneControlHandle();
	}

	@Override
	public Spatial getSpatial() {
		return this;
	}

	@Override
	public void collectControlHandles(final List<IControlHandle> handles,
			final IViewport viewport) {
		this.viewport = viewport;
		updateBackdrop();

		planeControlHandle.setCamera(viewport.getCamera());

		handles.add(planeControlHandle);
	}

	@Override
	public void viewportChanged(final IViewport viewport) {
		this.viewport = viewport;
		updateBackdrop();

		planeControlHandle.setCamera(viewport.getCamera());
	}

	@Override
	public void setActive(final boolean active) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatePositions() {
		updateBackdrop();
	}

	@Override
	public IToolAdapter getToolAdapter() {
		return toolAdapter;
	}

	private void updateBackdrop() {
		detachAllChildren();

		final BoundingBox boundingBox = viewport.getBoundingBox();
		float maxExtend = 1.0f;

		if (boundingBox.getXExtent() > maxExtend) {
			maxExtend = boundingBox.getXExtent();
		}
		if (boundingBox.getYExtent() > maxExtend) {
			maxExtend = boundingBox.getYExtent();
		}
		if (boundingBox.getZExtent() > maxExtend) {
			maxExtend = boundingBox.getZExtent();
		}

		final FloatBuffer vertexBuffer = BufferUtils.createVector3Buffer(4);
		final IntBuffer indexBuffer = BufferUtils.createIntBuffer(4);

		vertexBuffer.put(boundingBox.getCenter().x - maxExtend);
		vertexBuffer.put(boundingBox.getCenter().y - maxExtend);
		vertexBuffer.put(0.0f);
		indexBuffer.put(0);
		vertexBuffer.put(boundingBox.getCenter().x + maxExtend);
		vertexBuffer.put(boundingBox.getCenter().y - maxExtend);
		vertexBuffer.put(0.0f);
		indexBuffer.put(1);
		vertexBuffer.put(boundingBox.getCenter().x + maxExtend);
		vertexBuffer.put(boundingBox.getCenter().y + maxExtend);
		vertexBuffer.put(0.0f);
		indexBuffer.put(2);
		vertexBuffer.put(boundingBox.getCenter().x - maxExtend);
		vertexBuffer.put(boundingBox.getCenter().y + maxExtend);
		vertexBuffer.put(0.0f);
		indexBuffer.put(3);

		final QuadMesh quadMesh = new QuadMesh(null, vertexBuffer, null, null,
				null, indexBuffer);

		quadMesh.setDefaultColor(new ColorRGBA(1.0f, 1.0f, 1.0f, 0.3f));
		final BlendState blendState = DisplaySystem.getDisplaySystem()
				.getRenderer().createBlendState();
		blendState.setEnabled(true);
		blendState.setBlendEnabled(true);
		blendState.setSourceFunction(BlendState.SourceFunction.SourceAlpha);
		blendState
				.setDestinationFunction(BlendState.DestinationFunction.SourceAlpha);
		quadMesh.setRenderState(blendState);
		attachChild(quadMesh);

		final Vector3f position = toolAdapter.getCenter();
		FloatBuffer lineBuffer = BufferUtils.createVector3Buffer(2);
		lineBuffer.put(boundingBox.getCenter().x - maxExtend);
		lineBuffer.put(position.y);
		lineBuffer.put(0.0f);
		lineBuffer.put(boundingBox.getCenter().x + maxExtend);
		lineBuffer.put(position.y);
		lineBuffer.put(0.0f);
		Line lines = new Line("", lineBuffer, null, null, null);

		lines.setAntialiased(false);
		lines.setLineWidth(1.0f);
		lines.setDefaultColor(ColorRGBA.red.clone());
		attachChild(lines);

		lineBuffer = BufferUtils.createVector3Buffer(2);
		lineBuffer.put(position.x);
		lineBuffer.put(boundingBox.getCenter().y - maxExtend);
		lineBuffer.put(0.0f);
		lineBuffer.put(position.x);
		lineBuffer.put(boundingBox.getCenter().y + maxExtend);
		lineBuffer.put(0.0f);
		lines = new Line("", lineBuffer, null, null, null);

		lines.setAntialiased(false);
		lines.setLineWidth(1.0f);
		lines.setDefaultColor(ColorRGBA.green.clone());
		attachChild(lines);
	}

	public class PlaneControlHandle implements IControlHandle {
		private final float salience;
		private Camera camera;

		public PlaneControlHandle() {
			this.salience = MAX_SALIENCE;
		}

		@Override
		public float matches(final Vector2f screenCoord) {
			return salience;
		}

		@Override
		public void setActive(final boolean active) {
			// Do nothing
		}

		@Override
		public void handleClick(final Vector2f position,
				final EnumSet<Modifier> modifiers) {
			toolAdapter.handleClick(project(position), modifiers);
		}

		@Override
		public boolean handleMove(final Vector2f position,
				final EnumSet<Modifier> modifiers) {
			return toolAdapter.handleMove(PlaneRestrictedModelControl.this,
					project(position), modifiers);

			// PlaneRestrictedModelControl.this.position = project(position);

			// updateBackdrop();

			// return true;
		}

		@Override
		public void handleDragStart(final Vector2f dragStart,
				final EnumSet<Modifier> modifiers) {
			// TODO Auto-generated method stub

		}

		@Override
		public void handleDragMove(final Vector2f dragStart,
				final Vector2f dragEnd, final EnumSet<Modifier> modifiers) {
			// TODO Auto-generated method stub

		}

		@Override
		public void handleDragEnd(final Vector2f dragStart,
				final Vector2f dragEnd, final EnumSet<Modifier> modifiers) {
			// TODO Auto-generated method stub

		}

		void setCamera(final Camera camera) {
			this.camera = camera;
		}

		private Vector3f project(final Vector2f position) {
			final Vector3f origin = camera.getWorldCoordinates(position, 0);
			final Vector3f direction = camera
					.getWorldCoordinates(position, 0.3f).subtractLocal(origin)
					.normalizeLocal();

			final float dist = -origin.z / direction.z;

			direction.multLocal(dist);
			return origin.addLocal(direction);
		}
	}
}
