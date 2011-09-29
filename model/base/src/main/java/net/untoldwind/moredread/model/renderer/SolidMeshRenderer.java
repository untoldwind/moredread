package net.untoldwind.moredread.model.renderer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.PolyFace;
import net.untoldwind.moredread.model.mesh.PolyMesh;
import net.untoldwind.moredread.model.mesh.QuadFace;
import net.untoldwind.moredread.model.mesh.QuadMesh;
import net.untoldwind.moredread.model.mesh.TriangleFace;
import net.untoldwind.moredread.model.mesh.TriangleMesh;
import net.untoldwind.moredread.model.mesh.Vertex;
import net.untoldwind.moredread.model.op.ITriangulator;
import net.untoldwind.moredread.model.op.TriangulatorFactory;

import com.jme.renderer.ColorRGBA;
import com.jme.scene.Geometry;
import com.jme.scene.TriMesh;
import com.jme.util.geom.BufferUtils;

public class SolidMeshRenderer implements IMeshRendererAdapter {

	@Override
	public Geometry renderMesh(final IMesh mesh,
			final IColorProvider colorProvider) {
		switch (mesh.getMeshType()) {
		case TRIANGLE:
			return renderTriangleMesh((TriangleMesh) mesh, colorProvider);
		case QUAD:
			return renderQuadMesh((QuadMesh) mesh, colorProvider);
		case POLY:
			return renderPolyMesh((PolyMesh) mesh, colorProvider);
		}
		throw new RuntimeException("Unknown mesh type: " + mesh.getMeshType());
	}

	private Geometry renderTriangleMesh(final TriangleMesh mesh,
			final IColorProvider colorProvider) {
		final List<Vector3> points = new ArrayList<Vector3>();
		final List<Vector3> normals = new ArrayList<Vector3>();
		List<ColorRGBA> colors = null;

		if (colorProvider != null) {
			colors = new ArrayList<ColorRGBA>();
		}

		for (final TriangleFace face : mesh.getFaces()) {
			if (colors == null || colorProvider.isFaceVisible(face.getIndex())) {
				final Vector3 normal = face.getMeanNormal();
				for (final Vertex vertex : face.getVertices()) {
					final Vector3 point = vertex.getPoint();

					points.add(point);
					normals.add(normal);

					if (colors != null) {
						final ColorRGBA color = colorProvider.getFaceColor(face
								.getIndex());

						colors.add(color);
					}
				}
			}
		}

		if (points.isEmpty()) {
			return null;
		}

		final FloatBuffer vertexBuffer = BufferUtils.createVector3Buffer(points
				.size());
		final FloatBuffer normalBuffer = BufferUtils
				.createVector3Buffer(normals.size());
		final IntBuffer indexBuffer = BufferUtils
				.createIntBuffer(points.size());
		FloatBuffer colorBuffer = null;

		if (colors != null) {
			colorBuffer = BufferUtils.createColorBuffer(colors.size());
		}

		for (int i = 0; i < points.size(); i++) {
			final Vector3 point = points.get(i);
			final Vector3 normal = normals.get(i);
			vertexBuffer.put(point.x);
			vertexBuffer.put(point.y);
			vertexBuffer.put(point.z);
			normalBuffer.put(normal.x);
			normalBuffer.put(normal.y);
			normalBuffer.put(normal.z);
			if (colors != null) {
				final ColorRGBA color = colors.get(i);
				colorBuffer.put(color.r);
				colorBuffer.put(color.g);
				colorBuffer.put(color.b);
				colorBuffer.put(color.a);
			}
			indexBuffer.put(i);
		}

		final TriMesh triMesh = new TriMesh(null, vertexBuffer, normalBuffer,
				colorBuffer, null, indexBuffer);

		return triMesh;
	}

