package net.untoldwind.moredread.model.op.bool;

import java.util.Arrays;
import java.util.List;

import net.untoldwind.moredread.model.scene.BoundingBox;

import com.jme.math.Plane;

public class BoolFace {
	Plane plane;
	BoolVertex[] vertices;
	int split;
	int tag;
	BoolFace originalFace;
	BoundingBox boundingBox;

	public BoolFace(final BoolVertex v1, final BoolVertex v2,
			final BoolVertex v3, final Plane plane, final BoolFace originalFace) {
		this.vertices = new BoolVertex[] { v1, v2, v3 };
		this.plane = plane;
		this.originalFace = originalFace;
		this.tag = BoolTag.UNCLASSIFIED;
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

	public BoundingBox getBoundingBox() {
		if (boundingBox == null) {
			boundingBox = new BoundingBox(Arrays.asList(vertices));
		}
		return boundingBox;
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

	/**
	 * Replaces a face vertex index.
	 * 
	 * @param oldIndex
	 *            old vertex index
	 * @param newIndex
	 *            new vertex index
	 */
	void replaceVertexIndex(final BoolVertex oldIndex, final BoolVertex newIndex) {
		boundingBox = null;
		if (size() == 3) {
			/*
			 * if the old index really exists, and new index also exists
			 * already, don't create an edge with both vertices == newIndex
			 */

			if ((vertices[0] == oldIndex || vertices[1] == oldIndex || vertices[2] == oldIndex)
					&& (vertices[0] == newIndex || vertices[1] == newIndex || vertices[2] == newIndex)) {
				setTAG(BoolTag.BROKEN);
			}

			if (vertices[0] == oldIndex) {
				vertices[0] = newIndex;
			} else if (vertices[1] == oldIndex) {
				vertices[1] = newIndex;
			} else if (vertices[2] == oldIndex) {
				vertices[2] = newIndex;
			}
		} else {
			if (vertices[0] == oldIndex) {
				vertices[0] = newIndex;
			} else if (vertices[1] == oldIndex) {
				vertices[1] = newIndex;
			} else if (vertices[2] == oldIndex) {
				vertices[2] = newIndex;
			} else if (vertices[3] == oldIndex) {
				vertices[3] = newIndex;
			}
		}
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("BoolFace [plane=");
		builder.append(plane);
		builder.append(", tag=");
		builder.append(tag);
		builder.append(", vertices=");
		builder.append(Arrays.toString(vertices));
		builder.append("]");
		return builder.toString();
	}

}
