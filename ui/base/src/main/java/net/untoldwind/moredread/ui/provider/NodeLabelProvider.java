package net.untoldwind.moredread.ui.provider;

import net.untoldwind.moredread.model.scene.INode;

import org.eclipse.jface.viewers.LabelProvider;

public class NodeLabelProvider extends LabelProvider {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getText(final Object element) {
		if (element instanceof INode) {
			return ((INode) element).getName();
		}
		return super.getText(element);
	}

}
