package net.untoldwind.moredread.model.scene;

import java.io.IOException;
import java.util.List;

import net.untoldwind.moredread.model.generator.IGeneratorInput;
import net.untoldwind.moredread.model.generator.IMeshGenerator;
import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.Mesh;
import net.untoldwind.moredread.model.renderer.GhostNodeRenderer;
import net.untoldwind.moredread.model.renderer.INodeRendererAdapter;
import net.untoldwind.moredread.model.renderer.SubSelectionNodeRenderer;
import net.untoldwind.moredread.model.state.IStateReader;
import net.untoldwind.moredread.model.state.IStateWriter;

import com.jme.renderer.ColorRGBA;
import com.jme.scene.Spatial;

public class GeneratorNode extends AbstractSpatialComposite<IGeneratorInput>
		implements IMeshNode, IGeneratorInput {
	private IMeshGenerator meshGenerator;

	private transient com.jme.scene.Node displayNode;

	private transient IMesh generatedMesh;
	private transient List<Spatial> renderedGeometries;
	private transient BoundingBox worldBoundingBox;
	private transient BoundingBox localBoundingBox;
	private ColorRGBA modelColor;

	protected GeneratorNode(
			final AbstractSpatialComposite<? extends INode> parent) {
		super(parent, "Generator");
	}

	public GeneratorNode(
			final AbstractSpatialComposite<? extends INode> parent,
			final IMeshGenerator meshGenerator) {
		super(parent, meshGenerator.getName());

		this.meshGenerator = meshGenerator;
		this.modelColor = ColorRGBA.red.clone();
	}

	public ColorRGBA getModelColor() {
		return modelColor;
	}

	public ColorRGBA getModelColor(final float alpha) {
		return new ColorRGBA(modelColor.r, modelColor.g, modelColor.b, alpha);
	}

	public void setModelColor(final ColorRGBA modelColor) {
		this.modelColor = modelColor;
	}

	@Override
	public IMesh getRenderGeometry() {
		if (generatedMesh == null) {
			regenerate();
		}

		return generatedMesh;
	}

	@Override
	public IMesh getGeometry() {
		return null;
	}

	@Override
	public Mesh<?> getEditableGeometry() {
		return null;
	}

	@Override
	public BoundingBox getWorldBoundingBox() {
		if (worldBoundingBox == null) {
			worldBoundingBox = new BoundingBox(getRenderGeometry()
					.getVertices());
			worldBoundingBox = worldBoundingBox.transform(getWorldRotation(),
					getWorldTranslation(), getWorldScale());
		}
		return worldBoundingBox;
	}

	@Override
	public BoundingBox getLocalBoundingBox() {
		if (localBoundingBox == null) {
			localBoundingBox = new BoundingBox(getRenderGeometry()
					.getVertices());
		}
		return localBoundingBox;
	}

	@Override
	public void markDirty() {
		super.markDirty();

		worldBoundingBox = null;
		localBoundingBox = null;
		renderedGeometries = null;

		// TODO: Don't just throw existing mesh away, rather queue a regenerate
		// somewhere (Job). This needs some intelligence for cascading generator
		// nodes
		generatedMesh = null;
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
			final boolean isChildNodeSelected = getScene().getSceneSelection()
					.isChildNodeSelected(this);

			if (isChildNodeSelected) {
				final GhostNodeRenderer ghostNodeRenderer = new GhostNodeRenderer(
						rendererAdapter.getRenderer(),
						rendererAdapter.getSelectionMode());
				renderedGeometries = ghostNodeRenderer.renderNode(this);
			} else {
				renderedGeometries = rendererAdapter.renderNode(this);
			}

			displayNode.detachAllChildren();

			if (renderedGeometries.size() > 0) {
				renderedGeometries.get(0).setUserData(
						ISceneHolder.NODE_USERDATA_KEY, nodeRef);
			}

			for (final Spatial geometry : renderedGeometries) {
				displayNode.attachChild(geometry);
			}

			// Iterate childs only in case of a selection
			if (isChildNodeSelected) {
				final SubSelectionNodeRenderer subRendererAdapter = new SubSelectionNodeRenderer(
						rendererAdapter.getRenderer(),
						rendererAdapter.getSelectionMode());

				for (final IGeneratorInput child : children) {
					if (child instanceof AbstractSpatialNode) {
						((AbstractSpatialNode) child).updateDisplayNode(
								subRendererAdapter, displayNode, true);
					}
				}
			}
		}
	}

	public void regenerate() {
		generatedMesh = meshGenerator.generateMesh(children);
		renderedGeometries = null;
	}

	@Override
	public <T> T accept(final ISceneVisitor<T> visitor) {
		return visitor.visitGeneratorNode(this);
	}

	@Override
	public void readState(final IStateReader reader) throws IOException {
		name = reader.readString();
		localTranslation = reader.readVector3f();
		localScale = reader.readVector3f();
		localRotation = reader.readQuaternion();
		meshGenerator = reader.readObject();
	}

	@Override
	public void writeState(final IStateWriter writer) throws IOException {
		writer.writeString("name", name);
		writer.writeVector3f("localTranslation", localTranslation);
		writer.writeVector3f("localScale", localScale);
		writer.writeQuaternion("localRotation", localRotation);
		writer.writeObject("meshGenerator", meshGenerator);
	}
}
