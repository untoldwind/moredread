package net.untoldwind.moredread.model.mesh;

import java.util.Collection;
import java.util.List;

import net.untoldwind.moredread.model.enums.MeshType;

public interface IMesh extends IGeometry {
	MeshType getMeshType();

	List<? extends IPoint> getVertices();

	Collection<? extends IEdge> getEdges();

	List<? extends IPolygon> getFaces();
}
