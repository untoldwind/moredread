package net.untoldwind.moredread.model.scene.test;

import net.untoldwind.moredread.model.generator.CubeMeshGenerator;
import net.untoldwind.moredread.model.scene.AbstractSpatialNode;
import net.untoldwind.moredread.model.scene.GeneratorNode;
import net.untoldwind.moredread.model.scene.Scene;

import org.junit.Before;
import org.junit.Test;

public class TransformationTest {
	Scene scene;
	AbstractSpatialNode node;

	@Before
	public void createScene() {
		scene = new Scene();

		scene.getSceneChangeHandler().begin(false);

		try {
			node = new GeneratorNode(scene, new CubeMeshGenerator());
		} finally {
			scene.getSceneChangeHandler().commit();
		}
	}

	@Test
	public void testTranslation() {
	}
}
