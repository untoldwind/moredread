package net.untoldwind.moredread.model.op.utils;

import java.util.ArrayList;

public class IndexList extends ArrayList<Integer> {

	private static final long serialVersionUID = 1L;

	public IndexList() {
		super();
	}

	public IndexList(final int initialCapacity) {
		super(initialCapacity);
	}

	@Override
	public Integer get(final int idx) {
		return super.get(fixIndex(idx));
	}

	public void remove(int idx1, int idx2) {
		idx1 = fixIndex(idx1);
		idx2 = fixIndex(idx2);

		if (idx1 > idx2) {
			remove(idx1);
			remove(idx2);
		} else if (idx1 < idx2) {
			remove(idx2);
			remove(idx1);
		} else {
			remove(idx1);
		}
	}

	private int fixIndex(final int idx) {
		final int size = size();
		if (idx < 0) {
			return idx + size;
		} else if (idx >= size) {
			return idx - size;
		}
		return idx;
	}
}
