package net.untoldwind.moredread.model.renderer;

import net.untoldwind.moredread.model.mesh.EdgeId;
import net.untoldwind.moredread.model.mesh.FaceId;

import com.jme.renderer.ColorRGBA;

public interface IColorProvider {
	boolean isFaceVisible(FaceId faceIndex);

	ColorRGBA getFaceColor(FaceId faceIndex);

	boolean isEdgeVisible(EdgeId edgeIndex);

	ColorRGBA getEdgeColor(EdgeId edgeIndex);

	boolean isVertexVisible(int vertexIndex);

	ColorRGBA getVertexColor(int vertexIndex);
}
