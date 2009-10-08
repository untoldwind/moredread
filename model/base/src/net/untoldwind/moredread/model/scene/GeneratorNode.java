package net.untoldwind.moredread.model.scene;

import java.util.List;

import net.untoldwind.moredread.model.generator.IMeshGenerator;
import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.Mesh;
import net.untoldwind.moredread.model.renderer.INodeRendererAdapter;

import com.jme.scene.Geometry;

public class GeneratorNode extends ObjectNode {
	private final IMeshGenerator meshGenerator;

	private transient com.jme.scene.Node displayNode;

	private transient Mesh<?> generatedMesh;
	private transient List<Geometry> renderedGeometries;
	private transient BoundingBox boundingBox;

	public GeneratorNode(final Group parent, final IMeshGenerator meshGenerator) {
		super(parent, meshGenerator.getName());

		this.meshGenerator = meshGenerator;
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
		if (boundingBox == null) {
			boundingBox = new BoundingBox(getRenderGeometry().getVertices());
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

	public void regenerate() {
		generatedMesh = meshGenerator.generateMesh();
		renderedGeometries = null;
	}

	@Override
	public <T> T accept(final ISceneVisitor<T> visitor) {
		return visitor.visitGeneratorNode(this);
	}
}
