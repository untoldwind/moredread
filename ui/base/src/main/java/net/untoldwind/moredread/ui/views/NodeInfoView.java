package net.untoldwind.moredread.ui.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class NodeInfoView extends ViewPart {
	public static final String ID = "net.untoldwind.moredread.ui.nodeInfoView";

	Text nameText;
	Text hotpointXText;
	Text hotpointYText;
	Text hotpointZText;

	@Override
	public void createPartControl(final Composite parent) {
		parent.setLayout(new GridLayout(3, true));

		final Label nameLabel = new Label(parent, SWT.NONE);
		nameLabel.setText("Name");
		GridData layout = new GridData(GridData.FILL_HORIZONTAL);
		layout.horizontalSpan = 3;
		nameLabel.setLayoutData(layout);

		nameText = new Text(parent, SWT.BORDER | SWT.FLAT);
		layout = new GridData(GridData.FILL_HORIZONTAL);
		layout.horizontalSpan = 3;
		nameText.setLayoutData(layout);

		final Label hotPointLabel = new Label(parent, SWT.NONE);
		hotPointLabel.setText("Hotpoint");
		layout = new GridData(GridData.FILL_HORIZONTAL);
		layout.horizontalSpan = 3;
		hotPointLabel.setLayoutData(layout);

		final Label centerLabel = new Label(parent, SWT.NONE);
		centerLabel.setText("Center");
		layout = new GridData(GridData.FILL_HORIZONTAL);
		layout.horizontalSpan = 3;
		centerLabel.setLayoutData(layout);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
