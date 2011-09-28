package net.untoldwind.moredread.model.io.spi;

import java.io.IOException;
import java.io.InputStream;

import net.untoldwind.moredread.model.scene.ISceneHolder;
import net.untoldwind.moredread.model.scene.Scene;

public interface IModelReader {
	Scene readScene(ISceneHolder sceneHolder, InputStream in)
			throws IOException;
}
