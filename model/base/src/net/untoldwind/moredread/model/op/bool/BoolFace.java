package net.untoldwind.moredread.model.op.bool;

import java.util.Arrays;
import java.util.List;

import com.jme.math.Plane;

public class BoolFace {
	Plane plane;
	BoolVertex[] vertices;
	int split;
	int tag;
	BoolFace originalFace;

	public BoolFace(final BoolVertex v1, final BoolVertex v2,
			final BoolVertex v3, final Plane plane, final BoolFace originalFace) {
		this.vertices = new BoolVertex[] { v1, v2, v2 };
		this.plane = plane;
		this.originalFace = originalFace;
	}

	public BoolVertex getVertex(final int idx) {
		return vertices[idx];
	}

	public List<BoolVertex> getVertices() {
		return Arrays.asList(vertices);
	}

	public Plane getPlane() {
		return plane;
	}

	public void setSplit(final int split) {
		this.split = split;
	}

	public int getSplit() {
		return split;
	}

	public int size() {
		return vertices.length;
	}

	public int getTAG() {
		return tag;
	}

	public void setTAG(final int tag) {
		this.tag = tag;
	}

	public BoolFace getOriginalFace() {
		return originalFace;
	}

	public void setOriginalFace(final BoolFace originalFace) {
		this.originalFace = originalFace;
	}

	/**
	 * Returns the relative edge index (1,2,3) for the specified vertex indexs.
	 * 
	 * @param v1
	 *            vertex index
	 * @param v2
	 *            vertex index
	 * @param e
	 *            relative edge index (1,2,3)
	 * @return true if (v1,v2) is an edge of this face, false otherwise
	 */
	public int getEdgeIndex(final BoolVertex v1, final BoolVertex v2) {
		if (size() == 3) {
			if (vertices[0] == v1) {
				if (vertices[1] == v2) {
					return 1;
				} else if (vertices[2] == v2) {
					return 3;
				} else {
					return -1;
				}
			} else if (vertices[1] == v1) {
				if (vertices[0] == v2) {
					return 1;
				} else if (vertices[2] == v2) {
					return 2;
				} else {
					return -1;
				}
			} else if (vertices[2] == v1) {
				if (vertices[0] == v2) {
					return 3;
				} else if (vertices[1] == v2) {
					return 2;
				} else {
					return -1;
				}
			} else {
				return -1;
			}
		} else {
			if (vertices[0] == v1) {
				if (vertices[1] == v2) {
					return 1;
				} else if (vertices[3] == v2) {
					return 4;
				} else {
					return -1;
				}
			} else if (vertices[1] == v1) {
				if (vertices[0] == v2) {
					return 1;
				} else if (vertices[2] == v2) {
					return 2;
				} else {
					return -1;
				}
			} else if (vertices[2] == v1) {
				if (vertices[1] == v2) {
					return 2;
				} else if (vertices[3] == v2) {
					return 3;
				} else {
					return -1;
				}
			} else if (vertices[3] == v1) {
				if (vertices[2] == v2) {
					return 3;
				} else if (vertices[0] == v2) {
					return 4;
				} else {
					return -1;
				}
			} else {
				return -1;
			}
		}
	}

}
