package net.untoldwind.moredread.model.op.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.untoldwind.moredread.model.math.Plane;

public class PlaneMap<T> {
	private final List<T> allValues = new ArrayList<T>();
	private final Map<VectorKey, List<T>> map = new HashMap<VectorKey, List<T>>();

	public void add(final Plane plane, final T value) {
		final VectorKey key = new VectorKey(plane.getNormal());
		List<T> values = map.get(key);

		if (values == null) {
			values = new ArrayList<T>();
			map.put(key, values);
		}
		values.add(value);
		allValues.add(value);
	}

	public List<T> get(final Plane plane) {
		final VectorKey key = new VectorKey(plane.getNormal());
		return map.get(key);
	}

	public List<T> allValues() {
		return allValues;

	}

	public Collection<List<T>> valueSets() {
		return map.values();
	}

	public int size() {
		return map.size();
	}

	public int totalSize() {
		int totalSize = 0;
		for (final List<T> valueSet : map.values()) {
			totalSize += valueSet.size();
		}
		return totalSize;
	}
}
