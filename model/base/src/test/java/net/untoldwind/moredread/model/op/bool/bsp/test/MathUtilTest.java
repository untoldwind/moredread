package net.untoldwind.moredread.model.op.bool.bsp.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.op.bool.bspfilter.MathUtils;

import org.junit.Test;

public class MathUtilTest {
	@Test
	public void testIsOnLine() throws Exception {
		assertTrue(MathUtils.isOnLine(new Vector3(), new Vector3(),
				new Vector3(1, 2, 3)));
		assertTrue(MathUtils.isOnLine(new Vector3(1, 2, 3), new Vector3(),
				new Vector3(1, 2, 3)));
		assertTrue(MathUtils.isOnLine(new Vector3(0.5f, 1f, 1.5f),
				new Vector3(), new Vector3(1, 2, 3)));
		assertTrue(MathUtils.isOnLine(new Vector3(0.33333f, 0.66666f, 1f),
				new Vector3(), new Vector3(1, 2, 3)));
		assertFalse(MathUtils.isOnLine(new Vector3(0.3f, 0.6f, 1f),
				new Vector3(), new Vector3(1, 2, 3)));

		assertTrue(MathUtils.isOnLine(new Vector3(1, 2, 3),
				new Vector3(1, 2, 3), new Vector3(3, 2, 1)));
		assertTrue(MathUtils.isOnLine(new Vector3(3, 2, 1),
				new Vector3(1, 2, 3), new Vector3(3, 2, 1)));
		assertTrue(MathUtils.isOnLine(new Vector3(2, 2, 2),
				new Vector3(1, 2, 3), new Vector3(3, 2, 1)));
		assertTrue(MathUtils.isOnLine(new Vector3(1.5f, 2, 2.5f), new Vector3(
				1, 2, 3), new Vector3(3, 2, 1)));
		assertFalse(MathUtils.isOnLine(new Vector3(2.1f, 2, 2), new Vector3(1,
				2, 3), new Vector3(3, 2, 1)));
	}
}
