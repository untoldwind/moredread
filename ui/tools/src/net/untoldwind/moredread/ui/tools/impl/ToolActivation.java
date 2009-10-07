package net.untoldwind.moredread.ui.tools.impl;

import net.untoldwind.moredread.model.enums.SelectionMode;
import net.untoldwind.moredread.model.scene.SceneSelection;

import org.eclipse.core.runtime.IConfigurationElement;

public class ToolActivation {
	private final SelectionMode selectionMode;
	private final SelectionCount selectionCount;

	public ToolActivation(final IConfigurationElement element) {
		selectionMode = SelectionMode.valueOf(element
				.getAttribute("selectionMode"));
		selectionCount = SelectionCount.forCode(element
				.getAttribute("selectionCount"));
	}

	public boolean matches(final SelectionMode selectionMode,
			final SceneSelection sceneSelection) {
		if (this.selectionMode != selectionMode) {
			return false;
		}

		if (selectionCount == SelectionCount.ANY) {
			return true;
		}

		int count;

		switch (selectionMode) {
		case OBJECT:
			count = sceneSelection.getSelectedNodes().size();
			break;
		case FACE:
			count = sceneSelection.getSelectedFaces().size();
			break;
		case EDGE:
			count = sceneSelection.getSelectedEdges().size();
			break;
		case VERTEX:
			count = sceneSelection.getSelectedVertices().size();
			break;
		default:
			throw new RuntimeException("Invalid selectionMode: "
					+ selectionMode);
		}

		switch (selectionCount) {
		case ONE:
			return count == 1;
		case ONE_OR_MORE:
			return count >= 1;
		case ZERO_OR_ONE:
			return count == 0 || count == 1;
		default:
			throw new RuntimeException("Invalid selectionCount: "
					+ selectionCount);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((selectionCount == null) ? 0 : selectionCount.hashCode());
		result = prime * result
				+ ((selectionMode == null) ? 0 : selectionMode.hashCode());
		return result;
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
		final ToolActivation other = (ToolActivation) obj;
		if (selectionCount == null) {
			if (other.selectionCount != null) {
				return false;
			}
		} else if (!selectionCount.equals(other.selectionCount)) {
			return false;
		}
		if (selectionMode == null) {
			if (other.selectionMode != null) {
				return false;
			}
		} else if (!selectionMode.equals(other.selectionMode)) {
			return false;
		}
		return true;
	}

}
