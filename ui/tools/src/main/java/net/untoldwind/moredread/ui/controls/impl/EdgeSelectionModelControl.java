package net.untoldwind.moredread.ui.controls.impl;

import java.nio.FloatBuffer;
import java.util.List;

import net.untoldwind.moredread.model.mesh.EdgeId;
import net.untoldwind.moredread.model.mesh.IEdge;
import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.IPolygon;
import net.untoldwind.moredread.model.scene.IMeshNode;
import net.untoldwind.moredread.model.scene.IPolygonNode;
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

	private final IMeshNode meshNode;
	private final IPolygonNode polygonNode;
	private final EdgeId edgeIndex;

	private transient LineControlHandle lineControlHandle;

	public EdgeSelectionModelControl(final IMeshNode node,
			final EdgeId edgeIndex, final IToolAdapter toolAdapter) {
		this.toolAdapter = toolAdapter;
		this.meshNode = node;
		this.polygonNode = null;
		this.edgeIndex = edgeIndex;

		initialize();
	}

	public EdgeSelectionModelControl(final IPolygonNode node,
			final EdgeId edgeIndex, final IToolAdapter toolAdapter) {
		this.toolAdapter = toolAdapter;
		this.meshNode = null;
		this.polygonNode = node;
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
		Vector3f v1, v2;
		if (meshNode != null) {
			final IMesh mesh = meshNode.getGeometry();
			final IEdge edge = mesh.getEdge(edgeIndex);
			v1 = meshNode.localToWorld(edge.getVertex1().getPoint(),
					new Vector3f());
			v2 = meshNode.localToWorld(edge.getVertex2().getPoint(),
					new Vector3f());
		} else {
			final IPolygon polygon = polygonNode.getGeometry();
			final IEdge edge = polygon.getEdge(edgeIndex);
			v1 = polygonNode.localToWorld(edge.getVertex1().getPoint(),
					new Vector3f());
			v2 = polygonNode.localToWorld(edge.getVertex2().getPoint(),
					new Vector3f());

		}
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
		Vector3f v1, v2;
		if (meshNode != null) {
			final IMesh mesh = meshNode.getGeometry();
			final IEdge edge = mesh.getEdge(edgeIndex);
			v1 = meshNode.localToWorld(edge.getVertex1().getPoint(),
					new Vector3f());
			v2 = meshNode.localToWorld(edge.getVertex2().getPoint(),
					new Vector3f());
		} else {
			final IPolygon polygon = polygonNode.getGeometry();
			final IEdge edge = polygon.getEdge(edgeIndex);
			v1 = polygonNode.localToWorld(edge.getVertex1().getPoint(),
					new Vector3f());
			v2 = polygonNode.localToWorld(edge.getVertex2().getPoint(),
					new Vector3f());

		}

		if (lineControlHandle == null) {
			lineControlHandle = new LineControlHandle(this, camera, v1, v2);
		} else {
			lineControlHandle.update(camera, v1, v2);
		}
	}

}
