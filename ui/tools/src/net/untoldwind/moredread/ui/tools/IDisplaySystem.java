package net.untoldwind.moredread.ui.tools;

import net.untoldwind.moredread.model.scene.INode;

import com.jme.math.Vector2f;

public interface IDisplaySystem {
	INode pickNode(Vector2f screenCoord);
}
