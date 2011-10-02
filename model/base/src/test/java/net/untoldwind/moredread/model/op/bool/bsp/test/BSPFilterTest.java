package net.untoldwind.moredread.model.op.bool.bsp.test;

import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.op.IBooleanOperation.BoolOperation;
import net.untoldwind.moredread.model.op.bool.bspfilter.BSPFilterBooleanOperation;
import net.untoldwind.moredread.model.state.XMLStateReader;
import net.untoldwind.moredread.model.state.XMLStateWriter;

import org.junit.Test;

public class BSPFilterTest {
	@Test
	public void testUnion() throws Exception {
		final IMesh meshA = XMLStateReader.fromXML(getClass()
				.getResourceAsStream("boolIn3_1.xml"));
		final IMesh meshB = XMLStateReader.fromXML(getClass()
				.getResourceAsStream("boolIn3_2.xml"));

		final BSPFilterBooleanOperation op = new BSPFilterBooleanOperation();

		final IMesh result = op.performBoolean(BoolOperation.UNION, meshA,
				meshB);

		XMLStateWriter.toXML(result);
	}
}
