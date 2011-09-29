package net.untoldwind.moredread.model.triangulator.test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import net.untoldwind.moredread.model.mesh.IPoint;

import com.jme.math.Vector3;

public class PNGHelper {
	public static void drawPolygon(final List<? extends IPoint> vertices,
			final int[] stripCount, final String fileName)
			throws FileNotFoundException, IOException {
		float minX = Float.MAX_VALUE;
		float maxX = -Float.MAX_VALUE;
		float minY = Float.MAX_VALUE;
		float maxY = -Float.MAX_VALUE;

		for (final IPoint vertex : vertices) {
			minX = (minX < (int) vertex.getPoint().x) ? minX : (int) vertex
					.getPoint().x;
			maxX = (maxX > (int) vertex.getPoint().x) ? maxX : (int) vertex
					.getPoint().x;
			minY = (minY < (int) vertex.getPoint().y) ? minY : (int) vertex
					.getPoint().y;
			maxY = (maxY > (int) vertex.getPoint().y) ? maxY : (int) vertex
					.getPoint().y;
		}
		minX -= 5;
		maxX += 5;
		minY -= 5;
		maxY += 5;

		final BufferedImage image = new BufferedImage((int) (maxX - minX),
				(int) (maxY - minY), BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g = image.createGraphics();

		g.setColor(Color.white);
		g.fillRect(0, 0, (int) (maxX - minX), (int) (maxY - minY));

		g.setColor(Color.black);

		int offset = 0;
		for (int i = 0; i < stripCount.length; i++) {
			final int count = stripCount[i];

			final int x[] = new int[count];
			final int y[] = new int[count];
			for (int j = 0; j < count; j++) {
				final Vector3 v1 = vertices.get(offset + j).getPoint();

				x[j] = (int) (v1.x - minX);
				y[j] = (int) (v1.y - minY);
			}

			g.drawPolygon(x, y, count);

			offset += count;
		}

		ImageIO.write(image, "png", new FileOutputStream(fileName));
	}

	public static void drawTriangles(final List<? extends IPoint> vertices,
			final int[] indices, final String fileName)
			throws FileNotFoundException, IOException {
		float minX = Float.MAX_VALUE;
		float maxX = -Float.MAX_VALUE;
		float minY = Float.MAX_VALUE;
		float maxY = -Float.MAX_VALUE;

		for (final IPoint vertex : vertices) {
			minX = (minX < (int) vertex.getPoint().x) ? minX : (int) vertex
					.getPoint().x;
			maxX = (maxX > (int) vertex.getPoint().x) ? maxX : (int) vertex
					.getPoint().x;
			minY = (minY < (int) vertex.getPoint().y) ? minY : (int) vertex
					.getPoint().y;
			maxY = (maxY > (int) vertex.getPoint().y) ? maxY : (int) vertex
					.getPoint().y;
		}
		minX -= 5;
		maxX += 5;
		minY -= 5;
		maxY += 5;

		final BufferedImage image = new BufferedImage((int) (maxX - minX),
				(int) (maxY - minY), BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g = image.createGraphics();

		g.setColor(Color.white);
		g.fillRect(0, 0, (int) (maxX - minX), (int) (maxY - minY));

		for (int i = 0; i < indices.length; i += 3) {
			final Vector3 v1 = vertices.get(indices[i]).getPoint();
			final Vector3 v2 = vertices.get(indices[i + 1]).getPoint();
			final Vector3 v3 = vertices.get(indices[i + 2]).getPoint();
			final int[] x = { (int) (v1.x - minX), (int) (v2.x - minX),
					(int) (v3.x - minX) };
			final int[] y = { (int) (v1.y - minY), (int) (v2.y - minY),
					(int) (v3.y - minY) };

			g.setColor(Color.red);
			g.fillPolygon(x, y, 3);
			g.setColor(Color.black);
			g.drawPolygon(x, y, 3);

		}

		ImageIO.write(image, "png", new FileOutputStream(fileName));
	}
}
