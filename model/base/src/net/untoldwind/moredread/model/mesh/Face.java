package net.untoldwind.moredread.model.mesh;

import java.util.List;

import net.untoldwind.moredread.model.enums.GeometryType;
import net.untoldwind.moredread.model.state.IStateHolder;

import com.jme.math.Vector3f;

public abstract class Face<T extends Mesh<?>> implements IStateHolder, IFace {
	private final T owner;
	protected final int index;
	protected Vector3f center;
	protected Vector3f meanNormal;

	protected Face(final T owner, final int index) {
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

	public Vector3f getCenter() {
		if (center == null) {
			updateCenter();
		}
		return center;
	}

	public Vector3f getMeanNormal() {
		if (meanNormal == null) {
			updateMeanNormal();
		}
		return meanNormal;
	}

	void markDirty() {
		center = null;
		meanNormal = null;
	}

	public abstract List<Vertex> getVertices();

	public abstract void updateCenter();

	public abstract void updateMeanNormal();
}
