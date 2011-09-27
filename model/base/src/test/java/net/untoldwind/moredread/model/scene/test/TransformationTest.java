package net.untoldwind.moredread.model.scene.test;

import static net.untoldwind.moredread.model.test.AssertHelper.assertVectorEquals;

import java.util.Random;

import net.untoldwind.moredread.model.generator.CubeMeshGenerator;
import net.untoldwind.moredread.model.scene.AbstractSceneOperation;
import net.untoldwind.moredread.model.scene.AbstractSpatialNode;
import net.untoldwind.moredread.model.scene.GeneratorNode;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.model.scene.SceneHolder;
import net.untoldwind.moredread.model.transform.ITransformation;

import org.junit.Before;
import org.junit.Test;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

public class TransformationTest {
	private static float PI = (float) Math.PI;
	private static float SQRT_2_2 = (float) Math.sqrt(2) / 2;
	Scene scene;
	AbstractSpatialNode node;

	@Before
	public void createScene() {
		final SceneHolder sceneHolder = new SceneHolder();
		final Scene scene = sceneHolder.createScene();

		scene.notUndoableChange(new AbstractSceneOperation("Test operation") {
			@Override
			public void perform(final Scene scene) {
				node = new GeneratorNode(scene, new CubeMeshGenerator());
			}
		});
	}

	@Test
	public void testTranslation() {
		scene.notUndoableChange(new AbstractSceneOperation("Test operation") {
			@Override
			public void perform(final Scene scene) {
				node.setLocalTranslation(new Vector3f(1, 2, 3));

				final Vector3f r1 = node.localToWorld(new Vector3f(0, 0, 0),
						null);
				final Vector3f r2 = node.localToWorld(new Vector3f(3, 2, 1),
						null);

				assertVectorEquals(new Vector3f(1, 2, 3), r1, 1e-5f);
				assertVectorEquals(new Vector3f(4, 4, 4), r2, 1e-5f);

				final ITransformation transformation = node
						.getLocalTransformation();

				final Vector3f r3 = transformation.transformPoint(new Vector3f(
						0, 0, 0));
				final Vector3f r4 = transformation.transformPoint(new Vector3f(
						3, 2, 1));

				assertVectorEquals(new Vector3f(1, 2, 3), r3, 1e-5f);
				assertVectorEquals(new Vector3f(4, 4, 4), r4, 1e-5f);
			}
		});
	}

	@Test
	public void testScale() {
		scene.notUndoableChange(new AbstractSceneOperation("Test operation") {
			@Override
			public void perform(final Scene scene) {
				node.setLocalScale(new Vector3f(1, 2, 3));

				final Vector3f r1 = node.localToWorld(new Vector3f(1, 1, 1),
						null);
				final Vector3f r2 = node.localToWorld(new Vector3f(6, 3, 2),
						null);

				assertVectorEquals(new Vector3f(1, 2, 3), r1, 1e-5f);
				assertVectorEquals(new Vector3f(6, 6, 6), r2, 1e-5f);

				final ITransformation transformation = node
						.getLocalTransformation();

				final Vector3f r3 = transformation.transformPoint(new Vector3f(
						1, 1, 1));
				final Vector3f r4 = transformation.transformPoint(new Vector3f(
						6, 3, 2));

				assertVectorEquals(new Vector3f(1, 2, 3), r3, 1e-5f);
				assertVectorEquals(new Vector3f(6, 6, 6), r4, 1e-5f);
			}
		});
	}

	@Test
	public void testRotation() {
		scene.notUndoableChange(new AbstractSceneOperation("Test operation") {
			@Override
			public void perform(final Scene scene) {
				node.setLocalRotation(new Quaternion().fromAngles(
						(float) (Math.PI / 2), (float) (Math.PI / 4), 0));

				final Vector3f r1 = node.localToWorld(new Vector3f(1, 0, 0),
						null);
				final Vector3f r2 = node.localToWorld(new Vector3f(0, 1, 0),
						null);
				System.out.println(r1);
				System.out.println(r2);
				assertVectorEquals(new Vector3f(SQRT_2_2, 0, -SQRT_2_2), r1,
						1e-5f);
				assertVectorEquals(new Vector3f(SQRT_2_2, 0, SQRT_2_2), r2,
						1e-5f);

				final ITransformation transformation = node
						.getLocalTransformation();

				final Vector3f r3 = transformation.transformPoint(new Vector3f(
						1, 0, 0));
				final Vector3f r4 = transformation.transformPoint(new Vector3f(
						0, 1, 0));

				assertVectorEquals(new Vector3f(SQRT_2_2, 0, -SQRT_2_2), r3,
						1e-5f);
				assertVectorEquals(new Vector3f(SQRT_2_2, 0, SQRT_2_2), r4,
						1e-5f);
			}
		});
	}

	@Test
	public void testRandom() {
		final Random random = new Random(1234);

		for (int i = 0; i < 100; i++) {
			final Vector3f translation = new Vector3f(random.nextFloat() * 10,
					random.nextFloat() * 10, random.nextFloat() * 10);
			final Vector3f scale = new Vector3f(1 + random.nextFloat() * 5,
					1 + random.nextFloat() * 5, 1 + random.nextFloat() * 5);
			final Quaternion rotation = new Quaternion().fromAngles(
					random.nextFloat() * PI, random.nextFloat() * PI,
					random.nextFloat() * PI);

			scene.notUndoableChange(new AbstractSceneOperation("Test operation") {
				@Override
				public void perform(final Scene scene) {
					node.setLocalTranslation(translation);
					node.setLocalScale(scale);
					node.setLocalRotation(rotation);

					final Vector3f r1 = node.localToWorld(
							new Vector3f(1, 0, 0), null);
					final Vector3f r2 = node.localToWorld(
							new Vector3f(0, 1, 0), null);
					final Vector3f r3 = node.localToWorld(
							new Vector3f(0, 0, 1), null);

					final ITransformation transformation = node
							.getLocalTransformation();

					final Vector3f r4 = transformation
							.transformPoint(new Vector3f(1, 0, 0));
					final Vector3f r5 = transformation
							.transformPoint(new Vector3f(0, 1, 0));
					final Vector3f r6 = transformation
							.transformPoint(new Vector3f(0, 0, 1));

					assertVectorEquals(r1, r4, 1e-5f);
					assertVectorEquals(r2, r5, 1e-5f);
					assertVectorEquals(r3, r6, 1e-5f);
				}
			});
		}
	}
}
