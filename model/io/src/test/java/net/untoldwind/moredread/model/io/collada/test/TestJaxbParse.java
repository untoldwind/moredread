package net.untoldwind.moredread.model.io.collada.test;

import static org.junit.Assert.assertTrue;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.collada._2005._11.colladaschema.COLLADA;
import org.junit.Test;

public class TestJaxbParse {
	@Test
	public void testCube() throws Exception {
		final JAXBContext context = JAXBContext.newInstance(COLLADA.class);
		final Unmarshaller unmarshaller = context.createUnmarshaller();

		final Object result = unmarshaller.unmarshal(getClass()
				.getResourceAsStream("cube.dae"));

		assertTrue(result instanceof COLLADA);
	}
}
