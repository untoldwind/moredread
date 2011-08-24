package net.untoldwind.moredread.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import net.untoldwind.moredread.annotations.Singleton;
import net.untoldwind.moredread.jme.MoreDreadJME;
import net.untoldwind.moredread.model.generator.BooleanGenerator;
import net.untoldwind.moredread.model.generator.CubeMeshGenerator;
import net.untoldwind.moredread.model.generator.DodecahedronMeshGenerator;
import net.untoldwind.moredread.model.generator.GeosphereMeshGenerator;
import net.untoldwind.moredread.model.generator.IcosahedronMeshGenerator;
import net.untoldwind.moredread.model.generator.OctahedronMeshGenerator;
import net.untoldwind.moredread.model.mesh.PolyMesh;
import net.untoldwind.moredread.model.op.IBooleanOperation;
import net.untoldwind.moredread.model.scene.AbstractSpatialNode;
import net.untoldwind.moredread.model.scene.GeneratorNode;
import net.untoldwind.moredread.model.scene.ISceneHolder;
import net.untoldwind.moredread.model.scene.ISceneOperation;
import net.untoldwind.moredread.model.scene.MeshNode;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.model.scene.SceneHolder;
import net.untoldwind.moredread.ui.canvas.MDCanvasConstructor;
import net.untoldwind.moredread.ui.tools.IToolDescriptor;
import net.untoldwind.moredread.ui.tools.UIToolsPlugin;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.jme.math.Vector3f;
import com.jme.system.DisplaySystem;

/**
 * The activator class controls the plug-in life cycle
 */
@Singleton
public class MoreDreadUI extends AbstractUIPlugin {

	private ResourceBundle resourceBundle;

	private final Map<String, Image> images = new HashMap<String, Image>();

	private ISceneHolder sceneHolder;

	// The plug-in ID
	public static final String PLUGIN_ID = "net.untoldwind.moredread.ui.base";

	// The shared instance
	private static MoreDreadUI plugin;

	/**
	 * The constructor
	 */
	public MoreDreadUI() {
	}

	/**
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		initializeScene();

		final DisplaySystem displaySystem = MoreDreadJME.getDefault()
				.getDisplaySystem();
		displaySystem
				.registerCanvasConstructor("MD", MDCanvasConstructor.class);
	}

	/**
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static MoreDreadUI getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(final String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	/**
	 * Get an image from the plugin path.
	 * 
	 * @param path
	 *            The path relative to the plugin
	 * @return Image for <tt>path</tt>
	 */
	public synchronized Image getImage(final String path) {
		Image image = images.get(path);

		if (image != null) {
			return image;
		}

		final ImageDescriptor imageDescriptor = getImageDescriptor(path);

		if (imageDescriptor != null) {
			image = imageDescriptor.createImage();
		} else {
			image = PlatformUI.getWorkbench().getSharedImages()
					.getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}

		images.put(path, image);

		return image;
	}

	public String getString(final String key) {
		if (resourceBundle == null) {
			resourceBundle = Platform.getResourceBundle(getBundle());
		}

		return resourceBundle.getString(key);
	}

	public void log(final Throwable e) {
		getLog().log(
				new Status(Status.ERROR, PLUGIN_ID, Status.ERROR, e.toString(),
						e));
	}

