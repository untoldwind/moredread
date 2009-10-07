package net.untoldwind.moredread.model.renderer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.PolyFace;
import net.untoldwind.moredread.model.mesh.PolyMesh;
import net.untoldwind.moredread.model.mesh.QuadFace;
import net.untoldwind.moredread.model.mesh.QuadMesh;
import net.untoldwind.moredread.model.mesh.TriangleFace;
import net.untoldwind.moredread.model.mesh.TriangleMesh;
import net.untoldwind.moredread.model.mesh.Vertex;
import net.untoldwind.moredread.model.triangulator.ITriangulator;
import net.untoldwind.moredread.model.triangulator.TriangulatorFactory;

import com.jme.math.Vector3f;
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
		final FloatBuffer vertexBuffer = BufferUtils.createVector3Buffer(mesh
				.getFaces().size() * 3);
		final FloatBuffer normalBuffer = BufferUtils.createVector3Buffer(mesh
				.getFaces().size() * 3);
		final IntBuffer indexBuffer = BufferUtils.createIntBuffer(mesh
				.getFaces().size() * 3);
		FloatBuffer colorBuffer = null;

		if (colorProvider != null) {
			colorBuffer = BufferUtils
					.createColorBuffer(mesh.getFaces().size() * 3);
		}

		int indexCount = 0;
		for (final TriangleFace face : mesh.getFaces()) {
			final Vector3f normal = face.getMeanNormal();
			for (final Vertex vertex : face.getVertices()) {
				final Vector3f point = vertex.getPoint();
				vertexBuffer.put(point.x);
				vertexBuffer.put(point.y);
				vertexBuffer.put(point.z);
				normalBuffer.put(normal.x);
				normalBuffer.put(normal.y);
				normalBuffer.put(normal.z);

				if (colorBuffer != null) {
					final ColorRGBA color = colorProvider.getFaceColor(face
							.getIndex());
					colorBuffer.put(color.r);
					colorBuffer.put(color.g);
					colorBuffer.put(color.b);
					colorBuffer.put(color.a);
				}
				indexBuffer.put(indexCount++);
			}

		}

		final TriMesh triMesh = new TriMesh(null, vertexBuffer, normalBuffer,
				colorBuffer, null, indexBuffer);

		return triMesh;
	}

	private Geometry renderQuadMesh(final QuadMesh mesh,
			final IColorProvider colorProvider) {
		final FloatBuffer vertexBuffer = BufferUtils.createVector3Buffer(mesh
				.getFaces().size() * 4);
		final FloatBuffer normalBuffer = BufferUtils.createVector3Buffer(mesh
				.getFaces().size() * 4);
		final IntBuffer indexBuffer = BufferUtils.createIntBuffer(mesh
				.getFaces().size() * 4);
		FloatBuffer colorBuffer = null;

		if (colorProvider != null) {
			colorBuffer = BufferUtils
					.createColorBuffer(mesh.getFaces().size() * 4);
		}

		int indexCount = 0;
		for (final QuadFace face : mesh.getFaces()) {
			final Vector3f normal = face.getMeanNormal();
			for (final Vertex vertex : face.getVertices()) {
				final Vector3f point = vertex.getPoint();
				vertexBuffer.put(point.x);
				vertexBuffer.put(point.y);
				vertexBuffer.put(point.z);
				normalBuffer.put(normal.x);
				normalBuffer.put(normal.y);
				normalBuffer.put(normal.z);

				if (colorBuffer != null) {
					final ColorRGBA color = colorProvider.getFaceColor(face
							.getIndex());
					colorBuffer.put(color.r);
					colorBuffer.put(color.g);
					colorBuffer.put(color.b);
					colorBuffer.put(color.a);
				}
				indexBuffer.put(indexCount++);
			}

		}

		final com.jme.scene.QuadMesh quadMesh = new com.jme.scene.QuadMesh(
				null, vertexBuffer, normalBuffer, colorBuffer, null,
				indexBuffer);

		return quadMesh;
	}

	private Geometry renderPolyMesh(final PolyMesh mesh,
			final IColorProvider colorProvider) {
		final List<Vector3f> points = new ArrayList<Vector3f>();
		final List<Vector3f> normals = new ArrayList<Vector3f>();
		List<ColorRGBA> colors = null;

		if (colorProvider != null) {
			colors = new ArrayList<ColorRGBA>();
		}

		final ITriangulator triangulator = TriangulatorFactory
				.createTriangulator(TriangulatorFactory.Implementation.FIST);

		for (final PolyFace face : mesh.getFaces()) {
			final Vector3f normal = face.getMeanNormal();

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
			final Vector3f point = points.get(i);
			final Vector3f normal = normals.get(i);
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
