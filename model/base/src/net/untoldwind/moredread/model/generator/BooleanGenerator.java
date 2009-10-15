package net.untoldwind.moredread.model.generator;

import java.util.List;

import net.untoldwind.moredread.model.mesh.Mesh;
import net.untoldwind.moredread.model.op.IBooleanOperation.BoolOperation;

public class BooleanGenerator implements IMeshGenerator {
	private final BoolOperation boolOperation;

	public BooleanGenerator(final BoolOperation boolOperation) {
		super();
		this.boolOperation = boolOperation;
	}

	@Override
	public Mesh<?> generateMesh(final List<IGeneratorInput> generatorInputs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return "Boolean " + boolOperation;
	}

}
