package net.untoldwind.moredread.model.op.bool.bspfilter;

import static net.untoldwind.moredread.model.op.bool.bspfilter.VertexTag.IN;
import static net.untoldwind.moredread.model.op.bool.bspfilter.VertexTag.ON;
import static net.untoldwind.moredread.model.op.bool.bspfilter.VertexTag.OUT;

import java.util.HashMap;
import java.util.Map;

public enum TriangleTag {
	ON_ON_ON(ON, ON, ON),
	IN_IN_IN(IN, IN, IN),
	OUT_OUT_OUT(OUT, OUT, OUT),

	ON_ON_IN(ON, ON, IN),
	ON_IN_ON(ON, IN, ON),
	IN_ON_ON(IN, ON, ON),
	ON_ON_OUT(ON, ON, OUT),
	ON_OUT_ON(ON, OUT, ON),
	OUT_ON_ON(OUT, ON, ON),

	IN_IN_ON(IN, IN, ON),
	IN_ON_IN(IN, ON, IN),
	ON_IN_IN(ON, IN, IN),

	OUT_OUT_ON(OUT, OUT, ON),
	OUT_ON_OUT(OUT, ON, OUT),
	ON_OUT_OUT(ON, OUT, OUT),

	IN_OUT_OUT(IN, OUT, OUT),
	OUT_IN_OUT(OUT, IN, OUT),
	OUT_OUT_IN(OUT, OUT, IN),

	OUT_IN_IN(OUT, IN, IN),
	IN_OUT_IN(IN, OUT, IN),
	IN_IN_OUT(IN, IN, OUT),

	IN_ON_OUT(IN, ON, OUT),
	IN_OUT_ON(IN, OUT, ON),
	ON_IN_OUT(ON, IN, OUT),
	ON_OUT_IN(ON, OUT, IN),
	OUT_IN_ON(OUT, IN, ON),
	OUT_ON_IN(OUT, ON, IN);

	private static Map<Integer, TriangleTag> triangleByVertex;

	private VertexTag tag1;
	private VertexTag tag2;
	private VertexTag tag3;

	private TriangleTag(final VertexTag tag1, final VertexTag tag2,
			final VertexTag tag3) {
		this.tag1 = tag1;
		this.tag2 = tag2;
		this.tag3 = tag3;
	}

	public static TriangleTag fromVertexTags(final VertexTag tag1,
			final VertexTag tag2, final VertexTag tag3) {
		final int hash = hashVerticies(tag1, tag2, tag3);
		final TriangleTag tag = triangleByVertex.get(hash);

		if (tag == null) {
			throw new IllegalStateException("No tag for " + tag1 + " " + tag2
					+ " " + tag3);
		}

		return tag;
	}

	private static int hashVerticies(final VertexTag tag1,
			final VertexTag tag2, final VertexTag tag3) {
		return (tag1.ordinal() << 4) | (tag2.ordinal() << 2) | (tag3.ordinal());
	}

	static {
		triangleByVertex = new HashMap<Integer, TriangleTag>();

		for (final TriangleTag tag : values()) {
			final int hash = hashVerticies(tag.tag1, tag.tag2, tag.tag3);
			if (triangleByVertex.containsKey(hash)) {
				throw new IllegalStateException("Vertex hash not unique");
			}
			triangleByVertex.put(hash, tag);
		}
	}
}
