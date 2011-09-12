package net.untoldwind.moredread.model.scene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import net.untoldwind.moredread.model.mesh.EdgeId;
import net.untoldwind.moredread.model.scene.event.ISceneSelectionChangeListener;
import net.untoldwind.moredread.model.scene.event.SceneSelectionChangeEvent;

public class SceneSelection implements ISceneSelection {
	private final Scene scene;
	private Set<INode> selectedNodes;
	private final Set<IComposite> selectedParentNodes;
	private final Set<FaceSelection> selectedFaces;
	private final Set<EdgeSelection> selectedEdges;
	private final Set<VertexSelection> selectedVertices;

	private final List<ISceneSelectionChangeListener> selectionListeners;

	public SceneSelection(final Scene scene) {
		this.scene = scene;

		selectedNodes = new LinkedHashSet<INode>();
		selectedParentNodes = new HashSet<IComposite>();
		selectedFaces = new HashSet<FaceSelection>();
		selectedEdges = new HashSet<EdgeSelection>();
		selectedVertices = new HashSet<VertexSelection>();
		selectionListeners = new ArrayList<ISceneSelectionChangeListener>();
	}

	@Override
	public Set<INode> getSelectedNodes() {
		return Collections.unmodifiableSet(selectedNodes);
	}

	@Override
	public Set<FaceSelection> getSelectedFaces() {
		return Collections.unmodifiableSet(selectedFaces);
	}

	@Override
	public Set<EdgeSelection> getSelectedEdges() {
		return Collections.unmodifiableSet(selectedEdges);
	}

	@Override
	public Set<VertexSelection> getSelectedVertices() {
		return Collections.unmodifiableSet(selectedVertices);
	}

	public void setSelectedNodes(final Set<INode> selectedNodes) {
		if (selectedNodes != null && !selectedNodes.equals(this.selectedNodes)) {
			for (final INode selectNode : this.selectedNodes) {
				selectNode.markDirty();
			}

			this.selectedNodes = new LinkedHashSet<INode>(selectedNodes);
			this.selectedParentNodes.clear();

			for (final INode selectNode : this.selectedNodes) {
				selectNode.markDirty();

				IComposite parent = selectNode.getParent();
				while (parent != null) {
					this.selectedParentNodes.add(parent);
					parent = parent.getParent();
				}
			}

			fireSceneSelectionChangeEvent(new SceneSelectionChangeEvent(scene,
					this.selectedNodes));
		}
	}

	public void setSelectedNode(final INode node) {
		if (selectedNodes.size() != 1 || !selectedNodes.contains(node)) {
			for (final INode selectNode : selectedNodes) {
				selectNode.markDirty();
			}
			selectedNodes.clear();
			selectedNodes.add(node);

			this.selectedParentNodes.clear();
			IComposite parent = node.getParent();
			while (parent != null) {
				this.selectedParentNodes.add(parent);
				parent = parent.getParent();
			}

			node.markDirty();

			fireSceneSelectionChangeEvent(new SceneSelectionChangeEvent(scene,
					selectedNodes));
		}
	}

	public void switchSelectedNode(final INode node) {
		if (selectedNodes.contains(node)) {
			selectedNodes.remove(node);
		} else {
			selectedNodes.add(node);
		}
		node.markDirty();

		this.selectedParentNodes.clear();
		for (final INode selectedNode : selectedNodes) {
			IComposite parent = selectedNode.getParent();
			while (parent != null) {
				this.selectedParentNodes.add(parent);
				parent = parent.getParent();
			}
		}

		fireSceneSelectionChangeEvent(new SceneSelectionChangeEvent(scene,
				selectedNodes));
	}

