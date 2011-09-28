package net.untoldwind.moredread.model.io.impl.muf;

import java.io.IOException;
import java.io.OutputStream;

import net.untoldwind.moredread.model.io.spi.IModelWriter;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.model.state.XMLStateWriter;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * Most Useless Format writer.
 * 
 * This is considered to be an example for scene io.
 */
public class MUFModelWriter implements IModelWriter {

	@Override
	public void writeScene(final Scene scene, final OutputStream out)
			throws IOException {
		final XMLStateWriter writer = new XMLStateWriter("scene");

		scene.writeState(writer);

		final XMLWriter xmlWriter = new XMLWriter(out,
				OutputFormat.createPrettyPrint());

		xmlWriter.write(writer.getDocument());
		xmlWriter.flush();
		xmlWriter.close();
	}
}
