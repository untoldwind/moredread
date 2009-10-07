package net.untoldwind.moredread.model.io;

import net.untoldwind.moredread.model.io.spi.IModelReader;

public interface IFileReaderDescriptor {
	String getId();

	String getLabel();

	String getExtension();

	IModelReader getModelReader();
}
