package net.untoldwind.moredread.model.generator;

import java.io.IOException;
import java.util.List;

import net.untoldwind.moredread.model.enums.GeometryType;
import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.mesh.IFace;
import net.untoldwind.moredread.model.mesh.IGeometry;
import net.untoldwind.moredread.model.mesh.IGrid;
import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.IPolygon;
import net.untoldwind.moredread.model.mesh.IVertex;
import net.untoldwind.moredread.model.mesh.PolyMesh;
import net.untoldwind.moredread.model.mesh.VertexGeometry;
import net.untoldwind.moredread.model.state.IStateReader;
import net.untoldwind.moredread.model.state.IStateWriter;

public class ThicknessMeshGenerator extends AbstractGeometryGenerator<IMesh>
		implements IMeshGenerator {
	private float thickness;
	private int interpolationPoints;

	public ThicknessMeshGenerator() {
		this.thickness = 1.0f;
		this.interpolationPoints = 0;
	}

	public float getThickness() {
		return thickness;
	}

	public void setThickness(final float thickness) {
		this.thickness = thickness;
	}

	@Override
	public String getName() {
		return "Thickness";
	}

	@Override
	public GeometryType getGeometryType() {
		return GeometryType.MESH;
	}

	@Override
	public IMesh generateGeometry(final List<IGeneratorInput> generatorInputs) {
		if (generatorInputs.isEmpty()) {
			// TODO: Consider a nice placeholder geometry (or some other kind of
			// error handling)
			return new CubeMeshGenerator().generateGeometry(null);
		}
		IGeometry<?> geometry = generatorInputs.get(0).getRenderGeometry();

		geometry = (IGeometry<?>) geometry.transform(generatorInputs.get(0)
				.getLocalTransformation());

		switch (geometry.getGeometryType()) {
		case POINT:
			return generatePointThickness((VertexGeometry<?>) geometry);
		case POLYGON:
			return generatePolygonThickness((IPolygon) geometry);
		case GRID:
			return generateGridThickness((IGrid) geometry);
		case MESH:
			return generateMeshThickness((IMesh) geometry);
		default:
			throw new RuntimeException("Unknown geometry type: "
					+ geometry.getGeometryType());
		}
	}

	private IMesh generatePointThickness(final VertexGeometry<?> geometry) {
		return new CubeMeshGenerator().generateGeometry(null);
	}

	private IMesh generatePolygonThickness(final IPolygon geometry) {
		return new CubeMeshGenerator().generateGeometry(null);
	}

	private IMesh generateGridThickness(final IGrid geometry) {
		return new CubeMeshGenerator().generateGeometry(null);
	}

	private IMesh generateMeshThickness(final IMesh geometry) {
		final PolyMesh result = new PolyMesh();

		for (final IVertex vertex : geometry.getVertices()) {
			final Vector3 p = vertex.getPoint();
			final Vector3 offset = vertex.getMeanNormal().mult(thickness);

			result.addVertex(p.add(offset), vertex.isSmooth());
			result.addVertex(p.subtract(offset), vertex.isSmooth());
		}
		for (final IFace face : geometry.getFaces()) {
			final int[] outer = new int[face.getVertexCount()];
			final int[] inner = new int[face.getVertexCount()];

			for (int i = 0; i < face.getVertexCount(); i++) {
				final int idx = face.getVertex(i).getIndex();

				outer[i] = 2 * idx;
				inner[inner.length - i - 1] = 2 * idx + 1;
			}

			result.addFace(outer);
			result.addFace(inner);
		}

		return result;
	}

	@Override
	public void readState(final IStateReader reader) throws IOException {
		thickness = reader.readFloat();
		interpolationPoints = reader.readInt();
	}

	@Override
	public void writeState(final IStateWriter writer) throws IOException {
		writer.writeFloat("thickness", thickness);
		writer.writeInt("interpolationPoints", interpolationPoints);
	}
}
