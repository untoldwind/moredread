package net.untoldwind.moredread.model.state;

import java.io.IOException;

public interface IStateHolder {
	void readState(IStateReader reader) throws IOException;

	void writeState(IStateWriter writer) throws IOException;
}
