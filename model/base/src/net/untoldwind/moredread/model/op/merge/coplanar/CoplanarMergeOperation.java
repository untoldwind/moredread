package net.untoldwind.moredread.model.op.merge.coplanar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.untoldwind.moredread.model.mesh.IFace;
import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.Mesh;
import net.untoldwind.moredread.model.op.IUnaryOperation;

import com.jme.math.FastMath;
import com.jme.math.Vector3f;

public class CoplanarMergeOperation implements IUnaryOperation {
	@Override
	public Mesh<?> perform(final IMesh mesh) {
		final Set<Integer> visitedFaces = new HashSet<Integer>();
		final List<List<Integer>> facesToMerge = new ArrayList<List<Integer>>();

		for (final IFace face : mesh.getFaces()) {
			if (visitedFaces.contains(face.getIndex())) {
				continue;
			}
			final List<Integer> coplanarFaces = new ArrayList<Integer>();

			collectCoplanarNeigbours(face, coplanarFaces, visitedFaces);

			if (coplanarFaces.size() > 1) {
				// There is no point to add lists with single entry, there is
				// nothing to merge
				facesToMerge.add(coplanarFaces);
			}
		}
		// TODO Auto-generated method stub
		return null;
	}

	private void collectCoplanarNeigbours(final IFace face,
			final List<Integer> coplanarFaces, final Set<Integer> visitedFaces) {
		visitedFaces.add(face.getIndex());
		coplanarFaces.add(face.getIndex());
		for (final IFace neighbour : face.getNeighbours()) {
			if (!visitedFaces.contains(neighbour.getIndex())
					&& checkCoplanar(face, neighbour)) {
				collectCoplanarNeigbours(neighbour, coplanarFaces, visitedFaces);
			}
		}
	}

	private boolean checkCoplanar(final IFace face1, final IFace face2) {
		// Using angleBetween might be a better (more user-friendly) approach
		final Vector3f diff = face1.getMeanNormal().subtract(
				face2.getMeanNormal());

		return FastMath.abs(diff.x) < FastMath.FLT_EPSILON
				&& FastMath.abs(diff.y) < FastMath.FLT_EPSILON
				&& FastMath.abs(diff.z) < FastMath.FLT_EPSILON;
	}
}
