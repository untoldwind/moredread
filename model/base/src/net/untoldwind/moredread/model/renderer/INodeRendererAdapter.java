package net.untoldwind.moredread.model.renderer;

import java.util.List;

import net.untoldwind.moredread.model.scene.IMeshNode;

import com.jme.scene.Geometry;

public interface INodeRendererAdapter {
	List<Geometry> renderNode(IMeshNode node);
}
