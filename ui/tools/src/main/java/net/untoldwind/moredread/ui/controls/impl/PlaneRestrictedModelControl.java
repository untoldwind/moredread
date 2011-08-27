package net.untoldwind.moredread.ui.controls.impl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

import net.untoldwind.moredread.model.scene.BoundingBox;
import net.untoldwind.moredread.ui.controls.IControlHandle;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.controls.IViewport;
import net.untoldwind.moredread.ui.tools.spi.IToolAdapter;

import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.QuadMesh;
import com.jme.scene.Spatial;
import com.jme.scene.state.BlendState;
import com.jme.system.DisplaySystem;
import com.jme.util.geom.BufferUtils;

public class PlaneRestrictedModelControl extends Node implements IModelControl {
	private static final long serialVersionUID = 1L;

	IToolAdapter toolAdapter;

	public PlaneRestrictedModelControl(final IToolAdapter toolAdapter) {
		super("PlaneRestrictedModelControl");

		this.toolAdapter = toolAdapter;

	}

	@Override
	public Spatial getSpatial() {
		return this;
	}

	@Override
	public void collectControlHandles(final List<IControlHandle> handles,
			final IViewport viewport) {
		updateBackdrop(viewport);
		// TODO Auto-generated method stub

	}

	@Override
	public void viewportChanged(final IViewport viewport) {
		updateBackdrop(viewport);
		// TODO Auto-generated method stub

	}

	@Override
	public void setActive(final boolean active) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatePositions() {
		// TODO Auto-generated method stub

	}

	private void updateBackdrop(final IViewport viewport) {
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
	}
}
