package net.untoldwind.moredread.model.io.spi;

import java.io.IOException;
import java.io.OutputStream;

import net.untoldwind.moredread.model.scene.Scene;

public interface IModelWriter {
	void writeScene(Scene scene, OutputStream out) throws IOException;
}
