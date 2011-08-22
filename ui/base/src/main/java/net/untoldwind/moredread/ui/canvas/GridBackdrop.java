package net.untoldwind.moredread.ui.canvas;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.enums.StandardPlane;
import net.untoldwind.moredread.model.scene.BoundingBox;

import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Line;
import com.jme.scene.Node;
import com.jme.util.geom.BufferUtils;

public class GridBackdrop extends Node {
	private static final long serialVersionUID = -2945873367704608296L;

	private transient BoundingBox lastBoundingBox;
	private transient Vector3f lastDirection;

	public GridBackdrop(final BoundingBox boundingBox, final Camera camera) {
		super("GridBackdrop");

		updateGrid(boundingBox, camera);
	}

	public void updateGrid(final BoundingBox boundingBox, final Camera camera) {
		final Vector3f direction = new Vector3f(camera.getDirection());
		final Vector3f center = boundingBox.getCenter();

		direction.x = FastMath.sign(direction.x);
		direction.y = FastMath.sign(direction.y);
		direction.z = FastMath.sign(direction.z);

		if (lastBoundingBox != null && lastBoundingBox.equals(boundingBox)
				&& lastDirection != null && lastDirection.equals(direction)) {
			return;
		}

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
		final float size = FastMath.pow(10.0f,
				(int) (FastMath.log(maxExtend, 10.0f) - 0.3f));

		detachAllChildren();

		if (direction.z <= 0.0) {
			final Node backXY = generateGrid(StandardPlane.XY, center.x
					- maxExtend, center.x + maxExtend, center.y - maxExtend,
					center.y + maxExtend, size, ColorRGBA.green.clone(),
					ColorRGBA.red.clone());
			backXY.setLocalTranslation(0, 0, center.z - maxExtend);
			attachChild(backXY);
		}
		if (direction.z >= 0.0) {
			final Node frontXY = generateGrid(StandardPlane.XY, center.x
					- maxExtend, center.x + maxExtend, center.y - maxExtend,
					center.y + maxExtend, size, ColorRGBA.green.clone(),
					ColorRGBA.red.clone());
			frontXY.setLocalTranslation(0, 0, center.z + maxExtend);
			attachChild(frontXY);
		}
		if (direction.x <= 0.0) {
			final Node backYZ = generateGrid(StandardPlane.YZ, center.y
					- maxExtend, center.y + maxExtend, center.z - maxExtend,
					center.z + maxExtend, size, ColorRGBA.blue.clone(),
					ColorRGBA.green.clone());
			backYZ.setLocalTranslation(center.x - maxExtend, 0, 0);
			attachChild(backYZ);
		}
		if (direction.x >= 0.0) {
			final Node frontYZ = generateGrid(StandardPlane.YZ, center.y
					- maxExtend, center.y + maxExtend, center.z - maxExtend,
					center.z + maxExtend, size, ColorRGBA.blue.clone(),
					ColorRGBA.green.clone());
			frontYZ.setLocalTranslation(center.x + maxExtend, 0, 0);
			attachChild(frontYZ);
		}
		if (direction.y <= 0.0) {
			final Node backXZ = generateGrid(StandardPlane.XZ, center.x
					- maxExtend, center.x + maxExtend, center.z - maxExtend,
					center.z + maxExtend, size, ColorRGBA.blue.clone(),
					ColorRGBA.red.clone());
			backXZ.setLocalTranslation(0, center.y - maxExtend, 0);
			attachChild(backXZ);
		}
		if (direction.y >= 0.0) {
			final Node frontXZ = generateGrid(StandardPlane.XZ, center.x
					- maxExtend, center.x + maxExtend, center.z - maxExtend,
					center.z + maxExtend, size, ColorRGBA.blue.clone(),
					ColorRGBA.red.clone());
			frontXZ.setLocalTranslation(0, center.y + maxExtend, 0);
			attachChild(frontXZ);
		}

		lastBoundingBox = new BoundingBox(boundingBox);
		lastDirection = new Vector3f(direction);
	}

	Node generateGrid(final StandardPlane plane, final float uMin,
			final float uMax, final float vMin, final float vMax,
			final float size, final ColorRGBA uNullColor,
			final ColorRGBA vNullColor) {
		final Node result = new Node();
		final List<Vector3f> points = new ArrayList<Vector3f>();
		final List<Vector3f> uNullPoints = new ArrayList<Vector3f>();
		final List<Vector3f> vNullPoints = new ArrayList<Vector3f>();

		// Outside bounding quad (don't forget that we use segmented lines here)
		points.add(plane.getTranslation(uMin, vMin));
		points.add(plane.getTranslation(uMin, vMax));
		points.add(plane.getTranslation(uMin, vMax));
		points.add(plane.getTranslation(uMax, vMax));
		points.add(plane.getTranslation(uMax, vMax));
		points.add(plane.getTranslation(uMax, vMin));
		points.add(plane.getTranslation(uMax, vMin));
		points.add(plane.getTranslation(uMin, vMin));

		// U grid lines
		float u = (int) (uMin / size) * size;

		if (u > 0) {
			u += size;
		}
		while (u <= uMax) {
			if (Math.abs(u / (uMax - uMin)) < 1e-6) {
				uNullPoints.add(plane.getTranslation(u, vMin));
				uNullPoints.add(plane.getTranslation(u, vMax));
			} else {
				points.add(plane.getTranslation(u, vMin));
				points.add(plane.getTranslation(u, vMax));
			}
			u += size;
		}

		// V grid lines
		float v = (int) (vMin / size) * size;

		if (v > 0) {
			v += size;
		}

		while (v <= vMax) {
			if (Math.abs(v / (vMax - vMin)) < 1e-6) {
				vNullPoints.add(plane.getTranslation(uMin, v));
				vNullPoints.add(plane.getTranslation(uMax, v));
			} else {
				points.add(plane.getTranslation(uMin, v));
				points.add(plane.getTranslation(uMax, v));
			}
			v += size;
		}

		final FloatBuffer vertexBuffer = BufferUtils.createVector3Buffer(points
				.size());
		for (final Vector3f point : points) {
			vertexBuffer.put(point.x);
			vertexBuffer.put(point.y);
			vertexBuffer.put(point.z);
		}

		final Line lines = new Line("", vertexBuffer, null, null, null);

		lines.setAntialiased(false);
		lines.setLineWidth(0.5f);
		lines.setDefaultColor(ColorRGBA.gray.clone());
		result.attachChild(lines);

		if (!uNullPoints.isEmpty()) {
			final FloatBuffer nullBuffer = BufferUtils
					.createVector3Buffer(uNullPoints.size());
			for (final Vector3f point : uNullPoints) {
				nullBuffer.put(point.x);
				nullBuffer.put(point.y);
				nullBuffer.put(point.z);
			}
			final Line nullLines = new Line("", nullBuffer, null, null, null);

			nullLines.setAntialiased(false);
			nullLines.setLineWidth(1.0f);
			nullLines.setDefaultColor(uNullColor);
			result.attachChild(nullLines);
		}
		if (!vNullPoints.isEmpty()) {
			final FloatBuffer nullBuffer = BufferUtils
					.createVector3Buffer(vNullPoints.size());
			for (final Vector3f point : vNullPoints) {
				nullBuffer.put(point.x);
				nullBuffer.put(point.y);
				nullBuffer.put(point.z);
			}
			final Line nullLines = new Line("", nullBuffer, null, null, null);

			nullLines.setAntialiased(false);
			nullLines.setLineWidth(1.0f);
			nullLines.setDefaultColor(vNullColor);
			result.attachChild(nullLines);
		}

		return result;
	}
}
