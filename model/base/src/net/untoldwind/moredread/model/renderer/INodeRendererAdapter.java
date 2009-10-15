package net.untoldwind.moredread.model.renderer;

import java.util.List;

import net.untoldwind.moredread.model.scene.IMeshNode;

import com.jme.scene.Spatial;

public interface INodeRendererAdapter {
	List<Spatial> renderNode(IMeshNode node);
}
