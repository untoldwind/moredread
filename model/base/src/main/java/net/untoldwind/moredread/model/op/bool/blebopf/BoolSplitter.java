package net.untoldwind.moredread.model.op.bool.blebopf;

import net.untoldwind.moredread.model.math.Plane;
import net.untoldwind.moredread.model.math.Vector3;

public class BoolSplitter {
	/**
	 * Returns the split point resulting from intersect a plane and a mesh face
	 * according to its specified relative edge.
	 * 
	 * @param plane
	 *            split plane
	 * @param m
	 *            mesh
	 * @param f
	 *            face
	 * @param e
	 *            relative edge index
	 * @return intersection point
	 */
	static Vector3 splitEdge(final Plane plane, final BoolFace f, final int e) {
		BoolVertex v1, v2;

		switch (e) {
		case 1:
			v1 = f.getVertex(0);
			v2 = f.getVertex(1);
			break;
		case 2:
			v1 = f.getVertex(1);
			v2 = f.getVertex(2);
			break;
		case 3:
			v1 = f.getVertex(2);
			v2 = f.getVertex(0);
			break;
		default:
			throw new RuntimeException("Invalid e = " + e);
		}

		final Vector3 p1 = v1.getPoint();
		final Vector3 p2 = v2.getPoint();

		return MathUtils.intersectPlane(plane, p1, p2);
	}

	/**
	 * Returns the segment resulting from intersect a plane and a mesh face.
	 * 
	 * @param plane
	 *            split plane
	 * @param m
	 *            mesh
	 * @param f
	 *            face
	 * @return segment if there is intersection, NULL otherwise
	 */
	static BoolSegment splitFace(final Plane plane, final BoolFace f) {
		final BoolVertex v1 = f.getVertex(0);
		final BoolVertex v2 = f.getVertex(1);
		final BoolVertex v3 = f.getVertex(2);

		// Classify face vertices
		final int tag1 = BoolTag.createTAG(MathUtils.classify(v1.getPoint(),
				plane));
		final int tag2 = BoolTag.createTAG(MathUtils.classify(v2.getPoint(),
				plane));
		final int tag3 = BoolTag.createTAG(MathUtils.classify(v3.getPoint(),
				plane));

		// Classify face according to its vertices classification
		final int tag = BoolTag.createTAG(tag1, tag2, tag3);

		final BoolSegment s = new BoolSegment();

		switch (tag) {
		case BoolTag.IN_IN_IN:
		case BoolTag.OUT_OUT_OUT:
		case BoolTag.ON_ON_ON:
			s.cfg1 = s.cfg2 = BoolSegment.createUndefinedCfg();
			break;

		case BoolTag.ON_OUT_OUT:
		case BoolTag.ON_IN_IN:
			s.v1 = f.getVertex(0);
			s.cfg1 = BoolSegment.createVertexCfg(1);
			s.cfg2 = BoolSegment.createUndefinedCfg();
			break;

		case BoolTag.OUT_ON_OUT:
		case BoolTag.IN_ON_IN:
			s.v1 = f.getVertex(1);
			s.cfg1 = BoolSegment.createVertexCfg(2);
			s.cfg2 = BoolSegment.createUndefinedCfg();
			break;

		case BoolTag.OUT_OUT_ON:
		case BoolTag.IN_IN_ON:
			s.v1 = f.getVertex(2);
			s.cfg1 = BoolSegment.createVertexCfg(3);
			s.cfg2 = BoolSegment.createUndefinedCfg();
			break;

		case BoolTag.ON_ON_IN:
		case BoolTag.ON_ON_OUT:
			s.v1 = f.getVertex(0);
			s.v2 = f.getVertex(1);
			s.cfg1 = BoolSegment.createVertexCfg(1);
			s.cfg2 = BoolSegment.createVertexCfg(2);
			break;

		case BoolTag.ON_OUT_ON:
		case BoolTag.ON_IN_ON:
			s.v1 = f.getVertex(0);
			s.v2 = f.getVertex(2);
			s.cfg1 = BoolSegment.createVertexCfg(1);
			s.cfg2 = BoolSegment.createVertexCfg(3);
			break;

		case BoolTag.OUT_ON_ON:
		case BoolTag.IN_ON_ON:
			s.v1 = f.getVertex(1);
			s.v2 = f.getVertex(2);
			s.cfg1 = BoolSegment.createVertexCfg(2);
			s.cfg2 = BoolSegment.createVertexCfg(3);
			break;

		case BoolTag.IN_OUT_ON:
		case BoolTag.OUT_IN_ON:
			s.v2 = f.getVertex(2);
			s.cfg1 = BoolSegment.createEdgeCfg(1);
			s.cfg2 = BoolSegment.createVertexCfg(3);
			break;

		case BoolTag.IN_ON_OUT:
		case BoolTag.OUT_ON_IN:
			s.v1 = f.getVertex(1);
			s.cfg1 = BoolSegment.createVertexCfg(2);
			s.cfg2 = BoolSegment.createEdgeCfg(3);
			break;

		case BoolTag.ON_IN_OUT:
		case BoolTag.ON_OUT_IN:
			s.v1 = f.getVertex(0);
			s.cfg1 = BoolSegment.createVertexCfg(1);
			s.cfg2 = BoolSegment.createEdgeCfg(2);
			break;

		case BoolTag.OUT_IN_IN:
		case BoolTag.IN_OUT_OUT:
			s.cfg1 = BoolSegment.createEdgeCfg(1);
			s.cfg2 = BoolSegment.createEdgeCfg(3);
			break;

		case BoolTag.OUT_IN_OUT:
		case BoolTag.IN_OUT_IN:
			s.cfg1 = BoolSegment.createEdgeCfg(1);
			s.cfg2 = BoolSegment.createEdgeCfg(2);
			break;

		case BoolTag.OUT_OUT_IN:
		case BoolTag.IN_IN_OUT:
			s.cfg1 = BoolSegment.createEdgeCfg(2);
			s.cfg2 = BoolSegment.createEdgeCfg(3);
			break;

		default:
			// wrong TAG!
			break;
		}

		return s;
	}
}
