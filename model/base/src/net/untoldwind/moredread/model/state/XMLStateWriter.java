package net.untoldwind.moredread.model.state;

import java.io.IOException;
import java.util.Collection;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;

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
		vectorElement.addAttribute("z", String.valueOf(value.y));
	}

	public void writeObject(final String tag, final IStateHolder obj)
			throws IOException {
		final Element objectElement = element.addElement(tag);

		obj.writeState(new XMLStateWriter(document, objectElement));
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

	public void writeCollection(final String tag,
			final Collection<? extends IStateHolder> collection)
			throws IOException {
		final Element arrayElement = element.addElement(tag);

		arrayElement.addAttribute("size", String.valueOf(collection.size()));
		final XMLStateWriter writer = new XMLStateWriter(document, arrayElement);
		for (final IStateHolder element : collection) {
			element.writeState(writer);
		}
	}
}
