package net.untoldwind.moredread.model.op.bool;

import java.util.List;

import net.untoldwind.moredread.model.mesh.IEdge;

public class BoolEdge {
	List<BoolFace> boolFace;
	IEdge edge;

	public List<BoolFace> getFaces() {
		return boolFace;
	}
}
