package net.untoldwind.moredread.model.scene;

import java.util.List;

public interface IComposite extends INode {
	List<? extends INode> getChildren();

	void addChild(INode node);

	void removeChild(INode node);
}
