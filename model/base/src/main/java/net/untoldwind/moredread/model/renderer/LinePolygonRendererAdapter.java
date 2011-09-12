package net.untoldwind.moredread.model.renderer;

import java.nio.FloatBuffer;

import net.untoldwind.moredread.model.mesh.IEdge;
import net.untoldwind.moredread.model.mesh.IPolygon;

import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Geometry;
import com.jme.scene.Line;
import com.jme.util.geom.BufferUtils;

public class LinePolygonRendererAdapter implements IPolygonRendererAdapter {
	private final float lineWidth;
	private final boolean antialiased;

	public LinePolygonRendererAdapter(final float lineWidth,
			final boolean antialiased) {
		this.lineWidth = lineWidth;
		this.antialiased = antialiased;
	}

	@Override
	public Geometry renderPolygon(final IPolygon polygon,
			final IColorProvider colorProvider) {
		final FloatBuffer vertexBuffer = BufferUtils
				.createVector3Buffer(polygon.getVertices().size() * 2);
		FloatBuffer colorBuffer = null;

		if (colorProvider != null) {
			colorBuffer = BufferUtils.createColorBuffer(polygon.getVertices()
					.size() * 2);
		}

		for (final IEdge edge : polygon.getEdges()) {
			final Vector3f p1 = edge.getVertex1().getPoint();
			final Vector3f p2 = edge.getVertex2().getPoint();

			vertexBuffer.put(p1.x);
			vertexBuffer.put(p1.y);
			vertexBuffer.put(p1.z);
			vertexBuffer.put(p2.x);
			vertexBuffer.put(p2.y);
			vertexBuffer.put(p2.z);

			if (colorBuffer != null) {
				final ColorRGBA color = colorProvider.getEdgeColor(edge
						.getIndex());
				colorBuffer.put(color.r);
				colorBuffer.put(color.b);
				colorBuffer.put(color.g);
				colorBuffer.put(color.a);
				colorBuffer.put(color.r);
				colorBuffer.put(color.b);
				colorBuffer.put(color.g);
				colorBuffer.put(color.a);
			}
		}

		final Line lines = new Line("", vertexBuffer, null, colorBuffer, null);

		lines.setLineWidth(lineWidth);
		lines.setAntialiased(antialiased);

		return lines;
	}

}
