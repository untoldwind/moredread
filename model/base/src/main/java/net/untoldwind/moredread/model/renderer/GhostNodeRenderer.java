package net.untoldwind.moredread.model.renderer;

import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.enums.SelectionMode;
import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.IPolygon;
import net.untoldwind.moredread.model.scene.IGeometryNode;

import com.jme.bounding.BoundingBox;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Geometry;
import com.jme.scene.Spatial;

public class GhostNodeRenderer implements INodeRendererAdapter {
	private final Renderer renderer;
	private final SelectionMode selectionMode;
	private final WireframeMeshRenderer wireframeMeshRenderer;
	private final IMeshRendererAdapter solidMeshRenderer;
	private final IPolygonRendererAdapter polygonRendererAdapter;

	public GhostNodeRenderer(final Renderer renderer,
			final SelectionMode selectionMode) {

		this.renderer = renderer;
		this.selectionMode = selectionMode;

		this.wireframeMeshRenderer = new WireframeMeshRenderer(false, 1f, false);
		this.solidMeshRenderer = new SolidMeshRenderer();
		this.polygonRendererAdapter = new LinePolygonRendererAdapter(1f, false);
	}

	@Override
	public Renderer getRenderer() {
		return renderer;
	}

	@Override
	public SelectionMode getSelectionMode() {
		return selectionMode;
	}

	@Override
	public List<Spatial> renderNode(final IGeometryNode<?, ?> node) {
		switch (node.getGeometryType()) {
		case MESH: {
			final List<Spatial> geometries = new ArrayList<Spatial>();

			final Geometry solidGeometry = solidMeshRenderer.renderMesh(
					(IMesh) node.getRenderGeometry(), null);
			if (solidGeometry != null) {
				geometries.add(solidGeometry);
				solidGeometry.setModelBound(new BoundingBox());
				solidGeometry.updateModelBound();
				solidGeometry.setDefaultColor(node.getModelColor(0.4f));
				solidGeometry.setRenderQueueMode(Renderer.QUEUE_TRANSPARENT);
			}

			final Geometry wireframeGeometry = wireframeMeshRenderer
					.renderMesh((IMesh) node.getRenderGeometry(), null);
			if (wireframeGeometry != null) {
				geometries.add(wireframeGeometry);
				wireframeGeometry.setDefaultColor(ColorRGBA.gray.clone());
				wireframeGeometry.setModelBound(new BoundingBox());
				wireframeGeometry.updateModelBound();
				wireframeGeometry.setRenderQueueMode(Renderer.QUEUE_OPAQUE);
			}

			return geometries;
		}
		case POLYGON: {
			final List<Spatial> geometries = new ArrayList<Spatial>();

			final Geometry geometry = polygonRendererAdapter.renderPolygon(
					(IPolygon) node.getRenderGeometry(), null);
			if (geometry != null) {
				geometries.add(geometry);
				geometry.setDefaultColor(ColorRGBA.gray.clone());
				geometry.setModelBound(new BoundingBox());
				geometry.updateModelBound();
				geometry.setRenderQueueMode(Renderer.QUEUE_OPAQUE);
			}
			return geometries;
		}
		default:
			throw new RuntimeException("No render adapter for: "
					+ node.getGeometryType());
		}
	}
}
