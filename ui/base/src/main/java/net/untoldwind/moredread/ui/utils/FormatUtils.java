package net.untoldwind.moredread.ui.utils;

import com.jme.math.FastMath;

public class FormatUtils {
	public static String formatLength(final float value) {
		return String.valueOf(value);
	}

	public static float parseLength(final String text) {
		try {
			return Float.parseFloat(text);
		} catch (final NumberFormatException e) {
			return Float.NaN;
		}
	}

	public static String formatAngle(final float value) {
		return String.valueOf(180.0f * value / FastMath.PI);
	}

}
