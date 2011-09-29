package net.untoldwind.moredread.model.state;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.untoldwind.moredread.model.math.Quaternion;
import net.untoldwind.moredread.model.math.Vector3;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.jme.math.Vector2f;
import com.jme.renderer.ColorRGBA;

public class XMLStateReader implements IStateReader {
	private final Document document;
	private final Iterator<Element> childIterator;

	@SuppressWarnings("unchecked")
	public XMLStateReader(final Document document, final Element element) {
		this.document = document;
		this.childIterator = element.elementIterator();
	}

	@Override
	public boolean readBoolean() throws IOException {
		return Boolean.parseBoolean(childIterator.next().getTextTrim());
	}

	@Override
	public int readInt() throws IOException {
		return Integer.parseInt(childIterator.next().getTextTrim());
	}

	@Override
	public long readLong() throws IOException {
		return Long.parseLong(childIterator.next().getTextTrim());
	}

	@Override
	public int[] readIntArray() throws IOException {
		final Element arrayElement = childIterator.next();
		final int size = Integer.parseInt(arrayElement.attributeValue("size"));
		@SuppressWarnings("unchecked")
		final List<Element> children = arrayElement.elements();
		final int[] result = new int[size];

		for (int i = 0; i < size; i++) {
			result[i] = Integer.parseInt(children.get(i).getTextTrim());
		}

		return result;
	}

	@Override
	public float readFloat() throws IOException {
		return Float.parseFloat(childIterator.next().getTextTrim());
	}

	@Override
	public String readString() throws IOException {
		return childIterator.next().getText();
	}

	@Override
	public Vector2f readVector2f() throws IOException {
		final Element vectorElement = childIterator.next();

		return new Vector2f(
				Float.parseFloat(vectorElement.attributeValue("x")),
				Float.parseFloat(vectorElement.attributeValue("y")));
	}

	@Override
	public Vector3 readVector3() throws IOException {
		final Element vectorElement = childIterator.next();

		return new Vector3(Float.parseFloat(vectorElement.attributeValue("x")),
				Float.parseFloat(vectorElement.attributeValue("y")),
				Float.parseFloat(vectorElement.attributeValue("z")));
	}

	@Override
	public Quaternion readQuaternion() throws IOException {
		final Element quaternionElement = childIterator.next();

		return new Quaternion(Float.parseFloat(quaternionElement
				.attributeValue("x")), Float.parseFloat(quaternionElement
				.attributeValue("y")), Float.parseFloat(quaternionElement
				.attributeValue("z")), Float.parseFloat(quaternionElement
				.attributeValue("w")));
	}

