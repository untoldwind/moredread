package net.untoldwind.moredread.model.renderer;

import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.enums.SelectionMode;
import net.untoldwind.moredread.model.mesh.EdgeId;
import net.untoldwind.moredread.model.mesh.FaceId;
import net.untoldwind.moredread.model.scene.IGeometryNode;
import net.untoldwind.moredread.model.scene.IGridNode;
import net.untoldwind.moredread.model.scene.IMeshNode;
import net.untoldwind.moredread.model.scene.IPolygonNode;
import net.untoldwind.moredread.model.scene.SceneSelection;

import com.jme.bounding.BoundingBox;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Geometry;
import com.jme.scene.Spatial;

public class SolidNodeRenderer implements INodeRendererAdapter {
	private final SolidNodeRendererParam parameters;
	private final IMeshRendererAdapter wireframeMeshRenderer;
	private final IMeshRendererAdapter selectedWireframeMeshRenderer;
	private final IMeshRendererAdapter selectedVertexMeshRenderer;
	private final IMeshRendererAdapter solidMeshRenderer;
	private final IPolygonRendererAdapter polygonRenderer;
	private final IGridRendererAdapter gridRenderer;
	private final IPolygonRendererAdapter selectedPolygonRenderer;
	private final IPolygonRendererAdapter selectedVertexPolygonRenderer;
	private final IGridRendererAdapter selectedGridRenderer;
	private final IGridRendererAdapter selectedVertexGridRenderer;
	private final Renderer renderer;
	private final SelectionMode selectionMode;

