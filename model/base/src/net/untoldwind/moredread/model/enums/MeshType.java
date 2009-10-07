package net.untoldwind.moredread.model.enums;

public enum MeshType {
	TRIANGLE(0), QUAD(1), POLY(2);

	private final int code;

	private MeshType(final int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static MeshType forCode(final int code) {
		for (final MeshType meshType : values()) {
			if (meshType.getCode() == code) {
				return meshType;
			}
		}
		throw new RuntimeException("Invalid code: " + code);
	}
}
