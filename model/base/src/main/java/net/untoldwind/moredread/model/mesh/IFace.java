package net.untoldwind.moredread.model.mesh;

import java.util.Collection;
import java.util.List;

import net.untoldwind.moredread.model.math.Plane;

public interface IFace extends IPolygon {
	FaceId getIndex();

	IVertex getVertex(final int index);

	List<? extends IVertex> getVertices();

	Collection<? extends IEdge> getEdges();

	Plane getPlane();

	List<? extends IFace> getNeighbours();
}
