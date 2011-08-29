package net.untoldwind.moredread.model.triangulator.test;

import net.untoldwind.moredread.model.op.ITriangulator;
import net.untoldwind.moredread.model.op.TriangulatorFactory;
import net.untoldwind.moredread.model.op.TriangulatorFactory.Implementation;

import org.junit.Test;

public class FISTTriangulatorTest extends TriangulatorTestBase {
	public FISTTriangulatorTest() throws Exception {
		super();
	}

	@Override
	protected ITriangulator getTriangulator() {
		return TriangulatorFactory.createTriangulator(Implementation.FIST);
	}

	@Test
	public void testQuad() {
		super.testQuad(new int[] { 0, 1, 2, 0, 2, 3 });
	}

	@Test
	public void testPath1() throws Exception {
		super.testPoly("path1", new int[] { 8, 0, 7, 7, 0, 6, 6, 0, 5, 5, 0, 4,
				4, 0, 3, 3, 0, 2, 2, 0, 1 });
	}

	@Test
	public void testPath2() throws Exception {
		super.testPoly("path2", new int[] { 6, 0, 5, 5, 0, 4, 4, 0, 3, 3, 0, 2,
				2, 0, 1 });
	}

	@Test
	public void testPath3() throws Exception {
		super.testPoly("path3", new int[] { 11, 0, 1, 10, 11, 1, 9, 10, 1, 9,
				1, 2, 8, 9, 2, 8, 2, 3, 7, 8, 3, 7, 3, 4, 6, 7, 4, 6, 4, 5 });
	}

	@Test
	public void testPath4() throws Exception {
		super.testPoly("path4", new int[] { 16, 0, 15, 15, 0, 14, 2, 3, 1, 1,
				3, 0, 0, 3, 14, 3, 4, 14, 14, 4, 13, 5, 6, 4, 6, 7, 4, 7, 8, 4,
				8, 9, 4, 9, 10, 4, 10, 11, 4, 4, 11, 13, 13, 11, 12 });
	}

	@Test
	public void testPath5() throws Exception {
		super.testPoly("path5", new int[] { 22, 0, 21, 21, 0, 20, 2, 3, 1, 1,
				3, 0, 0, 3, 20, 3, 4, 20, 20, 4, 19, 19, 4, 18, 18, 4, 17, 17,
				4, 16, 4, 5, 16, 16, 5, 15, 15, 5, 14, 5, 6, 14, 6, 7, 14, 14,
				7, 13, 7, 8, 13, 8, 9, 13, 13, 9, 12, 12, 9, 11, 11, 9, 10 });
	}

	@Test
	public void testPath6() throws Exception {
		super.testPoly("path6", new int[] { 19, 0, 1, 18, 19, 1, 17, 18, 1, 16,
				17, 1, 15, 16, 1, 15, 1, 2, 14, 15, 2, 13, 14, 2, 13, 2, 3, 12,
				13, 3, 12, 3, 4, 11, 12, 4, 11, 4, 5, 10, 11, 5, 10, 5, 6, 9,
				10, 6, 9, 6, 7, 9, 7, 8 });
	}

	@Test
	public void testPath7() throws Exception {
		super.testPoly("path7", new int[] { 30, 0, 29, 29, 0, 28, 28, 0, 27,
				27, 0, 26, 0, 1, 26, 1, 2, 26, 2, 3, 26, 26, 3, 25, 3, 4, 25,
				5, 6, 4, 6, 7, 4, 7, 8, 4, 8, 9, 4, 4, 9, 25, 9, 10, 25, 10,
				11, 25, 25, 11, 24, 13, 14, 12, 15, 16, 14, 16, 17, 14, 14, 17,
				12, 12, 17, 11, 17, 18, 11, 11, 18, 24, 24, 18, 23, 18, 19, 23,
				19, 20, 23, 20, 21, 23, 23, 21, 22 });
	}

	@Test
	public void testPath101() throws Exception {
		super.testPoly("path101", new int[] { 6, 0, 5, 2, 3, 1, 3, 4, 1, 1, 4,
				0, 0, 4, 5 });
	}

	@Test
	public void testPath102() throws Exception {
		super.testPoly("path102", new int[] { 12, 0, 1, 11, 12, 1, 10, 11, 1,
				7, 8, 9, 3, 4, 5, 2, 3, 5, 1, 2, 5, 5, 6, 7, 1, 5, 7, 1, 7, 9,
				1, 9, 10 });
	}

	@Test
	public void testPath103() throws Exception {
		super.testPoly("path103", new int[] { 5, 9, 8, 0, 4, 3, 0, 3, 7, 0, 7,
				6, 0, 6, 5, 1, 0, 5, 1, 5, 8, 2, 1, 8, 2, 8, 7, 2, 7, 3 });
	}

	@Test
	public void testPath104() throws Exception {
		super.testPoly("path104",
				new int[] { 17, 14, 15, 22, 18, 19, 13, 10, 11, 9, 0, 1, 2, 13,
						11, 2, 11, 21, 2, 21, 22, 1, 2, 22, 1, 22, 19, 1, 19,
						16, 1, 16, 17, 9, 1, 17, 9, 17, 15, 8, 9, 15, 8, 15,
						16, 7, 8, 16, 7, 16, 19, 7, 19, 20, 6, 7, 20, 6, 20,
						21, 6, 21, 11, 6, 11, 12, 5, 6, 12, 5, 12, 13, 5, 13,
						2, 5, 2, 3, 5, 3, 4 });
	}

}
