package net.untoldwind.moredread.model.test;

import net.untoldwind.moredread.model.math.Vector3;

import org.junit.Assert;

public class AssertHelper {
	public static void assertVectorEquals(final Vector3 expected,
			final Vector3 actual, final float epsilon) {
		Assert.assertEquals("x", expected.x, actual.x, epsilon);
		Assert.assertEquals("y", expected.y, actual.y, epsilon);
		Assert.assertEquals("z", expected.z, actual.z, epsilon);
	}

	public static void assertVectorEquals(final String message,
			final Vector3 expected, final Vector3 actual, final float epsilon) {
		Assert.assertEquals(message + " x", expected.x, actual.x, epsilon);
		Assert.assertEquals(message + " y", expected.y, actual.y, epsilon);
		Assert.assertEquals(message + " z", expected.z, actual.z, epsilon);
	}
}
