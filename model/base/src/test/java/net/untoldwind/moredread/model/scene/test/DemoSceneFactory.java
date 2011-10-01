package net.untoldwind.moredread.model.scene.test;

import net.untoldwind.moredread.model.generator.BooleanGenerator;
import net.untoldwind.moredread.model.generator.CubeMeshGenerator;
import net.untoldwind.moredread.model.generator.DodecahedronMeshGenerator;
import net.untoldwind.moredread.model.generator.GeosphereMeshGenerator;
import net.untoldwind.moredread.model.generator.IcosahedronMeshGenerator;
import net.untoldwind.moredread.model.generator.OctahedronMeshGenerator;
import net.untoldwind.moredread.model.math.Vector3;
import net.untoldwind.moredread.model.mesh.PolyMesh;
import net.untoldwind.moredread.model.op.IBooleanOperation;
import net.untoldwind.moredread.model.scene.AbstractSceneOperation;
import net.untoldwind.moredread.model.scene.AbstractSpatialNode;
import net.untoldwind.moredread.model.scene.GeneratorNode;
import net.untoldwind.moredread.model.scene.MeshNode;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.model.scene.SceneHolder;

public class DemoSceneFactory {
	Scene createScene() {
		final SceneHolder sceneHolder = new SceneHolder();
		final Scene scene = sceneHolder.createScene();

		scene.notUndoableChange(new AbstractSceneOperation("Create demo scene") {
			@Override
			public void perform(final Scene scene) {
				final AbstractSpatialNode node1 = new GeneratorNode(scene,
						new CubeMeshGenerator());

				node1.setLocalScale(new Vector3(2.0f, 2.0f, 2.0f));

				final AbstractSpatialNode node2 = new MeshNode(scene,
						"Mesh Cube", new CubeMeshGenerator().generateGeometry(null));

				node2.setLocalTranslation(new Vector3(3.5f, 0, 0));

				final AbstractSpatialNode node3 = new GeneratorNode(scene,
						new OctahedronMeshGenerator());

				node3.setLocalTranslation(new Vector3(-3.5f, 0, 0));

				final AbstractSpatialNode node4 = new GeneratorNode(scene,
						new DodecahedronMeshGenerator());

				node4.setLocalScale(new Vector3(3.0f, 3.0f, 3.0f));
				node4.setLocalTranslation(new Vector3(10f, 0, 0));

				final AbstractSpatialNode node5 = new MeshNode(scene,
						"Mesh Dodecahedron", new DodecahedronMeshGenerator()
								.generateGeometry(null));

				node5.setLocalScale(new Vector3(3.0f, 3.0f, 3.0f));
				node5.setLocalTranslation(new Vector3(-10f, 0, 0));

				final AbstractSpatialNode node6 = new MeshNode(scene,
						"Mesh Octahedron", new OctahedronMeshGenerator()
								.generateGeometry(null));

				node6.setLocalScale(new Vector3(3.0f, 3.0f, 3.0f));
				node6.setLocalTranslation(new Vector3(0, 10f, 0));

				final PolyMesh cubeWithHole = new PolyMesh();

				cubeWithHole.addVertex(new Vector3(-1, -1, -1));
				cubeWithHole.addVertex(new Vector3(1, -1, -1));
				cubeWithHole.addVertex(new Vector3(1, 1, -1));
				cubeWithHole.addVertex(new Vector3(-1, 1, -1));
				cubeWithHole.addVertex(new Vector3(-1, -1, 1));
				cubeWithHole.addVertex(new Vector3(1, -1, 1));
				cubeWithHole.addVertex(new Vector3(1, 1, 1));
				cubeWithHole.addVertex(new Vector3(-1, 1, 1));

				cubeWithHole.addVertex(new Vector3(-0.75f, -0.75f, -1));
				cubeWithHole.addVertex(new Vector3(0.75f, -0.75f, -1));
				cubeWithHole.addVertex(new Vector3(0.75f, 0.75f, -1));
				cubeWithHole.addVertex(new Vector3(-0.75f, 0.75f, -1));

				cubeWithHole.addVertex(new Vector3(-0.75f, -0.75f, 1));
				cubeWithHole.addVertex(new Vector3(0.75f, -0.75f, 1));
				cubeWithHole.addVertex(new Vector3(0.75f, 0.75f, 1));
				cubeWithHole.addVertex(new Vector3(-0.75f, 0.75f, 1));

				cubeWithHole.addFace(new int[][] { { 3, 2, 1, 0 },
						{ 8, 9, 10, 11 } });

				cubeWithHole.addFace(new int[][] { { 4, 5, 6, 7 },
						{ 15, 14, 13, 12 } });

				cubeWithHole.addFace(4, 7, 3, 0);
				cubeWithHole.addFace(1, 2, 6, 5);
				cubeWithHole.addFace(0, 1, 5, 4);
				cubeWithHole.addFace(2, 3, 7, 6);

				cubeWithHole.addFace(8, 12, 13, 9);
				cubeWithHole.addFace(10, 14, 15, 11);
				cubeWithHole.addFace(13, 14, 10, 9);
				cubeWithHole.addFace(11, 15, 12, 8);

				final AbstractSpatialNode node7 = new MeshNode(scene,
						"Cube With Hole", cubeWithHole);

				node7.setLocalScale(new Vector3(3.0f, 3.0f, 3.0f));
				node7.setLocalTranslation(new Vector3(0, -10f, 0));

				final GeneratorNode node8 = new GeneratorNode(scene,
						new BooleanGenerator(
								IBooleanOperation.BoolOperation.INTERSECTION));

				new MeshNode(node8, "Cube1", new CubeMeshGenerator(new Vector3(
						0, 0, 0), 1f).generateGeometry(null));
				new MeshNode(node8, "Cube2", new CubeMeshGenerator(new Vector3(
						0.1f, 0.2f, 0.3f), 1f).generateGeometry(null));

				node8.setLocalScale(new Vector3(3.0f, 3.0f, 3.0f));
				node8.setLocalTranslation(new Vector3(-10f, -10f, 0));

				final GeneratorNode node9 = new GeneratorNode(scene,
						new BooleanGenerator(
								IBooleanOperation.BoolOperation.UNION));

				new MeshNode(node9, "Cube1", new CubeMeshGenerator(new Vector3(
						0, 0, 0), 1f).generateGeometry(null));
				new MeshNode(node9, "Cube2", new CubeMeshGenerator(new Vector3(
						0.1f, 0.2f, 0.3f), 1f).generateGeometry(null));

				node9.setLocalScale(new Vector3(3.0f, 3.0f, 3.0f));
				node9.setLocalTranslation(new Vector3(-20f, -10f, 0));

				final GeneratorNode node10 = new GeneratorNode(scene,
						new BooleanGenerator(
								IBooleanOperation.BoolOperation.DIFFERENCE));

				new MeshNode(node10, "Cube1", new CubeMeshGenerator(
						new Vector3(0, 0, 0), 1f).generateGeometry(null));
				new MeshNode(node10, "Cube2", new CubeMeshGenerator(
						new Vector3(0.1f, 0.2f, 0.3f), 1f).generateGeometry(null));

				node10.setLocalScale(new Vector3(3.0f, 3.0f, 3.0f));
				node10.setLocalTranslation(new Vector3(-30f, -10f, 0));

				final AbstractSpatialNode node11 = new MeshNode(scene,
						"Mesh Icosahedron", new IcosahedronMeshGenerator()
								.generateGeometry(null));

				node11.setLocalScale(new Vector3(3.0f, 3.0f, 3.0f));
				node11.setLocalTranslation(new Vector3(-20f, 10f, 0));

				final AbstractSpatialNode node12 = new MeshNode(scene,
						"Mesh Geosphere", new GeosphereMeshGenerator(5,
								new Vector3(), 1.0f).generateGeometry(null));

				node12.setLocalScale(new Vector3(5.0f, 5.0f, 5.0f));
				node12.setLocalTranslation(new Vector3(-30f, 10f, 0));
			}
		});

		return scene;
	}
}
