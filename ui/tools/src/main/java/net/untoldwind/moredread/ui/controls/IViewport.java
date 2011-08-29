package net.untoldwind.moredread.ui.controls;

import net.untoldwind.moredread.model.scene.BoundingBox;
import net.untoldwind.moredread.model.scene.INode;

import com.jme.math.Vector2f;
import com.jme.renderer.Camera;

public interface IViewport {
	Camera getCamera();

	BoundingBox getBoundingBox();

	INode pickNode(Vector2f screenCoord);
}
