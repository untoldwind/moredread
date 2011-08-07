package net.untoldwind.moredread.model.scene;

import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.mesh.IPoint;
import net.untoldwind.moredread.model.mesh.IPolygon;
import net.untoldwind.moredread.model.mesh.Point;
import net.untoldwind.moredread.model.mesh.Polygon;
import net.untoldwind.moredread.model.renderer.INodeRendererAdapter;
import net.untoldwind.moredread.model.scene.change.NodeTransformationChangeCommand;
import net.untoldwind.moredread.model.scene.change.SceneRemoveNodeChangeCommand;
import net.untoldwind.moredread.model.scene.properties.SpatialNodePropertySource;
import net.untoldwind.moredread.model.transform.ITransformation;
import net.untoldwind.moredread.model.transform.MatrixTransformation;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.views.properties.IPropertySource;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

public abstract class AbstractSpatialNode extends AbstractNode implements
		ISpatialNode, IAdaptable {
	/** The parent node (group). */
	protected final AbstractSpatialComposite<? extends INode> parent;

	/** Spatial's rotation relative to its parent. */
	protected Quaternion localRotation;
	/** Spatial's translation relative to its parent. */
	protected Vector3f localTranslation;
	/** Spatial's scale relative to its parent. */
	protected Vector3f localScale;

	protected AbstractSpatialNode(
			final AbstractSpatialComposite<? extends INode> parent,
			final String name) {
		super(parent, name);

		this.parent = parent;
		if (parent != null) {
			parent.addChild(this);
		}
		localRotation = new Quaternion();
		localTranslation = new Vector3f();
		localScale = new Vector3f(1, 1, 1);
	}

	public AbstractSpatialComposite<? extends INode> getParent() {
		return parent;
	}

	public boolean isSelected() {
		return scene.getSceneSelection().isNodeSelected(this);
	}

	@Override
	public Quaternion getLocalRotation() {
		return localRotation;
	}

	public void setLocalRotation(final Quaternion localRotation) {
		scene.getSceneChangeHandler().registerCommand(
				new NodeTransformationChangeCommand(this));

		this.localRotation = localRotation;
	}

	@Override
	public Vector3f getLocalTranslation() {
		return localTranslation;
	}

	public void setLocalTranslation(final Vector3f localTranslation) {
		scene.getSceneChangeHandler().registerCommand(
				new NodeTransformationChangeCommand(this));

		this.localTranslation = localTranslation;
	}

	@Override
	public Vector3f getLocalScale() {
		return localScale;
	}

	public void setLocalScale(final Vector3f localScale) {
		scene.getSceneChangeHandler().registerCommand(
				new NodeTransformationChangeCommand(this));

		this.localScale = localScale;
	}

	@Override
	public ITransformation getLocalTransformation() {
		return new MatrixTransformation(localScale, localRotation,
				localTranslation);
	}

	@Override
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

	@Override
	public IPoint localToWorld(final IPoint point) {
		return new Point(localToWorld(point.getPoint(), new Vector3f()));
	}

	@Override
	public IPolygon localToWorld(final IPolygon polygon) {
		final List<IPoint> points = new ArrayList<IPoint>();

		for (final IPoint point : polygon.getVertices()) {
			points.add(localToWorld(point));
		}

		return new Polygon(points, null, polygon.getPolygonStripCounts(),
				polygon.getPolygonContourCounts(), polygon.isClosed());
	}

	@Override
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

	@Override
	public void remove() {
		if (parent == null) {
			throw new RuntimeException(
					"Can not remove node without parent (e.g. scene node)");
		}

		scene.getSceneChangeHandler().registerCommand(
				new SceneRemoveNodeChangeCommand(parent, this));

		parent.removeChild(this);
		scene.unregisterNode(this);
	}

	// TODO: Replace this method by an NodeVisitor pattern (i.e. decouple model
	// and view/render)
	public abstract void updateDisplayNode(
			INodeRendererAdapter rendererAdapter, com.jme.scene.Node parent,
			boolean reattach);

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") final Class adapter) {
		if (adapter == IPropertySource.class) {
			return new SpatialNodePropertySource(this);
		}
		return null;
	}

}
