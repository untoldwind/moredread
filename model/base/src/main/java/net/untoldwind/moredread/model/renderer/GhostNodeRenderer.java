package net.untoldwind.moredread.model.renderer;

import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.enums.SelectionMode;
import net.untoldwind.moredread.model.scene.IMeshNode;

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

	public GhostNodeRenderer(final Renderer renderer,
			final SelectionMode selectionMode) {

		this.renderer = renderer;
		this.selectionMode = selectionMode;

		this.wireframeMeshRenderer = new WireframeMeshRenderer(false, 1f, false);
		this.solidMeshRenderer = new SolidMeshRenderer();
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
	public List<Spatial> renderNode(final IMeshNode node) {
		final List<Spatial> geometries = new ArrayList<Spatial>();

		final Geometry solidGeometry = solidMeshRenderer.renderMesh(
				node.getRenderGeometry(), null);
		if (solidGeometry != null) {
			geometries.add(solidGeometry);
			solidGeometry.setModelBound(new BoundingBox());
			solidGeometry.updateModelBound();
			solidGeometry.setDefaultColor(node.getModelColor(0.4f));
			solidGeometry.setRenderQueueMode(Renderer.QUEUE_TRANSPARENT);
		}

		final Geometry wireframeGeometry = wireframeMeshRenderer.renderMesh(
				node.getRenderGeometry(), null);
		if (wireframeGeometry != null) {
			geometries.add(wireframeGeometry);
			wireframeGeometry.setDefaultColor(ColorRGBA.gray.clone());
			wireframeGeometry.setModelBound(new BoundingBox());
			wireframeGeometry.updateModelBound();
			wireframeGeometry.setRenderQueueMode(Renderer.QUEUE_OPAQUE);
		}

		return geometries;
	}

}
