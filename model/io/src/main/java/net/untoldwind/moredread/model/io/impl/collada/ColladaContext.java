package net.untoldwind.moredread.model.io.impl.collada;

import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import net.untoldwind.moredread.model.io.ModelIOPlugin;

import org.collada._2005._11.colladaschema.COLLADA;

public class ColladaContext {
	public final static String VERSION = "1.4.1";
	JAXBContext jaxbContext;

	private static ColladaContext instance;

	private ColladaContext() {
		try {
			jaxbContext = JAXBContext.newInstance(COLLADA.class);
		} catch (final JAXBException e) {
			ModelIOPlugin.getDefault().log(e);
		}
	}

	public Marshaller createMarshaller() throws IOException {
		try {
			return jaxbContext.createMarshaller();
		} catch (final JAXBException e) {
			ModelIOPlugin.getDefault().log(e);

			throw new IOException(e);
		}
	}

	public DatatypeFactory createDatatypeFactory() throws IOException {
		try {
			return DatatypeFactory.newInstance();
		} catch (final DatatypeConfigurationException e) {
			ModelIOPlugin.getDefault().log(e);

			throw new IOException(e);
		}
	}

	public static ColladaContext getInstance() {
		if (instance == null) {
			instance = new ColladaContext();
		}
		return instance;
	}
}
