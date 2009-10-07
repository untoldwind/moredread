package net.untoldwind.moredread.model.scene;

import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.mesh.IPoint;
import net.untoldwind.moredread.model.mesh.IPolygon;
import net.untoldwind.moredread.model.mesh.Point;
import net.untoldwind.moredread.model.mesh.Polygon;
import net.untoldwind.moredread.model.renderer.INodeRendererAdapter;
import net.untoldwind.moredread.model.scene.change.NodeTransformationChangeCommand;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

public abstract class SpatialNode extends NodeBase implements INode {
	/** The parent node (group). */
	protected final Group parent;

	/** Spatial's rotation relative to its parent. */
	protected Quaternion localRotation;
	/** Spatial's translation relative to its parent. */
	protected Vector3f localTranslation;
	/** Spatial's scale relative to its parent. */
	protected Vector3f localScale;

	protected SpatialNode(final Group parent, final String name) {
		super(parent, name);

		this.parent = parent;
		if (parent != null) {
			parent.addNode(this);
		}
		localRotation = new Quaternion();
		localTranslation = new Vector3f();
		localScale = new Vector3f(1, 1, 1);
	}

	public Group getParent() {
		return parent;
	}

	public boolean isSelected() {
		return scene.getSceneSelection().isNodeSelected(this);
	}

	public Quaternion getLocalRotation() {
		return localRotation;
	}

	public void setLocalRotation(final Quaternion localRotation) {
		scene.getSceneChangeHandler().registerCommand(
				new NodeTransformationChangeCommand(this));

		this.localRotation = localRotation;
	}

	public Vector3f getLocalTranslation() {
		return localTranslation;
	}

	public void setLocalTranslation(final Vector3f localTranslation) {
		scene.getSceneChangeHandler().registerCommand(
				new NodeTransformationChangeCommand(this));

		this.localTranslation = localTranslation;
	}

	public Vector3f getLocalScale() {
		return localScale;
	}

	public void setLocalScale(final Vector3f localScale) {
		scene.getSceneChangeHandler().registerCommand(
				new NodeTransformationChangeCommand(this));

		this.localScale = localScale;
	}

	public Vector3f localToWorld(final Vector3f in, Vector3f store) {
		if (store == null) {
			store = new Vector3f();
		}
		// multiply with scale first, then rotate, finally translate (cf.
		// Eberly)
		return getWorldRotation().mult(
				store.set(in).multLocal(getWorldScale()), store).addLocal(
				getWorldTranslation());
	}

	public IPoint localToWorld(final IPoint point) {
		return new Point(point.getIndex(), localToWorld(point.getPoint(),
				new Vector3f()));
	}

	public IPolygon localToWorld(final IPolygon polygon) {
		final List<IPoint> points = new ArrayList<IPoint>();

		for (final IPoint point : polygon.getPolygonPoints()) {
			points.add(localToWorld(point));
		}

		return new Polygon(points, null, polygon.getPolygonStripCounts(),
				polygon.getPolygonContourCounts(), polygon.isClosed());
	}

	public Vector3f worldToLocal(final Vector3f in, Vector3f store) {
		if (store == null) {
			store = new Vector3f();
		}
		in.subtract(getWorldTranslation(), store).divideLocal(getWorldScale());
		getWorldRotation().inverse().mult(store, store);
		return store;
	}

	public Vector3f getWorldTranslation() {
		if (parent != null) {
			return parent.localToWorld(localTranslation, null);
		}
		return localTranslation;
	}

	public Quaternion getWorldRotation() {
		if (parent != null) {
			return parent.getWorldRotation().mult(localRotation);
		}
		return localRotation;
	}

	public Vector3f getWorldScale() {
		if (parent != null) {
			return parent.getWorldScale().mult(localScale);
		}
		return localScale;
	}

	public abstract void updateDisplayNode(
			INodeRendererAdapter rendererAdapter, com.jme.scene.Node parent);
}
