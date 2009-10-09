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
}