	public void setSelectedFace(final IMeshNode node, final int faceIndex) {
		if (!selectedNodes.contains(node)) {
			return;
		}
		final FaceSelection faceSelection = new FaceSelection(node, faceIndex);
		if (selectedFaces.size() != 1 || !selectedFaces.contains(faceSelection)) {
			for (final FaceSelection selection : selectedFaces) {
				selection.getNode().markDirty();
			}
			selectedFaces.clear();
			selectedFaces.add(faceSelection);
			faceSelection.getNode().markDirty();

			// TODO: Might require finer events
			fireSceneSelectionChangeEvent(new SceneSelectionChangeEvent(scene,
					selectedNodes));
		}
	}

	public void switchSelectedFace(final IMeshNode node, final int faceIndex) {
		if (!selectedNodes.contains(node)) {
			return;
		}

		final FaceSelection faceSelection = new FaceSelection(node, faceIndex);
		if (selectedFaces.contains(faceSelection)) {
			selectedFaces.remove(faceSelection);
		} else {
			selectedFaces.add(faceSelection);
		}
		node.markDirty();

		// TODO: Might require finer events
		fireSceneSelectionChangeEvent(new SceneSelectionChangeEvent(scene,
				selectedNodes));
	}

	public void setSelectedEdge(final IMeshNode node, final EdgeId edgeIndex) {
		if (!selectedNodes.contains(node)) {
			return;
		}

		final EdgeSelection edgeSelection = new EdgeSelection(node, edgeIndex);
		if (selectedEdges.size() != 1 || !selectedEdges.contains(edgeSelection)) {
			for (final EdgeSelection selection : selectedEdges) {
				selection.getNode().markDirty();
			}
			selectedEdges.clear();
			selectedEdges.add(edgeSelection);
			edgeSelection.getNode().markDirty();

			// TODO: Might require finer events
			fireSceneSelectionChangeEvent(new SceneSelectionChangeEvent(scene,
					selectedNodes));
		}
	}

	public void switchSelectedEdge(final IMeshNode node, final EdgeId edgeIndex) {
		if (!selectedNodes.contains(node)) {
			return;
		}

		final EdgeSelection edgeSelection = new EdgeSelection(node, edgeIndex);
		if (selectedEdges.contains(edgeSelection)) {
			selectedEdges.remove(edgeSelection);
		} else {
			selectedEdges.add(edgeSelection);
		}
		node.markDirty();

		// TODO: Might require finer events
		fireSceneSelectionChangeEvent(new SceneSelectionChangeEvent(scene,
				selectedNodes));
	}

	public void setSelectedVertex(final IMeshNode node, final int vertexIndex) {
		if (!selectedNodes.contains(node)) {
			return;
		}
		final VertexSelection vertexSelection = new VertexSelection(node,
				vertexIndex);
		if (selectedVertices.size() != 1
				|| !selectedVertices.contains(vertexSelection)) {
			for (final VertexSelection selection : selectedVertices) {
				selection.getNode().markDirty();
			}
			selectedVertices.clear();
			selectedVertices.add(vertexSelection);
			vertexSelection.getNode().markDirty();

			// TODO: Might require finer events
			fireSceneSelectionChangeEvent(new SceneSelectionChangeEvent(scene,
					selectedNodes));
		}
	}

	public void switchSelectedVertex(final IMeshNode node, final int vertexIndex) {
		if (!selectedNodes.contains(node)) {
			return;
		}

		final VertexSelection vertexSelection = new VertexSelection(node,
				vertexIndex);
		if (selectedVertices.contains(vertexSelection)) {
			selectedVertices.remove(vertexSelection);
		} else {
			selectedVertices.add(vertexSelection);
		}
		node.markDirty();

		// TODO: Might require finer events
		fireSceneSelectionChangeEvent(new SceneSelectionChangeEvent(scene,
				selectedNodes));
	}

	public boolean isNodeSelected(final INode node) {
		return selectedNodes.contains(node);
	}

	public boolean isChildNodeSelected(final IComposite node) {
		return selectedParentNodes.contains(node);
	}

	public boolean isFaceSelected(final IMeshNode node, final int faceIndex) {
		return selectedFaces.contains(new FaceSelection(node, faceIndex));
	}

