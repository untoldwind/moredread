package net.untoldwind.moredread.model.scene.change;

import net.untoldwind.moredread.model.scene.Scene;

import org.eclipse.core.commands.operations.IUndoableOperation;

public interface ISceneChangeCommand extends IUndoableOperation {
	String getStageId();

	void updateCurrentValues(final Scene scene);

}
