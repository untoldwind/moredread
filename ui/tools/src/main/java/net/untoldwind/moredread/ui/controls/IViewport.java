package net.untoldwind.moredread.ui.controls;

import net.untoldwind.moredread.model.math.Camera;
import net.untoldwind.moredread.model.math.Vector2;
import net.untoldwind.moredread.model.scene.BoundingBox;
import net.untoldwind.moredread.model.scene.INode;

public interface IViewport {
	Camera getCamera();

	BoundingBox getBoundingBox();

	INode pickNode(Vector2 screenCoord);
}
