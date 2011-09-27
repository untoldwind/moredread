package net.untoldwind.moredread.model.mesh;

import java.util.Collection;

import net.untoldwind.moredread.model.enums.MeshType;

public interface IMesh extends IEdgeGeometry<IMesh> {
	MeshType getMeshType();

	Collection<? extends IFace> getFaces();

	IFace getFace(final FaceId faceIndex);

	TriangleMesh toTriangleMesh();

	PolyMesh toPolyMesh();
}
