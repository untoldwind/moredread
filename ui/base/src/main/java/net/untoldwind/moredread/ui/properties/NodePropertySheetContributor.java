package net.untoldwind.moredread.ui.properties;

import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;

public class NodePropertySheetContributor implements
		ITabbedPropertySheetPageContributor {

	public final static String CONTRIBUTOR_ID = "net.untoldwind.moredread.properties.node";

	@Override
	public String getContributorId() {
		return CONTRIBUTOR_ID;
	}

}
