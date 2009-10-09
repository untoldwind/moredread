package net.untoldwind.moredread.model.op.bool;

import java.util.ArrayList;
import java.util.List;

public class BoolEdge {
	BoolVertex v1;
	BoolVertex v2;
	List<BoolFace> faces;

	public BoolEdge(final BoolVertex v1, final BoolVertex v2) {
		this.v1 = v1;
		this.v2 = v2;

		this.v1.addEdges(this);
		this.v2.addEdges(this);
		this.faces = new ArrayList<BoolFace>();
	}

	public List<BoolFace> getFaces() {
		return faces;
	}

	void addFace(final BoolFace face) {
		faces.add(face);
	}
}
