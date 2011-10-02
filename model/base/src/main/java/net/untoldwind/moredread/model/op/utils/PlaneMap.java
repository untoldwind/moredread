package net.untoldwind.moredread.model.op.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.untoldwind.moredread.model.math.Plane;

public class PlaneMap<T> {
	private final Map<VectorKey, List<T>> map = new HashMap<VectorKey, List<T>>();

	public void add(final Plane plane, final T value) {
		final VectorKey key = new VectorKey(plane.getNormal());
		List<T> values = map.get(key);

		if (values == null) {
			values = new ArrayList<T>();
			map.put(key, values);
		}
		values.add(value);
	}

	public List<T> get(final Plane plane) {
		final VectorKey key = new VectorKey(plane.getNormal());
		return map.get(key);
	}

	public Iterable<T> allValues() {
		return new Iterable<T>() {
			@Override
			public Iterator<T> iterator() {
				return new Iterator<T>() {
					Iterator<List<T>> outerIt = map.values().iterator();
					Iterator<T> innerIt = null;

					@Override
					public boolean hasNext() {
						if (innerIt != null && innerIt.hasNext()) {
							return true;
						}
						return outerIt.hasNext();
					}

					@Override
					public T next() {
						if (innerIt == null || !innerIt.hasNext()) {
							innerIt = outerIt.next().iterator();
						}
						return innerIt.next();
					}

					@Override
					public void remove() {
					}
				};
			}
		};
	}

	public Collection<List<T>> valueSets() {
		return map.values();
	}

	public int size() {
		return map.size();
	}

	public Set<Map.Entry<VectorKey, List<T>>> entrySet() {
		return map.entrySet();
	}

	public int totalSize() {
		int totalSize = 0;
		for (final List<T> valueSet : map.values()) {
			totalSize += valueSet.size();
		}
		return totalSize;
	}
}
