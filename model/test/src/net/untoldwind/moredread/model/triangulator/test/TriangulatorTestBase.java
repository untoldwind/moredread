package net.untoldwind.moredread.model.triangulator.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Map;

import net.untoldwind.moredread.model.mesh.IPoint;
import net.untoldwind.moredread.model.mesh.Point;
import net.untoldwind.moredread.model.mesh.Polygon;
import net.untoldwind.moredread.model.op.ITriangulator;

import com.jme.math.Vector3f;

public abstract class TriangulatorTestBase {
	private final static boolean DEBUG = false;

	Map<String, Polygon> polygons;

	protected TriangulatorTestBase() throws Exception {
		polygons = SVGHelper.readPaths("noholes-nointsections.svg");
		polygons.putAll(SVGHelper.readPaths("holes-intsections.svg"));
	}

	protected abstract ITriangulator getTriangulator();

	protected void testQuad(final int[] expected) {
		final IPoint[] vertices = new IPoint[] { new Point(0, 0, 0),
				new Point(1, 0, 0), new Point(1, 1, 0), new Point(0, 1, 0) };
		final Vector3f[] normals = new Vector3f[] { new Vector3f(0, 0, 1),
				new Vector3f(0, 0, 1), new Vector3f(0, 0, 1),
				new Vector3f(0, 0, 1) };
		final int[] stripCounts = new int[] { 4 };
		final int[] contourCounts = new int[] { 1 };

		final int[] result = getTriangulator()
				.triangulate(
						new Polygon(vertices, normals, stripCounts,
								contourCounts, true));

		assertTrue(
				Arrays.toString(expected) + " == " + Arrays.toString(result),
				Arrays.equals(expected, result));
	}

	protected void testPoly(final String polyId, final int[] expected)
			throws Exception {
		final Polygon polygon = polygons.get(polyId);

		assertNotNull(polygon);

		if (DEBUG) {
			PNGHelper.drawPolygon(polygon.getVertices(), polygon
					.getPolygonStripCounts(), polyId + "-poly.png");
		}

		final int[] result = getTriangulator().triangulate(polygon);

		if (DEBUG) {
			System.out.println(polyId + " : " + Arrays.toString(result));
			PNGHelper.drawTriangles(polygon.getVertices(), result, polyId
					+ "-tri.png");
		}

		assertTrue(
				Arrays.toString(expected) + " == " + Arrays.toString(result),
				Arrays.equals(expected, result));
	}
}