	protected void initializeScene() {
		final Scene scene = new Scene();

		scene.notUndoableChange(new ISceneOperation() {
			@Override
			public void perform(final Scene scene) {
				final AbstractSpatialNode node1 = new GeneratorNode(scene,
						new CubeMeshGenerator());

				node1.setLocalScale(new Vector3f(2.0f, 2.0f, 2.0f));

				final AbstractSpatialNode node2 = new MeshNode(scene,
						"Mesh Cube", new CubeMeshGenerator().generateMesh(null));

				node2.setLocalTranslation(new Vector3f(3.5f, 0, 0));

				final AbstractSpatialNode node3 = new GeneratorNode(scene,
						new OctahedronMeshGenerator());

				node3.setLocalTranslation(new Vector3f(-3.5f, 0, 0));

				final AbstractSpatialNode node4 = new GeneratorNode(scene,
						new DodecahedronMeshGenerator());

				node4.setLocalScale(new Vector3f(3.0f, 3.0f, 3.0f));
				node4.setLocalTranslation(new Vector3f(10f, 0, 0));

				final AbstractSpatialNode node5 = new MeshNode(scene,
						"Mesh Dodecahedron", new DodecahedronMeshGenerator()
								.generateMesh(null));

				node5.setLocalScale(new Vector3f(3.0f, 3.0f, 3.0f));
				node5.setLocalTranslation(new Vector3f(-10f, 0, 0));

				final AbstractSpatialNode node6 = new MeshNode(scene,
						"Mesh Octahedron", new OctahedronMeshGenerator()
								.generateMesh(null));

				node6.setLocalScale(new Vector3f(3.0f, 3.0f, 3.0f));
				node6.setLocalTranslation(new Vector3f(0, 10f, 0));

				final PolyMesh cubeWithHole = new PolyMesh();

				cubeWithHole.addVertex(new Vector3f(-1, -1, -1));
				cubeWithHole.addVertex(new Vector3f(1, -1, -1));
				cubeWithHole.addVertex(new Vector3f(1, 1, -1));
				cubeWithHole.addVertex(new Vector3f(-1, 1, -1));
				cubeWithHole.addVertex(new Vector3f(-1, -1, 1));
				cubeWithHole.addVertex(new Vector3f(1, -1, 1));
				cubeWithHole.addVertex(new Vector3f(1, 1, 1));
				cubeWithHole.addVertex(new Vector3f(-1, 1, 1));

				cubeWithHole.addVertex(new Vector3f(-0.75f, -0.75f, -1));
				cubeWithHole.addVertex(new Vector3f(0.75f, -0.75f, -1));
				cubeWithHole.addVertex(new Vector3f(0.75f, 0.75f, -1));
				cubeWithHole.addVertex(new Vector3f(-0.75f, 0.75f, -1));

				cubeWithHole.addVertex(new Vector3f(-0.75f, -0.75f, 1));
				cubeWithHole.addVertex(new Vector3f(0.75f, -0.75f, 1));
				cubeWithHole.addVertex(new Vector3f(0.75f, 0.75f, 1));
				cubeWithHole.addVertex(new Vector3f(-0.75f, 0.75f, 1));

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

				node7.setLocalScale(new Vector3f(3.0f, 3.0f, 3.0f));
				node7.setLocalTranslation(new Vector3f(0, -10f, 0));

				final GeneratorNode node8 = new GeneratorNode(scene,
						new BooleanGenerator(
								IBooleanOperation.BoolOperation.INTERSECTION));

				new MeshNode(node8, "Cube1", new CubeMeshGenerator(
						new Vector3f(0, 0, 0), 1f).generateMesh(null));
				new MeshNode(node8, "Cube2", new CubeMeshGenerator(
						new Vector3f(0.1f, 0.2f, 0.3f), 1f).generateMesh(null));

				node8.setLocalScale(new Vector3f(3.0f, 3.0f, 3.0f));
				node8.setLocalTranslation(new Vector3f(-10f, -10f, 0));

				final GeneratorNode node9 = new GeneratorNode(scene,
						new BooleanGenerator(
								IBooleanOperation.BoolOperation.UNION));

				new MeshNode(node9, "Cube1", new CubeMeshGenerator(
						new Vector3f(0, 0, 0), 1f).generateMesh(null));
				new MeshNode(node9, "Cube2", new CubeMeshGenerator(
						new Vector3f(0.1f, 0.2f, 0.3f), 1f).generateMesh(null));

				node9.setLocalScale(new Vector3f(3.0f, 3.0f, 3.0f));
				node9.setLocalTranslation(new Vector3f(-20f, -10f, 0));

				final GeneratorNode node10 = new GeneratorNode(scene,
						new BooleanGenerator(
								IBooleanOperation.BoolOperation.DIFFERENCE));

				new MeshNode(node10, "Cube1", new CubeMeshGenerator(
						new Vector3f(0, 0, 0), 1f).generateMesh(null));
				new MeshNode(node10, "Cube2", new CubeMeshGenerator(
						new Vector3f(0.1f, 0.2f, 0.3f), 1f).generateMesh(null));

				node10.setLocalScale(new Vector3f(3.0f, 3.0f, 3.0f));
				node10.setLocalTranslation(new Vector3f(-30f, -10f, 0));

				final AbstractSpatialNode node11 = new MeshNode(scene,
						"Mesh Icosahedron", new IcosahedronMeshGenerator()
								.generateMesh(null));

				node11.setLocalScale(new Vector3f(3.0f, 3.0f, 3.0f));
				node11.setLocalTranslation(new Vector3f(-20f, 10f, 0));

				final AbstractSpatialNode node12 = new MeshNode(scene,
						"Mesh Geosphere", new GeosphereMeshGenerator(5)
								.generateMesh(null));

				node12.setLocalScale(new Vector3f(5.0f, 5.0f, 5.0f));
				node12.setLocalTranslation(new Vector3f(-30f, 10f, 0));
			}
		});

		sceneHolder = new SceneHolder(scene);
	}

	public ISceneHolder getSceneHolder() {
		return sceneHolder;
	}

	public Set<IToolDescriptor> getActiveTools() {
		return UIToolsPlugin.getDefault().getActiveTools(
				sceneHolder.getSelectionMode(),
				sceneHolder.getScene().getSceneSelection());
	}
}
