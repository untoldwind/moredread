package net.untoldwind.moredread.model.scene;

import java.io.IOException;

import net.untoldwind.moredread.model.enums.NodeType;
import net.untoldwind.moredread.model.renderer.INodeRendererAdapter;
import net.untoldwind.moredread.model.state.IStateWriter;

public class Group extends AbstractSpatialComposite<AbstractSpatialNode> {

	private transient com.jme.scene.Node displayNode;
	private transient BoundingBox worldBoundingBox;
	private transient BoundingBox localBoundingBox;
	private transient boolean structuralChange;

	public Group(final Group parent, final String name) {
		super(parent, name);
	}

	@Override
	void addChild(final AbstractSpatialNode node) {
		super.addChild(node);
		structuralChange = true;
	}

	@Override
	void removeChild(final AbstractSpatialNode node) {
		super.removeChild(node);
		structuralChange = true;
	}

	@Override
	public BoundingBox getWorldBoundingBox() {
		if (worldBoundingBox == null) {
			for (final AbstractSpatialNode child : children) {
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
			for (final AbstractSpatialNode child : children) {
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
		super.markDirty();

		worldBoundingBox = null;
		localBoundingBox = null;
	}

	@Override
	public void updateDisplayNode(final INodeRendererAdapter rendererAdapter,
			final com.jme.scene.Node parent, boolean reattach) {
		worldBoundingBox = null;
		localBoundingBox = null;

		if (displayNode == null || reattach) {
			displayNode = new com.jme.scene.Node();

			parent.attachChild(displayNode);
		}

		displayNode.setLocalRotation(localRotation);
		displayNode.setLocalTranslation(localTranslation);
		displayNode.setLocalScale(localScale);

		if (structuralChange) {
			displayNode.detachAllChildren();

			reattach = true;
		}

		for (final AbstractSpatialNode child : children) {
			child.updateDisplayNode(rendererAdapter, displayNode, reattach);
		}
		structuralChange = false;
	}

	@Override
	public <T> T accept(final ISceneVisitor<T> visitor) {
		return visitor.visitGroup(this);
	}

	@Override
	public void writeState(final IStateWriter writer) throws IOException {
		writer.writeInt("nodeType", NodeType.GROUP.getCode());
		writer.writeVector3f("localTranslation", localTranslation);
		writer.writeVector3f("localScale", localScale);
		writer.writeQuaternion("localRotation", localRotation);
		writer.writeCollection("children", children);
	}

}
