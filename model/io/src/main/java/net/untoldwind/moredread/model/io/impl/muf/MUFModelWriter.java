package net.untoldwind.moredread.model.io.impl.muf;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import net.untoldwind.moredread.model.enums.MeshType;
import net.untoldwind.moredread.model.io.spi.IModelWriter;
import net.untoldwind.moredread.model.mesh.IFace;
import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.IPoint;
import net.untoldwind.moredread.model.mesh.IVertex;
import net.untoldwind.moredread.model.scene.GeneratorNode;
import net.untoldwind.moredread.model.scene.Group;
import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.ISceneVisitor;
import net.untoldwind.moredread.model.scene.MeshNode;
import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.model.scene.AbstractSpatialNode;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

/**
 * Most Useless Format writer.
 * 
 * This is considered to be an example for scene io.
 */
public class MUFModelWriter implements IModelWriter {

	@Override
	public void writeScene(final Scene scene, final OutputStream out)
			throws IOException {
		final Document document = DocumentFactory.getInstance()
				.createDocument();

		document.setRootElement(scene.accept(new DocumentSceneVisitor()));

		final XMLWriter writer = new XMLWriter(out, OutputFormat
				.createPrettyPrint());

		writer.write(document);
		writer.flush();
		writer.close();
	}

	private static class DocumentSceneVisitor implements ISceneVisitor<Element> {
		@Override
		public Element visitScene(final Scene scene) {
			final Element sceneElement = DocumentFactory.getInstance()
					.createElement("scene");

			for (final INode node : scene.getChildren()) {
				sceneElement.add(node.accept(this));
			}

			return sceneElement;
		}

		@Override
		public Element visitGroup(final Group group) {
			final Element groupElement = DocumentFactory.getInstance()
					.createElement("group");

			addTransformationElements(groupElement, group);

			for (final INode node : group.getChildren()) {
				groupElement.add(node.accept(this));
			}
			return groupElement;
		}

		@Override
		public Element visitGeneratorNode(final GeneratorNode generatorNode) {
			final Element nodeElement = DocumentFactory.getInstance()
					.createElement("generator-node");

			addTransformationElements(nodeElement, generatorNode);

			return nodeElement;
		}

		@Override
		public Element visitMeshNode(final MeshNode node) {
			final Element nodeElement = DocumentFactory.getInstance()
					.createElement("mesh-node");

			addTransformationElements(nodeElement, node);

			final IMesh mesh = node.getGeometry();

			final Element meshElement = nodeElement.addElement("mesh");

			meshElement.addAttribute("type", mesh.getMeshType().toString());

			final Element verticesELement = meshElement.addElement("vertices");

			for (final IPoint vertex : mesh.getVertices()) {
				final Vector3f point = vertex.getPoint();
				final Element vertexElement = verticesELement
						.addElement("vertex");

				vertexElement.addAttribute("x", String.valueOf(point.x));
				vertexElement.addAttribute("y", String.valueOf(point.y));
				vertexElement.addAttribute("z", String.valueOf(point.z));
			}

			final Element facesElement = meshElement.addElement("faces");

			if (mesh.getMeshType() == MeshType.POLY) {
				for (final IFace face : mesh.getFaces()) {
					final Element faceElement = facesElement.addElement("face");
					final int[] stripCount = face.getPolygonStripCounts();
					final Iterator<? extends IVertex> it = face.getVertices()
							.iterator();

					for (int i = 0; i < stripCount.length; i++) {
						final Element stripElement = faceElement
								.addElement("srip");

						for (int j = 0; j < stripCount[i]; j++) {
							final Element pointElement = stripElement
									.addElement("point");
							pointElement.addAttribute("index", String
									.valueOf(it.next().getIndex()));
						}
					}
				}
			} else {
				for (final IFace face : mesh.getFaces()) {
					final Element faceElement = facesElement.addElement("face");

					for (final IVertex point : face.getVertices()) {
						final Element pointElement = faceElement
								.addElement("point");
						pointElement.addAttribute("index", String.valueOf(point
								.getIndex()));
					}
				}
			}

			return nodeElement;
		}

		protected void addTransformationElements(final Element element,
				final AbstractSpatialNode node) {
			final Vector3f localTranslation = node.getLocalTranslation();
			final Vector3f localScale = node.getLocalScale();
			final Quaternion localRotation = node.getLocalRotation();

			if (node.getName() != null) {
				element.addAttribute("name", node.getName());
			}
			element.addAttribute("tx", String.valueOf(localTranslation.x));
			element.addAttribute("ty", String.valueOf(localTranslation.y));
			element.addAttribute("tz", String.valueOf(localTranslation.z));
			element.addAttribute("sx", String.valueOf(localScale.x));
			element.addAttribute("sy", String.valueOf(localScale.y));
			element.addAttribute("sz", String.valueOf(localScale.z));
			element.addAttribute("rx", String.valueOf(localRotation.x));
			element.addAttribute("ry", String.valueOf(localRotation.y));
			element.addAttribute("rz", String.valueOf(localRotation.z));
			element.addAttribute("rw", String.valueOf(localRotation.w));
		}
	}
}
