package net.untoldwind.moredread.model.mesh;

import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.enums.GeometryType;
import net.untoldwind.moredread.model.math.Plane;
import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.state.IStateHolder;

public abstract class Face<FaceK extends FaceId, FaceT extends Face<?, ?>>
		implements IStateHolder, IFace {
	protected final Mesh<?, ?> owner;
	protected final FaceK index;
	private Vector3 center;
	private Vector3 meanNormal;

	protected Face(final Mesh<?, ?> owner, final FaceK index) {
		this.owner = owner;
		this.index = index;
	}

	@Override
	public GeometryType getGeometryType() {
		return GeometryType.POLYGON;
	}

	public Mesh<?, ?> getOwner() {
		return owner;
	}

	public FaceK getIndex() {
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
	public Vector3 getCenter() {
		if (center == null) {
			center = calculateCenter();
		}
		return center;
	}

	@Override
	public Vector3 getMeanNormal() {
		if (meanNormal == null) {
			meanNormal = calculateMeanNormal();
		}
		return meanNormal;
	}

	@Override
	public Plane getPlane() {
		final Vector3 n = getMeanNormal();

		final float d = n.dot(getCenter());

		return new Plane(n, d);
	}

	void markDirty() {
		center = null;
		meanNormal = null;
	}

	public abstract List<Vertex> getVertices();

	public abstract Vector3 calculateCenter();

	public abstract Vector3 calculateMeanNormal();

	abstract void remove();
}