	@Override
	public ColorRGBA readColor() throws IOException {
		final Element colorElement = childIterator.next();

		return new ColorRGBA(
				Float.parseFloat(colorElement.attributeValue("r")),
				Float.parseFloat(colorElement.attributeValue("g")),
				Float.parseFloat(colorElement.attributeValue("b")),
				Float.parseFloat(colorElement.attributeValue("a")));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IStateHolder> T readObject() throws IOException {
		final Element objectElement = childIterator.next();
		final String className = objectElement.attributeValue("class");
		try {
			final XMLStateReader reader = new XMLStateReader(document,
					objectElement);
			final Class<T> clazz = (Class<T>) Class.forName(className);
			final Constructor<T> constructor = clazz.getDeclaredConstructor();
			constructor.setAccessible(true);
			final T instance = constructor.newInstance();
			instance.readState(reader);

			return instance;
		} catch (final Exception e) {
			throw new IOException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IStateHolder> T readObject(
			final IInstanceCreator<T> creator) throws IOException {
		final Element objectElement = childIterator.next();
		final String className = objectElement.attributeValue("class");
		try {
			final T instance = creator.createInstance((Class<T>) Class
					.forName(className));
			final XMLStateReader reader = new XMLStateReader(document,
					objectElement);
			instance.readState(reader);

			return instance;
		} catch (final Exception e) {
			throw new IOException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IStateHolder> T[] readTypedArray() throws IOException {
		final Element arrayElement = childIterator.next();
		final String className = arrayElement.attributeValue("class");
		final int len = Integer.parseInt(arrayElement.attributeValue("size"));
		try {
			final XMLStateReader reader = new XMLStateReader(document,
					arrayElement);

			final T[] result = (T[]) Array.newInstance(
					Class.forName(className), len);

			for (int i = 0; i < len; i++) {
				final Class<T> clazz = (Class<T>) Class.forName(className);
				final Constructor<T> constructor = clazz
						.getDeclaredConstructor();
				constructor.setAccessible(true);
				final T instance = constructor.newInstance();
				instance.readState(reader);
				result[i] = instance;
			}

			return result;
		} catch (final Exception e) {
			throw new IOException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IStateHolder> List<T> readTypedList() throws IOException {
		final Element arrayElement = childIterator.next();
		final String className = arrayElement.attributeValue("class");
		final int len = Integer.parseInt(arrayElement.attributeValue("size"));
		try {
			final XMLStateReader reader = new XMLStateReader(document,
					arrayElement);
			final List<T> result = new ArrayList<T>();

			for (int i = 0; i < len; i++) {
				final Class<T> clazz = (Class<T>) Class.forName(className);
				final Constructor<T> constructor = clazz
						.getDeclaredConstructor();
				constructor.setAccessible(true);
				final T instance = constructor.newInstance();
				instance.readState(reader);
				result.add(instance);
			}

			return result;
		} catch (final Exception e) {
			throw new IOException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IStateHolder> List<T> readUntypedList()
			throws IOException {
		final Element arrayElement = childIterator.next();
		final int size = Integer.parseInt(arrayElement.attributeValue("size"));
		final XMLStateReader reader = new XMLStateReader(document, arrayElement);

		final List<T> result = new ArrayList<T>();
		for (int i = 0; i < size; i++) {
			result.add((T) reader.readObject());
		}

		return result;
	}

	@Override
	public <T extends IStateHolder> List<T> readUntypedList(
			final IInstanceCreator<T> creator) throws IOException {
		final Element arrayElement = childIterator.next();
		final int size = Integer.parseInt(arrayElement.attributeValue("size"));
		final XMLStateReader reader = new XMLStateReader(document, arrayElement);

		final List<T> result = new ArrayList<T>();
		for (int i = 0; i < size; i++) {
			result.add(reader.readObject(creator));
		}

		return result;
	}

	public static <T extends IStateHolder> T fromXML(final String xml) {
		return fromXML(new StringReader(xml));
	}

	public static <T extends IStateHolder> T fromXML(final InputStream in) {
		try {
			final SAXReader reader = new SAXReader();
			reader.setIgnoreComments(true);
			reader.setStripWhitespaceText(true);
			final Document document = reader.read(in);

			return fromXML(document);
		} catch (final Exception e) {
			throw new RuntimeException("Very unexpected IOException", e);
		}
	}

	public static <T extends IStateHolder> T fromXML(final Reader in) {
		try {
			final SAXReader reader = new SAXReader();
			reader.setIgnoreComments(true);
			reader.setStripWhitespaceText(true);
			final Document document = reader.read(in);

			return fromXML(document);
		} catch (final Exception e) {
			throw new RuntimeException("Very unexpected IOException", e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends IStateHolder> T fromXML(final Document document) {
		try {
			final Element element = document.getRootElement();
			final String className = element.attributeValue("class");
			final XMLStateReader stateReader = new XMLStateReader(document,
					element);

			final T instance = (T) Class.forName(className).newInstance();

			instance.readState(stateReader);

			return instance;
		} catch (final Exception e) {
			throw new RuntimeException("Very unexpected IOException", e);
		}
	}
}
