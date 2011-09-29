package net.untoldwind.moredread.model.scene;

import java.util.Iterator;
import java.util.List;

import net.untoldwind.moredread.model.math.Matrix3;
import net.untoldwind.moredread.model.math.Quaternion;
import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.mesh.IPoint;

import com.jme.math.FastMath;

public class BoundingBox {
	private float xExtent, yExtent, zExtent;
	private final Vector3 center = new Vector3();

	public BoundingBox() {
	}

	public BoundingBox(final BoundingBox boundingBox) {
		center.set(boundingBox.center);
		xExtent = boundingBox.xExtent;
		yExtent = boundingBox.yExtent;
		zExtent = boundingBox.zExtent;
	}

	public BoundingBox(final List<? extends IPoint> points) {
		computeFromPoints(points);
	}

	public Vector3 getCenter() {
		return center;
	}

	public float getXExtent() {
		return xExtent;
	}

	public float getYExtent() {
		return yExtent;
	}

	public float getZExtent() {
		return zExtent;
	}

	public void add(final Vector3 point) {
		float minX = center.x - xExtent, minY = center.y - yExtent, minZ = center.z
				- zExtent;
		float maxX = center.x + xExtent, maxY = center.y + yExtent, maxZ = center.z
				+ zExtent;

		if (point.x < minX) {
			minX = point.x;
		} else if (point.x > maxX) {
			maxX = point.x;
		}

		if (point.y < minY) {
			minY = point.y;
		} else if (point.y > maxY) {
			maxY = point.y;
		}

		if (point.z < minZ) {
			minZ = point.z;
		} else if (point.z > maxZ) {
			maxZ = point.z;
		}

		center.set(minX + maxX, minY + maxY, minZ + maxZ);
		center.multLocal(0.5f);

		xExtent = maxX - center.x;
		yExtent = maxY - center.y;
		zExtent = maxZ - center.z;

	}

	public void computeFromPoints(final List<? extends IPoint> points) {
		if (points == null || points.isEmpty()) {
			return;
		}

		final Iterator<? extends IPoint> it = points.iterator();

		Vector3 p = it.next().getPoint();

		float minX = p.x, minY = p.y, minZ = p.z;
		float maxX = p.x, maxY = p.y, maxZ = p.z;

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

		center.set(minX + maxX, minY + maxY, minZ + maxZ);
		center.multLocal(0.5f);

		xExtent = maxX - center.x;
		yExtent = maxY - center.y;
		zExtent = maxZ - center.z;
	}

	public void mergeLocal(final BoundingBox boundingBox) {
		final Vector3 v1 = new Vector3();
		final Vector3 v2 = new Vector3();

		v1.x = center.x - xExtent;
		if (v1.x > boundingBox.center.x - boundingBox.xExtent) {
			v1.x = boundingBox.center.x - boundingBox.xExtent;
		}
		v1.y = center.y - yExtent;
		if (v1.y > boundingBox.center.y - boundingBox.yExtent) {
			v1.y = boundingBox.center.y - boundingBox.yExtent;
		}
		v1.z = center.z - zExtent;
		if (v1.z > boundingBox.center.z - boundingBox.zExtent) {
			v1.z = boundingBox.center.z - boundingBox.zExtent;
		}

		v2.x = center.x + xExtent;
		if (v2.x < boundingBox.center.x + boundingBox.xExtent) {
			v2.x = boundingBox.center.x + boundingBox.xExtent;
		}
		v2.y = center.y + yExtent;
		if (v2.y < boundingBox.center.y + boundingBox.yExtent) {
			v2.y = boundingBox.center.y + boundingBox.yExtent;
		}
		v2.z = center.z + zExtent;
		if (v2.z < boundingBox.center.z + boundingBox.zExtent) {
			v2.z = boundingBox.center.z + boundingBox.zExtent;
		}

		center.set(v2).addLocal(v1).multLocal(0.5f);

		xExtent = v2.x - center.x;
		yExtent = v2.y - center.y;
		zExtent = v2.z - center.z;
	}

	public Vector3 getMinPosition() {
		return new Vector3(center.x - xExtent, center.y - yExtent, center.z
				- zExtent);
	}

	public Vector3 getMaxPosition() {
		return new Vector3(center.x + xExtent, center.y + yExtent, center.z
				+ zExtent);
	}

	public BoundingBox transform(final Quaternion rotate,
			final Vector3 translate, final Vector3 scale) {
		final BoundingBox box = new BoundingBox();

		center.mult(scale, box.center);
		rotate.mult(box.center, box.center);
		box.center.addLocal(translate);

		final Matrix3 transMatrix = new Matrix3();
		transMatrix.set(rotate);
		// Make the rotation matrix all positive to get the maximum x/y/z extent
		transMatrix.m00 = FastMath.abs(transMatrix.m00);
		transMatrix.m01 = FastMath.abs(transMatrix.m01);
		transMatrix.m02 = FastMath.abs(transMatrix.m02);
		transMatrix.m10 = FastMath.abs(transMatrix.m10);
		transMatrix.m11 = FastMath.abs(transMatrix.m11);
		transMatrix.m12 = FastMath.abs(transMatrix.m12);
		transMatrix.m20 = FastMath.abs(transMatrix.m20);
		transMatrix.m21 = FastMath.abs(transMatrix.m21);
		transMatrix.m22 = FastMath.abs(transMatrix.m22);

		final Vector3 v1 = new Vector3();
		final Vector3 v2 = new Vector3();

		v1.set(xExtent * scale.x, yExtent * scale.y, zExtent * scale.z);
		transMatrix.mult(v1, v2);
		// Assign the biggest rotations after scales.
		box.xExtent = FastMath.abs(v2.x);
		box.yExtent = FastMath.abs(v2.y);
		box.zExtent = FastMath.abs(v2.z);

		return box;
	}

	public boolean intersects(final BoundingBox bb) {
		if (!Vector3.isValidVector(center) || !Vector3.isValidVector(bb.center)) {
			return false;
		}

		if (center.x + xExtent < bb.center.x - bb.xExtent
				|| center.x - xExtent > bb.center.x + bb.xExtent) {
			return false;
		} else if (center.y + yExtent < bb.center.y - bb.yExtent
				|| center.y - yExtent > bb.center.y + bb.yExtent) {
			return false;
		} else if (center.z + zExtent < bb.center.z - bb.zExtent
				|| center.z - zExtent > bb.center.z + bb.zExtent) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((center == null) ? 0 : center.hashCode());
		result = prime * result + Float.floatToIntBits(xExtent);
		result = prime * result + Float.floatToIntBits(yExtent);
		result = prime * result + Float.floatToIntBits(zExtent);
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final BoundingBox other = (BoundingBox) obj;
		if (center == null) {
			if (other.center != null) {
				return false;
			}
		} else if (!center.equals(other.center)) {
			return false;
		}
		if (Float.floatToIntBits(xExtent) != Float
				.floatToIntBits(other.xExtent)) {
			return false;
		}
		if (Float.floatToIntBits(yExtent) != Float
				.floatToIntBits(other.yExtent)) {
			return false;
		}
		if (Float.floatToIntBits(zExtent) != Float
				.floatToIntBits(other.zExtent)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("BoundingBox [center=");
		builder.append(center);
		builder.append(", xExtent=");
		builder.append(xExtent);
		builder.append(", yExtent=");
		builder.append(yExtent);
		builder.append(", zExtent=");
		builder.append(zExtent);
		builder.append("]");
		return builder.toString();
	}

}
