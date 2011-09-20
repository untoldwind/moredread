package net.untoldwind.moredread.ui.views;

import static net.untoldwind.moredread.ui.utils.FormatUtils.formatAngle;
import static net.untoldwind.moredread.ui.utils.FormatUtils.formatLength;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import net.untoldwind.moredread.model.scene.BoundingBox;
import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.ISpatialNode;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.model.scene.event.ISceneChangeListener;
import net.untoldwind.moredread.model.scene.event.ISceneSelectionChangeListener;
import net.untoldwind.moredread.model.scene.event.SceneChangeEvent;
import net.untoldwind.moredread.model.scene.event.SceneSelectionChangeEvent;
import net.untoldwind.moredread.ui.MoreDreadUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISizeProvider;
import org.eclipse.ui.part.ViewPart;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

;

public class SelectionInfoView extends ViewPart implements
		ISceneChangeListener, ISceneSelectionChangeListener, ISizeProvider {
	public static final String ID = "net.untoldwind.moredread.ui.selectionInfoView";

	private final AtomicInteger updateQueueCount = new AtomicInteger(0);

	Composite container;
	Text nameText;
	Text hotpointXText;
	Text hotpointYText;
	Text hotpointZText;
	Text centerXText;
	Text centerYText;
	Text centerZText;
	Text rotateXText;
	Text rotateYText;
	Text rotateZText;

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

		nameText = new Text(parent, SWT.BORDER);
		nameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		final Label hotPointLabel = new Label(parent, SWT.NONE);
		hotPointLabel.setText("Hotpoint");
		hotPointLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		final Display display = Display.getDefault();

		final Composite hotpoint = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, true);
		layout.marginHeight = 1;
		layout.marginWidth = 1;
		layout.horizontalSpacing = 1;
		layout.verticalSpacing = 1;
		hotpoint.setLayout(layout);
		hotpoint.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		hotpoint.setBackground(display.getSystemColor(SWT.COLOR_BLACK));

		hotpointXText = new Text(hotpoint, SWT.FLAT);
		hotpointXText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		hotpointXText.setBackground(display.getSystemColor(SWT.COLOR_RED));

		hotpointYText = new Text(hotpoint, SWT.FLAT);
		hotpointYText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		hotpointYText.setBackground(display.getSystemColor(SWT.COLOR_GREEN));

		hotpointZText = new Text(hotpoint, SWT.FLAT);
		hotpointZText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		hotpointZText.setBackground(display.getSystemColor(SWT.COLOR_BLUE));

		final Label centerLabel = new Label(parent, SWT.NONE);
		centerLabel.setText("Center");
		centerLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		final Composite center = new Composite(parent, SWT.NONE);
		layout = new GridLayout(3, true);
		layout.marginHeight = 1;
		layout.marginWidth = 1;
		layout.horizontalSpacing = 1;
		layout.verticalSpacing = 1;
		center.setLayout(layout);
		center.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		center.setBackground(display.getSystemColor(SWT.COLOR_BLACK));

		centerXText = new Text(center, SWT.FLAT);
		centerXText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		centerXText.setBackground(display.getSystemColor(SWT.COLOR_RED));

		centerYText = new Text(center, SWT.FLAT);
		centerYText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		centerYText.setBackground(display.getSystemColor(SWT.COLOR_GREEN));

		centerZText = new Text(center, SWT.FLAT);
		centerZText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		centerZText.setBackground(display.getSystemColor(SWT.COLOR_BLUE));

		final Label rotateLabel = new Label(parent, SWT.NONE);
		rotateLabel.setText("Rotate");
		rotateLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		final Composite rotate = new Composite(parent, SWT.NONE);
		layout = new GridLayout(3, true);
		layout.marginHeight = 1;
		layout.marginWidth = 1;
		layout.horizontalSpacing = 1;
		layout.verticalSpacing = 1;
		rotate.setLayout(layout);
		rotate.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		rotate.setBackground(display.getSystemColor(SWT.COLOR_BLACK));

		rotateXText = new Text(rotate, SWT.FLAT);
		rotateXText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		rotateXText.setBackground(display.getSystemColor(SWT.COLOR_RED));

		rotateYText = new Text(rotate, SWT.FLAT);
		rotateYText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		rotateYText.setBackground(display.getSystemColor(SWT.COLOR_GREEN));

		rotateZText = new Text(rotate, SWT.FLAT);
		rotateZText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		rotateZText.setBackground(display.getSystemColor(SWT.COLOR_BLUE));

		MoreDreadUI.getDefault().getSceneHolder().getScene()
				.addSceneChangeListener(this);
		MoreDreadUI.getDefault().getSceneHolder().getScene()
				.getSceneSelection().addSceneSelectionChangeListener(this);
	}

	@Override
	public void dispose() {
		MoreDreadUI.getDefault().getSceneHolder().getScene()
				.removeSceneChangeListener(this);
		MoreDreadUI.getDefault().getSceneHolder().getScene()
				.getSceneSelection().removeSceneSelectionChangeListener(this);
		super.dispose();
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	@Override
	public int computePreferredSize(final boolean width,
			final int availableParallel, final int availablePerpendicular,
			final int preferredResult) {
		final Point size = container.computeSize(SWT.DEFAULT, SWT.DEFAULT);

		return width ? size.x + 10 : size.y;
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
		final Vector3f hotpoint = new Vector3f();
		final Quaternion rotation = new Quaternion();

		final List<ISpatialNode> selection = new ArrayList<ISpatialNode>();
		for (final INode node : scene.getSceneSelection().getSelectedNodes()) {
			if (node instanceof ISpatialNode) {
				selection.add((ISpatialNode) node);
			}
		}

		if (selection.isEmpty()) {
			centerXText.setText("");
			centerYText.setText("");
			centerZText.setText("");

			hotpointXText.setText("");
			hotpointYText.setText("");
			hotpointZText.setText("");

			rotateXText.setText("");
			rotateYText.setText("");
			rotateZText.setText("");

			return;
		} else if (selection.size() == 1) {
			final ISpatialNode spatialNode = selection.get(0);
			hotpoint.set(spatialNode.getWorldTranslation());
			boundingBox = spatialNode.getWorldBoundingBox();
			rotation.set(spatialNode.getLocalRotation());
		} else {
			int count = 0;

			for (final ISpatialNode spatialNode : selection) {
				if (boundingBox == null) {
					boundingBox = spatialNode.getWorldBoundingBox();
				} else {
					boundingBox.mergeLocal(spatialNode.getWorldBoundingBox());
				}
				hotpoint.addLocal(spatialNode.getWorldTranslation());

				count++;
			}
			hotpoint.divideLocal(count);
		}

		centerXText.setText(formatLength(boundingBox.getCenter().x));
		centerYText.setText(formatLength(boundingBox.getCenter().y));
		centerZText.setText(formatLength(boundingBox.getCenter().z));

		hotpointXText.setText(formatLength(hotpoint.x));
		hotpointYText.setText(formatLength(hotpoint.y));
		hotpointZText.setText(formatLength(hotpoint.z));

		final float[] angles = rotation.toAngles(null);
		rotateXText.setText(formatAngle(angles[0]));
		rotateYText.setText(formatAngle(angles[1]));
		rotateZText.setText(formatAngle(angles[2]));
	}
}
