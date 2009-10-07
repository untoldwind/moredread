package net.untoldwind.moredread.model.mesh;

public interface IEdge {
	EdgeId getIndex();

	IPoint getVertex1();

	IPoint getVertex2();
}
