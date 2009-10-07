package net.untoldwind.moredread.model.io.impl;

import net.untoldwind.moredread.model.io.IFileWriterDescriptor;
import net.untoldwind.moredread.model.io.spi.IModelWriter;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

public class FileWriterDescriptor implements IFileWriterDescriptor {
	private final String id;
	private final String label;
	private final String extension;
	private final IModelWriter modelWriter;

	public FileWriterDescriptor(final IConfigurationElement element)
			throws CoreException {
		id = element.getAttribute("id");
		label = element.getAttribute("label");
		extension = element.getAttribute("extension");
		modelWriter = (IModelWriter) element.createExecutableExtension("class");
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
	public IModelWriter getModelWriter() {
		return modelWriter;
	}
}
