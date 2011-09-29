package net.untoldwind.moredread.ui.controls.impl;

import net.untoldwind.moredread.model.enums.CartesianDirection;
import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.controls.IViewport;
import net.untoldwind.moredread.ui.tools.spi.IToolAdapter;

import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.shape.Box;

public class MoveCrossModelControl extends CompositeModelControl implements
		IModelControl {

	private static final long serialVersionUID = 1L;

	private final IToolAdapter toolAdapter;

	DirectionArrowModelControl xControl;
	DirectionArrowModelControl yControl;
	DirectionArrowModelControl zControl;

	public MoveCrossModelControl(final IToolAdapter toolAdapter) {
		super("MoveCrossControl");

		xControl = new DirectionArrowModelControl(CartesianDirection.X,
				toolAdapter);
		this.attachChild(xControl);
		subControls.add(xControl);
		yControl = new DirectionArrowModelControl(CartesianDirection.Y,
				toolAdapter);
		this.attachChild(yControl);
		subControls.add(yControl);
		zControl = new DirectionArrowModelControl(CartesianDirection.Z,
				toolAdapter);
		this.attachChild(zControl);
		subControls.add(zControl);

		final Box box = new Box("center", new Vector3f(0, 0, 0), 0.05f, 0.05f,
				0.05f);
		box.setDefaultColor(ColorRGBA.yellow.clone());
		this.attachChild(box);

		this.toolAdapter = toolAdapter;

		updatePositions();
	}

	@Override
	public void viewportChanged(final IViewport viewport) {
		final Vector3 worldCenter = new Vector3(getWorldTranslation());
		final Vector3 center = viewport.getCamera().getScreenCoordinates(
				worldCenter);
		worldCenter.addLocal(viewport.getCamera().getUp());
		final Vector3 up = viewport.getCamera().getScreenCoordinates(
				worldCenter);

		final float dist = (center.x - up.x) * (center.x - up.x)
				+ (center.y - up.y) * (center.y - up.y);

		setLocalScale(60.0f / FastMath.sqrt(dist));

		updateWorldVectors(true);

		super.viewportChanged(viewport);
	}

	@Override
	public void updatePositions() {
		this.setLocalTranslation(toolAdapter.getCenter().toJME());
	}

	@Override
	public IToolAdapter getToolAdapter() {
		return toolAdapter;
	}

	@Override
	public String toString() {
		return "MoveCrossModelControl [xControl=" + xControl + ", yControl="
				+ yControl + ", zControl=" + zControl + "]";
	}

}
