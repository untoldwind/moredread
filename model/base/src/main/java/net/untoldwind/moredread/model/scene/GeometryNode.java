package net.untoldwind.moredread.model.scene;

import java.io.IOException;
import java.util.List;

import net.untoldwind.moredread.model.mesh.IVertexGeometry;
import net.untoldwind.moredread.model.renderer.INodeRendererAdapter;
import net.untoldwind.moredread.model.scene.change.NodeGeometryChangedCommand;
import net.untoldwind.moredread.model.state.IStateReader;
import net.untoldwind.moredread.model.state.IStateWriter;

import com.jme.scene.Spatial;

public abstract class GeometryNode<RO_GEOMETRY extends IVertexGeometry<?>, RW_GEOMETRY extends IVertexGeometry<?>>
		extends ObjectNode implements IGeometryNode<RO_GEOMETRY, RW_GEOMETRY> {
	protected RW_GEOMETRY geometry;

	protected transient volatile BoundingBox worldBoundingBox;
	protected transient volatile BoundingBox localBoundingBox;
	protected transient List<Spatial> renderedGeometries;

	protected transient com.jme.scene.Node displayNode;

	protected GeometryNode(
			final AbstractSpatialComposite<? extends INode> parent,
			final String name) {
		super(parent, name);
	}

	protected GeometryNode(
			final AbstractSpatialComposite<? extends INode> parent,
			final String name, final RW_GEOMETRY geometry) {
		super(parent, name);

		this.geometry = geometry;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RO_GEOMETRY getRenderGeometry() {
		return (RO_GEOMETRY) geometry;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RO_GEOMETRY getGeometry() {
		return (RO_GEOMETRY) geometry;
	}

	@Override
	public RW_GEOMETRY getEditableGeometry() {
		scene.getSceneChangeHandler().registerCommand(
				new NodeGeometryChangedCommand(this));

		return geometry;
	}

	@Override
	public void setGeometry(final RW_GEOMETRY geometry) {
		scene.getSceneChangeHandler().registerCommand(
				new NodeGeometryChangedCommand(this));

		this.geometry = geometry;
	}

	@Override
	public BoundingBox getWorldBoundingBox() {
		if (worldBoundingBox == null) {
			final BoundingBox newWorldBoundingBox = new BoundingBox(
					geometry.getVertices());
			worldBoundingBox = newWorldBoundingBox.transform(
					getWorldRotation(), getWorldTranslation(), getWorldScale());
		}
		return worldBoundingBox;
	}

	@Override
	public BoundingBox getLocalBoundingBox() {
		if (localBoundingBox == null) {
			localBoundingBox = new BoundingBox(geometry.getVertices());
		}
		return localBoundingBox;
	}

	@Override
	public void markDirty() {
		super.markDirty();

		worldBoundingBox = null;
		localBoundingBox = null;
		renderedGeometries = null;
	}

	@Override
	public void updateDisplayNode(final INodeRendererAdapter rendererAdapter,
			final com.jme.scene.Node parent, final boolean reattach) {
		worldBoundingBox = null;
		localBoundingBox = null;

		final SpatialNodeReference nodeRef = new SpatialNodeReference(this);

		if (displayNode == null || reattach) {
			displayNode = new com.jme.scene.Node();
			renderedGeometries = null;

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

			if (renderedGeometries.size() > 0) {
				renderedGeometries.get(0).setUserData(
						ISceneHolder.NODE_USERDATA_KEY, nodeRef);
			}

			for (final Spatial geometry : renderedGeometries) {
				displayNode.attachChild(geometry);
			}
		}
	}

	@Override
	public void readState(final IStateReader reader) throws IOException {
		name = reader.readString();
		modelColor = reader.readColor();
		localTranslation = reader.readVector3();
		localScale = reader.readVector3();
		localRotation = reader.readQuaternion();
		geometry = reader.readObject();
	}

	@Override
	public void writeState(final IStateWriter writer) throws IOException {
		writer.writeString("name", name);
		writer.writeColor("modelColor", modelColor);
		writer.writeVector3("localTranslation", localTranslation);
		writer.writeVector3("localScale", localScale);
		writer.writeQuaternion("localRotation", localRotation);
		writer.writeObject("geometry", geometry);
	}

}
