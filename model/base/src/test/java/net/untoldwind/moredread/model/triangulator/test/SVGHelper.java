package net.untoldwind.moredread.model.triangulator.test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import net.untoldwind.moredread.model.mesh.IPoint;
import net.untoldwind.moredread.model.mesh.Point;
import net.untoldwind.moredread.model.mesh.Polygon;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.jme.math.Vector3f;

public class SVGHelper {
	public static Map<String, Polygon> readPaths(final String resource)
			throws DocumentException {
		return readPaths(SVGHelper.class.getResourceAsStream(resource));
	}

	public static Map<String, Polygon> readPaths(final InputStream in)
			throws DocumentException {
		final SAXReader reader = new SAXReader();
		reader.setIgnoreComments(true);

		return readPaths(reader.read(in));
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Polygon> readPaths(final Document document) {
		final Map<String, Polygon> result = new HashMap<String, Polygon>();

		for (final Element element : (List<Element>) document
				.selectNodes("//svg:path")) {
			final String id = element.attributeValue("id");
			final String d = element.attributeValue("d").trim();
			final StringTokenizer t = new StringTokenizer(d, " ");

			final List<IPoint> vertices = new ArrayList<IPoint>();
			final List<Vector3f> normals = new ArrayList<Vector3f>();
			final List<Integer> stripCounts = new ArrayList<Integer>();
			int count = 0;

			while (t.hasMoreTokens()) {
				final String cmd = t.nextToken();

				switch (cmd.charAt(0)) {
				case 'M':
				case 'm':
					vertices.add(parseCoordinate(t.nextToken()));
					normals.add(new Vector3f(0, 0, 1));
					count++;
					break;
				case 'L':
				case 'l':
					vertices.add(parseCoordinate(t.nextToken()));
					normals.add(new Vector3f(0, 0, 1));
					count++;
					break;
				case 'Z':
				case 'z':
					stripCounts.add(count);
					count = 0;
					break;
				}
			}

			final int[] stripCountsArr = new int[stripCounts.size()];

			for (int i = 0; i < stripCountsArr.length; i++) {
				stripCountsArr[i] = stripCounts.get(i);
			}

			result.put(id, new Polygon(vertices.toArray(new IPoint[vertices
					.size()]), normals.toArray(new Vector3f[normals.size()]),
					stripCountsArr, new int[] { stripCountsArr.length }, true));
		}

		return result;
	}

	protected static IPoint parseCoordinate(final String coord) {
		final String[] coordArr = coord.split(",");

		return new Point(new Vector3f(Float.parseFloat(coordArr[0]), Float
				.parseFloat(coordArr[1]), 0));
	}
}
