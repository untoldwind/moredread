package net.untoldwind.moredread.model.scene;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.EnumSet;
import java.util.List;

import net.untoldwind.moredread.model.enums.GeometryType;
import net.untoldwind.moredread.model.enums.SelectionMode;
import net.untoldwind.moredread.model.generator.AbstractGeometryGenerator;
import net.untoldwind.moredread.model.generator.IGeneratorInput;
import net.untoldwind.moredread.model.generator.IGeometryGenerator;
import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.IVertexGeometry;
import net.untoldwind.moredread.model.mesh.Mesh;
import net.untoldwind.moredread.model.mesh.VertexGeometry;
import net.untoldwind.moredread.model.renderer.GhostNodeRenderer;
import net.untoldwind.moredread.model.renderer.INodeRendererAdapter;
import net.untoldwind.moredread.model.renderer.SubSelectionNodeRenderer;
import net.untoldwind.moredread.model.scene.change.GeneratorNodeChangeCommand;
import net.untoldwind.moredread.model.state.IStateReader;
import net.untoldwind.moredread.model.state.IStateWriter;

import com.jme.renderer.ColorRGBA;
import com.jme.scene.Spatial;

public class GeneratorNode extends AbstractSpatialComposite<IGeneratorInput>
		implements IVertexGeometryNode<IVertexGeometry<?>, VertexGeometry<?>>,
		IGeneratorInput {
	private AbstractGeometryGenerator<? extends IVertexGeometry<?>> generator;

	private transient com.jme.scene.Node displayNode;

	private transient IVertexGeometry<?> generatedGeometry;
	private transient List<Spatial> renderedGeometries;
	private transient volatile BoundingBox worldBoundingBox;
	private transient volatile BoundingBox localBoundingBox;
	private ColorRGBA modelColor;

	protected GeneratorNode(
			final AbstractSpatialComposite<? extends INode> parent) {
		super(parent, "Generator");
	}

	public GeneratorNode(
			final AbstractSpatialComposite<? extends INode> parent,
			final AbstractGeometryGenerator<? extends IVertexGeometry<?>> generator) {
		super(parent, generator.getName());

		this.generator = generator;
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
	public IVertexGeometry<?> getRenderGeometry() {
		if (generatedGeometry == null) {
			regenerate();
		}

		return generatedGeometry;
	}

	@Override
	public IMesh getGeometry() {
		return null;
	}

	@Override
	public Mesh<?, ?> getEditableGeometry() {
		return null;
	}

	@Override
	public void setGeometry(final VertexGeometry<?> geometry) {
	}

	@Override
	public GeometryType getGeometryType() {
		return generator.getGeometryType();
	}

	public IGeometryGenerator<? extends IVertexGeometry<?>> getGenerator() {
		return generator;
	}

	public void setGenerator(
			final AbstractGeometryGenerator<? extends IVertexGeometry<?>> generator) {
		scene.getSceneChangeHandler().registerCommand(
				new GeneratorNodeChangeCommand(this));

		if (this.generator != null) {
			this.generator.setGeneratorNode(null);
		}

		this.generator = generator;

		if (this.generator != null) {
			this.generator.setGeneratorNode(this);
		}
	}

	@Override
	public BoundingBox getWorldBoundingBox() {
		if (worldBoundingBox == null) {
			final BoundingBox newWorldBoundingBox = new BoundingBox(
					getRenderGeometry().getVertices());
			worldBoundingBox = newWorldBoundingBox.transform(
					getWorldRotation(), getWorldTranslation(), getWorldScale());
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
		generatedGeometry = null;
	}

	@Override
	public EnumSet<SelectionMode> getSupportedSelectionModes() {
		return EnumSet.of(SelectionMode.OBJECT);
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
		generatedGeometry = generator.generateGeometry(children);
		renderedGeometries = null;
	}

	@Override
	public <T> T accept(final ISceneVisitor<T> visitor) {
		return visitor.visitGeneratorNode(this);
	}

	@Override
	public void readState(final IStateReader reader) throws IOException {
		name = reader.readString();
		modelColor = reader.readColor();
		localTranslation = reader.readVector3();
		localScale = reader.readVector3();
		localRotation = reader.readQuaternion();
		generator = reader.readObject();
		reader.readUntypedList(new IStateReader.IInstanceCreator<INode>() {
			@Override
			public INode createInstance(final Class<INode> clazz) {
				try {
					final Constructor<INode> constructor = clazz
							.getDeclaredConstructor(AbstractSpatialComposite.class);

					constructor.setAccessible(true);
					return constructor.newInstance(GeneratorNode.this);
				} catch (final Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
	}

	@Override
	public void writeState(final IStateWriter writer) throws IOException {
		writer.writeString("name", name);
		writer.writeColor("modelColor", modelColor);
		writer.writeVector3("localTranslation", localTranslation);
		writer.writeVector3("localScale", localScale);
		writer.writeQuaternion("localRotation", localRotation);
		writer.writeObject("meshGenerator", generator);
		writer.writeUntypedList("children", children);
	}
}
