package net.untoldwind.moredread.model.op.bool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.untoldwind.moredread.model.mesh.EdgeId;

public class BoolMesh {
	List<BoolVertex> vertices;
	Map<EdgeId, BoolEdge> edges;
	List<BoolFace> faces;

	public BoolMesh() {
		vertices = new ArrayList<BoolVertex>();
		edges = new HashMap<EdgeId, BoolEdge>();
		faces = new ArrayList<BoolFace>();
	}

	public void addFace(final BoolFace face) {
		if (face.size() == 3) {
			addFace3(face);
		} else {
			addFace4(face);
		}
	}

	void addFace3(final BoolFace face) {
		faces.add(face);

		final BoolVertex index1 = face.getVertex(0);
		final BoolVertex index2 = face.getVertex(1);
		final BoolVertex index3 = face.getVertex(2);

		BoolEdge edge = getOrAddEdge(index1, index2);

		edge.addFace(face);

		edge = getOrAddEdge(index2, index3);

		edge.addFace(face);

		edge = getOrAddEdge(index3, index1);

		edge.addFace(face);

		if ((index1 == index2) || (index1 == index3) || (index2 == index3)) {
			face.setTAG(BoolTag.BROKEN);
		}
	}

	void addFace4(final BoolFace face) {
		faces.add(face);

		final BoolVertex index1 = face.getVertex(0);
		final BoolVertex index2 = face.getVertex(1);
		final BoolVertex index3 = face.getVertex(2);
		final BoolVertex index4 = face.getVertex(3);

		BoolEdge edge = getOrAddEdge(index1, index2);

		edge.addFace(face);

		edge = getOrAddEdge(index2, index3);

		edge.addFace(face);

		edge = getOrAddEdge(index3, index4);

		edge.addFace(face);

		edge = getOrAddEdge(index4, index1);

		edge.addFace(face);

		if ((index1 == index2) || (index1 == index3) || (index1 == index4)
				|| (index2 == index3) || (index2 == index4)
				|| (index3 == index4)) {
			face.setTAG(BoolTag.BROKEN);
		}
	}

	public BoolEdge getOrAddEdge(final BoolVertex v1, final BoolVertex v2) {
		final EdgeId edgeId = new EdgeId(v1.getIndex(), v2.getIndex());

		BoolEdge edge = edges.get(edgeId);

		if (edge == null) {
			edge = new BoolEdge(v1, v2);
			edges.put(edgeId, edge);
		}

		return edge;
	}

	public BoolEdge getEdge(final BoolVertex v1, final BoolVertex v2) {
		final EdgeId edgeId = new EdgeId(v1.getIndex(), v2.getIndex());

		return edges.get(edgeId);
	}

	/**
	 * Returns the mesh edge on the specified face and relative edge index.
	 * 
	 * @param face
	 *            mesh face
	 * @param edge
	 *            face relative edge index
	 * @return mesh edge on the specified face and relative index, NULL
	 *         otherwise
	 */
	public BoolEdge getEdge(final BoolFace face, final int edge) {
		if (face.size() == 3) {
			return getEdge3(face, edge);
		} else {
			return getEdge4(face, edge);
		}
	}

	/**
	 * Returns the mesh edge on the specified triangle and relative edge index.
	 * 
	 * @param face
	 *            mesh triangle
	 * @param edge
	 *            face relative edge index
	 * @return mesh edge on the specified triangle and relative index, NULL
	 *         otherwise
	 */
	public BoolEdge getEdge3(final BoolFace face, final int edge) {
		switch (edge) {
		case 1:
			return getEdge(face.getVertex(0), face.getVertex(1));
		case 2:
			return getEdge(face.getVertex(1), face.getVertex(2));
		case 3:
			return getEdge(face.getVertex(2), face.getVertex(0));
		}

		return null;
	}

	/**
	 * Returns the mesh edge on the specified quad and relative edge index.
	 * 
	 * @param face
	 *            mesh quad
	 * @param edge
	 *            face relative edge index
	 * @return mesh edge on the specified quad and relative index, NULL
	 *         otherwise
	 */
	public BoolEdge getEdge4(final BoolFace face, final int edge) {
		switch (edge) {
		case 1:
			return getEdge(face.getVertex(0), face.getVertex(1));
		case 2:
			return getEdge(face.getVertex(1), face.getVertex(2));
		case 3:
			return getEdge(face.getVertex(2), face.getVertex(3));
		case 4:
			return getEdge(face.getVertex(3), face.getVertex(0));
		}

		return null;
	}
}
