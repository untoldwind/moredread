package net.untoldwind.moredread.model.scene;

import java.io.IOException;
import java.util.List;

import net.untoldwind.moredread.model.generator.IGeneratorInput;
import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.Mesh;
import net.untoldwind.moredread.model.renderer.INodeRendererAdapter;
import net.untoldwind.moredread.model.scene.change.MeshNodeGeometryChangedCommand;
import net.untoldwind.moredread.model.state.IStateReader;
import net.untoldwind.moredread.model.state.IStateWriter;

import com.jme.scene.Spatial;

public class MeshNode extends ObjectNode implements IMeshNode, IGeneratorInput {
	private Mesh<?, ?> mesh;

	private transient com.jme.scene.Node displayNode;

	private transient List<Spatial> renderedGeometries;
	private transient BoundingBox worldBoundingBox;
	private transient BoundingBox localBoundingBox;

	protected MeshNode(final AbstractSpatialComposite<? extends INode> parent) {
		super(parent, "Mesh");
	}

	public MeshNode(final AbstractSpatialComposite<? extends INode> parent,
			final Mesh<?, ?> mesh) {
		super(parent, "Mesh");

		this.mesh = mesh;
	}

	public MeshNode(final AbstractSpatialComposite<? extends INode> parent,
			final String name, final Mesh<?, ?> mesh) {
		super(parent, name);

		this.mesh = mesh;
	}

	@Override
	public IMesh getRenderGeometry() {
		return mesh;
	}

	@Override
	public IMesh getGeometry() {
		return mesh;
	}

	@Override
	public Mesh<?, ?> getEditableGeometry() {
		scene.getSceneChangeHandler().registerCommand(
				new MeshNodeGeometryChangedCommand(this));

		return mesh;
	}

	public void setMesh(final Mesh<?, ?> mesh) {
		scene.getSceneChangeHandler().registerCommand(
				new MeshNodeGeometryChangedCommand(this));

		this.mesh = mesh;
	}

	@Override
	public BoundingBox getWorldBoundingBox() {
		if (worldBoundingBox == null) {
			worldBoundingBox = new BoundingBox(mesh.getVertices());
			worldBoundingBox = worldBoundingBox.transform(getWorldRotation(),
					getWorldTranslation(), getWorldScale());
		}
		return worldBoundingBox;
	}

	@Override
	public BoundingBox getLocalBoundingBox() {
		if (localBoundingBox == null) {
			localBoundingBox = new BoundingBox(mesh.getVertices());
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
	public <T> T accept(final ISceneVisitor<T> visitor) {
		return visitor.visitMeshNode(this);
	}

	@Override
	public void readState(final IStateReader reader) throws IOException {
		name = reader.readString();
		modelColor = reader.readColor();
		localTranslation = reader.readVector3f();
		localScale = reader.readVector3f();
		localRotation = reader.readQuaternion();
		mesh = reader.readObject();
	}

	@Override
	public void writeState(final IStateWriter writer) throws IOException {
		writer.writeString("name", name);
		writer.writeColor("modelColor", modelColor);
		writer.writeVector3f("localTranslation", localTranslation);
		writer.writeVector3f("localScale", localScale);
		writer.writeQuaternion("localRotation", localRotation);
		writer.writeObject("mesh", mesh);
	}

}
