package net.untoldwind.moredread.model.mesh;

import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.enums.GeometryType;
import net.untoldwind.moredread.model.state.IStateHolder;

import com.jme.math.Plane;
import com.jme.math.Vector3f;

public abstract class AbstractFace<T extends Mesh<?>> implements IStateHolder,
		IFace {
	private final T owner;
	protected final int index;
	protected Vector3f center;
	protected Vector3f meanNormal;

	protected AbstractFace(final T owner, final int index) {
		this.owner = owner;
		this.index = index;
	}

	@Override
	public GeometryType getGeometryType() {
		return GeometryType.POLYGON;
	}

	public T getOwner() {
		return owner;
	}

	public int getIndex() {
		return index;
	}

	@Override
	public List<? extends IFace> getNeighbours() {
		final List<IFace> neighbours = new ArrayList<IFace>();

		for (final IEdge edge : getEdges()) {
			for (final IFace face : edge.getFaces()) {
				if (face != this) {
					neighbours.add(face);
				}
			}
		}

		return neighbours;
	}

	@Override
	public Vector3f getCenter() {
		if (center == null) {
			updateCenter();
		}
		return center;
	}

	@Override
	public Vector3f getMeanNormal() {
		if (meanNormal == null) {
			updateMeanNormal();
		}
		return meanNormal;
	}

	@Override
	public Plane getPlane() {
		final Vector3f n = getMeanNormal();

		final float d = n.dot(getCenter());

		return new Plane(n, d);
	}

	void markDirty() {
		center = null;
		meanNormal = null;
	}

	public abstract List<Vertex> getVertices();

	public abstract void updateCenter();

	public abstract void updateMeanNormal();
}
