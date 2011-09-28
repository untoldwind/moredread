package net.untoldwind.moredread.ui.controls.impl;

import java.nio.FloatBuffer;
import java.util.List;

import net.untoldwind.moredread.model.mesh.EdgeId;
import net.untoldwind.moredread.model.mesh.IEdge;
import net.untoldwind.moredread.model.mesh.IEdgeGeometry;
import net.untoldwind.moredread.model.scene.IEdgeGeometryNode;
import net.untoldwind.moredread.ui.controls.IControlHandle;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.controls.IViewport;
import net.untoldwind.moredread.ui.tools.spi.IToolAdapter;

import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Line;
import com.jme.scene.Spatial;
import com.jme.scene.state.BlendState;
import com.jme.system.DisplaySystem;
import com.jme.util.geom.BufferUtils;

public class EdgeSelectionModelControl extends Line implements IModelControl {
	private static final long serialVersionUID = -3620958811020769978L;

	private final IToolAdapter toolAdapter;

	private final IEdgeGeometryNode<?, ?> node;
	private final EdgeId edgeIndex;

	private transient LineControlHandle lineControlHandle;

	public EdgeSelectionModelControl(final IEdgeGeometryNode<?, ?> node,
			final EdgeId edgeIndex, final IToolAdapter toolAdapter) {
		this.toolAdapter = toolAdapter;
		this.node = node;
		this.edgeIndex = edgeIndex;

		initialize();
	}

	private void initialize() {
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
		setLineWidth(3f);
		setAntialiased(true);
	}

	@Override
	public void viewportChanged(final IViewport viewport) {
		updateHandle(viewport.getCamera());
	}

	@Override
	public void collectControlHandles(final List<IControlHandle> handles,
			final IViewport viewport) {
		updateHandle(viewport.getCamera());

		handles.add(lineControlHandle);
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

	@Override
	public IToolAdapter getToolAdapter() {
		return toolAdapter;
	}

	void updateGeometry() {
		IEdge edge = null;

		if (node instanceof IEdgeGeometryNode<?, ?>) {
			final IEdgeGeometry<?> geometry = node.getGeometry();
			edge = geometry.getEdge(edgeIndex);
		}

		if (edge == null) {
			return;
		}

		final Vector3f v1 = node.localToWorld(edge.getVertex1().getPoint(),
				new Vector3f());
		final Vector3f v2 = node.localToWorld(edge.getVertex2().getPoint(),
				new Vector3f());
		final FloatBuffer vertexBuffer = BufferUtils.createVector3Buffer(2);

		vertexBuffer.put(v1.x);
		vertexBuffer.put(v1.y);
		vertexBuffer.put(v1.z);
		vertexBuffer.put(v2.x);
		vertexBuffer.put(v2.y);
		vertexBuffer.put(v2.z);

		setVertexBuffer(vertexBuffer);
		generateIndices();
	}

	void updateHandle(final Camera camera) {
		IEdge edge = null;

		if (node instanceof IEdgeGeometryNode<?, ?>) {
			final IEdgeGeometry<?> geometry = node.getGeometry();
			edge = geometry.getEdge(edgeIndex);
		}

		if (edge == null) {
			return;
		}

		final Vector3f v1 = node.localToWorld(edge.getVertex1().getPoint(),
				new Vector3f());
		final Vector3f v2 = node.localToWorld(edge.getVertex2().getPoint(),
				new Vector3f());

		if (lineControlHandle == null) {
			lineControlHandle = new LineControlHandle(this, camera, v1, v2);
		} else {
			lineControlHandle.update(camera, v1, v2);
		}
	}

}
