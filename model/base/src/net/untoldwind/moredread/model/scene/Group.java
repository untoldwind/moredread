package net.untoldwind.moredread.model.scene;

import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.renderer.INodeRendererAdapter;

public class Group extends SpatialNode implements IComposite {
	private final List<SpatialNode> children = new ArrayList<SpatialNode>();

	private transient com.jme.scene.Node displayNode;
	private transient BoundingBox boundingBox;

	public Group(final Group parent, final String name) {
		super(parent, name);
	}

	void addNode(final SpatialNode node) {
		children.add(node);
	}

	public List<? extends INode> getChildren() {
		return children;
	}

	@Override
	public BoundingBox getWorldBoundingBox() {
		if (boundingBox == null) {
			for (final SpatialNode child : children) {
				final BoundingBox childBoundingBox = child
						.getWorldBoundingBox();
				if (childBoundingBox != null) {
					if (boundingBox == null) {
						boundingBox = new BoundingBox(childBoundingBox);
					} else {
						boundingBox.mergeLocal(childBoundingBox);
					}
				}
			}
			if (boundingBox == null) {
				boundingBox = new BoundingBox();
			} else {
				boundingBox = boundingBox.transform(getWorldRotation(),
						getWorldTranslation(), getWorldScale());
			}

		}

		return boundingBox;
	}

	@Override
	public void markDirty() {
		boundingBox = null;
	}

	@Override
	public void updateDisplayNode(final INodeRendererAdapter rendererAdapter,
			final com.jme.scene.Node parent) {
		boundingBox = null;

		if (displayNode == null) {
			displayNode = new com.jme.scene.Node();

			parent.attachChild(displayNode);
		}

		displayNode.setLocalRotation(localRotation);
		displayNode.setLocalTranslation(localTranslation);
		displayNode.setLocalScale(localScale);

		for (final SpatialNode child : children) {
			child.updateDisplayNode(rendererAdapter, displayNode);
		}
	}

	@Override
	public <T> T accept(final ISceneVisitor<T> visitor) {
		return visitor.visitGroup(this);
	}

}
