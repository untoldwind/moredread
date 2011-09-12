package net.untoldwind.moredread.model.scene;

import java.io.IOException;
import java.util.List;

import net.untoldwind.moredread.model.mesh.IPolygon;
import net.untoldwind.moredread.model.mesh.Polygon;
import net.untoldwind.moredread.model.renderer.INodeRendererAdapter;
import net.untoldwind.moredread.model.state.IStateReader;
import net.untoldwind.moredread.model.state.IStateWriter;

import com.jme.scene.Node;
import com.jme.scene.Spatial;

public class PolygonNode extends ObjectNode implements IPolygonNode {
	private Polygon polygon;

	private transient com.jme.scene.Node displayNode;

	private transient List<Spatial> renderedGeometries;
	private transient BoundingBox worldBoundingBox;
	private transient BoundingBox localBoundingBox;

	protected PolygonNode(final AbstractSpatialComposite<? extends INode> parent) {
		super(parent, "Polygon");
	}

	public PolygonNode(final AbstractSpatialComposite<? extends INode> parent,
			final Polygon polygon) {
		super(parent, "Mesh");

		this.polygon = polygon;
	}

	public PolygonNode(final AbstractSpatialComposite<? extends INode> parent,
			final String name, final Polygon polygon) {
		super(parent, name);

		this.polygon = polygon;
	}

	@Override
	public IPolygon getRenderGeometry() {
		return polygon;
	}

	@Override
	public IPolygon getGeometry() {
		return polygon;
	}

	@Override
	public Polygon getEditableGeometry() {
		return polygon;
	}

	@Override
	public BoundingBox getLocalBoundingBox() {
		if (localBoundingBox == null) {
			localBoundingBox = new BoundingBox(polygon.getVertices());
		}
		return localBoundingBox;
	}

	@Override
	public BoundingBox getWorldBoundingBox() {
		if (worldBoundingBox == null) {
			worldBoundingBox = new BoundingBox(polygon.getVertices());
			worldBoundingBox = worldBoundingBox.transform(getWorldRotation(),
					getWorldTranslation(), getWorldScale());
		}
		return worldBoundingBox;
	}

	@Override
	public <T> T accept(final ISceneVisitor<T> visitor) {
		return visitor.visitPolygonNode(this);
	}

	@Override
	public void updateDisplayNode(final INodeRendererAdapter rendererAdapter,
			final Node parent, final boolean reattach) {
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
		localTranslation = reader.readVector3f();
		localScale = reader.readVector3f();
		localRotation = reader.readQuaternion();
		polygon = reader.readObject();
	}

	@Override
	public void writeState(final IStateWriter writer) throws IOException {
		writer.writeString("name", name);
		writer.writeColor("modelColor", modelColor);
		writer.writeVector3f("localTranslation", localTranslation);
		writer.writeVector3f("localScale", localScale);
		writer.writeQuaternion("localRotation", localRotation);
		writer.writeObject("polygon", polygon);
	}

}
