package net.untoldwind.moredread.model.renderer;

import java.nio.FloatBuffer;

import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.mesh.IEdge;
import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.IPolygon;

import com.jme.renderer.ColorRGBA;
import com.jme.scene.Geometry;
import com.jme.scene.Line;
import com.jme.util.geom.BufferUtils;

public class WireframeMeshRenderer implements IMeshRendererAdapter {
	private final boolean drawNormals;
	private final float lineWidth;
	private final boolean antialiased;

	public WireframeMeshRenderer(final boolean drawNormals,
			final float lineWidth, final boolean antialiased) {
		this.drawNormals = drawNormals;
		this.lineWidth = lineWidth;
		this.antialiased = antialiased;
	}

	@Override
	public Geometry renderMesh(final IMesh mesh,
			final IColorProvider colorProvider) {
		final FloatBuffer vertexBuffer = BufferUtils.createVector3Buffer(mesh
				.getEdges().size()
				* 2
				+ (drawNormals ? mesh.getFaces().size() * 2 : 0));
		FloatBuffer colorBuffer = null;

		if (colorProvider != null) {
			colorBuffer = BufferUtils.createColorBuffer(mesh.getEdges().size()
					* 2 + (drawNormals ? mesh.getFaces().size() * 2 : 0));
		}

		for (final IEdge edge : mesh.getEdges()) {
			final Vector3 p1 = edge.getVertex1().getPoint();
			final Vector3 p2 = edge.getVertex2().getPoint();

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
		if (drawNormals) {
			for (final IPolygon face : mesh.getFaces()) {
				final Vector3 p1 = face.getCenter();
				final Vector3 p2 = p1.add(face.getMeanNormal());

				vertexBuffer.put(p1.x);
				vertexBuffer.put(p1.y);
				vertexBuffer.put(p1.z);
				vertexBuffer.put(p2.x);
				vertexBuffer.put(p2.y);
				vertexBuffer.put(p2.z);
				if (colorBuffer != null) {
					final ColorRGBA color = ColorRGBA.white.clone();
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
		}

		final Line lines = new Line("", vertexBuffer, null, colorBuffer, null);

		lines.setLineWidth(lineWidth);
		lines.setAntialiased(antialiased);

		return lines;
	}

}
