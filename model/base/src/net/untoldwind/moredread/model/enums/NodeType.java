package net.untoldwind.moredread.model.enums;

public enum NodeType {
	SCENE(0), GROUP(1), MESH(2), GENERATOR(3);

	private final int code;

	private NodeType(final int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static NodeType forCode(final int code) {
		for (final NodeType meshType : values()) {
			if (meshType.getCode() == code) {
				return meshType;
			}
		}
		throw new RuntimeException("Invalid code: " + code);
	}

}
