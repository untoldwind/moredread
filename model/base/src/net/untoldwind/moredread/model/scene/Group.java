package net.untoldwind.moredread.model.scene;

import net.untoldwind.moredread.model.renderer.INodeRendererAdapter;

public class Group extends
		AbstractSpatialComposite<Group, AbstractSpatialNode<?>> implements
		IComposite {
	private transient com.jme.scene.Node displayNode;
	private transient BoundingBox worldBoundingBox;
	private transient BoundingBox localBoundingBox;

	public Group(final Group parent, final String name) {
		super(parent, name);
	}

	@Override
	public BoundingBox getWorldBoundingBox() {
		if (worldBoundingBox == null) {
			for (final AbstractSpatialNode<?> child : children) {
				final BoundingBox childBoundingBox = child
						.getWorldBoundingBox();
				if (childBoundingBox != null) {
					if (worldBoundingBox == null) {
						worldBoundingBox = new BoundingBox(childBoundingBox);
					} else {
						worldBoundingBox.mergeLocal(childBoundingBox);
					}
				}
			}
			if (worldBoundingBox == null) {
				worldBoundingBox = new BoundingBox();
			}
		}

		return worldBoundingBox;
	}

	@Override
	public BoundingBox getLocalBoundingBox() {
		if (localBoundingBox == null) {
			for (final AbstractSpatialNode<?> child : children) {
				final BoundingBox childBoundingBox = child
						.getLocalBoundingBox();
				if (childBoundingBox != null) {
					childBoundingBox.transform(child.getLocalRotation(), child
							.getLocalTranslation(), child.getLocalScale());
					if (localBoundingBox == null) {
						localBoundingBox = new BoundingBox(childBoundingBox);
					} else {
						localBoundingBox.mergeLocal(childBoundingBox);
					}
				}
			}
			if (localBoundingBox == null) {
				localBoundingBox = new BoundingBox();
			}

		}

		return localBoundingBox;
	}

	@Override
	public void markDirty() {
		worldBoundingBox = null;
		localBoundingBox = null;
	}

	@Override
	public void updateDisplayNode(final INodeRendererAdapter rendererAdapter,
			final com.jme.scene.Node parent) {
		worldBoundingBox = null;
		localBoundingBox = null;

		if (displayNode == null) {
			displayNode = new com.jme.scene.Node();

			parent.attachChild(displayNode);
		}

		displayNode.setLocalRotation(localRotation);
		displayNode.setLocalTranslation(localTranslation);
		displayNode.setLocalScale(localScale);

		for (final AbstractSpatialNode<?> child : children) {
			child.updateDisplayNode(rendererAdapter, displayNode);
		}
	}

	@Override
	public <T> T accept(final ISceneVisitor<T> visitor) {
		return visitor.visitGroup(this);
	}

}
