package net.untoldwind.moredread.model.op.bool;

import net.untoldwind.moredread.model.scene.BoundingBox;

public class BSPTree {
	BSPNode root;
	BSPNode bspBB;
	BoundingBox boundingBox;

	public BSPNode getRoot() {
		return root;
	}

	public void setRoot(final BSPNode root) {
		this.root = root;
	}

}
