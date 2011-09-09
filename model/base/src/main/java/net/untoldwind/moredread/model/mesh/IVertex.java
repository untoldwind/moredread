package net.untoldwind.moredread.model.mesh;

import java.util.List;
import java.util.Set;

public interface IVertex extends IPoint {
	int getIndex();

	boolean isSmooth();

	Set<? extends IEdge> getEdges();

	Set<? extends IFace> getFaces();

	List<? extends IVertex> getNeighbours();
}
