package net.untoldwind.moredread.model.mesh;

import java.util.Collection;
import java.util.List;

import net.untoldwind.moredread.model.enums.MeshType;

public interface IMesh extends IGeometry {
	MeshType getMeshType();

	List<? extends IVertex> getVertices();

	IVertex getVertex(final int vertexIndes);

	Collection<? extends IEdge> getEdges();

	IEdge getEdge(final EdgeId edgeIndex);

	List<? extends IFace> getFaces();

	IFace getFace(final int faceIndex);

	TriangleMesh toTriangleMesh();
}
