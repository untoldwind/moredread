package net.untoldwind.moredread.ui.controls;

import net.untoldwind.moredread.model.scene.BoundingBox;

import com.jme.renderer.Camera;

public interface IViewport {
	Camera getCamera();

	BoundingBox getBoundingBox();
}
