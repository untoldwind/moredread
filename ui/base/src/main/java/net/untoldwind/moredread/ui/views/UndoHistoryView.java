package net.untoldwind.moredread.ui.views;

import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IOperationHistoryListener;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.OperationHistoryEvent;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;

public class UndoHistoryView extends ViewPart {
	private TableViewer viewer;

	private final IOperationHistory history = OperationHistoryFactory
			.getOperationHistory();

	private final IUndoContext fContext = IOperationHistory.GLOBAL_UNDO_CONTEXT;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createPartControl(final Composite parent) {
		viewer = new TableViewer(parent, SWT.SINGLE | SWT.H_SCROLL
				| SWT.V_SCROLL);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(getViewSite());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	class ViewContentProvider implements IStructuredContentProvider,
			IOperationHistoryListener {

		public void inputChanged(final Viewer v, final Object oldInput,
				final Object newInput) {
			// we never change inputs, so we just use this as a place to add our
			// listener.
			history.addOperationHistoryListener(this);
		}

		public void dispose() {
			history.removeOperationHistoryListener(this);
		}

		public Object[] getElements(final Object input) {
			// show the items in the operations history.
			return history.getUndoHistory(fContext);
		}

		public void historyNotification(final OperationHistoryEvent event) {
			if (viewer.getTable().isDisposed()) {
				return;
			}
			final Display display = viewer.getTable().getDisplay();
			switch (event.getEventType()) {
			case OperationHistoryEvent.OPERATION_ADDED:
			case OperationHistoryEvent.OPERATION_REMOVED:
			case OperationHistoryEvent.UNDONE:
			case OperationHistoryEvent.REDONE:
				if (event.getOperation().hasContext(fContext)
						&& display != null) {
					display.syncExec(new Runnable() {
						public void run() {
							// refresh all labels in case any operation has
							// changed dynamically
							// without notifying the operation history.
							if (!viewer.getTable().isDisposed()) {
								viewer.refresh(true);
							}
						}
					});
				}
				break;
			}
		}
	}

	class ViewLabelProvider extends LabelProvider implements
			ITableLabelProvider {
		public String getColumnText(final Object obj, final int index) {
			return getText(obj);
		}

		public Image getColumnImage(final Object obj, final int index) {
			return getImage(obj);
		}

		@Override
		public String getText(final Object obj) {
			if (obj instanceof IUndoableOperation) {
				return ((IUndoableOperation) obj).getLabel();
			}
			return obj.toString();
		}
	}

}
