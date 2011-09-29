package net.untoldwind.moredread.ui.controls.impl;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.enums.CartesianPlane;
import net.untoldwind.moredread.model.math.Camera;
import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.ui.controls.IControlHandle;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.controls.IViewport;
import net.untoldwind.moredread.ui.tools.spi.IToolAdapter;

import com.jme.math.FastMath;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Line;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.util.geom.BufferUtils;

public class RotateCircleModelControl extends Node implements IModelControl {
	private static final long serialVersionUID = 1L;

	private static final int SECTIONS = 72;

	private final ColorRGBA color;
	private final Line line;
	private final IToolAdapter toolAdapter;
	private final List<Vector3> circlePoints;

	private transient PolyLineControlHandle polyLineControlHandle;

	public RotateCircleModelControl(final CartesianPlane plane,
			final IToolAdapter toolAdapter) {
		this.toolAdapter = toolAdapter;

		circlePoints = new ArrayList<Vector3>(SECTIONS + 1);
		for (int i = 0; i < SECTIONS; i++) {
			final float theta = i * FastMath.TWO_PI / SECTIONS;
			circlePoints.add(plane.project(Vector3.ZERO, Vector3.ZERO,
					0.7f * FastMath.sin(theta), 0.7f * FastMath.cos(theta)));
		}
		circlePoints.add(circlePoints.get(0));

		switch (plane) {
		case XY:
			color = ColorRGBA.blue.clone();
			break;
		case XZ:
			color = ColorRGBA.green.clone();
			break;
		case YZ:
			color = ColorRGBA.red.clone();
			break;
		default:
			color = ColorRGBA.black.clone();
			break;
		}

		final FloatBuffer vertexBuffer = BufferUtils
				.createVector3Buffer(SECTIONS * 2);
		for (int i = 0; i < SECTIONS; i++) {
			final Vector3 v1 = circlePoints.get(i);
			final Vector3 v2 = circlePoints.get(i + 1);

			vertexBuffer.put(v1.x);
			vertexBuffer.put(v1.y);
			vertexBuffer.put(v1.z);
			vertexBuffer.put(v2.x);
			vertexBuffer.put(v2.y);
			vertexBuffer.put(v2.z);
		}

		line = new Line(plane + "Rotation", vertexBuffer, null, null, null);
		line.setDefaultColor(color);
		line.setLineWidth(2f);
		this.attachChild(line);
	}

	@Override
	public Spatial getSpatial() {
		return this;
	}

	@Override
	public void collectControlHandles(final List<IControlHandle> handles,
			final IViewport viewport) {
		updateHandle(viewport.getCamera());

		handles.add(polyLineControlHandle);
	}

	@Override
	public void viewportChanged(final IViewport viewport) {
		updateHandle(viewport.getCamera());
	}

	@Override
	public void setActive(final boolean active) {
		if (active) {
			line.setLineWidth(4f);
			line.setDefaultColor(ColorRGBA.white.clone());
		} else {
			line.setLineWidth(2f);
			line.setDefaultColor(color);
		}
	}

	@Override
	public void updatePositions() {
	}

	@Override
	public IToolAdapter getToolAdapter() {
		return toolAdapter;
	}

	void updateHandle(final Camera camera) {
		final List<Vector3> worldPoints = new ArrayList<Vector3>();
		for (final Vector3 point : circlePoints) {
			worldPoints.add(new Vector3(localToWorld(point.toJME(), null)));
		}
		if (polyLineControlHandle == null) {
			polyLineControlHandle = new PolyLineControlHandle(this, camera,
					worldPoints, IControlHandle.MAX_SALIENCE);
		} else {
			polyLineControlHandle.update(camera, worldPoints);
		}
	}
}
