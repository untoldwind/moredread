package net.untoldwind.moredread.model.op.bool.blebopf;

import java.util.Iterator;
import java.util.List;

import net.untoldwind.moredread.model.math.Vector3;

public class BoolBoundingBox {
	float minX;
	float minY;
	float minZ;
	float maxX;
	float maxY;
	float maxZ;
	float centerX;
	float centerY;
	float centerZ;
	float extentX;
	float extentY;
	float extentZ;

	public BoolBoundingBox(final List<BoolVertex> points) {
		computeFromPoints(points);
	}

	public void computeFromPoints(final List<BoolVertex> points) {
		if (points == null || points.isEmpty()) {
			return;
		}

		final Iterator<BoolVertex> it = points.iterator();

		Vector3 p = it.next().getPoint();

		minX = p.x;
		minY = p.y;
		minZ = p.z;
		maxX = p.x;
		maxY = p.y;
		maxZ = p.z;

		while (it.hasNext()) {
			p = it.next().getPoint();

			if (p.x < minX) {
				minX = p.x;
			} else if (p.x > maxX) {
				maxX = p.x;
			}

			if (p.y < minY) {
				minY = p.y;
			} else if (p.y > maxY) {
				maxY = p.y;
			}

			if (p.z < minZ) {
				minZ = p.z;
			} else if (p.z > maxZ) {
				maxZ = p.z;
			}
		}
		extentX = (maxX - minX) / 2.0f;
		extentY = (maxY - minY) / 2.0f;
		extentZ = (maxZ - minZ) / 2.0f;
		centerX = minX + extentX;
		centerY = minY + extentY;
		centerZ = minZ + extentZ;
	}

	public boolean intersect(final BoolBoundingBox b) {
		return (!((MathUtils.comp(maxX, b.minX) < 0)
				|| (MathUtils.comp(b.maxX, minX) < 0)
				|| (MathUtils.comp(maxY, b.minY) < 0)
				|| (MathUtils.comp(b.maxY, minY) < 0)
				|| (MathUtils.comp(maxZ, b.minZ) < 0) || (MathUtils.comp(
				b.maxZ, minZ) < 0)));
	};

}
