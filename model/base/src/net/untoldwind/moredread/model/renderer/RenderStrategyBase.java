package net.untoldwind.moredread.model.renderer;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.untoldwind.moredread.model.scene.Group;
import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.ISceneVisitor;
import net.untoldwind.moredread.model.scene.Scene;

import com.jme.light.DirectionalLight;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Spatial;
import com.jme.scene.state.BlendState;
import com.jme.scene.state.LightState;
import com.jme.scene.state.MaterialState;
import com.jme.scene.state.ZBufferState;
import com.jme.scene.state.MaterialState.ColorMaterial;
import com.jme.scene.state.MaterialState.MaterialFace;

public abstract class RenderStrategyBase implements ISceneVisitor<Spatial> {
	protected Renderer renderer;
	protected Map<Long, RenderEntry> renderEntries;

	protected RenderStrategyBase(final Renderer renderer,
			final Map<Long, RenderEntry> renderEntries) {
		this.renderer = renderer;
		this.renderEntries = renderEntries;
	}

	@Override
	public Spatial visitGroup(final Group group) {
		RenderEntry entry = renderEntries.get(group.getNodeId());
		com.jme.scene.Node displayNode;

		if (entry == null) {
			displayNode = new com.jme.scene.Node();
			entry = new RenderEntry(group.getNodeId(), -1, displayNode);
			renderEntries.put(group.getNodeId(), entry);
		} else {
			displayNode = (com.jme.scene.Node) entry.getRendererSpatial();
		}

		final Set<Spatial> updatedSet = new HashSet<Spatial>();
		for (final INode node : group.getChildren()) {
			final Spatial renderedChild = node.accept(this);

			displayNode.attachChild(renderedChild);
			updatedSet.add(renderedChild);
		}

		if (entry.getETag() != group.getETag()) {
			displayNode.setLocalRotation(group.getLocalRotation());
			displayNode.setLocalTranslation(group.getLocalTranslation());
			displayNode.setLocalScale(group.getLocalScale());

			if (displayNode.getChildren() != null) {
				for (final Spatial child : displayNode.getChildren()) {
					if (!updatedSet.contains(child)) {
						child.removeFromParent();
					}
				}
			}
			entry.setETag(group.getETag());
		}

		return displayNode;
	}

	@Override
	public Spatial visitScene(final Scene scene) {
		RenderEntry entry = renderEntries.get(scene.getNodeId());
		com.jme.scene.Node displayNode;

		if (entry == null) {
			displayNode = new com.jme.scene.Node();

			final DirectionalLight light = new DirectionalLight();
			light.setDiffuse(new ColorRGBA(1.0f, 1.0f, 1.0f, 1.0f));
			light.setAmbient(new ColorRGBA(0.5f, 0.5f, 0.5f, 1.0f));
			light.setDirection(new Vector3f(-1, -1, -1));
			light.setEnabled(true);

			/** Attach the light to a lightState and the lightState to rootNode. */
			final LightState lightState = renderer.createLightState();
			lightState.setEnabled(true);
			lightState.attach(light);
			displayNode.setRenderState(lightState);

			final MaterialState materialState = renderer.createMaterialState();
			materialState.setMaterialFace(MaterialFace.FrontAndBack);
			materialState.setColorMaterial(ColorMaterial.AmbientAndDiffuse);
			materialState.setEnabled(true);
			final BlendState sceneBlendState = renderer.createBlendState();
			sceneBlendState.setEnabled(true);
			sceneBlendState.setBlendEnabled(true);
			sceneBlendState
					.setSourceFunction(BlendState.SourceFunction.SourceAlpha);
			sceneBlendState
					.setDestinationFunction(BlendState.DestinationFunction.OneMinusSourceAlpha);

			displayNode.setRenderState(materialState);
			displayNode.setRenderState(sceneBlendState);

			final ZBufferState buf = renderer.createZBufferState();
			buf.setEnabled(true);
			buf.setFunction(ZBufferState.TestFunction.LessThanOrEqualTo);

			displayNode.setRenderState(buf);

			displayNode.updateGeometricState(0.0f, true);
			displayNode.updateRenderState();

			entry = new RenderEntry(scene.getNodeId(), -1, displayNode);
			renderEntries.put(scene.getNodeId(), entry);
		} else {
			displayNode = (com.jme.scene.Node) entry.getRendererSpatial();
		}

		final Set<Spatial> updatedSet = new HashSet<Spatial>();
		for (final INode node : scene.getChildren()) {
			final Spatial renderedChild = node.accept(this);

			displayNode.attachChild(renderedChild);
			updatedSet.add(renderedChild);
		}

		if (entry.getETag() != scene.getETag()) {
			if (displayNode.getChildren() != null) {
				for (final Spatial child : displayNode.getChildren()) {
					if (!updatedSet.contains(child)) {
						child.removeFromParent();
					}
				}
			}

			entry.setETag(scene.getETag());
		}

		displayNode.updateGeometricState(0.0f, true);
		displayNode.updateRenderState();

		return displayNode;
	}

	protected static class RenderEntry {
		private final Spatial rendererSpatial;
		private final long nodeId;
		private int eTag;

		public RenderEntry(final long nodeId, final int eTag,
				final Spatial rendererSpatial) {
			this.nodeId = nodeId;
			this.eTag = eTag;
			this.rendererSpatial = rendererSpatial;
		}

		public long getNodeId() {
			return nodeId;
		}

		public int getETag() {
			return eTag;
		}

		public void setETag(final int eTag) {
			this.eTag = eTag;
		}

		public Spatial getRendererSpatial() {
			return rendererSpatial;
		}
	}
}
