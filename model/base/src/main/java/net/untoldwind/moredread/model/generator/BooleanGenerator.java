package net.untoldwind.moredread.model.generator;

import java.io.IOException;
import java.util.List;

import net.untoldwind.moredread.model.enums.GeometryType;
import net.untoldwind.moredread.model.mesh.IGeometry;
import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.op.BooleanOperationFactory;
import net.untoldwind.moredread.model.op.IBooleanOperation;
import net.untoldwind.moredread.model.op.IBooleanOperation.BoolOperation;
import net.untoldwind.moredread.model.state.IStateReader;
import net.untoldwind.moredread.model.state.IStateWriter;

public class BooleanGenerator implements IMeshGenerator {
	private BoolOperation boolOperation;

	protected BooleanGenerator() {

	}

	public BooleanGenerator(final BoolOperation boolOperation) {
		super();
		this.boolOperation = boolOperation;
	}

	@Override
	public IMesh generateMesh(final List<IGeneratorInput> generatorInputs) {
		final IMesh inputs[] = new IMesh[2];
		int validInputCount = 0;

		for (final IGeneratorInput generatorInput : generatorInputs) {
			final IGeometry<?> geometry = generatorInput.getRenderGeometry();
			if (geometry.getGeometryType() == GeometryType.MESH) {
				inputs[validInputCount++] = ((IMesh) geometry)
						.transform(generatorInput.getLocalTransformation());
			}
		}

		if (validInputCount == 0) {
			// TODO: Consider a nice placeholder geometry (or some other kind of
			// error handling)
			return new CubeMeshGenerator().generateMesh(null);
		} else if (validInputCount == 1) {
			return inputs[0];
		} else {
			final IBooleanOperation booleanOperation = BooleanOperationFactory
					.createDefault();

			return booleanOperation.performBoolean(boolOperation,
					inputs[0].toTriangleMesh(), inputs[1].toTriangleMesh());
		}
	}

	@Override
	public String getName() {
		return "Boolean " + boolOperation;
	}

	@Override
	public void readState(final IStateReader reader) throws IOException {
		boolOperation = BoolOperation.valueOf(reader.readString());
	}

	@Override
	public void writeState(final IStateWriter writer) throws IOException {
		writer.writeString("operation", boolOperation.name());
	}
}
