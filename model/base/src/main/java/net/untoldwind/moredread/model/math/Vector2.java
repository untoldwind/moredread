package net.untoldwind.moredread.model.math;

import com.jme.math.Vector2f;

public class Vector2 extends Vector2f {

	public Vector2() {
		super();
	}

	public Vector2(final float x, final float y) {
		super(x, y);
	}

	public Vector2(final Vector2f vector2f) {
		super(vector2f);
	}

	@Override
	public Vector2 add(final Vector2f vec) {
		return new Vector2(x + vec.x, y + vec.y);
	}

	public Vector2 add(final Vector2 vec, final Vector2 result) {
		result.x = x + vec.x;
		result.y = y + vec.y;
		return result;
	}

	public Vector2 addLocal(final Vector2 vec) {
		x += vec.x;
		y += vec.y;
		return this;
	}

	public Vector2 subtract(final Vector2 vec) {
		return new Vector2(x - vec.x, y - vec.y);
	}

	public Vector2 subtract(final Vector2 vec, final Vector2 result) {
		result.x = x - vec.x;
		result.y = y - vec.y;
		return result;
	}

	@Override
	public Vector2 mult(final float scalar) {
		return new Vector2(x * scalar, y * scalar);
	}

	@Override
	public Vector2 multLocal(final float scalar) {
		x *= scalar;
		y *= scalar;
		return this;
	}

	@Override
	public Vector2 divide(final float scalar) {
		return new Vector2(x / scalar, y / scalar);
	}

	@Override
	public Vector2 divideLocal(final float scalar) {
		x /= scalar;
		y /= scalar;
		return this;
	}

	@Override
	public float length() {
		return (float) Math.sqrt(lengthSquared());
	}

	@Override
	public float lengthSquared() {
		return x * x + y * y;
	}

	public float dot(final Vector2 vec) {
		return x * vec.x + y * vec.y;
	}

	public Vector3 cross(final Vector2 v) {
		return new Vector3(0, 0, determinant(v));
	}

	public float determinant(final Vector2 v) {
		return (x * v.y) - (y * v.x);
	}

	public float distanceSquared(final Vector2 v) {
		final double dx = x - v.x;
		final double dy = y - v.y;
		return (float) (dx * dx + dy * dy);
	}

	public float distance(final Vector2 v) {
		return (float) Math.sqrt(distanceSquared(v));
	}

	@Override
	public Vector2 clone() {
		return (Vector2) super.clone();
	}
}
