package net.untoldwind.moredread.model.io;

import net.untoldwind.moredread.model.io.spi.IModelWriter;

public interface IFileWriterDescriptor {
	String getId();

	String getLabel();

	String getExtension();

	IModelWriter getModelWriter();
}
