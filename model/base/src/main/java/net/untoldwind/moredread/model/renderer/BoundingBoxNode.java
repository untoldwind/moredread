package net.untoldwind.moredread.model.renderer;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.scene.BoundingBox;

import com.jme.renderer.ColorRGBA;
import com.jme.scene.Line;
import com.jme.scene.Node;
import com.jme.util.geom.BufferUtils;

/**
 * A visual display of the bounding box of a scene element.
 */
public class BoundingBoxNode extends Node {
	private static final long serialVersionUID = 6974214094762466349L;

	public BoundingBoxNode(final BoundingBox boundingBox) {
		super("BoundingBox");

		updateBoundingBox(boundingBox);
	}

	private void updateBoundingBox(final BoundingBox boundingBox) {
		final Vector3 min = boundingBox.getMinPosition();
		final Vector3 max = boundingBox.getMaxPosition();

		final List<Vector3> strongPoints = new ArrayList<Vector3>();
		final List<Vector3> weakPoints = new ArrayList<Vector3>();

		// Also handle 2d- or even 1d-elements
		if (max.z > min.z) {
			strongPoints.add(new Vector3(min.x, min.y, min.z));
			strongPoints.add(new Vector3(min.x, min.y, min.z + 0.25f
					* (max.z - min.z)));
			strongPoints.add(new Vector3(min.x, max.y, min.z));
			strongPoints.add(new Vector3(min.x, max.y, min.z + 0.25f
					* (max.z - min.z)));
			strongPoints.add(new Vector3(max.x, max.y, min.z));
			strongPoints.add(new Vector3(max.x, max.y, min.z + 0.25f
					* (max.z - min.z)));
			strongPoints.add(new Vector3(max.x, min.y, min.z));
			strongPoints.add(new Vector3(max.x, min.y, min.z + 0.25f
					* (max.z - min.z)));

			strongPoints.add(new Vector3(min.x, min.y, max.z));
			strongPoints.add(new Vector3(min.x, min.y, max.z - 0.25f
					* (max.z - min.z)));
			strongPoints.add(new Vector3(min.x, max.y, max.z));
			strongPoints.add(new Vector3(min.x, max.y, max.z - 0.25f
					* (max.z - min.z)));
			strongPoints.add(new Vector3(max.x, max.y, max.z));
			strongPoints.add(new Vector3(max.x, max.y, max.z - 0.25f
					* (max.z - min.z)));
			strongPoints.add(new Vector3(max.x, min.y, max.z));
			strongPoints.add(new Vector3(max.x, min.y, max.z - 0.25f
					* (max.z - min.z)));

			weakPoints.add(new Vector3(min.x, min.y, min.z + 0.25f
					* (max.z - min.z)));
			weakPoints.add(new Vector3(min.x, min.y, max.z - 0.25f
					* (max.z - min.z)));
			weakPoints.add(new Vector3(min.x, max.y, min.z + 0.25f
					* (max.z - min.z)));
			weakPoints.add(new Vector3(min.x, max.y, max.z - 0.25f
					* (max.z - min.z)));
			weakPoints.add(new Vector3(max.x, max.y, min.z + 0.25f
					* (max.z - min.z)));
			weakPoints.add(new Vector3(max.x, max.y, max.z - 0.25f
					* (max.z - min.z)));
			weakPoints.add(new Vector3(max.x, min.y, min.z + 0.25f
					* (max.z - min.z)));
			weakPoints.add(new Vector3(max.x, min.y, max.z - 0.25f
					* (max.z - min.z)));
		}

		// Also handle 2d- or even 1d-elements
		if (max.y > min.y) {
			strongPoints.add(new Vector3(min.x, min.y, min.z));
			strongPoints.add(new Vector3(min.x,
					min.y + 0.25f * (max.y - min.y), min.z));
			strongPoints.add(new Vector3(min.x, min.y, max.z));
			strongPoints.add(new Vector3(min.x,
					min.y + 0.25f * (max.y - min.y), max.z));
			strongPoints.add(new Vector3(max.x, min.y, max.z));
			strongPoints.add(new Vector3(max.x,
					min.y + 0.25f * (max.y - min.y), max.z));
			strongPoints.add(new Vector3(max.x, min.y, min.z));
			strongPoints.add(new Vector3(max.x,
					min.y + 0.25f * (max.y - min.y), min.z));

			strongPoints.add(new Vector3(min.x, max.y, min.z));
			strongPoints.add(new Vector3(min.x,
					max.y - 0.25f * (max.y - min.y), min.z));
			strongPoints.add(new Vector3(min.x, max.y, max.z));
			strongPoints.add(new Vector3(min.x,
					max.y - 0.25f * (max.y - min.y), max.z));
			strongPoints.add(new Vector3(max.x, max.y, max.z));
			strongPoints.add(new Vector3(max.x,
					max.y - 0.25f * (max.y - min.y), max.z));
			strongPoints.add(new Vector3(max.x, max.y, min.z));
			strongPoints.add(new Vector3(max.x,
					max.y - 0.25f * (max.y - min.y), min.z));

			weakPoints.add(new Vector3(min.x, min.y + 0.25f * (max.y - min.y),
					min.z));
			weakPoints.add(new Vector3(min.x, max.y - 0.25f * (max.y - min.y),
					min.z));
			weakPoints.add(new Vector3(min.x, min.y + 0.25f * (max.y - min.y),
					max.z));
			weakPoints.add(new Vector3(min.x, max.y - 0.25f * (max.y - min.y),
					max.z));
			weakPoints.add(new Vector3(max.x, min.y + 0.25f * (max.y - min.y),
					max.z));
			weakPoints.add(new Vector3(max.x, max.y - 0.25f * (max.y - min.y),
					max.z));
			weakPoints.add(new Vector3(max.x, min.y + 0.25f * (max.y - min.y),
					min.z));
			weakPoints.add(new Vector3(max.x, max.y - 0.25f * (max.y - min.y),
					min.z));
		}

		// Also handle 2d- or even 1d-elements
		if (max.x > min.x) {
			strongPoints.add(new Vector3(min.x, min.y, min.z));
			strongPoints.add(new Vector3(min.x + 0.25f * (max.x - min.x),
					min.y, min.z));
			strongPoints.add(new Vector3(min.x, min.y, max.z));
			strongPoints.add(new Vector3(min.x + 0.25f * (max.x - min.x),
					min.y, max.z));
			strongPoints.add(new Vector3(min.x, max.y, max.z));
			strongPoints.add(new Vector3(min.x + 0.25f * (max.x - min.x),
					max.y, max.z));
			strongPoints.add(new Vector3(min.x, max.y, min.z));
			strongPoints.add(new Vector3(min.x + 0.25f * (max.x - min.x),
					max.y, min.z));

			strongPoints.add(new Vector3(max.x, min.y, min.z));
			strongPoints.add(new Vector3(max.x - 0.25f * (max.x - min.x),
					min.y, min.z));
			strongPoints.add(new Vector3(max.x, min.y, max.z));
			strongPoints.add(new Vector3(max.x - 0.25f * (max.x - min.x),
					min.y, max.z));
			strongPoints.add(new Vector3(max.x, max.y, max.z));
			strongPoints.add(new Vector3(max.x - 0.25f * (max.x - min.x),
					max.y, max.z));
			strongPoints.add(new Vector3(max.x, max.y, min.z));
			strongPoints.add(new Vector3(max.x - 0.25f * (max.x - min.x),
					max.y, min.z));

			weakPoints.add(new Vector3(min.x + 0.25f * (max.x - min.x), min.y,
					min.z));
			weakPoints.add(new Vector3(max.x - 0.25f * (max.x - min.x), min.y,
					min.z));
			weakPoints.add(new Vector3(min.x + 0.25f * (max.x - min.x), min.y,
					max.z));
			weakPoints.add(new Vector3(max.x - 0.25f * (max.x - min.x), min.y,
					max.z));
			weakPoints.add(new Vector3(min.x + 0.25f * (max.x - min.x), max.y,
					max.z));
			weakPoints.add(new Vector3(max.x - 0.25f * (max.x - min.x), max.y,
					max.z));
			weakPoints.add(new Vector3(min.x + 0.25f * (max.x - min.x), max.y,
					min.z));
			weakPoints.add(new Vector3(max.x - 0.25f * (max.x - min.x), max.y,
					min.z));
		}

		final FloatBuffer strongBuffer = BufferUtils
				.createVector3Buffer(strongPoints.size());
		for (final Vector3 point : strongPoints) {
			strongBuffer.put(point.x);
			strongBuffer.put(point.y);
			strongBuffer.put(point.z);
		}

		final Line strongLines = new Line("", strongBuffer, null, null, null);

		strongLines.setAntialiased(true);
		strongLines.setLineWidth(2f);
		strongLines.setDefaultColor(ColorRGBA.black.clone());

		final FloatBuffer weakBuffer = BufferUtils
				.createVector3Buffer(weakPoints.size());
		for (final Vector3 point : weakPoints) {
			weakBuffer.put(point.x);
			weakBuffer.put(point.y);
			weakBuffer.put(point.z);
		}

		final Line weakLines = new Line("", weakBuffer, null, null, null);

		weakLines.setAntialiased(false);
		weakLines.setLineWidth(0.5f);
		weakLines.setDefaultColor(ColorRGBA.gray.clone());

		detachAllChildren();

		attachChild(strongLines);
		attachChild(weakLines);
	}
}
