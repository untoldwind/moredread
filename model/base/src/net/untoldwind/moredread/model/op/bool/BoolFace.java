package net.untoldwind.moredread.model.op.bool;

import java.util.List;

import net.untoldwind.moredread.model.mesh.IFace;
import net.untoldwind.moredread.model.mesh.IVertex;

import com.jme.math.Plane;

public abstract class BoolFace {
	IFace face;
	int split;
	int tag;
	int originalFace;
	List<BoolEdge> edges;

	public IVertex getVertex(final int idx) {
		return face.getVertex(idx);
	}

	public BoolEdge getEdge(final int idx) {
		return edges.get(idx);
	}

	public List<? extends IVertex> getVertices() {
		return face.getVertices();
	}

	public Plane getPlane() {
		return face.getPlane();
	}

	public void setSplit(final int split) {
		this.split = split;
	}

	public int getSplit() {
		return split;
	}

	public int size() {
		return face.getVertexCount();
	}

	public int getTAG() {
		return tag;
	}

	public int getOriginalFace() {
		return originalFace;
	}

	public void setOriginalFace(final int originalFace) {
		this.originalFace = originalFace;
	}

}
