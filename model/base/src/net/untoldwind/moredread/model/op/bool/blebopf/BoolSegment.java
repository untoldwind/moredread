package net.untoldwind.moredread.model.op.bool.blebopf;

public class BoolSegment {
	private final static int UNDEFINED = 0;

	// Cfg : Configuration of the vertices
	// Values:
	// 20 IN,
	// 1X Intersected edge X{1,2,3} of the face,
	// 0X Coincident vertice X{1,2,3} of the face,
	// 0 otherwise
	int cfg1, cfg2;
	BoolVertex v1, v2; // if cfgX >0, vX is the vertice index of the face

	/**
	 * Sorts the segment according to ends configuration. The criterion to sort
	 * is ...
	 * 
	 * UNDEFINED < VERTEX < EDGE < IN cfg1 > cfg2
	 * 
	 * so ...
	 * 
	 * VERTEX(cfg1) => UNDEFINED(cfg2) || VERTEX(cfg2) EDGE(cfg1) =>
	 * UNDEFINED(cfg2) || VERTEX(cfg2) || EDGE(cfg2) IN(cfg1) => UNDEFINED(cfg2)
	 * || VERTEX(cfg2) || EDGE(cfg2) || IN(cfg2)
	 */
	void sort() {
		if (cfg1 < cfg2) {
			invert();
		}
	}

	/**
	 * Inverts the segment, swapping ends data.
	 */
	void invert() {
		final BoolVertex auxV = v1;
		v1 = v2;
		v2 = auxV;
		final int aux = cfg1;
		cfg1 = cfg2;
		cfg2 = aux;
	}

	/**
	 * Returns if the specified end segment configuration is IN.
	 * 
	 * @return true if the specified end segment configuration is IN, false
	 *         otherwise
	 */
	static boolean isIn(final int cfg) {
		return (cfg == 20);
	}

	/**
	 * Returns if the specified end segment configuration is EDGE.
	 * 
	 * @return true if the specified end segment configuration is EDGE, false
	 *         otherwise
	 */
	static boolean isEdge(final int cfg) {
		return (cfg > 10) && (cfg < 20);
	}

	/**
	 * Returns if the specified end segment configuration is VERTEX.
	 * 
	 * @return true if the specified end segment configuration is VERTEX, false
	 *         otherwise
	 */
	static boolean isVertex(final int cfg) {
		return (cfg != UNDEFINED) && (cfg < 10);
	}

	/**
	 * Returns if the specified end segment configuration is DEFINED (not
	 * UNDEFINED).
	 * 
	 * @return true if the specified end segment configuration is DEFINED, false
	 *         otherwise
	 */
	static boolean isDefined(final int cfg) {
		return (cfg != UNDEFINED);
	}

	/**
	 * Returns if the specified end segment configuration is UNDEFINED.
	 * 
	 * @return true if the specified end segment configuration is UNDEFINED,
	 *         false otherwise
	 */
	static boolean isUndefined(final int cfg) {
		return (cfg == UNDEFINED);
	}

	/**
	 * Returns the end segment UNDEFINED configuration.
	 * 
	 * @return end segment UNDEFINED configuration
	 */
	static int createUndefinedCfg() {
		return UNDEFINED;
	}

	/**
	 * Returns the end segment configuration for the specified relative edge
	 * index.
	 * 
	 * @return end segment configuration for the specified relative edge index
	 */
	static int createEdgeCfg(final int edge) {
		return 10 + edge;
	}

	/**
	 * Returns the end segment configuration for the specified relative vertex
	 * index.
	 * 
	 * @return end segment configuration for the specified relative vertex index
	 */
	static int createVertexCfg(final int vertex) {
		return vertex;
	}

	/**
	 * Returns the relative edge index from the specified end segment
	 * configuration.
	 * 
	 * @return relative edge index from the specified end segment configuration
	 */
	static int getEdge(final int cfg) {
		return cfg - 10;
	}

	/**
	 * Returns the end segment IN configuration.
	 * 
	 * @return end segment IN configuration
	 */
	int createInCfg() {
		return 20;
	}

	/**
	 * Returns the relative edge index between two relative vertex indices.
	 * 
	 * @param v1
	 *            relative vertex index
	 * @param v2
	 *            relative vertex index
	 * @return relative edge index between two relative vertex indices, -1
	 *         otherwise
	 */
	int getEdgeBetween(final int v1, final int v2) {
		if ((v1 == 1 && v2 == 2) || (v1 == 2 && v2 == 1)) {
			return 1;
		}
		if ((v1 == 3 && v2 == 2) || (v1 == 2 && v2 == 3)) {
			return 2;
		}
		if ((v1 == 1 && v2 == 3) || (v1 == 3 && v2 == 1)) {
			return 3;
		}
		return -1;
	}

	/**
	 * Returns the relative vertex index from the specified end segment
	 * configuration.
	 * 
	 * @return relative vertex index from the specified end segment
	 *         configuration
	 */
	int getVertex(final int cfg) {
		return cfg;
	}

	/**
	 * Returns if a relative vertex index is on a relative edge index.
	 * 
	 * @param v
	 *            relative vertex index
	 * @param e
	 *            relative edge index
	 * @return true if the relative vertex index is on the relative edge index,
	 *         false otherwise.
	 */
	boolean isOnEdge(final int v, final int e) {
		if (v == 1 && (e == 1 || e == 3)) {
			return true;
		}
		if (v == 2 && (e == 1 || e == 2)) {
			return true;
		}
		if (v == 3 && (e == 2 || e == 3)) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the inner segment configuration.
	 * 
	 * @return inner segment configuration
	 */
	int getConfig() {
		if (isUndefined(cfg1)) {
			return cfg2;
		} else if (isUndefined(cfg2)) {
			return cfg1;
		} else if (isVertex(cfg1)) {
			// v1 is vertex
			if (isVertex(cfg2)) {
				// v2 is vertex
				return createEdgeCfg(getEdgeBetween(getVertex(cfg1),
						getVertex(cfg2)));
			} else if (isEdge(cfg2)) {
				// v2 is edge
				if (isOnEdge(cfg1, getEdge(cfg2))) {
					return cfg2;
				} else {
					return createInCfg(); // IN
				}
			} else {
				return createInCfg(); // IN
			}
		} else if (isEdge(cfg1)) {
			// v1 is edge
			if (isVertex(cfg2)) {
				// v2 is vertex
				if (isOnEdge(cfg2, getEdge(cfg1))) {
					return cfg1;
				} else {
					return createInCfg(); // IN
				}
			} else if (isEdge(cfg2)) {
				// v2 is edge
				if (cfg1 == cfg2) {
					return cfg1;
				} else {
					return createInCfg(); // IN
				}
			} else {
				return createInCfg(); // IN
			}
		} else {
			return createInCfg(); // IN
		}
	}

}
