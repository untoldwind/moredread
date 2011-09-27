package net.untoldwind.moredread.model.scene.test;

import static net.untoldwind.moredread.model.test.AssertHelper.assertVectorEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Constructor;

import net.untoldwind.moredread.model.generator.CubeMeshGenerator;
import net.untoldwind.moredread.model.mesh.IFace;
import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.Mesh;
import net.untoldwind.moredread.model.scene.AbstractSpatialComposite;
import net.untoldwind.moredread.model.scene.MeshNode;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.model.scene.SceneHolder;
import net.untoldwind.moredread.model.state.BinaryStateReader;
import net.untoldwind.moredread.model.state.BinaryStateWriter;
import net.untoldwind.moredread.model.state.IStateReader;

import org.junit.Test;

import com.jme.math.FastMath;

public class MeshNodeTest {
	@Test
	public void testReadWriteState() throws Exception {
		final Mesh<?, ?> mesh = new CubeMeshGenerator().generateMesh(null);
		final SceneHolder sceneHolder = new SceneHolder();
		final Scene scene = sceneHolder.createScene();

		scene.getSceneChangeHandler().beginNotUndoable();
		final MeshNode node = new MeshNode(scene, mesh);
		scene.getSceneChangeHandler().commit();

		final byte[] ser = BinaryStateWriter.toByteArray(node);

		final BinaryStateReader reader = new BinaryStateReader(
				new ByteArrayInputStream(ser));

		scene.getSceneChangeHandler().beginNotUndoable();
		final MeshNode otherNode = reader
				.readObject(new IStateReader.IInstanceCreator<MeshNode>() {
					@Override
					public MeshNode createInstance(final Class<MeshNode> clazz) {
						try {
							final Constructor<MeshNode> constructor = clazz
									.getDeclaredConstructor(AbstractSpatialComposite.class);

							constructor.setAccessible(true);
							return constructor.newInstance(scene);
						} catch (final Exception e) {
							throw new RuntimeException(e);
						}
					}
				});
		scene.getSceneChangeHandler().commit();

		assertEquals(2, scene.getChildren().size());
		assertNotSame(node, otherNode);
		assertEquals(node.getName(), otherNode.getName());

		final IMesh otherMesh = otherNode.getGeometry();

		assertEquals(mesh.getVertices().size(), otherMesh.getVertices().size());
		for (int i = 0; i < mesh.getVertices().size(); i++) {
			assertVectorEquals(mesh.getVertex(i).getPoint(), otherMesh
					.getVertex(i).getPoint(), FastMath.ZERO_TOLERANCE);
		}
		assertEquals(mesh.getFaces().size(), otherMesh.getFaces().size());
		for (final IFace face : mesh.getFaces()) {
			final IFace otherFace = otherMesh.getFace(face.getIndex());

			assertEquals(face.getVertices().size(), otherFace.getVertices()
					.size());
			for (int j = 0; j < face.getVertices().size(); j++) {
				assertVectorEquals(face.getVertex(j).getPoint(), otherFace
						.getVertex(j).getPoint(), FastMath.ZERO_TOLERANCE);
			}
		}
	}
}
