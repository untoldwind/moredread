package net.untoldwind.moredread.model.io.impl;

import net.untoldwind.moredread.model.io.IFileReaderDescriptor;
import net.untoldwind.moredread.model.io.spi.IModelReader;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

public class FileReaderDescriptor implements IFileReaderDescriptor {
	private final String id;
	private final String label;
	private final String extension;
	private final IModelReader modelReader;

	public FileReaderDescriptor(final IConfigurationElement element)
			throws CoreException {
		id = element.getAttribute("id");
		label = element.getAttribute("label");
		extension = element.getAttribute("extension");
		modelReader = (IModelReader) element.createExecutableExtension("class");
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

}
