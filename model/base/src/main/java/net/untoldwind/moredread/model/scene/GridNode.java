package net.untoldwind.moredread.model.scene;

import java.util.EnumSet;

import net.untoldwind.moredread.model.enums.GeometryType;
import net.untoldwind.moredread.model.enums.SelectionMode;
import net.untoldwind.moredread.model.mesh.Grid;
import net.untoldwind.moredread.model.mesh.IGrid;

public class GridNode extends GeometryNode<IGrid, Grid> implements IGridNode {

	protected GridNode(final AbstractSpatialComposite<? extends INode> parent) {
		super(parent, "Grid");
	}

	public GridNode(final AbstractSpatialComposite<? extends INode> parent,
			final Grid grid) {
		super(parent, "Grid", grid);
	}

	public GridNode(final AbstractSpatialComposite<? extends INode> parent,
			final String name, final Grid grid) {
		super(parent, name, grid);
	}

	@Override
	public GeometryType getGeometryType() {
		return GeometryType.GRID;
	}

	@Override
	public <T> T accept(final ISceneVisitor<T> visitor) {
		return visitor.visitGridNode(this);
	}

	@Override
	public EnumSet<SelectionMode> getSupportedSelectionModes() {
		return EnumSet.of(SelectionMode.OBJECT, SelectionMode.EDGE,
				SelectionMode.VERTEX);
	}

}
