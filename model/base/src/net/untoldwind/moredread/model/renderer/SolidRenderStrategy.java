package net.untoldwind.moredread.model.renderer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.untoldwind.moredread.model.enums.SelectionMode;
import net.untoldwind.moredread.model.scene.GeneratorNode;
import net.untoldwind.moredread.model.scene.ISceneHolder;
import net.untoldwind.moredread.model.scene.MeshNode;
import net.untoldwind.moredread.model.scene.SpatialNodeReference;

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
		RenderEntry entry = renderEntries.get(generatorNode.getNodeId());
		com.jme.scene.Node displayNode;
		final SpatialNodeReference nodeRef = new SpatialNodeReference(
				generatorNode);

		if (entry == null) {
			displayNode = new com.jme.scene.Node();
			entry = new RenderEntry(generatorNode.getNodeId(), -1, displayNode);
			renderEntries.put(generatorNode.getNodeId(), entry);
		} else {
			displayNode = (com.jme.scene.Node) entry.getRendererSpatial();
		}

		if (entry.getETag() != generatorNode.getETag()) {
			displayNode.setUserData(ISceneHolder.NODE_USERDATA_KEY, nodeRef);

			displayNode.setName(generatorNode.getName());
			displayNode.setLocalRotation(generatorNode.getLocalRotation());
			displayNode
					.setLocalTranslation(generatorNode.getLocalTranslation());
			displayNode.setLocalScale(generatorNode.getLocalScale());

			displayNode.detachAllChildren();

			final List<Spatial> renderedGeometries = rendererAdapter
					.renderNode(generatorNode);
			if (renderedGeometries != null) {
				if (renderedGeometries.size() > 0) {
					renderedGeometries.get(0).setUserData(
							ISceneHolder.NODE_USERDATA_KEY, nodeRef);
				}

				for (final Spatial geometry : renderedGeometries) {
					displayNode.attachChild(geometry);
				}
			}

			entry.setETag(generatorNode.getETag());
		}

		return displayNode;
	}

	@Override
	public Spatial visitMeshNode(final MeshNode node) {
		RenderEntry entry = renderEntries.get(node.getNodeId());
		com.jme.scene.Node displayNode;
		final SpatialNodeReference nodeRef = new SpatialNodeReference(node);

		if (entry == null) {
			displayNode = new com.jme.scene.Node();
			entry = new RenderEntry(node.getNodeId(), -1, displayNode);
			renderEntries.put(node.getNodeId(), entry);
		} else {
			displayNode = (com.jme.scene.Node) entry.getRendererSpatial();
		}

		if (entry.getETag() != node.getETag()) {
			displayNode.setUserData(ISceneHolder.NODE_USERDATA_KEY, nodeRef);

			displayNode.setName(node.getName());
			displayNode.setLocalRotation(node.getLocalRotation());
			displayNode.setLocalTranslation(node.getLocalTranslation());
			displayNode.setLocalScale(node.getLocalScale());

			displayNode.detachAllChildren();

			final List<Spatial> renderedGeometries = rendererAdapter
					.renderNode(node);
			if (renderedGeometries != null) {
				if (renderedGeometries.size() > 0) {
					renderedGeometries.get(0).setUserData(
							ISceneHolder.NODE_USERDATA_KEY, nodeRef);
				}

				for (final Spatial geometry : renderedGeometries) {
					displayNode.attachChild(geometry);
				}
			}

			entry.setETag(node.getETag());
		}

		return displayNode;
	}

}
