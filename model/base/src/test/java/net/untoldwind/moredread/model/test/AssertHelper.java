package net.untoldwind.moredread.model.test;

import org.junit.Assert;

import com.jme.math.Vector3f;

public class AssertHelper {
	public static void assertVectorEquals(final Vector3f expected,
			final Vector3f actual, final float epsilon) {
		Assert.assertEquals("x", expected.x, actual.x, epsilon);
		Assert.assertEquals("y", expected.y, actual.y, epsilon);
		Assert.assertEquals("z", expected.z, actual.z, epsilon);
	}

	public static void assertVectorEquals(final String message,
			final Vector3f expected, final Vector3f actual, final float epsilon) {
		Assert.assertEquals(message + " x", expected.x, actual.x, epsilon);
		Assert.assertEquals(message + " y", expected.y, actual.y, epsilon);
		Assert.assertEquals(message + " z", expected.z, actual.z, epsilon);
	}
}
