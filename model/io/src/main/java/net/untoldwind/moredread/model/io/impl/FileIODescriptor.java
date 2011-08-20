package net.untoldwind.moredread.model.io.impl;

import net.untoldwind.moredread.model.io.IFileIODescriptor;
import net.untoldwind.moredread.model.io.spi.IModelReader;
import net.untoldwind.moredread.model.io.spi.IModelWriter;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

public class FileIODescriptor implements IFileIODescriptor {
	private final String id;
	private final String label;
	private final String extension;
	private final IModelReader modelReader;
	private final IModelWriter modelWriter;

	public FileIODescriptor(final IConfigurationElement element)
			throws CoreException {
		id = element.getAttribute("id");
		label = element.getAttribute("label");
		extension = element.getAttribute("extension");
		modelReader = (IModelReader) element
				.createExecutableExtension("readerClass");
		modelWriter = (IModelWriter) element
				.createExecutableExtension("writerClass");
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public String getExtension() {
		return extension;
	}

	@Override
	public IModelReader getModelReader() {
		return modelReader;
	}

	@Override
	public IModelWriter getModelWriter() {
		return modelWriter;
	}

}
