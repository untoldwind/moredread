package net.untoldwind.moredread.model.io;

import net.untoldwind.moredread.model.io.spi.IModelReader;
import net.untoldwind.moredread.model.io.spi.IModelWriter;

public interface IFileIODescriptor {
	String getId();

	String getLabel();

	String getExtension();

	IModelReader getModelReader();

	IModelWriter getModelWriter();
}
