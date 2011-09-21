package net.untoldwind.moredread.model.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;

public class TestHelper {
	public static String readString(final InputStream in) throws IOException {
		final Reader reader = new InputStreamReader(in, "UTF-8");
		final StringWriter out = new StringWriter();
		final char[] buffer = new char[8192];
		int readed;

		while ((readed = reader.read(buffer)) > 0) {
			out.write(buffer, 0, readed);
		}

		return out.toString();
	}

}
