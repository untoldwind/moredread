package net.untoldwind.moredread.ui.views;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import net.untoldwind.moredread.model.math.Quaternion;
import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.mesh.IEdgeGeometry;
import net.untoldwind.moredread.model.mesh.IGeometry;
import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.IVertexGeometry;
import net.untoldwind.moredread.model.scene.BoundingBox;
import net.untoldwind.moredread.model.scene.IGeometryNode;
import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.ISpatialNode;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.model.scene.event.ISceneChangeListener;
import net.untoldwind.moredread.model.scene.event.ISceneSelectionChangeListener;
import net.untoldwind.moredread.model.scene.event.SceneChangeEvent;
import net.untoldwind.moredread.model.scene.event.SceneSelectionChangeEvent;
import net.untoldwind.moredread.ui.MoreDreadUI;
import net.untoldwind.moredread.ui.utils.RotationValueField;
import net.untoldwind.moredread.ui.utils.StringText;
import net.untoldwind.moredread.ui.utils.XYZValueField;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISizeProvider;
import org.eclipse.ui.part.ViewPart;

public class SelectionInfoView extends ViewPart implements
		ISceneChangeListener, ISceneSelectionChangeListener, ISizeProvider {
	public static final String ID = "net.untoldwind.moredread.ui.selectionInfoView";

	private final AtomicInteger updateQueueCount = new AtomicInteger(0);

	Composite container;
	StringText nameText;
	XYZValueField hotpointText;
	XYZValueField centerText;
	RotationValueField rotateText;
	Label faceCountLabel;
	Label edgeCountLabel;
	Label vertexCountLabel;

	Runnable updateSelectionRun;

	public SelectionInfoView() {
		updateSelectionRun = new Runnable() {
			@Override
			public void run() {
				try {
					updateSelection();
				} finally {
					updateQueueCount.decrementAndGet();
				}
			}
		};
	}

	@Override
	public void createPartControl(final Composite parent) {
		container = parent;
		parent.setLayout(new GridLayout(2, false));

		final Label nameLabel = new Label(parent, SWT.NONE);
		nameLabel.setText("Name");
		nameLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		nameText = new StringText(parent);

		final Label hotPointLabel = new Label(parent, SWT.NONE);
		hotPointLabel.setText("Hotpoint");
		hotPointLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		hotpointText = new XYZValueField(parent);

		final Label centerLabel = new Label(parent, SWT.NONE);
		centerLabel.setText("Center");
		centerLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		centerText = new XYZValueField(parent);

		final Label rotateLabel = new Label(parent, SWT.NONE);
		rotateLabel.setText("Rotate");
		rotateLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		rotateText = new RotationValueField(parent);

		MoreDreadUI.getDefault().getSceneHolder().addSceneChangeListener(this);
		MoreDreadUI.getDefault().getSceneHolder()
				.addSceneSelectionChangeListener(this);

		final Composite geometryCounts = new Composite(parent, SWT.NONE);
		final GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.horizontalSpan = 2;
		geometryCounts.setLayoutData(layoutData);
		geometryCounts.setLayout(new GridLayout(6, false));

		final Label facesLabel = new Label(geometryCounts, SWT.NONE);
		facesLabel.setText("F:");
		faceCountLabel = new Label(geometryCounts, SWT.NONE);
		faceCountLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		final Label edgesLabel = new Label(geometryCounts, SWT.NONE);
		edgesLabel.setText("E:");
		edgeCountLabel = new Label(geometryCounts, SWT.NONE);
		edgeCountLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		final Label verticesLabel = new Label(geometryCounts, SWT.NONE);
		verticesLabel.setText("V:");
		vertexCountLabel = new Label(geometryCounts, SWT.NONE);
		vertexCountLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

	}

	@Override
	public void dispose() {
		MoreDreadUI.getDefault().getSceneHolder()
				.removeSceneChangeListener(this);
		MoreDreadUI.getDefault().getSceneHolder()
				.removeSceneSelectionChangeListener(this);
		super.dispose();
	}

	@Override
	public void setFocus() {
	}

	@Override
	public int computePreferredSize(final boolean width,
			final int availableParallel, final int availablePerpendicular,
			final int preferredResult) {
		final Point size = container.computeSize(SWT.DEFAULT, SWT.DEFAULT);

		return width ? size.x : size.y;
	}

	@Override
	public int getSizeFlags(final boolean width) {
		return SWT.MIN;
	}

	@Override
	public void sceneChanged(final SceneChangeEvent event) {
		if (updateQueueCount.get() < 2) {
			updateQueueCount.incrementAndGet();

			Display.getDefault().asyncExec(updateSelectionRun);
		}
	}

	@Override
	public void sceneSelectionChanged(final SceneSelectionChangeEvent event) {
		updateSelection();
	}

	private void updateSelection() {
		final Scene scene = MoreDreadUI.getDefault().getSceneHolder()
				.getScene();
		BoundingBox boundingBox = null;
		final Vector3 hotpoint = new Vector3();
		final Quaternion rotation = new Quaternion();

		final List<ISpatialNode> selection = new ArrayList<ISpatialNode>();
		for (final INode node : scene.getSceneSelection().getSelectedNodes()) {
			if (node instanceof ISpatialNode) {
				selection.add((ISpatialNode) node);
			}
		}

		faceCountLabel.setText("");
		edgeCountLabel.setText("");
		vertexCountLabel.setText("");
		if (selection.isEmpty()) {
			nameText.setValue(null);
			centerText.setValue(null);
			hotpointText.setValue(null);
			rotateText.setValue(null);

			return;
		} else if (selection.size() == 1) {
			final ISpatialNode spatialNode = selection.get(0);
			hotpoint.set(spatialNode.getWorldTranslation());
			boundingBox = spatialNode.getWorldBoundingBox();
			rotation.set(spatialNode.getLocalRotation());
			nameText.setValue(spatialNode.getName());
			nameText.setEnabled(true);

			int vertexCount = 0;
			int edgeCount = 0;
			int faceCount = 0;
			if (spatialNode instanceof IGeometryNode<?, ?>) {
				final IGeometry<?> geometry = ((IGeometryNode<?, ?>) spatialNode)
						.getRenderGeometry();

				switch (geometry.getGeometryType()) {
				case MESH:
					faceCount += ((IMesh) geometry).getFaces().size();
				case GRID:
				case POLYGON:
					edgeCount += ((IEdgeGeometry<?>) geometry).getEdges()
							.size();
				case POINT:
					vertexCount += ((IVertexGeometry<?>) geometry)
							.getVertices().size();
				}
			}
			faceCountLabel.setText(String.valueOf(faceCount));
			edgeCountLabel.setText(String.valueOf(edgeCount));
			vertexCountLabel.setText(String.valueOf(vertexCount));
		} else {
			int count = 0;

			int vertexCount = 0;
			int edgeCount = 0;
			int faceCount = 0;
			for (final ISpatialNode spatialNode : selection) {
				if (boundingBox == null) {
					boundingBox = new BoundingBox(
							spatialNode.getWorldBoundingBox());
				} else {
					boundingBox.mergeLocal(spatialNode.getWorldBoundingBox());
				}
				hotpoint.addLocal(spatialNode.getWorldTranslation());

				if (spatialNode instanceof IGeometryNode<?, ?>) {
					final IGeometry<?> geometry = ((IGeometryNode<?, ?>) spatialNode)
							.getRenderGeometry();

					switch (geometry.getGeometryType()) {
					case MESH:
						faceCount += ((IMesh) geometry).getFaces().size();
					case GRID:
					case POLYGON:
						edgeCount += ((IEdgeGeometry<?>) geometry).getEdges()
								.size();
					case POINT:
						vertexCount += ((IVertexGeometry<?>) geometry)
								.getVertices().size();
					}
				}
				count++;
			}
			faceCountLabel.setText(String.valueOf(faceCount));
			edgeCountLabel.setText(String.valueOf(edgeCount));
			vertexCountLabel.setText(String.valueOf(vertexCount));
			hotpoint.divideLocal(count);
			nameText.setValue("Multiple");
			nameText.setEnabled(false);
		}

		centerText.setValue(boundingBox.getCenter());
		hotpointText.setValue(hotpoint);
		rotateText.setValue(rotation);
	}
}
