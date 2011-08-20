package net.untoldwind.moredread.model.mesh;

import java.util.List;

import net.untoldwind.moredread.model.transform.ITransformable;

import com.jme.math.Vector3f;

public interface IPolygon extends IGeometry, ITransformable<IPolygon> {
	Vector3f getMeanNormal();

	int getVertexCount();

	List<? extends IPoint> getVertices();

	int[] getPolygonStripCounts();

	int[] getPolygonContourCounts();

	boolean isClosed();

	Vector3f getCenter();
}