	public SolidNodeRenderer(final Renderer renderer,
			final SelectionMode selectionMode,
			final SolidNodeRendererParam param) {
		this.parameters = param;
		this.renderer = renderer;
		this.selectionMode = selectionMode;
		this.wireframeMeshRenderer = new WireframeMeshRenderer(false, 1f, true);
		this.selectedWireframeMeshRenderer = new WireframeMeshRenderer(
				param.isShowNormalsOnSelected(), 3f, false);
		this.selectedVertexMeshRenderer = new VertexMeshRenderer();
		this.solidMeshRenderer = new SolidMeshRenderer();
		this.polygonRenderer = new LinePolygonRendererAdapter(1f, true);
		this.selectedPolygonRenderer = new LinePolygonRendererAdapter(3f, false);
		this.selectedVertexPolygonRenderer = new VertexPolygonRendererAdapter();
		this.gridRenderer = new LineGridRendererAdapter(1f, true);
		this.selectedGridRenderer = new LineGridRendererAdapter(3f, false);
		this.selectedVertexGridRenderer = new VertexGridRendererAdapter();
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

			if (node.isSelected()) {
				renderSelected((IMeshNode) node, geometries);
			} else {
				renderNormal((IMeshNode) node, geometries);
			}

			return geometries;
		}
		case POLYGON: {
			final List<Spatial> geometries = new ArrayList<Spatial>();

			if (node.isSelected()) {
				renderSelected((IPolygonNode) node, geometries);
			} else {
				renderNormal((IPolygonNode) node, geometries);
			}

			return geometries;
		}
		case GRID: {
			final List<Spatial> geometries = new ArrayList<Spatial>();

			if (node.isSelected()) {
				renderSelected((IGridNode) node, geometries);
			} else {
				renderNormal((IGridNode) node, geometries);
			}

			return geometries;
		}
		default:
			throw new RuntimeException("No render adapter for: "
					+ node.getGeometryType());
		}
	}

	private void renderSelected(final IPolygonNode node,
			final List<Spatial> geometries) {
		IColorProvider colorProvider = null;
		switch (selectionMode) {
		case EDGE:
			colorProvider = getEdgeSelectionColors(node);
			break;
		case VERTEX:
			colorProvider = getVertexSelectionColors(node);
			break;
		}

		final Geometry geometry = selectedPolygonRenderer.renderPolygon(
				node.getRenderGeometry(), colorProvider);
		if (geometry != null) {
			geometry.setDefaultColor(ColorRGBA.black.clone());
			geometry.setModelBound(new BoundingBox());
			geometry.updateModelBound();
			geometry.setRenderQueueMode(Renderer.QUEUE_OPAQUE);
			geometries.add(geometry);
		}

		final Geometry vertexGeometry = selectedVertexPolygonRenderer
				.renderPolygon(node.getRenderGeometry(), colorProvider);
		if (vertexGeometry != null) {
			geometries.add(vertexGeometry);
			vertexGeometry.setDefaultColor(ColorRGBA.black.clone());
			vertexGeometry.setModelBound(new BoundingBox());
			vertexGeometry.updateModelBound();
			vertexGeometry.setRenderQueueMode(Renderer.QUEUE_OPAQUE);
		}
		if (parameters.isShowBoundingBoxOnSelected()) {
			geometries.add(new BoundingBoxNode(node.getLocalBoundingBox()));
		}

	}

	private void renderSelected(final IGridNode node,
			final List<Spatial> geometries) {
		IColorProvider colorProvider = null;
		switch (selectionMode) {
		case EDGE:
			colorProvider = getEdgeSelectionColors(node);
			break;
		case VERTEX:
			colorProvider = getVertexSelectionColors(node);
			break;
		}

		final Geometry geometry = selectedGridRenderer.renderGrid(
				node.getRenderGeometry(), colorProvider);
		if (geometry != null) {
			geometry.setDefaultColor(ColorRGBA.black.clone());
			geometry.setModelBound(new BoundingBox());
			geometry.updateModelBound();
			geometry.setRenderQueueMode(Renderer.QUEUE_OPAQUE);
			geometries.add(geometry);
		}

		final Geometry vertexGeometry = selectedVertexGridRenderer.renderGrid(
				node.getRenderGeometry(), colorProvider);
		if (vertexGeometry != null) {
			geometries.add(vertexGeometry);
			vertexGeometry.setDefaultColor(ColorRGBA.black.clone());
			vertexGeometry.setModelBound(new BoundingBox());
			vertexGeometry.updateModelBound();
			vertexGeometry.setRenderQueueMode(Renderer.QUEUE_OPAQUE);
		}
		if (parameters.isShowBoundingBoxOnSelected()) {
			geometries.add(new BoundingBoxNode(node.getLocalBoundingBox()));
		}

	}

	private void renderNormal(final IPolygonNode node,
			final List<Spatial> geometries) {
		final Geometry geometry = polygonRenderer.renderPolygon(
				node.getRenderGeometry(), null);
		if (geometry != null) {
			geometry.setDefaultColor(ColorRGBA.black.clone());
			geometry.setModelBound(new BoundingBox());
			geometry.updateModelBound();
			geometry.setRenderQueueMode(Renderer.QUEUE_OPAQUE);
			geometries.add(geometry);
		}

	}

	private void renderNormal(final IGridNode node,
			final List<Spatial> geometries) {
		final Geometry geometry = gridRenderer.renderGrid(
				node.getRenderGeometry(), null);
		if (geometry != null) {
			geometry.setDefaultColor(ColorRGBA.black.clone());
			geometry.setModelBound(new BoundingBox());
			geometry.updateModelBound();
			geometry.setRenderQueueMode(Renderer.QUEUE_OPAQUE);
			geometries.add(geometry);
		}

	}

	private void renderSelected(final IMeshNode node,
			final List<Spatial> geometries) {
		IColorProvider colorProvider = null;

		switch (selectionMode) {
		case FACE:
			colorProvider = getFaceSelectionColors(node);
			break;
		case EDGE:
			colorProvider = getEdgeSelectionColors(node);
			break;
		case VERTEX:
			colorProvider = getVertexSelectionColors(node);
			break;
		}

		final Geometry solidGeometry = solidMeshRenderer.renderMesh(
				node.getRenderGeometry(), colorProvider);
		if (solidGeometry != null) {
			geometries.add(solidGeometry);
			solidGeometry.setModelBound(new BoundingBox());
			solidGeometry.updateModelBound();
			solidGeometry.setDefaultColor(node.getModelColor(0.5f));
			solidGeometry.setRenderQueueMode(Renderer.QUEUE_TRANSPARENT);
		}

		final Geometry wireframeGeometry = selectedWireframeMeshRenderer
				.renderMesh(node.getRenderGeometry(), colorProvider);
		if (wireframeGeometry != null) {
			geometries.add(wireframeGeometry);
			wireframeGeometry.setDefaultColor(ColorRGBA.black.clone());
			wireframeGeometry.setModelBound(new BoundingBox());
			wireframeGeometry.updateModelBound();
			wireframeGeometry.setRenderQueueMode(Renderer.QUEUE_OPAQUE);
		}

		final Geometry vertexGeometry = selectedVertexMeshRenderer.renderMesh(
				node.getRenderGeometry(), colorProvider);
		if (vertexGeometry != null) {
			geometries.add(vertexGeometry);
			vertexGeometry.setDefaultColor(ColorRGBA.black.clone());
			vertexGeometry.setModelBound(new BoundingBox());
			vertexGeometry.updateModelBound();
			vertexGeometry.setRenderQueueMode(Renderer.QUEUE_OPAQUE);
		}

		if (parameters.isShowBoundingBoxOnSelected()) {
			geometries.add(new BoundingBoxNode(node.getLocalBoundingBox()));
		}
	}

	private IColorProvider getFaceSelectionColors(final IMeshNode node) {
		final ColorRGBA modelColor = node.getModelColor(0.5f);
		final ColorRGBA selectedColor = new ColorRGBA(1.0f - modelColor.r,
				1.0f - modelColor.g, 1.0f - modelColor.b, 0.5f);
		final SceneSelection sceneSelection = node.getScene()
				.getSceneSelection();

		return new IColorProvider() {
			@Override
			public ColorRGBA getFaceColor(final FaceId faceIndex) {
				return sceneSelection.isFaceSelected(node, faceIndex) ? selectedColor
						: modelColor;
			}

			@Override
			public ColorRGBA getEdgeColor(final EdgeId edgeIndex) {
				return ColorRGBA.black.clone();
			}

			@Override
			public ColorRGBA getVertexColor(final int vertexIndex) {
				return ColorRGBA.black.clone();
			}

			@Override
			public boolean isFaceVisible(final FaceId faceIndex) {
				return true;
			}

			@Override
			public boolean isEdgeVisible(final EdgeId edgeIndex) {
				return true;
			}

			@Override
			public boolean isVertexVisible(final int vertexIndex) {
				return true;
			}

		};
	}

	private IColorProvider getEdgeSelectionColors(final IGeometryNode<?, ?> node) {
		final ColorRGBA modelColor = node.getModelColor(0.5f);
		final ColorRGBA selectedColor = new ColorRGBA(1.0f - modelColor.r,
				1.0f - modelColor.g, 1.0f - modelColor.b, 0.5f);

		final SceneSelection sceneSelection = node.getScene()
				.getSceneSelection();
		return new IColorProvider() {
			@Override
			public ColorRGBA getFaceColor(final FaceId faceIndex) {
				return modelColor;
			}

			@Override
			public ColorRGBA getEdgeColor(final EdgeId edgeIndex) {
				return sceneSelection.isEdgeSelected(node, edgeIndex) ? selectedColor
						: ColorRGBA.black.clone();
			}

			@Override
			public ColorRGBA getVertexColor(final int vertexIndex) {
				return ColorRGBA.black.clone();
			}

			@Override
			public boolean isFaceVisible(final FaceId faceIndex) {
				return true;
			}

			@Override
			public boolean isEdgeVisible(final EdgeId edgeIndex) {
				return true;
			}

			@Override
			public boolean isVertexVisible(final int vertexIndex) {
				return true;
			}
		};
	}

	private IColorProvider getVertexSelectionColors(
			final IGeometryNode<?, ?> node) {
		final ColorRGBA modelColor = node.getModelColor(0.5f);
		final ColorRGBA selectedColor = new ColorRGBA(1.0f - modelColor.r,
				1.0f - modelColor.g, 1.0f - modelColor.b, 0.5f);

		final SceneSelection sceneSelection = node.getScene()
				.getSceneSelection();

		return new IColorProvider() {
			@Override
			public ColorRGBA getFaceColor(final FaceId faceIndex) {
				return modelColor;
			}

			@Override
			public ColorRGBA getEdgeColor(final EdgeId edgeIndex) {
				return ColorRGBA.black.clone();
			}

			@Override
			public ColorRGBA getVertexColor(final int vertexIndex) {
				return sceneSelection.isVertexSelected(node, vertexIndex) ? selectedColor
						: ColorRGBA.black.clone();
			}

			@Override
			public boolean isFaceVisible(final FaceId faceIndex) {
				return true;
			}

			@Override
			public boolean isEdgeVisible(final EdgeId edgeIndex) {
				return true;
			}

			@Override
			public boolean isVertexVisible(final int vertexIndex) {
				return true;
			}

		};
	}

	private void renderNormal(final IMeshNode node,
			final List<Spatial> geometries) {
		final Geometry solidGeometry = solidMeshRenderer.renderMesh(
				node.getRenderGeometry(), null);
		if (solidGeometry != null) {
			geometries.add(solidGeometry);
			solidGeometry.setDefaultColor(node.getModelColor(1.0f));
			solidGeometry.setModelBound(new BoundingBox());
			solidGeometry.updateModelBound();
			solidGeometry.setRenderQueueMode(Renderer.QUEUE_OPAQUE);
		}

		final Geometry wireframeGeometry = wireframeMeshRenderer.renderMesh(
				node.getRenderGeometry(), null);
		if (wireframeGeometry != null) {
			geometries.add(wireframeGeometry);
			wireframeGeometry.setDefaultColor(ColorRGBA.black.clone());
			wireframeGeometry.setModelBound(new BoundingBox());
			wireframeGeometry.updateModelBound();
			wireframeGeometry.setRenderQueueMode(Renderer.QUEUE_OPAQUE);
		}
	}
}
