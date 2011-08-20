package net.untoldwind.moredread.ui.provider;

import net.untoldwind.moredread.model.scene.IComposite;
import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.ISceneHolder;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class SceneContentProvider implements ITreeContentProvider {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] getElements(final Object inputElement) {
		if (inputElement instanceof ISceneHolder) {
			return new Object[] { ((ISceneHolder) inputElement).getScene() };
		}
		return getChildren(inputElement);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] getChildren(final Object parentElement) {
		if (parentElement instanceof IComposite) {
			return ((IComposite) parentElement).getChildren().toArray();
		}
		return new Object[0];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getParent(final Object element) {
		if (element instanceof INode) {
			return ((INode) element).getParent();
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasChildren(final Object element) {
		if (element instanceof IComposite) {
			return !((IComposite) element).getChildren().isEmpty();
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void inputChanged(final Viewer viewer, final Object oldInput,
			final Object newInput) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
	}
}
