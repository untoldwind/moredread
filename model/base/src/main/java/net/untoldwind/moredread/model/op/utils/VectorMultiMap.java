package net.untoldwind.moredread.model.op.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.untoldwind.moredread.model.math.Vector3;

public class VectorMultiMap<T> {
	private final Map<VectorKey, Set<T>> map = new HashMap<VectorKey, Set<T>>();

	public void add(final Vector3 vector, final T value) {
		final VectorKey key = new VectorKey(vector);
		Set<T> values = map.get(key);

		if (values == null) {
			values = new HashSet<T>();
			map.put(key, values);
		}
		values.add(value);
	}

	public Set<T> get(final Vector3 vector) {
		final VectorKey key = new VectorKey(vector);
		return map.get(key);
	}

	public Collection<Set<T>> valueSets() {
		return map.values();
	}
}
