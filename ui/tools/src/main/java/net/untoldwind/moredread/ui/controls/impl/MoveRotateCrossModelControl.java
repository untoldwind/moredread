package net.untoldwind.moredread.ui.controls.impl;

import net.untoldwind.moredread.model.enums.CartesianDirection;
import net.untoldwind.moredread.model.enums.CartesianPlane;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.controls.IViewport;
import net.untoldwind.moredread.ui.tools.spi.IToolAdapter;

import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.shape.Box;

public class MoveRotateCrossModelControl extends CompositeModelControl
		implements IModelControl {
	private static final long serialVersionUID = 1L;

	private final IToolAdapter toolAdapter;

	DirectionArrowModelControl xControl;
	DirectionArrowModelControl yControl;
	DirectionArrowModelControl zControl;
	RotateCircleModelControl xRotateControl;
	RotateCircleModelControl yRotateControl;
	RotateCircleModelControl zRotateControl;

	public MoveRotateCrossModelControl(final IToolAdapter translateToolAdapter,
			final IToolAdapter rotateToolAdapter) {
		super("MoveCrossControl");

		xControl = new DirectionArrowModelControl(CartesianDirection.X,
				translateToolAdapter);
		this.attachChild(xControl);
		subControls.add(xControl);
		yControl = new DirectionArrowModelControl(CartesianDirection.Y,
				translateToolAdapter);
		this.attachChild(yControl);
		subControls.add(yControl);
		zControl = new DirectionArrowModelControl(CartesianDirection.Z,
				translateToolAdapter);
		this.attachChild(zControl);
		subControls.add(zControl);
		xRotateControl = new RotateCircleModelControl(CartesianPlane.YZ,
				rotateToolAdapter);
		this.attachChild(xRotateControl);
		subControls.add(xRotateControl);
		yRotateControl = new RotateCircleModelControl(CartesianPlane.XZ,
				rotateToolAdapter);
		this.attachChild(yRotateControl);
		subControls.add(yRotateControl);
		zRotateControl = new RotateCircleModelControl(CartesianPlane.XY,
				rotateToolAdapter);
		this.attachChild(zRotateControl);
		subControls.add(zRotateControl);

		final Box box = new Box("center", new Vector3f(0, 0, 0), 0.05f, 0.05f,
				0.05f);
		box.setDefaultColor(ColorRGBA.yellow.clone());
		this.attachChild(box);

		this.toolAdapter = translateToolAdapter;

		updatePositions();
	}

	@Override
	public void viewportChanged(final IViewport viewport) {
		final Vector3f worldCenter = getWorldTranslation();
		final Vector3f center = viewport.getCamera().getScreenCoordinates(
				worldCenter);
		worldCenter.addLocal(viewport.getCamera().getUp());
		final Vector3f up = viewport.getCamera().getScreenCoordinates(
				worldCenter);

		final float dist = (center.x - up.x) * (center.x - up.x)
				+ (center.y - up.y) * (center.y - up.y);

		setLocalScale(80.0f / FastMath.sqrt(dist));

		updateWorldVectors(true);

		super.viewportChanged(viewport);
	}

	@Override
	public void updatePositions() {
		this.setLocalTranslation(toolAdapter.getCenter());
	}

	@Override
	public IToolAdapter getToolAdapter() {
		return toolAdapter;
	}

}
