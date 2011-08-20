package net.untoldwind.moredread.model.scene;

import java.util.List;

public interface IComposite extends INode {
	public List<? extends INode> getChildren();
}
