package net.untoldwind.moredread.model.renderer;

import java.nio.FloatBuffer;

import net.untoldwind.moredread.model.mesh.IPolygon;
import net.untoldwind.moredread.model.mesh.IVertex;

import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Geometry;
import com.jme.scene.Point;
import com.jme.util.geom.BufferUtils;

public class VertexPolygonRendererAdapter implements IPolygonRendererAdapter {

	@Override
	public Geometry renderPolygon(final IPolygon polygon,
			final IColorProvider colorProvider) {
		final FloatBuffer vertexBuffer = BufferUtils
				.createVector3Buffer(polygon.getVertices().size());
		FloatBuffer colorBuffer = null;

		if (colorProvider != null) {
			colorBuffer = BufferUtils.createColorBuffer(polygon.getVertices()
					.size());
		}

		for (final IVertex vertex : polygon.getVertices()) {
			final Vector3f p = vertex.getPoint();

			vertexBuffer.put(p.x);
			vertexBuffer.put(p.y);
			vertexBuffer.put(p.z);

			if (colorBuffer != null) {
				final ColorRGBA color = colorProvider.getVertexColor(vertex
						.getIndex());

				colorBuffer.put(color.r);
				colorBuffer.put(color.b);
				colorBuffer.put(color.g);
				colorBuffer.put(color.a);
			}
		}

		final Point point = new Point("", vertexBuffer, null, colorBuffer, null);
		point.setPointSize(6f);
		point.setAntialiased(false);

		return point;
	}

}
