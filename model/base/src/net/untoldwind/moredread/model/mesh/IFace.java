package net.untoldwind.moredread.model.mesh;

import java.util.List;

public interface IFace extends IPolygon {
	int getIndex();

	List<? extends IVertex> getVertices();
}
