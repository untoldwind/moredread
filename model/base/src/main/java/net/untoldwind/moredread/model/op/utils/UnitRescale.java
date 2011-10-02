package net.untoldwind.moredread.model.op.utils;

import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.mesh.IVertex;
import net.untoldwind.moredread.model.mesh.IVertexGeometry;
import net.untoldwind.moredread.model.mesh.Vertex;
import net.untoldwind.moredread.model.mesh.VertexGeometry;
import net.untoldwind.moredread.model.scene.BoundingBox;

/**
 * Helper class to rescale a vertex geometry to the unit box (-1,-1,-1) to
 * (1,1,1).
 * 
 * Some algorithms work best if the coordinates of the verticies do not become
 * too large. The simplest solution is just to translate and rescale the
 * geometry into the (-1,-1,-1) - (1,1,1) box, perform the algorithm and then
 * transform the resulting geometry to the original size and position.
 */
public class UnitRescale {
	BoundingBox originalBoundingBox;
	Vector3 scale;
	Vector3 translation;

	public UnitRescale(final IVertexGeometry<?>... geometries) {
		originalBoundingBox = new BoundingBox();
		for (final IVertexGeometry<?> geometry : geometries) {
			for (final IVertex vertex : geometry.getVertices()) {
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
