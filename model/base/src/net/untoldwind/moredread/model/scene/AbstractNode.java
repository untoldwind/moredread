package net.untoldwind.moredread.model.scene;

import java.util.concurrent.atomic.AtomicLong;

import net.untoldwind.moredread.model.scene.change.NodeNameChangeCommand;

public abstract class AbstractNode implements INode {

	private final static AtomicLong NODE_ID_COUNTER = new AtomicLong(0);

	/** The scene owning the node. */
	protected Scene scene;
	protected final long nodeId;
	protected String name;

	protected AbstractNode(final IComposite parent, final String name) {
		nodeId = NODE_ID_COUNTER.getAndIncrement();
		if (parent == null) {
			scene = (Scene) this;
		} else {
			scene = parent.getScene();
			scene.registerNode(this);
		}

		this.name = name;
	}

	public long getNodeId() {
		return nodeId;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		scene.getSceneChangeHandler().registerCommand(
				new NodeNameChangeCommand(this));

		this.name = name;
	}

	public Scene getScene() {
		return scene;
	}

}
