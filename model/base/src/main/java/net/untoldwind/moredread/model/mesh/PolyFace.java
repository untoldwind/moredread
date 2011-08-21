package net.untoldwind.moredread.model.mesh;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.untoldwind.moredread.model.state.IStateWriter;
import net.untoldwind.moredread.model.transform.ITransformation;

import com.jme.math.Vector3f;

public class PolyFace extends AbstractFace<PolyMesh> {
	private final List<Vertex> vertices;
	private final List<AbstractEdge> edges;
	private final List<Integer> stripCounts;

	PolyFace(final PolyMesh owner, final int index,
			final List<Vertex> vertices, final List<AbstractEdge> edges) {
		super(owner, index);

		this.vertices = vertices;
		this.edges = edges;
		this.stripCounts = new ArrayList<Integer>();
		this.stripCounts.add(vertices.size());

		for (final Vertex vertex : vertices) {
			vertex.getFaces().add(this);
		}

		for (final AbstractEdge edge : edges) {
			edge.getFaces().add(this);
		}
	}

	PolyFace(final PolyMesh owner, final int index,
			final Vertex[][] vertexStrips, final List<AbstractEdge> edges) {
		super(owner, index);

		this.edges = edges;

		this.vertices = new ArrayList<Vertex>();
		this.stripCounts = new ArrayList<Integer>();
		for (final Vertex[] strip : vertexStrips) {
			this.stripCounts.add(strip.length);
			for (final Vertex vertex : strip) {
				this.vertices.add(vertex);
			}
		}
		for (final Vertex vertex : vertices) {
			vertex.getFaces().add(this);
		}

		for (final AbstractEdge edge : edges) {
			edge.getFaces().add(this);
		}
	}

	@Override
	public int getVertexCount() {
		return vertices.size();
	}

	@Override
	public Vertex getVertex(final int index) {
		return vertices.get(index);
	}

	@Override
	public List<Vertex> getVertices() {
		return vertices;
	}

	public List<AbstractEdge> getEdges() {
		return edges;
	}

	public List<Integer> getStripCounts() {
		return stripCounts;
	}

	@Override
	public int[] getPolygonContourCounts() {
		return new int[] { stripCounts.size() };
	}

	@Override
	public int[] getPolygonStripCounts() {
		final int[] stripCountArray = new int[stripCounts.size()];

		for (int i = 0; i < stripCountArray.length; i++) {
			stripCountArray[i] = stripCounts.get(i);
		}

		return stripCountArray;
	}

	@Override
	public boolean isClosed() {
		return true;
	}

	@Override
	public void updateCenter() {
		center = new Vector3f(0, 0, 0);

		for (final Vertex vertex : vertices) {
			center.addLocal(vertex.getPoint());
		}

		center.divideLocal(vertices.size());
	}

	@Override
	public void updateMeanNormal() {
		meanNormal = new Vector3f(0, 0, 0);
		final int outerStripCount = stripCounts.get(0);
		for (int i = 0; i < outerStripCount; i++) {
			final Vector3f v1 = vertices.get(i).getPoint();
			final Vector3f v2 = vertices.get((i + 1) % outerStripCount)
					.getPoint();
			meanNormal.addLocal(v1.cross(v2));
		}
		final float len = meanNormal.length();

		if (len < 1e-6) {
			meanNormal.set(0, 0, 1);
		} else {
			meanNormal.divideLocal(len);
		}
	}

	@Override
	public IPolygon transform(final ITransformation transformation) {
		final List<IPoint> new_vertices = new ArrayList<IPoint>(vertices.size());

		for (final IPoint point : vertices) {
			new_vertices.add(point.transform(transformation));
		}

		return new Polygon(new_vertices, null, getPolygonStripCounts(),
				getPolygonContourCounts(), true);
	}

	public void writeState(final IStateWriter writer) throws IOException {
		writer.writeInt("vertexNumber", vertices.size());
		for (final Vertex vertex : vertices) {
			writer.writeInt("vertexIndex", vertex.getIndex());
		}
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		final PolyFace castObj = (PolyFace) obj;

		return index == castObj.index && vertices.equals(castObj.vertices)
				&& edges.equals(castObj.edges);
	}

	@Override
	public int hashCode() {
		return index;
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer("PolyFace(");
		buffer.append("index=").append(index);
		buffer.append(", vertices=").append(vertices);
		buffer.append(", edges=").append(edges);
		buffer.append(", stripCounts=").append(stripCounts);

		return buffer.toString();
	}
}