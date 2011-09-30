package net.untoldwind.moredread.model.scene;

import java.util.EnumSet;

import net.untoldwind.moredread.model.enums.GeometryType;
import net.untoldwind.moredread.model.enums.SelectionMode;
import net.untoldwind.moredread.model.mesh.IPolygon;
import net.untoldwind.moredread.model.mesh.Polygon;
import net.untoldwind.moredread.model.renderer.INodeRendererAdapter;

import com.jme.scene.Node;
import com.jme.scene.Spatial;

public class PolygonNode extends GeometryNode<IPolygon, Polygon> implements
		IPolygonNode {
	protected PolygonNode(final AbstractSpatialComposite<? extends INode> parent) {
		super(parent, "Polygon");
	}

	public PolygonNode(final AbstractSpatialComposite<? extends INode> parent,
			final Polygon polygon) {
		super(parent, "Polygon", polygon);
	}

	public PolygonNode(final AbstractSpatialComposite<? extends INode> parent,
			final String name, final Polygon polygon) {
		super(parent, name, polygon);
	}

	@Override
	public GeometryType getGeometryType() {
		return GeometryType.POLYGON;
	}

	@Override
	public <T> T accept(final ISceneVisitor<T> visitor) {
		return visitor.visitPolygonNode(this);
	}

	@Override
	public EnumSet<SelectionMode> getSupportedSelectionModes() {
		return EnumSet.of(SelectionMode.OBJECT, SelectionMode.EDGE,
				SelectionMode.VERTEX);
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
}
