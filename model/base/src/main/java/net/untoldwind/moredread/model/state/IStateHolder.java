package net.untoldwind.moredread.model.state;

import java.io.IOException;

public interface IStateHolder {
	void writeState(IStateWriter writer) throws IOException;
}
