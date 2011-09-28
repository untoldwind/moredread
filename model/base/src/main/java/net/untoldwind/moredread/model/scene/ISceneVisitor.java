package net.untoldwind.moredread.model.scene;

public interface ISceneVisitor<T> {
	T visitScene(final Scene scene);

	T visitGroup(Group group);

	T visitMeshNode(MeshNode node);

	T visitGridNode(GridNode node);

	T visitPolygonNode(PolygonNode node);

	T visitGeneratorNode(GeneratorNode generatorNode);
}
