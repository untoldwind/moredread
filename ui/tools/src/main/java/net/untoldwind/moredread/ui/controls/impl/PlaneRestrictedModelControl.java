package net.untoldwind.moredread.ui.controls.impl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

import net.untoldwind.moredread.model.enums.CartesianPlane;
import net.untoldwind.moredread.model.scene.BoundingBox;
import net.untoldwind.moredread.ui.controls.IControlHandle;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.controls.IViewport;
import net.untoldwind.moredread.ui.tools.spi.IToolAdapter;

import com.jme.math.Vector3f;
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
	PlaneProjectControlHandle controlHandle;

	IViewport viewport;
	CartesianPlane plane;

	public PlaneRestrictedModelControl(final IToolAdapter toolAdapter) {
		super("PlaneRestrictedModelControl");

		this.toolAdapter = toolAdapter;
		this.controlHandle = new PlaneProjectControlHandle(this,
				IControlHandle.MAX_SALIENCE);
	}

	@Override
	public Spatial getSpatial() {
		return this;
	}

	@Override
	public void collectControlHandles(final List<IControlHandle> handles,
			final IViewport viewport) {
		this.viewport = viewport;
		this.plane = CartesianPlane.choose(viewport.getCamera().getDirection());
		updateBackdrop();

		controlHandle.setProjection(plane, viewport.getCamera());

		handles.add(controlHandle);
	}

	@Override
	public void viewportChanged(final IViewport viewport) {
		this.viewport = viewport;
		this.plane = CartesianPlane.choose(viewport.getCamera().getDirection());

		updateBackdrop();

		controlHandle.setProjection(plane, viewport.getCamera());
	}

	@Override
	public void setActive(final boolean active) {
	}

	@Override
	public void updatePositions() {
		controlHandle.setPosition(toolAdapter.getCenter());

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

		final Vector3f position = toolAdapter.getFeedbackPoint();
		final Vector3f v1 = plane.project(boundingBox.getCenter(), position,
				-maxExtend, -maxExtend);
		final Vector3f v2 = plane.project(boundingBox.getCenter(), position,
				maxExtend, -maxExtend);
		final Vector3f v3 = plane.project(boundingBox.getCenter(), position,
				maxExtend, maxExtend);
		final Vector3f v4 = plane.project(boundingBox.getCenter(), position,
				-maxExtend, maxExtend);
		vertexBuffer.put(v1.x);
		vertexBuffer.put(v1.y);
		vertexBuffer.put(v1.z);
		vertexBuffer.put(v2.x);
		vertexBuffer.put(v2.y);
		vertexBuffer.put(v2.z);
		vertexBuffer.put(v3.x);
		vertexBuffer.put(v3.y);
		vertexBuffer.put(v3.z);
		vertexBuffer.put(v4.x);
		vertexBuffer.put(v4.y);
		vertexBuffer.put(v4.z);
		if (viewport.getCamera().getDirection().dot(plane.getNormal()) < 0.0) {
			indexBuffer.put(0);
			indexBuffer.put(1);
			indexBuffer.put(2);
			indexBuffer.put(3);
		} else {
			indexBuffer.put(3);
			indexBuffer.put(2);
			indexBuffer.put(1);
			indexBuffer.put(0);
		}

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

		FloatBuffer lineBuffer;
		Line lines;
		lineBuffer = BufferUtils.createVector3Buffer(2);
		lineBuffer.put(boundingBox.getCenter().x - maxExtend);
		lineBuffer.put(position.y);
		lineBuffer.put(position.z);
		lineBuffer.put(boundingBox.getCenter().x + maxExtend);
		lineBuffer.put(position.y);
		lineBuffer.put(position.z);
		lines = new Line("", lineBuffer, null, null, null);

		lines.setAntialiased(false);
		lines.setLineWidth(1.0f);
		lines.setDefaultColor(ColorRGBA.red.clone());
		attachChild(lines);

		lineBuffer = BufferUtils.createVector3Buffer(2);
		lineBuffer.put(position.x);
		lineBuffer.put(boundingBox.getCenter().y - maxExtend);
		lineBuffer.put(position.z);
		lineBuffer.put(position.x);
		lineBuffer.put(boundingBox.getCenter().y + maxExtend);
		lineBuffer.put(position.z);
		lines = new Line("", lineBuffer, null, null, null);

		lines.setAntialiased(false);
		lines.setLineWidth(1.0f);
		lines.setDefaultColor(ColorRGBA.green.clone());
		attachChild(lines);

		lineBuffer = BufferUtils.createVector3Buffer(2);
		lineBuffer.put(position.x);
		lineBuffer.put(position.y);
		lineBuffer.put(boundingBox.getCenter().z - maxExtend);
		lineBuffer.put(position.x);
		lineBuffer.put(position.y);
		lineBuffer.put(boundingBox.getCenter().z + maxExtend);
		lines = new Line("", lineBuffer, null, null, null);

		lines.setAntialiased(false);
		lines.setLineWidth(1.0f);
		lines.setDefaultColor(ColorRGBA.blue.clone());
		attachChild(lines);
	}
}
