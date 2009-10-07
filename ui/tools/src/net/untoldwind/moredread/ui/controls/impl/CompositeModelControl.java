package net.untoldwind.moredread.ui.controls.impl;

import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.ui.controls.IModelControl;

import com.jme.renderer.Camera;
import com.jme.scene.Node;
import com.jme.scene.Spatial;

public abstract class CompositeModelControl extends Node implements
		IModelControl {
	private static final long serialVersionUID = 1L;

	protected List<IModelControl> subControls = new ArrayList<IModelControl>();

	public CompositeModelControl(final String name) {
		super(name);
	}

	@Override
	public Spatial getSpatial() {
		return this;
	}

	@Override
	public void cameraUpdated(final Camera camera) {
		for (final IModelControl control : subControls) {
			control.cameraUpdated(camera);
		}
	}

	@Override
	public void setActive(final boolean active) {
		for (final IModelControl control : subControls) {
			control.setActive(active);
		}
	}
}
