package net.untoldwind.moredread.model.scene;

import java.util.List;

import net.untoldwind.moredread.model.mesh.Mesh;
import net.untoldwind.moredread.model.renderer.INodeRendererAdapter;
import net.untoldwind.moredread.model.scene.change.MeshNodeGeometryChangedCommand;

import com.jme.scene.Geometry;

public class MeshNode extends ObjectNode {
	private Mesh<?> mesh;

	private transient com.jme.scene.Node displayNode;

	private transient List<Geometry> renderedGeometries;
	private transient BoundingBox boundingBox;

	public MeshNode(final Group parent, final Mesh<?> mesh) {
		super(parent, "Mesh");

		this.mesh = mesh;
	}

	@Override
	public Mesh<?> getRenderGeometry() {
		return mesh;
	}

	@Override
	public Mesh<?> getEditableGeometry(final boolean forChange) {
		if (forChange) {
			scene.getSceneChangeHandler().registerCommand(
					new MeshNodeGeometryChangedCommand(this, mesh));
		}
		return mesh;
	}

	public void setMesh(final Mesh<?> mesh) {
		this.mesh = mesh;
	}

	@Override
	public BoundingBox getWorldBoundingBox() {
		if (boundingBox == null) {
			boundingBox = new BoundingBox(mesh.getVertices());
			boundingBox = boundingBox.transform(getWorldRotation(),
					getWorldTranslation(), getWorldScale());
		}
		return boundingBox;
	}

	@Override
	public void markDirty() {
		boundingBox = null;
		renderedGeometries = null;
	}

	@Override
	public void updateDisplayNode(final INodeRendererAdapter rendererAdapter,
			final com.jme.scene.Node parent) {
		boundingBox = null;

		final SpatialNodeReference nodeRef = new SpatialNodeReference(this);

		if (displayNode == null) {
			displayNode = new com.jme.scene.Node();

			parent.attachChild(displayNode);
		}

		displayNode.setUserData(ISceneHolder.NODE_USERDATA_KEY, nodeRef);

		displayNode.setName(name);
		displayNode.setLocalRotation(localRotation);
		displayNode.setLocalTranslation(localTranslation);
		displayNode.setLocalScale(localScale);

		if (renderedGeometries == null) {
			renderedGeometries = rendererAdapter.renderNode(this);

			displayNode.detachAllChildren();

			renderedGeometries.get(0).setUserData(
					ISceneHolder.NODE_USERDATA_KEY, nodeRef);

			for (final Geometry geometry : renderedGeometries) {
				displayNode.attachChild(geometry);
			}
		}
	}
}
