package net.untoldwind.moredread.model.scene;

import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.enums.SelectionMode;
import net.untoldwind.moredread.model.renderer.INodeRendererAdapter;
import net.untoldwind.moredread.model.scene.event.ISceneSelectionModeListener;
import net.untoldwind.moredread.model.scene.event.SceneSelectionModeEvent;

import com.jme.intersection.PickResults;
import com.jme.light.DirectionalLight;
import com.jme.math.Ray;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.state.BlendState;
import com.jme.scene.state.LightState;
import com.jme.scene.state.MaterialState;
import com.jme.scene.state.ZBufferState;
import com.jme.scene.state.MaterialState.ColorMaterial;
import com.jme.scene.state.MaterialState.MaterialFace;

public class SceneHolder implements ISceneHolder {
	private final Scene scene;
	private SelectionMode selectionMode;
	private final List<ISceneSelectionModeListener> selectionModeListeners;

	private transient com.jme.scene.Node displayNode;

	public SceneHolder(final Scene scene) {
		this.scene = scene;
		this.selectionModeListeners = new ArrayList<ISceneSelectionModeListener>();
		this.selectionMode = SelectionMode.OBJECT;
	}

	@Override
	public Scene getScene() {
		return scene;
	}

	public SelectionMode getSelectionMode() {
		return selectionMode;
	}

	public void setSelectionMode(final SelectionMode selectionMode) {
		this.selectionMode = selectionMode;

		fireSceneGeometryChangeEvent(new SceneSelectionModeEvent(selectionMode));
	}

	@Override
	public void render(final Renderer renderer,
			final INodeRendererAdapter rendererAdapter) {
		if (displayNode == null) {
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
		}

		if (rendererAdapter != null) {
			scene.updateDisplayNode(rendererAdapter, displayNode);
			displayNode.updateGeometricState(0.0f, true);
			displayNode.updateRenderState();
		}

		renderer.draw(displayNode);
	}

	public void findPick(final Ray toTest, final PickResults results) {
		if (displayNode != null) {
			displayNode.findPick(toTest, results);
		}
	}

	@Override
	public void addSceneSelectionModeListener(
			final ISceneSelectionModeListener listener) {
		synchronized (selectionModeListeners) {
			selectionModeListeners.add(listener);
		}
	}

	@Override
	public void removeSceneSelectionModeListener(
			final ISceneSelectionModeListener listener) {
		synchronized (selectionModeListeners) {
			selectionModeListeners.remove(listener);
		}
	}

	protected void fireSceneGeometryChangeEvent(
			final SceneSelectionModeEvent event) {
		final ISceneSelectionModeListener listenerArray[];

		synchronized (selectionModeListeners) {
			listenerArray = selectionModeListeners
					.toArray(new ISceneSelectionModeListener[selectionModeListeners
							.size()]);
		}

		for (final ISceneSelectionModeListener listener : listenerArray) {
			listener.sceneSelectionModeChanged(event);
		}
	}

}
