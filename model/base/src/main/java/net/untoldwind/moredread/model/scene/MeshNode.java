package net.untoldwind.moredread.model.scene;

import java.util.EnumSet;

import net.untoldwind.moredread.model.enums.GeometryType;
import net.untoldwind.moredread.model.enums.SelectionMode;
import net.untoldwind.moredread.model.generator.IGeneratorInput;
import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.Mesh;

public class MeshNode extends GeometryNode<IMesh, Mesh<?, ?>> implements
		IMeshNode, IGeneratorInput {

	protected MeshNode(final AbstractSpatialComposite<? extends INode> parent) {
		super(parent, "Mesh");
	}

	public MeshNode(final AbstractSpatialComposite<? extends INode> parent,
			final Mesh<?, ?> mesh) {
		super(parent, "Mesh", mesh);
	}

	public MeshNode(final AbstractSpatialComposite<? extends INode> parent,
			final String name, final Mesh<?, ?> mesh) {
		super(parent, name, mesh);
	}

	@Override
	public GeometryType getGeometryType() {
		return GeometryType.MESH;
	}

	@Override
	public <T> T accept(final ISceneVisitor<T> visitor) {
		return visitor.visitMeshNode(this);
	}

	@Override
	public EnumSet<SelectionMode> getSupportedSelectionModes() {
		return EnumSet.allOf(SelectionMode.class);
	}

}
