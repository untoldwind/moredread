package net.untoldwind.moredread.model.mesh;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import net.untoldwind.moredread.model.enums.GeometryType;
import net.untoldwind.moredread.model.enums.MeshType;
import net.untoldwind.moredread.model.state.IStateHolder;

public abstract class Mesh<FaceK extends FaceId, FaceT extends Face<?, ?>>
		extends EdgeGeometry<IMesh> implements IMesh, IStateHolder {
	protected final Map<FaceK, FaceT> faces;

	protected Mesh() {
		faces = new LinkedHashMap<FaceK, FaceT>();
	}

	@Override
	public GeometryType getGeometryType() {
		return GeometryType.MESH;
	}

	@Override
	public Collection<FaceT> getFaces() {
		return faces.values();
	}

	public FaceT getFace(final FaceId faceIndex) {
		return faces.get(faceIndex);
	}

	public void removeFaces(final Set<FaceId> faceIds) {
		for (final FaceId faceId : faceIds) {
			final FaceT face = faces.get(faceId);
			if (face != null) {
				face.remove();
				faces.remove(faceId);
			}
		}
		markDirty();
	}

	public abstract MeshType getMeshType();
}
