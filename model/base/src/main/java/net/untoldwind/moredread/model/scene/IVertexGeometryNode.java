package net.untoldwind.moredread.model.scene;

import net.untoldwind.moredread.model.mesh.IVertexGeometry;
import net.untoldwind.moredread.model.mesh.VertexGeometry;

public interface IVertexGeometryNode<RO_GEOMETRY extends IVertexGeometry<?>, RW_GEOMETRY extends VertexGeometry<?>>
		extends IGeometryNode<RO_GEOMETRY, RW_GEOMETRY> {

}
