package net.untoldwind.moredread.model.scene.change;

import java.util.List;

import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.Scene;

import org.eclipse.core.commands.operations.IUndoableOperation;

public interface ISceneChangeCommand extends IUndoableOperation {
	String getStageId();

	void updateOriginalValues(final Scene scene);

	void updateCurrentValues(final Scene scene);

	void collectAffectedNodes(final Scene scene, final List<INode> nodes);

	void setLabel(String label);
}
