package net.untoldwind.moredread.ui.controls.impl;

import java.util.List;

import net.untoldwind.moredread.model.enums.Direction;
import net.untoldwind.moredread.ui.controls.IControlHandle;
import net.untoldwind.moredread.ui.controls.IModelControl;
import net.untoldwind.moredread.ui.tools.spi.IToolAdapter;

import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
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

		xControl = new DirectionArrowModelControl(Direction.X, toolAdapter);
		this.attachChild(xControl);
		subControls.add(xControl);
		yControl = new DirectionArrowModelControl(Direction.Y, toolAdapter);
		this.attachChild(yControl);
		subControls.add(yControl);
		zControl = new DirectionArrowModelControl(Direction.Z, toolAdapter);
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
	public void cameraUpdated(final Camera camera) {
		final Vector3f worldCenter = getWorldTranslation();
		final Vector3f center = camera.getScreenCoordinates(worldCenter);
		worldCenter.addLocal(camera.getUp());
		final Vector3f up = camera.getScreenCoordinates(worldCenter);

		final float dist = (center.x - up.x) * (center.x - up.x)
				+ (center.y - up.y) * (center.y - up.y);

		setLocalScale(60.0f / FastMath.sqrt(dist));

		updateWorldVectors(true);

		super.cameraUpdated(camera);
	}

	@Override
	public void collectControlHandles(final List<IControlHandle> handles,
			final Camera camera) {
		xControl.collectControlHandles(handles, camera);
		yControl.collectControlHandles(handles, camera);
		zControl.collectControlHandles(handles, camera);
	}

	@Override
	public void updatePositions() {
		this.setLocalTranslation(toolAdapter.getCenter());
	}

	@Override
	public String toString() {
		return "MoveCrossModelControl [xControl=" + xControl + ", yControl="
				+ yControl + ", zControl=" + zControl + "]";
	}

}