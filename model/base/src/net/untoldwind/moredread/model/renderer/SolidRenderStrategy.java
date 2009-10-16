package net.untoldwind.moredread.model.renderer;

import java.util.HashMap;
import java.util.Map;

import net.untoldwind.moredread.model.enums.SelectionMode;
import net.untoldwind.moredread.model.scene.GeneratorNode;
import net.untoldwind.moredread.model.scene.MeshNode;

import com.jme.renderer.Renderer;
import com.jme.scene.Spatial;

public class SolidRenderStrategy extends RenderStrategyBase {
	INodeRendererAdapter rendererAdapter;

	public SolidRenderStrategy(final Renderer renderer,
			final SelectionMode selectionMode,
			final SolidNodeRendererParam param) {
		super(renderer, new HashMap<Long, RenderEntry>());

		rendererAdapter = new SolidNodeRenderer(renderer, selectionMode, param);
	}

	public SolidRenderStrategy(final Renderer renderer,
			final Spatial parentDisplayNode,
			final Map<Long, RenderEntry> renderEntries) {
		super(renderer, renderEntries);
	}

	@Override
	public Spatial visitGeneratorNode(final GeneratorNode generatorNode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Spatial visitMeshNode(final MeshNode node) {
		// TODO Auto-generated method stub
		return null;
	}

}
