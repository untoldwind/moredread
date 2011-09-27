package net.untoldwind.moredread.ui.canvas;

import java.util.ArrayList;

import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.scene.ISceneHolder;
import net.untoldwind.moredread.model.scene.event.ISceneSelectionChangeListener;
import net.untoldwind.moredread.model.scene.event.SceneSelectionChangeEvent;

import org.eclipse.core.commands.common.EventManager;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;

public class SceneSelectionProvider extends EventManager implements
		ISelectionProvider, ISceneSelectionChangeListener {
	private final ISceneHolder sceneHolder;

	public SceneSelectionProvider(final ISceneHolder sceneHolder) {
		this.sceneHolder = sceneHolder;

		this.sceneHolder.addSceneSelectionChangeListener(this);
	}

	@Override
	public void addSelectionChangedListener(
			final ISelectionChangedListener listener) {
		addListenerObject(listener);
	}

	@Override
	public ISelection getSelection() {
		return new StructuredSelection(new ArrayList<INode>(sceneHolder
				.getScene().getSceneSelection().getSelectedNodes()));
	}

	@Override
	public void removeSelectionChangedListener(
			final ISelectionChangedListener listener) {
		removeListenerObject(listener);
	}

	@Override
	public void setSelection(final ISelection selection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sceneSelectionChanged(final SceneSelectionChangeEvent e) {
		// pass on the notification to listeners
		final Object[] listeners = getListeners();
		final SelectionChangedEvent event = new SelectionChangedEvent(this,
				getSelection());
		for (int i = 0; i < listeners.length; ++i) {
			final ISelectionChangedListener l = (ISelectionChangedListener) listeners[i];
			SafeRunner.run(new SafeRunnable() {
				public void run() {
					l.selectionChanged(event);
				}
			});
		}
	}

}
