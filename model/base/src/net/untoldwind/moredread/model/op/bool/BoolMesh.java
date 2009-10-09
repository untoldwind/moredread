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
}
