package net.untoldwind.moredread.model.scene;

import java.util.List;

import net.untoldwind.moredread.model.generator.IMeshGenerator;
import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.Mesh;
import net.untoldwind.moredread.model.renderer.INodeRendererAdapter;

import com.jme.scene.Spatial;

public class GeneratorNode extends ObjectNode {
	private final IMeshGenerator meshGenerator;

	private transient com.jme.scene.Node displayNode;

	private transient Mesh<?> generatedMesh;
	private transient List<Spatial> renderedGeometries;
	private transient BoundingBox worldBoundingBox;
	private transient BoundingBox localBoundingBox;

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
		worldBoundingBox = null;
		localBoundingBox = null;
		renderedGeometries = null;
	}

	@Override
	public void updateDisplayNode(final INodeRendererAdapter rendererAdapter,
			final com.jme.scene.Node parent) {
		worldBoundingBox = null;
		localBoundingBox = null;

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

			for (final Spatial geometry : renderedGeometries) {
				displayNode.attachChild(geometry);
			}
		}
	}

	public void regenerate() {
		generatedMesh = meshGenerator.generateMesh(null);
		renderedGeometries = null;
	}

	@Override
	public <T> T accept(final ISceneVisitor<T> visitor) {
		return visitor.visitGeneratorNode(this);
	}
}
