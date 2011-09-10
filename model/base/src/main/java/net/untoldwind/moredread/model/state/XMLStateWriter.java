package net.untoldwind.moredread.model.state;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.jme.math.Quaternion;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;

public class XMLStateWriter implements IStateWriter {
	private final Document document;
	private final Element element;

	public XMLStateWriter(final String name) {
		document = DocumentFactory.getInstance().createDocument();
		element = document.addElement(name);
	}

	public Document getDocument() {
		return document;
	}

	public XMLStateWriter(final Document document, final Element element) {
		this.document = document;
		this.element = element;
	}

	public void writeBoolean(final String tag, final boolean value)
			throws IOException {
		element.addElement(tag).setText(String.valueOf(value));
	}

	public void writeInt(final String tag, final int value) throws IOException {
		element.addElement(tag).setText(String.valueOf(value));
	}

	public void writeFloat(final String tag, final float value)
			throws IOException {
		element.addElement(tag).setText(String.valueOf(value));
	}

	@Override
	public void writeString(final String tag, final String value)
			throws IOException {
		element.addElement(tag).setText(value);
	}

	public void writeVector2f(final String tag, final Vector2f value)
			throws IOException {
		final Element vectorElement = element.addElement(tag);

		vectorElement.addAttribute("x", String.valueOf(value.x));
		vectorElement.addAttribute("y", String.valueOf(value.y));
	}

	public void writeVector3f(final String tag, final Vector3f value)
			throws IOException {
		final Element vectorElement = element.addElement(tag);

		vectorElement.addAttribute("x", String.valueOf(value.x));
		vectorElement.addAttribute("y", String.valueOf(value.y));
		vectorElement.addAttribute("z", String.valueOf(value.z));
	}

	@Override
	public void writeQuaternion(final String tag, final Quaternion quaterion)
			throws IOException {
		final Element quaternionElement = element.addElement(tag);

		quaternionElement.addAttribute("x", String.valueOf(quaterion.x));
		quaternionElement.addAttribute("y", String.valueOf(quaterion.y));
		quaternionElement.addAttribute("z", String.valueOf(quaterion.z));
		quaternionElement.addAttribute("w", String.valueOf(quaterion.w));
	}

	@Override
	public void writeObject(final String tag, final IStateHolder obj)
			throws IOException {
		final Element objectElement = element.addElement(tag);

		objectElement.addAttribute("class", obj.getClass().getName());
		obj.writeState(new XMLStateWriter(document, objectElement));
	}

	@Override
	public <T extends IStateHolder> void writeTypedArray(final String tag,
			final T[] arr) throws IOException {
		final Element arrayElement = element.addElement(tag);

		arrayElement.addAttribute("class", arr.getClass().getComponentType()
				.getName());
		arrayElement.addAttribute("size", String.valueOf(arr.length));
		final XMLStateWriter writer = new XMLStateWriter(document, arrayElement);
		for (final T element : arr) {
			element.writeState(writer);
		}
	}

	public void writeArray(final String tag, final IStateHolder[] arr)
			throws IOException {
		final Element arrayElement = element.addElement(tag);

		arrayElement.addAttribute("size", String.valueOf(arr.length));
		final XMLStateWriter writer = new XMLStateWriter(document, arrayElement);
		for (final IStateHolder element : arr) {
			element.writeState(writer);
		}
	}

	@Override
	public <T extends IStateHolder> void writeTypedList(final String tag,
			final Class<T> type, final List<T> collection) throws IOException {
		final Element arrayElement = element.addElement(tag);

		arrayElement.addAttribute("class", type.getClass().getName());
		arrayElement.addAttribute("size", String.valueOf(collection.size()));
		final XMLStateWriter writer = new XMLStateWriter(document, arrayElement);
		for (final IStateHolder element : collection) {
			element.writeState(writer);
		}
	}

	@Override
	public <T extends IStateHolder> void writeUntypedList(final String tag,
			final List<T> collection) throws IOException {
		final Element arrayElement = element.addElement(tag);

		arrayElement.addAttribute("size", String.valueOf(collection.size()));
		final XMLStateWriter writer = new XMLStateWriter(document, arrayElement);
		for (final IStateHolder element : collection) {
			writer.writeObject("element", element);
		}
	}

	public static String toXML(final IStateHolder stateHolder) {
		try {
			final Document document = DocumentFactory.getInstance()
					.createDocument();
			final Element objectElement = document.addElement("state");
			objectElement.addAttribute("class", stateHolder.getClass()
					.getName());

			final XMLStateWriter stateWriter = new XMLStateWriter(document,
					objectElement);

			stateHolder.writeState(stateWriter);

			final StringWriter out = new StringWriter();
			final XMLWriter xmlWriter = new XMLWriter(out,
					OutputFormat.createPrettyPrint());
			xmlWriter.write(document);
			xmlWriter.flush();

			return out.toString();
		} catch (final IOException e) {
			throw new RuntimeException("Very unexpected IOException", e);
		}
	}
}
