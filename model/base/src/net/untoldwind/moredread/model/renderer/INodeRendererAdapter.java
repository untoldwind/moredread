package net.untoldwind.moredread.model.renderer;

import java.util.List;

import net.untoldwind.moredread.model.enums.SelectionMode;
import net.untoldwind.moredread.model.scene.IMeshNode;

import com.jme.renderer.Renderer;
import com.jme.scene.Spatial;

public interface INodeRendererAdapter {
	Renderer getRenderer();

	SelectionMode getSelectionMode();

	List<Spatial> renderNode(IMeshNode node);
}
