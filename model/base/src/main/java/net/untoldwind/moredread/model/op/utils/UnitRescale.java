package net.untoldwind.moredread.model.op.utils;

import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.IVertex;
import net.untoldwind.moredread.model.mesh.Vertex;
import net.untoldwind.moredread.model.mesh.VertexGeometry;
import net.untoldwind.moredread.model.scene.BoundingBox;

public class UnitRescale {
	BoundingBox originalBoundingBox;
	Vector3 scale;
	Vector3 translation;

	public UnitRescale(final IMesh... meshes) {
		originalBoundingBox = new BoundingBox();
		for (final IMesh mesh : meshes) {
			for (final IVertex vertex : mesh.getVertices()) {
				originalBoundingBox.add(vertex.getPoint());
			}
		}

		translation = originalBoundingBox.getCenter();
		scale = new Vector3(originalBoundingBox.getXExtent(),
				originalBoundingBox.getYExtent(),
				originalBoundingBox.getZExtent());
	}

	public void rescaleInput(final VertexGeometry<?> input) {
		for (final Vertex vertex : input.getVertices()) {
			vertex.setPoint(vertex.getPoint().subtract(translation)
					.divideLocal(scale));
		}
	}

	public void rescaleOutput(final VertexGeometry<?> output) {
		for (final Vertex vertex : output.getVertices()) {
			vertex.setPoint(vertex.getPoint().mult(scale).addLocal(translation));
		}
	}
}
