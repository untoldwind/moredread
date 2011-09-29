package net.untoldwind.moredread.model.mesh;

import java.io.IOException;

import net.untoldwind.moredread.model.enums.GeometryType;
import net.untoldwind.moredread.model.state.IStateReader;
import net.untoldwind.moredread.model.state.IStateWriter;
import net.untoldwind.moredread.model.transform.ITransformation;

public class Grid extends EdgeGeometry<IGrid> implements IGrid {

	public Grid() {

	}

	@Override
	public GeometryType getGeometryType() {
		return GeometryType.GRID;
	}

	public Edge addEdge(final int index1, final int index2) {
		return addEdge(vertices.get(index1), vertices.get(index2));
	}

	@Override
	public IGrid transform(final ITransformation transformation) {
		final Grid grid = new Grid();

		for (final Vertex vertex : vertices) {
			grid.addVertex(vertex.getPoint(), vertex.isSmooth());
		}
		for (final Edge edge : edges.values()) {
			grid.addEdge(edge.getVertex1().getIndex(), edge.getVertex2()
					.getIndex());
		}
		return grid;
	}

	@Override
	public void readState(final IStateReader reader) throws IOException {
		final int numVerices = reader.readInt();

		for (int i = 0; i < numVerices; i++) {
			addVertex(reader.readVector3(), false);
		}

		final int numEdges = reader.readInt();

		for (int i = 0; i < numEdges; i++) {
			final int index1 = reader.readInt();
			final int index2 = reader.readInt();

			addEdge(vertices.get(index1), vertices.get(index2));
		}
	}

	@Override
	public void writeState(final IStateWriter writer) throws IOException {
		writer.writeInt("numVerices", vertices.size());
		for (final IPoint vertex : vertices) {
			writer.writeVector3("vertex", vertex.getPoint());
		}
		writer.writeInt("numEdges", edges.size());
		for (final IEdge edge : edges.values()) {
			writer.writeInt("idx1", edge.getVertex1().getIndex());
			writer.writeInt("idx2", edge.getVertex2().getIndex());
		}
	}
}
