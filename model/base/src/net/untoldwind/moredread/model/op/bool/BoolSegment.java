package net.untoldwind.moredread.model.op.bool;

import net.untoldwind.moredread.model.mesh.IVertex;

public class BoolSegment {
	private final static int UNDEFINED = 0;

	// Cfg : Configuration of the vertices
	// Values:
	// 20 IN,
	// 1X Intersected edge X{1,2,3} of the face,
	// 0X Coincident vertice X{1,2,3} of the face,
	// 0 otherwise
	int cfg1, cfg2;
	IVertex v1, v2; // if cfgX >0, vX is the vertice index of the face

	/**
	 * Inverts the segment, swapping ends data.
	 */
	void invert() {
		final IVertex auxV = v1;
		v1 = v2;
		v2 = auxV;
		final int aux = cfg1;
		cfg1 = cfg2;
		cfg2 = aux;
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

}
