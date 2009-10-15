package net.untoldwind.moredread.model.renderer;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.untoldwind.moredread.model.enums.SelectionMode;
import net.untoldwind.moredread.model.mesh.EdgeId;
import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.IVertex;
import net.untoldwind.moredread.model.scene.IMeshNode;
import net.untoldwind.moredread.model.scene.SceneSelection;

import com.jme.bounding.BoundingBox;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Geometry;
import com.jme.scene.Point;
import com.jme.scene.Spatial;
import com.jme.scene.state.MaterialState;
import com.jme.scene.state.MaterialState.MaterialFace;
import com.jme.util.geom.BufferUtils;

public class SubSelectionNodeRendererAdapter implements INodeRendererAdapter {
	private final Renderer renderer;
	private final SelectionMode selectionMode;
	private final WireframeMeshRenderer wireframeMeshRenderer;

	public SubSelectionNodeRendererAdapter(final Renderer renderer,
			final SelectionMode selectionMode) {

		this.renderer = renderer;
		this.selectionMode = selectionMode;

		this.wireframeMeshRenderer = new WireframeMeshRenderer(false, 3f, false);
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

		switch (selectionMode) {
		case VERTEX:
			renderVertexSelections(node, geometries);
			break;
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
			public ColorRGBA getFaceColor(final int faceIndex) {
				return sceneSelection.isFaceSelected(node, faceIndex) ? selectedColor
						: modelColor;
			}

			@Override
			public ColorRGBA getEdgeColor(final EdgeId edgeIndex) {
				return ColorRGBA.black.clone();
			}

			@Override
			public boolean isFaceVisible(final int faceIndex) {
				return sceneSelection.isFaceSelected(node, faceIndex);
			}

			@Override
			public boolean isEdgeVisible(final EdgeId edgeIndex) {
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
			public ColorRGBA getFaceColor(final int faceIndex) {
				return modelColor;
			}

			@Override
			public ColorRGBA getEdgeColor(final EdgeId edgeIndex) {
				return sceneSelection.isEdgeSelected(node, edgeIndex) ? selectedColor
						: ColorRGBA.black.clone();
			}

			@Override
			public boolean isFaceVisible(final int faceIndex) {
				return false;
			}

			@Override
			public boolean isEdgeVisible(final EdgeId edgeIndex) {
				return true;
			}
		};
	}

	private void renderVertexSelections(final IMeshNode node,
			final List<Spatial> geometries) {
		final IMesh mesh = node.getRenderGeometry();
		final SceneSelection sceneSelection = node.getScene()
				.getSceneSelection();
		final List<Vector3f> points = new ArrayList<Vector3f>();

		for (final IVertex vertex : mesh.getVertices()) {
			if (sceneSelection.isVertexSelected(node, vertex.getIndex())) {
				points.add(vertex.getPoint());
			}
		}

		final FloatBuffer vertexBuffer = BufferUtils.createVector3Buffer(points
				.size());
		for (final Vector3f point : points) {
			vertexBuffer.put(point.x);
			vertexBuffer.put(point.y);
			vertexBuffer.put(point.z);
		}

		final Point selectedVertexGeometry = new Point(null, vertexBuffer,
				null, null, null);

		selectedVertexGeometry.setPointSize(6f);
		selectedVertexGeometry.setAntialiased(false);

		final MaterialState materialState = renderer.createMaterialState();
		materialState.setMaterialFace(MaterialFace.FrontAndBack);
		materialState.setAmbient(new ColorRGBA(0.9f, 0.9f, 0.9f, 0.9f));
		materialState.setDiffuse(new ColorRGBA(0.9f, 0.9f, 0.9f, 0.9f));
		materialState.setEnabled(true);

		selectedVertexGeometry.setRenderQueueMode(Renderer.QUEUE_OPAQUE);
		selectedVertexGeometry.setRenderState(materialState);

		geometries.add(selectedVertexGeometry);
	}

}