	private Geometry renderQuadMesh(final QuadMesh mesh,
			final IColorProvider colorProvider) {
		final List<Vector3> points = new ArrayList<Vector3>();
		final List<Vector3> normals = new ArrayList<Vector3>();
		List<ColorRGBA> colors = null;

		if (colorProvider != null) {
			colors = new ArrayList<ColorRGBA>();
		}

		for (final QuadFace face : mesh.getFaces()) {
			if (colors == null || colorProvider.isFaceVisible(face.getIndex())) {
				final Vector3 normal = face.getMeanNormal();
				for (final Vertex vertex : face.getVertices()) {
					final Vector3 point = vertex.getPoint();

					points.add(point);
					normals.add(normal);

					if (colors != null) {
						final ColorRGBA color = colorProvider.getFaceColor(face
								.getIndex());

						colors.add(color);
					}
				}
			}
		}

		if (points.isEmpty()) {
			return null;
		}

		final FloatBuffer vertexBuffer = BufferUtils.createVector3Buffer(points
				.size());
		final FloatBuffer normalBuffer = BufferUtils
				.createVector3Buffer(normals.size());
		final IntBuffer indexBuffer = BufferUtils
				.createIntBuffer(points.size());
		FloatBuffer colorBuffer = null;

		if (colors != null) {
			colorBuffer = BufferUtils.createColorBuffer(colors.size());
		}

		for (int i = 0; i < points.size(); i++) {
			final Vector3 point = points.get(i);
			final Vector3 normal = normals.get(i);
			vertexBuffer.put(point.x);
			vertexBuffer.put(point.y);
			vertexBuffer.put(point.z);
			normalBuffer.put(normal.x);
			normalBuffer.put(normal.y);
			normalBuffer.put(normal.z);
			if (colors != null) {
				final ColorRGBA color = colors.get(i);
				colorBuffer.put(color.r);
				colorBuffer.put(color.g);
				colorBuffer.put(color.b);
				colorBuffer.put(color.a);
			}
			indexBuffer.put(i);
		}

		final com.jme.scene.QuadMesh quadMesh = new com.jme.scene.QuadMesh(
				null, vertexBuffer, normalBuffer, colorBuffer, null,
				indexBuffer);

		return quadMesh;
	}

	private Geometry renderPolyMesh(final PolyMesh mesh,
			final IColorProvider colorProvider) {
		final List<Vector3> points = new ArrayList<Vector3>();
		final List<Vector3> normals = new ArrayList<Vector3>();
		List<ColorRGBA> colors = null;

		if (colorProvider != null) {
			colors = new ArrayList<ColorRGBA>();
		}

		final ITriangulator triangulator = TriangulatorFactory.createDefault();

		for (final PolyFace face : mesh.getFaces()) {
			if (colors == null || colorProvider.isFaceVisible(face.getIndex())) {
				final Vector3 normal = face.getMeanNormal();

				final List<Vertex> vertices = face.getVertices();
				final int[] indices = triangulator.triangulate(face);

				// TODO: This can (should) be slightly optimized
				for (int i = 0; i < indices.length; i++) {
					points.add(vertices.get(indices[i]).getPoint());
					normals.add(normal);

					if (colors != null) {
						colors.add(colorProvider.getFaceColor(face.getIndex()));
					}
				}
			}
		}

		if (points.isEmpty()) {
			return null;
		}

		final FloatBuffer vertexBuffer = BufferUtils.createVector3Buffer(points
				.size());
		final FloatBuffer normalBuffer = BufferUtils
				.createVector3Buffer(normals.size());
		final IntBuffer indexBuffer = BufferUtils
				.createIntBuffer(points.size());
		FloatBuffer colorBuffer = null;

		if (colors != null) {
			colorBuffer = BufferUtils.createColorBuffer(colors.size());
		}

		for (int i = 0; i < points.size(); i++) {
			final Vector3 point = points.get(i);
			final Vector3 normal = normals.get(i);
			vertexBuffer.put(point.x);
			vertexBuffer.put(point.y);
			vertexBuffer.put(point.z);
			normalBuffer.put(normal.x);
			normalBuffer.put(normal.y);
			normalBuffer.put(normal.z);
			if (colors != null) {
				final ColorRGBA color = colors.get(i);
				colorBuffer.put(color.r);
				colorBuffer.put(color.g);
				colorBuffer.put(color.b);
				colorBuffer.put(color.a);
			}
			indexBuffer.put(i);
		}

		final TriMesh triMesh = new TriMesh(null, vertexBuffer, normalBuffer,
				colorBuffer, null, indexBuffer);

		return triMesh;
	}
}
