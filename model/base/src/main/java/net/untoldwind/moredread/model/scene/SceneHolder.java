package net.untoldwind.moredread.model.scene;

import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.enums.SelectionMode;
import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.renderer.INodeRendererAdapter;
import net.untoldwind.moredread.model.scene.event.ISceneChangeListener;
import net.untoldwind.moredread.model.scene.event.ISceneSelectionChangeListener;
import net.untoldwind.moredread.model.scene.event.ISceneSelectionModeListener;
import net.untoldwind.moredread.model.scene.event.SceneChangeEvent;
import net.untoldwind.moredread.model.scene.event.SceneSelectionChangeEvent;
import net.untoldwind.moredread.model.scene.event.SceneSelectionModeEvent;

import com.jme.intersection.PickResults;
import com.jme.light.DirectionalLight;
import com.jme.math.Ray;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.state.BlendState;
import com.jme.scene.state.LightState;
import com.jme.scene.state.MaterialState;
import com.jme.scene.state.MaterialState.ColorMaterial;
import com.jme.scene.state.MaterialState.MaterialFace;
import com.jme.scene.state.ZBufferState;

public class SceneHolder implements ISceneHolder {
	private Scene scene;
	private SelectionMode selectionMode;
	private final List<ISceneSelectionModeListener> selectionModeListeners;
	private final List<ISceneChangeListener> changeListeners;
	private final List<ISceneSelectionChangeListener> selectionListeners;

	private transient com.jme.scene.Node displayNode;

	public SceneHolder() {
		this.selectionModeListeners = new ArrayList<ISceneSelectionModeListener>();
		this.selectionMode = SelectionMode.OBJECT;
		changeListeners = new ArrayList<ISceneChangeListener>();
		selectionListeners = new ArrayList<ISceneSelectionChangeListener>();
	}

	public Scene createScene() {
		scene = new Scene(this);

		return scene;
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
			light.setDirection(new Vector3(-1, -1, -1));
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
			scene.updateDisplayNode(rendererAdapter, displayNode, false);
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
	public void addSceneChangeListener(final ISceneChangeListener listener) {
		synchronized (changeListeners) {
			changeListeners.add(listener);
		}
	}

	@Override
	public void removeSceneChangeListener(final ISceneChangeListener listener) {
		synchronized (changeListeners) {
			changeListeners.remove(listener);
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

	public void addSceneSelectionChangeListener(
			final ISceneSelectionChangeListener listener) {
		synchronized (selectionListeners) {
			selectionListeners.add(listener);
		}
	}

	public void removeSceneSelectionChangeListener(
			final ISceneSelectionChangeListener listener) {
		synchronized (selectionListeners) {
			selectionListeners.remove(listener);
		}
	}

	protected void fireSceneSelectionChangeEvent(
			final SceneSelectionChangeEvent event) {
		ISceneSelectionChangeListener listenerArray[];

		synchronized (selectionListeners) {
			listenerArray = selectionListeners
					.toArray(new ISceneSelectionChangeListener[selectionListeners
							.size()]);
		}

		for (final ISceneSelectionChangeListener listener : listenerArray) {
			listener.sceneSelectionChanged(event);
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

	protected void fireSceneGeometryChangeEvent(final SceneChangeEvent event) {
		final ISceneChangeListener listenerArray[];

		synchronized (changeListeners) {
			listenerArray = changeListeners
					.toArray(new ISceneChangeListener[changeListeners.size()]);
		}

		for (final ISceneChangeListener listener : listenerArray) {
			listener.sceneChanged(event);
		}

	}

}
