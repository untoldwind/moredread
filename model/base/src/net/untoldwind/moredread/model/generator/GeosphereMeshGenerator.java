package net.untoldwind.moredread.model.generator;

import java.util.List;

import net.untoldwind.moredread.model.mesh.IMesh;

import com.jme.math.Vector3f;

public class GeosphereMeshGenerator implements IMeshGenerator {
	private final Vector3f center = new Vector3f();
	private final float size = 1f;

	@Override
	public String getName() {
		return "Geosphere";
	}

	@Override
	public IMesh generateMesh(final List<IGeneratorInput> generatorInputs) {
		// TODO Auto-generated method stub
		return null;
	}

}
