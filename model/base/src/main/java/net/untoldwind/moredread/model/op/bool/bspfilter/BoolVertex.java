package net.untoldwind.moredread.model.op.bool.bspfilter;

import net.untoldwind.moredread.model.mesh.IVertex;

import com.jme.math.Vector3f;

public class BoolVertex {
	private final Vector3f point;
	private final Integer orginalIndex;

	public BoolVertex(final Vector3f point) {
		this.point = point;
		this.orginalIndex = null;
	}

	public BoolVertex(final IVertex vertex) {
		this.point = vertex.getPoint();
		this.orginalIndex = vertex.getIndex();
	}

	public Vector3f getPoint() {
		return point;
	}

	public Integer getOrginalIndex() {
		return orginalIndex;
	}

}