	public boolean isEdgeSelected(final IMeshNode node, final EdgeId edgeIndex) {
		return selectedEdges.contains(new EdgeSelection(node, edgeIndex));
	}

	public boolean isVertexSelected(final IMeshNode node, final int vertexIndex) {
		return selectedVertices
				.contains(new VertexSelection(node, vertexIndex));
	}

	public void addSceneSelectionChangeListener(
			final ISceneSelectionChangeListener listener) {
		synchronized (selectionListeners) {
			selectionListeners.add(listener);
		}
	}

	public void removeSceneSelectionChangeListener(
			final ISceneSelectionChangeListener listener) {
		synchronized (selectionListeners) {
			selectionListeners.remove(listener);
		}
	}

	protected void fireSceneSelectionChangeEvent(
			final SceneSelectionChangeEvent event) {
		ISceneSelectionChangeListener listenerArray[];

		synchronized (selectionListeners) {
			listenerArray = selectionListeners
					.toArray(new ISceneSelectionChangeListener[selectionListeners
							.size()]);
		}

		for (final ISceneSelectionChangeListener listener : listenerArray) {
			listener.sceneSelectionChanged(event);
		}
	}

	public static class FaceSelection {
		private final IMeshNode node;
		private final int faceIndex;

		FaceSelection(final IMeshNode node, final int faceIndex) {
			this.node = node;
			this.faceIndex = faceIndex;
		}

		public IMeshNode getNode() {
			return node;
		}

		public int getFaceIndex() {
			return faceIndex;
		}

		@Override
		public int hashCode() {
			return 31 * node.hashCode() + faceIndex;
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final FaceSelection other = (FaceSelection) obj;
			return node.equals(other.node) && faceIndex == other.faceIndex;
		}

		@Override
		public String toString() {
			final StringBuilder builder = new StringBuilder();
			builder.append("FaceSelection [faceIndex=");
			builder.append(faceIndex);
			builder.append(", node=");
			builder.append(node);
			builder.append("]");
			return builder.toString();
		}
	}

	public static class EdgeSelection {
		private final IMeshNode node;
		private final EdgeId edgeIndex;

		EdgeSelection(final IMeshNode node, final EdgeId edgeIndex) {
			this.node = node;
			this.edgeIndex = edgeIndex;
		}

		public IMeshNode getNode() {
			return node;
		}

		public EdgeId getEdgeIndex() {
			return edgeIndex;
		}

		@Override
		public int hashCode() {
			return 31 * node.hashCode() + edgeIndex.hashCode();
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final EdgeSelection other = (EdgeSelection) obj;
			return node.equals(other.node) && edgeIndex.equals(other.edgeIndex);
		}

		@Override
		public String toString() {
			final StringBuilder builder = new StringBuilder();
			builder.append("EdgeSelection [edgeIndex=");
			builder.append(edgeIndex);
			builder.append(", node=");
			builder.append(node);
			builder.append("]");
			return builder.toString();
		}
	}

	public static class VertexSelection {
		private final IMeshNode node;
		private final int vertexIndex;

		public VertexSelection(final IMeshNode node, final int vertexIndex) {
			this.node = node;
			this.vertexIndex = vertexIndex;
		}

		public IMeshNode getNode() {
			return node;
		}

		public int getVertexIndex() {
			return vertexIndex;
		}

		@Override
		public int hashCode() {
			return 31 * node.hashCode() + vertexIndex;
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final VertexSelection other = (VertexSelection) obj;
			return node.equals(other.node) && vertexIndex == other.vertexIndex;
		}

		@Override
		public String toString() {
			final StringBuilder builder = new StringBuilder();
			builder.append("VertexSelection [node=");
			builder.append(node);
			builder.append(", vertexIndex=");
			builder.append(vertexIndex);
			builder.append("]");
			return builder.toString();
		}

	}
}
