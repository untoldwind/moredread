package net.untoldwind.moredread.model.scene;

public interface ISceneVisitor<T> {
	T visitScene(final Scene scene);

	T visitGroup(Group group);

	T visitMeshNode(MeshNode node);

	T visitGeneratorNode(GeneratorNode generatorNode);
}
