package net.untoldwind.moredread.ui.tools.spi;

import java.util.List;

import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.controls.IViewport;
import net.untoldwind.moredread.ui.tools.IToolController;

/**
 * Interface of all tool handlers.
 * 
 * Tool handlers perform the actual work behind each tool.
 */
public interface IToolHandler {
	/**
	 * Invoked if the tools is activated/clicked by the user.
	 * 
	 * 'toggle' type tools may do some initialization here. 'push' type tools
	 * should perform all there work here.
	 * 
	 * @param toolController
	 *            The tool controller
	 * @param scene
	 *            The current scene
	 */
	void activated(IToolController toolController, Scene scene);

	/**
	 * Invoked if the tool is aborted by the user.
	 * 
	 * This should never be called on 'push' type tools.
	 */
	void aborted();

	/**
	 * Get all model controls that should be displayed while the tool is active.
	 * 
	 * This only applied for 'toggle' type tool. 'push' type tools should just
	 * oerate on the current selection and become inactive at once.
	 * 
	 * @param scene
	 *            The current scene
	 * @param displaySystem
	 *            The current display system
	 * @return List of model controls to display while the tool is active.
	 */
	List<? extends IModelControl> getModelControls(Scene scene,
			IViewport viewport);
}
