package net.untoldwind.moredread.ui.controls.impl;

import java.nio.FloatBuffer;
import java.util.List;

import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.IPoint;
import net.untoldwind.moredread.model.scene.IMeshNode;
import net.untoldwind.moredread.ui.controls.IControlHandle;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.controls.IViewport;
import net.untoldwind.moredread.ui.tools.spi.IToolAdapter;

import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Point;
import com.jme.scene.Spatial;
import com.jme.scene.state.BlendState;
import com.jme.system.DisplaySystem;
import com.jme.util.geom.BufferUtils;

public class VertexSelectionModelControl extends Point implements IModelControl {

	private static final long serialVersionUID = 2311223574741358155L;

	private final IToolAdapter toolAdapter;

	private final IMeshNode node;
	private final int vertexIndex;

	private transient PointControlHandle pointControlHandle;

	public VertexSelectionModelControl(final IMeshNode node,
			final int vertexIndex, final IToolAdapter toolAdapter) {
		this.toolAdapter = toolAdapter;
		this.node = node;
		this.vertexIndex = vertexIndex;

		updateGeometry();

		final BlendState blendState = DisplaySystem.getDisplaySystem()
				.getRenderer().createBlendState();
		blendState.setEnabled(true);
		blendState.setBlendEnabled(true);
		blendState.setSourceFunction(BlendState.SourceFunction.SourceAlpha);
		blendState
				.setDestinationFunction(BlendState.DestinationFunction.OneMinusSourceAlpha);

		setDefaultColor(new ColorRGBA(1, 1, 1, 0.0f));
		setRenderState(blendState);
		setPointSize(6f);
		setAntialiased(false);
	}

	@Override
	public void viewportChanged(final IViewport viewport) {
		updateHandle(viewport.getCamera());
	}

	@Override
	public void collectControlHandles(final List<IControlHandle> handles,
			final IViewport viewport) {
		updateHandle(viewport.getCamera());

		handles.add(pointControlHandle);
	}

	@Override
	public Spatial getSpatial() {
		return this;
	}

	@Override
	public void setActive(final boolean active) {
		if (active) {
			setDefaultColor(new ColorRGBA(1, 1, 1, 0.5f));
		} else {
			setDefaultColor(new ColorRGBA(1, 1, 1, 0.0f));
		}
	}

	@Override
	public void updatePositions() {
		updateGeometry();
	}

	void updateGeometry() {
		final IMesh mesh = node.getGeometry();
		final IPoint vertex = mesh.getVertex(vertexIndex);
		final FloatBuffer vertexBuffer = BufferUtils.createVector3Buffer(1);
		final Vector3f v = node.localToWorld(vertex.getPoint(), new Vector3f());

		vertexBuffer.put(v.x);
		vertexBuffer.put(v.y);
		vertexBuffer.put(v.z);

		setVertexBuffer(vertexBuffer);
		generateIndices();
	}

	void updateHandle(final Camera camera) {
		final IMesh mesh = node.getGeometry();
		final IPoint vertex = mesh.getVertex(vertexIndex);
		final Vector3f v = node.localToWorld(vertex.getPoint(), new Vector3f());

		if (pointControlHandle == null) {
			pointControlHandle = new PointControlHandle(this, toolAdapter,
					camera, v);
		} else {
			pointControlHandle.update(camera, v);
		}
	}

}
