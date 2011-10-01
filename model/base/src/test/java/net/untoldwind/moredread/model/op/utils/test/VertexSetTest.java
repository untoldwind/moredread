package net.untoldwind.moredread.model.op.utils.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import net.untoldwind.moredread.model.enums.GeometryType;
import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.mesh.IEdge;
import net.untoldwind.moredread.model.mesh.IFace;
import net.untoldwind.moredread.model.mesh.IPoint;
import net.untoldwind.moredread.model.mesh.IVertex;
import net.untoldwind.moredread.model.op.utils.VertexSet;
import net.untoldwind.moredread.model.state.IStateReader;
import net.untoldwind.moredread.model.state.IStateWriter;
import net.untoldwind.moredread.model.transform.ITransformation;

import org.junit.Test;

public class VertexSetTest {
	@Test
	public void testSimple() throws Exception {
		final VertexSet vertexSet = new VertexSet();

		vertexSet.addVertex(new TestVertex(new Vector3(0, 0, 0), 0));
		vertexSet.addVertex(new TestVertex(new Vector3(1, 0, 0), 1));
		vertexSet.addVertex(new TestVertex(new Vector3(0, 1, 0), 2));
		vertexSet.addVertex(new TestVertex(new Vector3(0, 0, 1), 3));
		vertexSet.addVertex(new TestVertex(new Vector3(1, 1, 0), 4));
		vertexSet.addVertex(new TestVertex(new Vector3(1, 0, 1), 5));
		vertexSet.addVertex(new TestVertex(new Vector3(0, 1, 1), 6));
		vertexSet.addVertex(new TestVertex(new Vector3(-1, 0, 0), 7));

		IVertex found = vertexSet.findVertex(new Vector3(0, 0, 0));
		assertNotNull(found);
		assertEquals(0, found.getIndex());
		found = vertexSet.findVertex(new Vector3(0.000009f, 0, 0));
		assertNotNull(found);
		assertEquals(0, found.getIndex());
		found = vertexSet.findVertex(new Vector3(0.000009f, -0.000009f, 0));
		assertNotNull(found);
		assertEquals(0, found.getIndex());
		found = vertexSet.findVertex(new Vector3(0.000009f, -0.000009f,
				-0.000009f));
		assertNotNull(found);
		assertEquals(0, found.getIndex());
		found = vertexSet.findVertex(new Vector3(1.000009f, -0.000009f,
				-0.000009f));
		assertNotNull(found);
		assertEquals(1, found.getIndex());
		found = vertexSet.findVertex(new Vector3(-0.000009f, 0.999991f,
				-0.000009f));
		assertNotNull(found);
		assertEquals(2, found.getIndex());
		found = vertexSet.findVertex(new Vector3(-0.000009f, 0.000009f,
				1.000009f));
		assertNotNull(found);
		assertEquals(3, found.getIndex());
		found = vertexSet.findVertex(new Vector3(1.000009f, 0.999991f,
				0.000009f));
		assertNotNull(found);
		assertEquals(4, found.getIndex());
		found = vertexSet.findVertex(new Vector3(1.000009f, 0.000009f,
				0.999991f));
		assertNotNull(found);
		assertEquals(5, found.getIndex());
		found = vertexSet.findVertex(new Vector3(-0.000009f, 1.000009f,
				0.999991f));
		assertNotNull(found);
		assertEquals(6, found.getIndex());
		found = vertexSet.findVertex(new Vector3(-1.000009f, -0.000009f,
				-0.000009f));
		assertNotNull(found);
		assertEquals(7, found.getIndex());
	}

	private static class TestVertex implements IVertex {
		private final Vector3 point;
		private final int index;

		public TestVertex(final Vector3 point, final int index) {
			this.point = point;
			this.index = index;
		}

		@Override
		public Vector3 getPoint() {
			return point;
		}

		@Override
		public int getIndex() {
			return index;
		}

		@Override
		public GeometryType getGeometryType() {
			return GeometryType.POINT;
		}

		@Override
		public IPoint transform(final ITransformation transformation) {
			return null;
		}

		@Override
		public void readState(final IStateReader reader) throws IOException {
		}

		@Override
		public void writeState(final IStateWriter writer) throws IOException {
		}

		@Override
		public boolean isSmooth() {
			return false;
		}

		@Override
		public Set<? extends IEdge> getEdges() {
			return null;
		}

		@Override
		public Set<? extends IFace> getFaces() {
			return null;
		}

		@Override
		public List<? extends IVertex> getNeighbours() {
			return null;
		}

		@Override
		public Vector3 getMeanNormal() {
			return null;
		}

	}
}
