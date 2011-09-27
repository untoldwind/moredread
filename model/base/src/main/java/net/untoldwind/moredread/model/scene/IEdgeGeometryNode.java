package net.untoldwind.moredread.model.scene;

import net.untoldwind.moredread.model.mesh.EdgeGeometry;
import net.untoldwind.moredread.model.mesh.IEdgeGeometry;

public interface IEdgeGeometryNode<RO_GEOMETRY extends IEdgeGeometry<?>, RW_GEOMETRY extends EdgeGeometry<?>>
		extends IVertexGeometryNode<RO_GEOMETRY, RW_GEOMETRY> {

}
