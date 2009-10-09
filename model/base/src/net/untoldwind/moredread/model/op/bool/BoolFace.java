package net.untoldwind.moredread.model.op.bool;

import java.util.List;

import net.untoldwind.moredread.model.mesh.IFace;
import net.untoldwind.moredread.model.mesh.IVertex;

import com.jme.math.Plane;

public class BoolFace {
	IFace face;
	int split;
	int tag;

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
		return face.getVertices().size();
	}

	public int getTAG() {
		return tag;
	}
}
