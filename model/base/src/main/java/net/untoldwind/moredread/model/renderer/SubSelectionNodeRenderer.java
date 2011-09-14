package net.untoldwind.moredread.model.renderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.untoldwind.moredread.model.enums.SelectionMode;
import net.untoldwind.moredread.model.mesh.EdgeId;
import net.untoldwind.moredread.model.mesh.FaceId;
import net.untoldwind.moredread.model.scene.IMeshNode;
import net.untoldwind.moredread.model.scene.IPolygonNode;
import net.untoldwind.moredread.model.scene.SceneSelection;

import com.jme.bounding.BoundingBox;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Geometry;
import com.jme.scene.Spatial;

public class SubSelectionNodeRenderer implements INodeRendererAdapter {
	private final Renderer renderer;
	private final SelectionMode selectionMode;
	private final WireframeMeshRenderer wireframeMeshRenderer;
	private final VertexMeshRenderer vertexMeshRenderer;
	private final IMeshRendererAdapter solidMeshRenderer;
	private final IPolygonRendererAdapter polygonRendererAdapter;

	public SubSelectionNodeRenderer(final Renderer renderer,
			final SelectionMode selectionMode) {

		this.renderer = renderer;
		this.selectionMode = selectionMode;

		this.wireframeMeshRenderer = new WireframeMeshRenderer(false, 3f, false);
		this.vertexMeshRenderer = new VertexMeshRenderer();
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
	public List<Spatial> renderNode(final IMeshNode node) {
		if (!node.isSelected()) {
			return Collections.emptyList();
		}

		final List<Spatial> geometries = new ArrayList<Spatial>();

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

		if (colorProvider != null) {
			final Geometry solidGeometry = solidMeshRenderer.renderMesh(
					node.getRenderGeometry(), colorProvider);
			if (solidGeometry != null) {
				geometries.add(solidGeometry);
				solidGeometry.setModelBound(new BoundingBox());
				solidGeometry.updateModelBound();
				solidGeometry.setDefaultColor(node.getModelColor(0.5f));
				solidGeometry.setRenderQueueMode(Renderer.QUEUE_TRANSPARENT);
			}
		}

		final Geometry wireframeGeometry = wireframeMeshRenderer.renderMesh(
				node.getRenderGeometry(), colorProvider);
		if (wireframeGeometry != null) {
			geometries.add(wireframeGeometry);
			wireframeGeometry.setDefaultColor(ColorRGBA.black.clone());
			wireframeGeometry.setModelBound(new BoundingBox());
			wireframeGeometry.updateModelBound();
			wireframeGeometry.setRenderQueueMode(Renderer.QUEUE_OPAQUE);
		}

		final Geometry vertexGeometry = vertexMeshRenderer.renderMesh(
				node.getRenderGeometry(), colorProvider);
		if (vertexGeometry != null) {
			geometries.add(vertexGeometry);
			vertexGeometry.setDefaultColor(ColorRGBA.black.clone());
			vertexGeometry.setModelBound(new BoundingBox());
			vertexGeometry.updateModelBound();
			vertexGeometry.setRenderQueueMode(Renderer.QUEUE_OPAQUE);
		}

		return geometries;
	}

	@Override
	public List<Spatial> renderNode(final IPolygonNode node) {
		final List<Spatial> geometries = new ArrayList<Spatial>();

		final Geometry geometry = polygonRendererAdapter.renderPolygon(
				node.getRenderGeometry(), null);
		if (geometry != null) {
			geometries.add(geometry);
			geometry.setDefaultColor(ColorRGBA.gray.clone());
			geometry.setModelBound(new BoundingBox());
			geometry.updateModelBound();
			geometry.setRenderQueueMode(Renderer.QUEUE_OPAQUE);
		}
		return geometries;
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
				return sceneSelection.isFaceSelected(node, faceIndex);
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

	private IColorProvider getEdgeSelectionColors(final IMeshNode node) {
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
				return false;
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

	private IColorProvider getVertexSelectionColors(final IMeshNode node) {
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
				return false;
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
}
