package net.untoldwind.moredread.model.mesh;

import java.util.Set;

public interface IEdge {
	EdgeId getIndex();

	IVertex getVertex1();

	IVertex getVertex2();

	Set<? extends IFace> getFaces();

	boolean isConnection(IVertex vertex1, IVertex vertex2);
}
