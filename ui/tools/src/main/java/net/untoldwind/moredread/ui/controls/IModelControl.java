package net.untoldwind.moredread.ui.controls;

import java.util.List;

import net.untoldwind.moredread.ui.tools.spi.IToolAdapter;

import com.jme.scene.Spatial;

/**
 * Interface of a model control.
 * 
 * A model control as any kind of control displayed inside the 3D view allowing
 * the user to manipulate the model in some way. Note that the control itself
 * does not handle the user interactions directly, that what IControlHandle's
 * are fore.
 */
public interface IModelControl {
	/**
	 * Get the spatial object displayed in the 3D view.
	 */
	Spatial getSpatial();

	void collectControlHandles(List<IControlHandle> handles, IViewport viewport);

	/**
	 * Inform the control that the viewport of the 3D view has been updated.
	 */
	void viewportChanged(IViewport viewport);

	void setActive(boolean active);

	void updatePositions();

	IToolAdapter getToolAdapter();
}
