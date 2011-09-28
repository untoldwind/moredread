package net.untoldwind.moredread.model.io.impl.muf;

import java.io.IOException;
import java.io.InputStream;

import net.untoldwind.moredread.model.io.spi.IModelReader;
import net.untoldwind.moredread.model.scene.ISceneHolder;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.model.state.XMLStateReader;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

/**
 * Most Useless Format reader.
 * 
 * This is considered to be an example for scene io.
 */
public class MUFModelReader implements IModelReader {

	@Override
	public Scene readScene(final ISceneHolder sceneHolder, final InputStream in)
			throws IOException {
		try {
			final SAXReader saxReader = new SAXReader();
			final Document document = saxReader.read(in);
			final XMLStateReader reader = new XMLStateReader(document,
					document.getRootElement());

			final Scene scene = sceneHolder.createScene();
			scene.getSceneChangeHandler().beginNotUndoable();
			try {
				scene.readState(reader);
			} finally {
				scene.getSceneChangeHandler().commit();
			}

			return scene;
		} catch (final DocumentException e) {
			throw new IOException(e);
		}
	}
}
