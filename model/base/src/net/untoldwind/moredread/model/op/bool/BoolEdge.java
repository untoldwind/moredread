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

		this.v1.addEdge(this);
		this.v2.addEdge(this);
		this.faces = new ArrayList<BoolFace>();
	}

	public BoolVertex getVertex1() {
		return v1;
	}

	public BoolVertex getVertex2() {
		return v2;
	}

	public List<BoolFace> getFaces() {
		return faces;
	}

	void addFace(final BoolFace face) {
		faces.add(face);
	}

	/**
	 * Replaces an edge vertex index.
	 * 
	 * @param oldIndex
	 *            old vertex index
	 * @param newIndex
	 *            new vertex index
	 */
	void replaceVertexIndex(final BoolVertex oldIndex, final BoolVertex newIndex) {
		if (v1 == oldIndex) {
			v1 = newIndex;
		} else if (v2 == oldIndex) {
			v2 = newIndex;
		}
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("BoolEdge [v1=");
		builder.append(v1.getIndex());
		builder.append(", v2=");
		builder.append(v2.getIndex());
		builder.append("]");
		return builder.toString();
	}

}
