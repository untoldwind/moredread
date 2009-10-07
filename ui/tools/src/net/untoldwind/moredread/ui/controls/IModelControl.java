package net.untoldwind.moredread.ui.controls;

import java.util.List;

import com.jme.renderer.Camera;
import com.jme.scene.Spatial;

public interface IModelControl {
	Spatial getSpatial();

	void collectControlHandles(List<IControlHandle> handles, Camera camera);

	void cameraUpdated(Camera camera);

	void setActive(boolean active);

	void updatePositions();
}
