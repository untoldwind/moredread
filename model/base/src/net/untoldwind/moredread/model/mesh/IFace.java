package net.untoldwind.moredread.model.mesh;

import java.util.List;

import com.jme.math.Plane;

public interface IFace extends IPolygon {
	int getIndex();

	List<? extends IVertex> getVertices();

	Plane getPlane();

}
