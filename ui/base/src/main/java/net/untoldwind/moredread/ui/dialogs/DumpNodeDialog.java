package net.untoldwind.moredread.ui.dialogs;

import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.state.XMLStateWriter;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class DumpNodeDialog extends MessageDialog {

	String nodeDump;

	public DumpNodeDialog(final Shell parent, final INode node) {
		super(parent, "Node Dump", null, node.getName(), INFORMATION,
				new String[] { IDialogConstants.OK_LABEL }, 0);

		setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE
				| getDefaultOrientation());

		nodeDump = XMLStateWriter.toXML(node);
	}

	@Override
	protected Control createCustomArea(final Composite parent) {
		final Text text = new Text(parent, SWT.BORDER | SWT.V_SCROLL
				| SWT.H_SCROLL | SWT.MULTI);

		final GC gc = new GC(text);
		final FontMetrics fm = gc.getFontMetrics();
		final int width = 40 * fm.getAverageCharWidth();
		final int height = 20 * fm.getHeight();
		gc.dispose();

		final GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.widthHint = width;
		gridData.heightHint = height;
		text.setLayoutData(gridData);
		text.setText(nodeDump);

		return text;
	}

}
