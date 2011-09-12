package net.untoldwind.moredread.ui.tools.creation;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import net.untoldwind.moredread.model.generator.BooleanGenerator;
import net.untoldwind.moredread.model.op.IBooleanOperation;
import net.untoldwind.moredread.model.scene.GeneratorNode;
import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.ISceneOperation;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.controls.IViewport;
import net.untoldwind.moredread.ui.tools.IToolController;
import net.untoldwind.moredread.ui.tools.spi.IToolHandler;

public class BooleanCreationToolHandler implements IToolHandler {

	@Override
	public void activated(final IToolController toolController,
			final Scene scene) {
		final Set<INode> nodes = scene.getSceneSelection().getSelectedNodes();

		if (nodes.size() != 2) {
			return;
		}

		scene.undoableChange(new ISceneOperation() {
			@Override
			public void perform(final Scene scene) {
				final GeneratorNode booleanNode = new GeneratorNode(scene,
						new BooleanGenerator(
								IBooleanOperation.BoolOperation.DIFFERENCE));
				for (final INode node : nodes) {
					node.setParent(booleanNode);
				}
			}
		});
	}

	@Override
	public void aborted(final IToolController toolController, final Scene scene) {
	}

	@Override
	public List<? extends IModelControl> getModelControls(final Scene scene,
			final IViewport viewport) {
		return Collections.emptyList();
	}

}
